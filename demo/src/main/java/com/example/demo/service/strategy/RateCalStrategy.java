package com.example.demo.service.strategy;

import com.example.demo.service.RateCalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.stereotype.Service;

/**
 * @author xhliu
 * @create 2022-07-13-8:43
 **/
@Service
public class RateCalStrategy {

    private final RateCalculateService loanRateCalculate;
    private final RateCalculateService lprRateCalculate;

    @Autowired
    public RateCalStrategy(
            @Qualifier("loanRateCalculate") RateCalculateService loanRateCalculate,
            @Qualifier("lprRateCalculate") RateCalculateService lprRateCalculate
    ) {
        this.loanRateCalculate = loanRateCalculate;
        this.lprRateCalculate = lprRateCalculate;
    }

    public void handler() {
        loanRateCalculate.calculate("1");
        lprRateCalculate.calculate("2");
    }
}
