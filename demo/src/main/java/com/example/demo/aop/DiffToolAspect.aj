package com.example.demo.aop;

import com.example.demo.tools.DiffTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public aspect DiffToolAspect {
    private final static Logger log = LoggerFactory.getLogger(DiffToolAspect.class);

    pointcut callExecute(DiffTool t): target(t) && call(* *(..));

    long start, end;

    before(DiffTool t) : callExecute(t) {
        start = System.currentTimeMillis();
        log.info("开始执行。。。。");
    }

    after(DiffTool t) : callExecute(t) {
        end = System.currentTimeMillis();
        log.info("执行结束。。。 耗时 {} ms", (end - start));
    }
}
