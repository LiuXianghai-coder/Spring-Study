package com.example.demo.service;

import cn.hutool.core.collection.CollUtil;
import com.example.demo.entity.BigColsSchema;
import com.example.demo.mapper.BigColsSchemaMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 *@author lxh
 */
public class BigColsSchemaIterator
        extends AbstractList<BigColsSchema>
        implements Iterator<BigColsSchema> {

    private final static Logger logger = LoggerFactory.getLogger(BigColsSchemaIterator.class);

    private final static int PAGE_SIZE = 3000;

    private final BigColsSchemaMapper schemaMapper;

    private int pageNum = 1;

    private int index = 0;
    private final List<BigColsSchema> buffer = new ArrayList<>();

    public BigColsSchemaIterator(BigColsSchemaMapper schemaMapper) {
        this.schemaMapper = schemaMapper;
    }

    @Override
    public boolean hasNext() {
        if (index >= buffer.size()) {
            List<BigColsSchema> schemaList = queryNextPage();
            if (CollUtil.isEmpty(schemaList)) {
                return false;
            }
            this.buffer.clear();;
            this.buffer.addAll(schemaList);
            this.index = 0;
            this.pageNum += 1;
        }
        return true;
    }

    @Override
    public BigColsSchema next() {
        if (!hasNext()) {
            return null;
        }
        return buffer.get(index++);
    }

    private List<BigColsSchema> queryNextPage() {
        BigColsSchema schema = new BigColsSchema();
        logger.info("============= query page, pageNum: {}, pageSize: {} ==============", pageNum, PAGE_SIZE);
        try (Page<BigColsSchema> page = PageHelper.startPage(pageNum, PAGE_SIZE)) {
            schemaMapper.select(schema);

            if (CollUtil.isEmpty(page)) {
                return new ArrayList<>();
            }
            return page;
        }
    }

    @Override
    public BigColsSchema get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }
}
