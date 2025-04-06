package org.xhliu.springredission.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *@author lxh
 */
public class OrderPayTask
        implements Delayed {

    /*
        该任务的一些简要描述
     */
    private final String taskName;

    /*
        当前任务处理的订单信息 id
     */
    private final Long orderId;

    /*
        该任务创建的时间，主要是为了后续排查
     */
    private final long startTime = System.currentTimeMillis();

    /*
        该任务需要延迟多长时间后再执行，这里的单位可以自己定义，但是需要与后续 getDelay
        方法的实现保持一致
     */
    private final long delayTimes;

    public OrderPayTask(String taskName, Long orderId, long endTime) {
        this.taskName = taskName;
        this.orderId = orderId;
        this.delayTimes = endTime;
    }

    /*
        简单地讲，这个方法如果返回的是一个 <= 0 的数，则说明该任务已经到期了，否则，说明该任务未到期
        因此在重写该方法的实现时需要注意单位的匹配
     */
    @Override
    public long getDelay(TimeUnit unit) {
        /*
            这里我们假定输入的 delayTimes 表示的是毫秒数，因此如果任务创建时间加上延迟时间小于当前时间的话，
            说明该任务已经到期了
         */
        return unit.convert((startTime + delayTimes) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /*
        这个方法是 Comparable 接口要求的实现，因为 DelayQueue 的底层是通过 PriorityQueue 存储的元素，只能支持 Comparable 类型的元素
        不过一般情况下我们都可以假设快到期的任务优先级较高（getDelay() 较小），这一般是合理的
        如果有特殊要求的话也可以重写该方法的比对逻辑，提高任务的处理优先级（如 vip 任务）
     */
    @Override
    public int compareTo(Delayed o) {
        if (!(o instanceof OrderPayTask)) {
            throw new IllegalArgumentException("比较的类型必须为: " + OrderPayTask.class.getName());
        }
        OrderPayTask that = (OrderPayTask) o;
        return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), that.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return "OrderPayTask{" +
                "taskName='" + taskName + '\'' +
                ", orderId=" + orderId +
                ", startTime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(startTime)) +
                ", delayTimes=" + delayTimes +
                '}';
    }
}
