package com.example.demo.service.impl;

import com.example.demo.service.RateCalculateService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author xhliu
 * @create 2022-07-13-8:41
 **/
@Async
@Service(value = "loanRateCalculate")
public class LoanRateCalculate implements RateCalculateService {
    @Override
    public void calculate(String id) {

    }
}
