package com.example.demo.controller;

import com.example.demo.service.SimpleExcelStreamingResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 *@author lxh
 */
@RestController
@RequestMapping(path = "/streamController")
public class StreamController {

    @Resource
    private ApplicationContext context;

    @PostMapping(path = "/exportBigExcel")
    public ResponseEntity<StreamingResponseBody> exportBigExcel(HttpServletResponse response) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=big.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new SimpleExcelStreamingResponse(context));
    }
}
