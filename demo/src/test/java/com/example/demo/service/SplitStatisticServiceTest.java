package com.example.demo.service;

import com.example.demo.TestApplication;
import com.example.demo.dto.SplitStatisticDto;
import com.example.demo.rpo.SplitStatisticRpo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {TestApplication.class})
public class SplitStatisticServiceTest {

    private final static Logger log = LoggerFactory.getLogger(SplitStatisticService.class);

    @Resource
    SplitStatisticService splitStatisticService;

    @Resource
    ObjectMapper mapper;

    @Test
    public void multiplePageSearchTest() throws JsonProcessingException {
        for (int i = 0, pageNo = 1, pageSize = 10; i < 100; i++, pageNo++) {
            SplitStatisticRpo rpo = new SplitStatisticRpo();
            rpo.setPageNo(pageNo);
            rpo.setPageSize(pageSize);

            Page<SplitStatisticDto> unionAllPage = splitStatisticService.unionAllPage(rpo);
            Page<SplitStatisticDto> multiplePage = splitStatisticService.multiplePageSearch(rpo);
            Assertions.assertEquals(unionAllPage.size(), multiplePage.size());
            for (int j = 0; j < unionAllPage.size(); j++) {
                SplitStatisticDto rawRow = unionAllPage.get(j);
                SplitStatisticDto newRow = multiplePage.get(j);
                if (!rawRow.equals(newRow)) {
                    log.error("{}", mapper.writeValueAsString(unionAllPage));
                    log.error("{}", mapper.writeValueAsString(multiplePage));
                }
                Assertions.assertEquals(rawRow.getId(), newRow.getId());
            }
            log.info("page [{}] pageSize [{}] identify", pageNo, pageSize);
        }
    }


    @Test
    public void pageSearchTest() throws JsonProcessingException {
        SplitStatisticRpo rpo = new SplitStatisticRpo();
        rpo.setPageNo(99);
        rpo.setPageSize(10);

        Page<SplitStatisticDto> multiplePage = splitStatisticService.multiplePageSearch(rpo);
        log.info("{}", mapper.writeValueAsString(multiplePage));
    }
}