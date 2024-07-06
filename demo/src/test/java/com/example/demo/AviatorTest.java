package com.example.demo;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AviatorTest {

    @Test
    public void executeTest() throws IOException {
        Expression exp = AviatorEvaluator.getInstance().compileScript("method_invoke.av");
        exp.execute();
    }
}
