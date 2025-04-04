package org.xhliu.springredission.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *@author lxh
 */
public class OrderPayTask
        implements Delayed {

    private final String taskName;

    private final Long orderId;

    private final long startTime = System.currentTimeMillis();

    private final long delayTimes;

    public OrderPayTask(String taskName, Long orderId, long endTime) {
        this.taskName = taskName;
        this.orderId = orderId;
        this.delayTimes = endTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert((startTime + delayTimes) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

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
                ", startTime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(startTime))+
                ", delayTimes=" + delayTimes +
                '}';
    }
}
