package com.example.demo.entity;

import com.example.demo.repository.MeetingReactiveRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Meeting Info
 *
 * @author xhliu2
 * @create 2021-08-10 9:08
 **/
@Slf4j
@Component
public class MeetingInfo {
    private final MeetingReactiveRepo meetingReactiveRepo;

    private final AtomicInteger count = new AtomicInteger();

    @Autowired
    public MeetingInfo(MeetingReactiveRepo meetingReactiveRepo) {
        this.meetingReactiveRepo = meetingReactiveRepo;
    }

    private Function<Integer, Meeting> getMeeting() {
        return (val) -> {
            Meeting meeting = new Meeting();
            meeting.setMeetingId(val);
            meeting.setMeetingNumber("CN" + String.format("%07d", val));

            return meeting;
        };
    }

//    public Mono<ServerResponse> logging(ServerRequest request) {
//        meetingReactiveRepo.save(
//                getMeeting().apply(count.incrementAndGet())
//        );
//
//        log.info("meetingId: " + count.get());
//        return ServerResponse.ok().bodyValue("OK");
//    }
}
