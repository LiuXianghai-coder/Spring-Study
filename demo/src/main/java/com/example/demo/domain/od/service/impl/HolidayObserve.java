package com.example.demo.domain.od.service.impl;

import com.example.demo.domain.od.service.Observe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author xhliu
 * @create 2022-07-21-9:22
 **/
@Service(value = "holidayObserve")
public class HolidayObserve implements Observe {

    private final static Logger log = LoggerFactory.getLogger(HolidayObserve.class);

    @Override
    public void notify(Object obj) {
        log.info("notify content " + obj.toString());
    }
}
