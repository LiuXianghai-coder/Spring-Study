package org.xhliu.springtransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.Callable;

/**
 * 用于 {@link DataSourceTransactionManager} 事务管理对应的任务线程，这个类的存在是为了使得
 * Spring 中的事务管理能够在多线程的环境下依旧能够有效地工作.
 * 对于要要执行的任务，可以将其封装成此任务对象，该任务对象在执行时将会绑定与 {@link  DataSourceTransactionManager#getResourceFactory()}
 * 对应的 {@link TransactionSynchronizationManager#getResourceMap()} 中关联的事务对象，以使得要执行的任务包含在已有的事务中
 * （至少能保证存在一种可行的方式能够得到父线程的所处事务上下文），从而使得当前待执行的任务能够被现有统领事务进行管理
 *
 * @see DataSourceTransactionManager
 * @see TransactionSynchronizationManager
 *@author lxh
 */
public class DataSourceTransactionTask
        implements Callable<TransactionStatus> {

    private final static Logger log = LoggerFactory.getLogger(DataSourceTransactionTask.class);

    /*
        与 TransactionSynchronizationManager.resources 关联的事务属性对象的 Value 值，
        在当前上下文中，为了保存与原有事务的完整性，这里的 resource 存储的是 DataSourceTransactionObject
     */
    private final Object resource;

    // 当前 Spring 平台的事务管理对象
    private final DataSourceTransactionManager txManager;

    // 实际需要运行的任务
    private final Runnable runnable;

    // 与事务有关的描述信息
    private final TransactionDefinition definition;

    public DataSourceTransactionTask(Object resource,
                                     DataSourceTransactionManager txManager,
                                     Runnable runnable,
                                     TransactionDefinition definition) {
        this.resource = resource;
        this.txManager = txManager;
        this.runnable = runnable;
        this.definition = definition;
    }

    @Override
    public TransactionStatus call() {
        Object key = txManager.getResourceFactory();
        TransactionSynchronizationManager.bindResource(key, resource);
        TransactionStatus status = txManager.getTransaction(definition);
        try {
            runnable.run();
        } catch (Throwable t) {
            log.debug("任务执行出现异常", t);
            status.setRollbackOnly();
        } finally {
            // 移除与当前线程执行的关联关系，避免任务执行过程中的资源混乱
            TransactionSynchronizationManager.unbindResource(key);
        }
        return status;
    }

    public Runnable actualRunnable() {
        return this.runnable;
    }

    public TransactionDefinition getDefinition() {
        return definition;
    }

    public static final class Builder {
        private Object resource;
        private DataSourceTransactionManager txManager;
        private Runnable runnable;
        private TransactionDefinition definition = new DefaultTransactionDefinition();

        private Builder() {
        }

        public static Builder aTask() {
            return new Builder();
        }

        public Builder resource(Object resource) {
            this.resource = resource;
            return this;
        }

        public Builder txManager(DataSourceTransactionManager txManager) {
            this.txManager = txManager;
            return this;
        }

        public Builder runnable(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        public Builder definition(TransactionDefinition definition) {
            this.definition = definition;
            return this;
        }

        public DataSourceTransactionTask build() {
            return new DataSourceTransactionTask(resource, txManager, runnable, definition);
        }
    }
}
