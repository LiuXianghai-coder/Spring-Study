package com.example.demo.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author lxh
 */
@RegisterMapper
public interface ExtendsMapper<T>
        extends Mapper<T> {

    @InsertProvider(type = ExtendsInsertProvider.class, method = "dynamicSQL")
    int saveAll(List<? extends T> data);

    @InsertProvider(type = ExtendsInsertProvider.class, method = "dynamicSQL")
    int mysqlUpdateAll(List<? extends T> data);
}
