package org.xhliu.springtransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.xhliu.springtransaction.tools.ReflectTool;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 用于需要执行并行任务的事务管理线程池，由于现有的 Spring 声明式事务在不同的线程中不能很好地工作，
 * 而在某些情况下，可能需要考虑使用多个处理器的优势来提高方法的执行性能，因此定义了此类来提供一种类似
 * 线程池的方式来执行可以并行执行的任务，并对将这些任务整合到 Spring 的事务管理中 <br />
 * 具体的使用方式:
 * <ol>
 *     <li>
 *         如果你希望自己配置线程池的相关属性，可以手动创建一个 {@link ThreadPoolExecutor} 来作为构造参数通过
 *         {@link DataSourceTransactionExecutor#DataSourceTransactionExecutor(ThreadPoolExecutor,
 *         DataSourceTransactionManager, TransactionDefinition)} 进行构建，在构造时会复制这个 {@link ThreadPoolExecutor}
 *         的相关属性来重新创建一个 {@link ThreadPoolExecutor} 进行任务的处理，因此不会影响到现有线程池的工作. 同时，值得注意的是，
 *         对于传入的 {@link ThreadPoolExecutor} 参数中的 {@link ThreadPoolExecutor#getQueue()} 工作队列类型，必须保证提供一个
 *         无参的构造函数来使得工作队列对象能够被重新创建，否则将会抛出异常 <br />
 *         此外，除了任务执行者的参数外，至少还需要指定 {@link DataSourceTransactionManager} 用于任务执行时线程事务的统一管理，使得每个任务
 *         执行时能够被 Spring 事务进行管理，由于 Spring 提供了对于事务的不同处理方式，因此也可以自定义传入 {@link TransactionDefinition}
 *         来定义这些行为
 *     </li>
 *     <li>
 *         对于需要执行的任务，只要将其作为 {@link Runnable} 参数通过 {@link #addTask(Runnable)} 的形式加入到当前的任务列表中，
 *         在这个过程中实际的任务不会被执行.
 *         当确定已经将任务加入完成后，通过调用 {@link #execute()} 方法来执行这些任务，这些任务的执行会被构造时传入的
 *         {@link DataSourceTransactionManager} 进行统一的事务管理，同时，任务执行完毕之后，当前的任务执行者将会被关闭，
 *         并不能再继续添加任务
 *     </li>
 *     以下面的例子为例，我们可以执行如下的几个业务处理:
 *     <pre>
 *         DataSourceTransactionExecutor executor = new DataSourceTransactionExecutor(txManager);
 *         executor.addTask(this::service1);
 *         executor.addTask(this::service2);
 *         executor.addTask(this::service3);
 *         executor.execute();
 *     </pre>
 * </ol>
 *
 * @see DataSourceTransactionTask
 * @see DataSourceTransactionManager
 *@author lxh
 */
public class DataSourceTransactionExecutor {

    private final static Logger log = LoggerFactory.getLogger(DataSourceTransactionExecutor.class);

    private final List<Callable<TransactionStatus>> callableList = new ArrayList<>();

    private final DataSourceTransactionManager txManager;

    private final TransactionStatus txStatus;

    private final Object txResource;

    private final ThreadPoolExecutor executor;

    private final TransactionDefinition definition;

    public DataSourceTransactionExecutor(int coreSize,
                                         int maxSize,
                                         int keepTime,
                                         TimeUnit timeUnit,
                                         BlockingQueue<Runnable> workQueue,
                                         ThreadFactory threadFactory,
                                         RejectedExecutionHandler rejectHandler,
                                         DataSourceTransactionManager txManager,
                                         TransactionDefinition definition) {
        this.txManager = txManager;
        this.txStatus = txManager.getTransaction(definition);
        this.definition = definition;
        this.txResource = TransactionSynchronizationManager.getResource(txManager.getResourceFactory());
        executor = new ThreadPoolExecutor(coreSize, maxSize, keepTime,
                timeUnit, workQueue, threadFactory, rejectHandler);
    }

    public DataSourceTransactionExecutor(DataSourceTransactionManager txManager,
                                         TransactionDefinition definition) {
        this(Runtime.getRuntime().availableProcessors() * 2,
                Runtime.getRuntime().availableProcessors() * 2,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                Thread::new,
                new ThreadPoolExecutor.AbortPolicy(),
                txManager,
                definition
        );
    }

    @SuppressWarnings("unchecked")
    public DataSourceTransactionExecutor(ThreadPoolExecutor executor,
                                         DataSourceTransactionManager txManager,
                                         TransactionDefinition definition) {
        // 复制一个线程池对象，避免一些线程问题
        this.executor = new ThreadPoolExecutor(
                executor.getCorePoolSize(),
                executor.getMaximumPoolSize(),
                executor.getKeepAliveTime(TimeUnit.SECONDS),
                TimeUnit.SECONDS,
                ReflectTool.createInstance(executor.getQueue().getClass()),
                executor.getThreadFactory(),
                ReflectTool.createInstance(executor.getRejectedExecutionHandler().getClass())
        );
        this.txManager = txManager;
        this.txStatus = txManager.getTransaction(definition);
        this.definition = definition;
        this.txResource = TransactionSynchronizationManager.getResource(txManager.getResourceFactory());
    }

    public DataSourceTransactionExecutor(DataSourceTransactionManager txManager) {
        this(txManager, new DefaultTransactionDefinition());
    }

    public void addTask(Runnable task) {
        callableList.add(DataSourceTransactionTask.Builder.aTask()
                .runnable(task).txManager(txManager)
                .resource(txResource).definition(new DefaultTransactionDefinition())
                .build()
        );
    }

    public void addTask(Runnable task, TransactionDefinition def) {
        callableList.add(DataSourceTransactionTask.Builder.aTask()
                .runnable(task).txManager(txManager)
                .resource(txResource).definition(def)
                .build()
        );
    }

    public void execute() throws InterruptedException {
        List<Future<TransactionStatus>> futures = new ArrayList<>();
        for (Callable<TransactionStatus> callable : callableList) {
            futures.add(executor.submit(callable));
        }
        executor.shutdown();
        List<TransactionStatus> statusList = new ArrayList<>();
        for (Future<TransactionStatus> future : futures) {
            try {
                statusList.add(future.get());
            } catch (ExecutionException e) {
                log.error("任务执行出现异常", e);
                statusList.add(null);
            }
        }
        Object[] statusArgs = new Object[statusList.size()];
        statusList.toArray(statusArgs);
        mergeTaskResult(statusArgs); // 合并每个任务的事务信息
    }

    /**
     * 以异步的方式执行这些任务
     */
    public void asyncExecute() {
        List<Mono<TransactionStatus>> monoList = new ArrayList<>();

        Scheduler scheduler = Schedulers.fromExecutor(this.executor);
        for (Callable<TransactionStatus> callable : callableList) {
            monoList.add(Mono.fromCallable(callable)
                    .subscribeOn(scheduler));
        }
        Flux.zip(monoList, Tuples::fromArray)
                .single()
                .flatMap(tuple2 -> Mono.fromRunnable(() -> {
                    TransactionSynchronizationManager.bindResource(txManager.getResourceFactory(), txResource);
                    mergeTaskResult(tuple2.toArray());
                }))
                .subscribeOn(scheduler)
                .doOnSubscribe(any -> log.info("开始执行事务的合并操作"))
                .doFinally(any -> {
                    log.debug("合并事务处理执行完成");
                    scheduler.dispose();
                    executor.shutdown();
                })
                .subscribe();
    }

    private void mergeTaskResult(Object... statusList) {
        boolean exFlag = false;
        for (Object obj : statusList) {
            if (obj == null) {
                exFlag = true;
                continue;
            }
            // 在当前上下文中一定是 TransactionStatus 类型的对象
            TransactionStatus status = (TransactionStatus) obj;
            if (status.isRollbackOnly()) exFlag = true;
        }
        if (exFlag) {
            log.debug("由于任务执行时出现异常，因此会将整个业务进操作进行回滚");
            txManager.rollback(txStatus);
        } else {
            txManager.commit(txStatus);
        }
    }
}
