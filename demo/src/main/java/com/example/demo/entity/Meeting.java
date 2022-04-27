package com.example.demo.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * Meeting
 *
 * @author xhliu2
 * @create 2021-08-09 17:04
 **/
@Slf4j
@Data
@Entity
@Table(name = "meeting")
public class Meeting {
    @Id
    @Column(name = "meeting_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer meetingId;

    @Basic
    @Column(name = "meeting_number")
    private String meetingNumber;
}
