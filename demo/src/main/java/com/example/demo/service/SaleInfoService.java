package com.example.demo.service;


import com.example.demo.entity.SaleInfo;
import com.example.demo.mapper.SaleInfoMapper;
import com.example.demo.transaction.DataSourceHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SaleInfoService {

    private final static Logger log = LoggerFactory.getLogger(SaleInfoService.class);

    @Resource
    private SaleInfoMapper saleInfoMapper;

    public void updateSaleInfo() {
        Example example = Example.builder(SaleInfo.class)
                .andWhere(Sqls.custom().andBetween("id", 50001L, 50009L))
                .build();
        DataSourceHolder.setCurDataSource("mysql");
        List<SaleInfo> data = saleInfoMapper.selectByExample(example);
        saleInfoMapper.mysqlUpdateAll(data);

        DataSourceHolder.setCurDataSource("postgresql");
        data = saleInfoMapper.selectByExample(example);
        saleInfoMapper.psqlUpdateAll(data);
    }
}
