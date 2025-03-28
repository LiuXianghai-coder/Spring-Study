package org.xhliu.sharding.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.xhliu.sharding.ShardingApplicationTestApplication;
import org.xhliu.sharding.dto.SplitStatisticDto;
import org.xhliu.sharding.rpo.SplitStatisticRpo;

import javax.annotation.Resource;

/**
 *@author lxh
 */
@SpringBootTest(classes = {ShardingApplicationTestApplication.class})
public class SplitStatisticServiceTest {

    private final static Logger log = LoggerFactory.getLogger(SplitStatisticService.class);

    @Resource
    SplitStatisticService splitStatisticService;

    @Test
    public void multiplePageSearchTest() {
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
                Assertions.assertEquals(rawRow.getId(), newRow.getId());
            }
            log.info("page [{}] pageSize [{}] identify", pageNo, pageSize);
        }
    }
}
