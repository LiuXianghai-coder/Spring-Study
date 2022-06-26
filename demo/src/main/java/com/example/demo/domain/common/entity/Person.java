package com.example.demo.domain.common.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lxh
 * @date 2022/6/19-下午8:39
 */
@Data
public class Person {
    private String name;

    private LocalDateTime createdTime;
}
