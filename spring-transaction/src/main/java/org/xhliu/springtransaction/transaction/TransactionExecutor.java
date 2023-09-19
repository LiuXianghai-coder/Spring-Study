package org.xhliu.springtransaction.transaction;

import org.springframework.transaction.TransactionDefinition;

/**
 *@author lxh
 */
public interface TransactionExecutor {

    void addTask(Runnable task);

    void addTask(Runnable task, TransactionDefinition def);

    void execute() throws Throwable;

    void asyncExecute() throws Throwable;
}
