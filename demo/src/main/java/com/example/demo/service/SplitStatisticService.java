package com.example.demo.service;

import com.example.demo.dto.SplitStatisticDto;
import com.example.demo.mapper.OaStatisticMapper;
import com.example.demo.rpo.SplitStatisticRpo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *@author lxh
 */
@Service
@Transactional
public class SplitStatisticService {

    private final static Logger log = LoggerFactory.getLogger(SplitStatisticService.class);

    @Resource
    private OaStatisticMapper oaStatisticMapper;

    @Resource
    private ObjectMapper mapper;

    public Page<SplitStatisticDto> unionAllPage(SplitStatisticRpo splitStatisticRpo) {
        Assert.notNull(splitStatisticRpo, "请求参数不能为 null");
        Integer pageNo = splitStatisticRpo.getPageNo();
        Integer pageSize = splitStatisticRpo.getPageSize();
        try (Page<SplitStatisticDto> page = PageHelper.startPage(pageNo, pageSize)) {
            oaStatisticMapper.unionAllPageSearch(splitStatisticRpo);
            return page;
        }
    }

    public Page<SplitStatisticDto> multiplePageSearch(SplitStatisticRpo splitStatisticRpo) {
        Assert.notNull(splitStatisticRpo, "请求参数不能为 null");
        Integer pageNo = splitStatisticRpo.getPageNo();
        Integer pageSize = splitStatisticRpo.getPageSize();

        // 计算每个子表的偏移量
        int allOffset = (pageNo - 1) * pageSize;
        List<String> tableNames = currSplitTableNames();
        int offset = allOffset / tableNames.size();
        int limit = pageSize + (allOffset - tableNames.size() * offset);
        int maxLimit = pageSize * tableNames.size();

        // 首次查询每个子表偏移量分页数据
        Map<String, List<SplitStatisticDto>> firstSubDataMap = Maps.newTreeMap();
        for (String tableName : tableNames) {
            SplitStatisticRpo rpo = SplitStatisticRpo.valueOf(splitStatisticRpo);
            rpo.setOffset(offset);
            rpo.setTableName(tableName);
            rpo.setPageSize(limit);
            firstSubDataMap.put(tableName, oaStatisticMapper.firstPageSearch(rpo));
        }

        int subCnt = 0;
        for (Map.Entry<String, List<SplitStatisticDto>> entry : firstSubDataMap.entrySet()) {
            if (CollectionUtils.isEmpty(entry.getValue())) {
                SplitStatisticRpo rpo = SplitStatisticRpo.valueOf(splitStatisticRpo);
                rpo.setTableName(entry.getKey());
                long cnt = oaStatisticMapper.countFirstPageSearch(rpo);
                if (offset > cnt) {
                    subCnt += (int) ((long) offset - cnt);
                }
            }
        }


        // 遍历每个子数据集合，得到最小的记录; 以及每个子数据集的最大记录
        SplitStatisticDto min = null;
        Map<String, SplitStatisticDto> maxRecordMap = Maps.newTreeMap();
        for (Map.Entry<String, List<SplitStatisticDto>> entry : firstSubDataMap.entrySet()) {
            List<SplitStatisticDto> dtoList = entry.getValue();
            if (CollectionUtils.isEmpty(dtoList)) {
                SplitStatisticDto max = new SplitStatisticDto();
                max.setCreatedTime(LocalDateTime.MAX);
                maxRecordMap.put(entry.getKey(), max);
                continue;
            }

            SplitStatisticDto firstRow = dtoList.get(0);
            if (min == null) {
                min = firstRow;
            } else if (firstRow.getCreatedTime().isBefore(min.getCreatedTime())) {
                min = firstRow;
            }

            maxRecordMap.put(entry.getKey(), dtoList.get(dtoList.size() - 1));
        }
        if (Objects.isNull(min)) {
            return new Page<>();
        }

        // 第二次查询，得到实际需要的结果集合
        Map<String, List<SplitStatisticDto>> secondSubDataMap = Maps.newTreeMap();
        for (String tableName : tableNames) {
            SplitStatisticRpo rpo = SplitStatisticRpo.valueOf(splitStatisticRpo);
            SplitStatisticDto maxRecord = maxRecordMap.get(tableName);
            rpo.setTableName(tableName);
            rpo.setCondition("t.created_time BETWEEN '" + formatDateTime(min.getCreatedTime()) + "'"
                    + " AND '" + formatDateTime(maxRecord.getCreatedTime()) + "'");
            secondSubDataMap.put(tableName, oaStatisticMapper.secondPageSearch(rpo));
        }

        // 计算在整个数据集合中所需的偏移量
        int allDataOffset = allOffset - (offset * tableNames.size()) + subCnt;
        for (String tableName : tableNames) {
            List<SplitStatisticDto> firstRows = firstSubDataMap.get(tableName);
            List<SplitStatisticDto> secondRows = secondSubDataMap.get(tableName);
            if (CollectionUtils.isEmpty(firstRows)) {
                allDataOffset += secondRows.size();
                continue;
            }

            int f = 0;
            SplitStatisticDto firstRow = firstRows.get(0);
            for (SplitStatisticDto secondRow : secondRows) {
                if (firstRow.equals(secondRow)) {
                    break;
                }
                f++;
            }
            allDataOffset += f;
        }

        if (allDataOffset >= pageSize) {
            for (String tableName : tableNames) {
                List<SplitStatisticDto> firstQuery = firstSubDataMap.get(tableName);
                if (CollectionUtils.isEmpty(firstQuery)) continue;
                if (min != firstQuery.get(0)) continue;

                SplitStatisticRpo rpo = SplitStatisticRpo.valueOf(splitStatisticRpo);
                rpo.setTableName(tableName);
                rpo.setPageSize(maxLimit);
                rpo.setOffset(offset);
                secondSubDataMap.put(tableName, oaStatisticMapper.firstPageSearch(rpo));
            }
        }

        // 整合所有数据，作为返回结果
        List<SplitStatisticDto> data = Lists.newArrayList();
        for (Map.Entry<String, List<SplitStatisticDto>> entry : secondSubDataMap.entrySet()) {
            data.addAll(entry.getValue());
        }
        data.sort(Comparator.comparing(SplitStatisticDto::getCreatedTime));
        Page<SplitStatisticDto> res = new Page<>();
        res.addAll(data.subList(allDataOffset, Math.min(data.size(), allDataOffset + pageSize)));
        return res;
    }

    protected String formatDateTime(LocalDateTime dateTime) {
        return String.format("%4s-%2s-%2s$%2s:%2s:%2s",
                        dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                        dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond()
                ).replaceAll(" ", "0")
                .replaceAll("\\$", " ");
    }

    protected List<String> currSplitTableNames() {
        return Lists.newArrayList("oa_statistic_2025_a",
                "oa_statistic_2025_b", "oa_statistic_2025_c");
    }
}
