package com.example.demo.type;

import java.math.BigDecimal;

public class StringToDecimalConvert implements TypeHandler<String, BigDecimal> {
    @Override
    public BigDecimal convert(String s) {
        return new BigDecimal(s);
    }
}
