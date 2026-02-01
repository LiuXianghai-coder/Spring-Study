package com.example.integration.processor;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 *@author lxh
 */
public interface StreamProcessor {

    String INPUT = "input";
    String OUTPUT = "output";

    @Input(INPUT)
    MessageChannel input();

    @Output(OUTPUT)
    MessageChannel output();
}


