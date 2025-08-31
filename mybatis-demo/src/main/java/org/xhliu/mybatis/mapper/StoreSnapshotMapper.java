package org.xhliu.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.xhliu.mybatis.vo.StoreSnapshot;

import java.util.List;

@Mapper
public interface StoreSnapshotMapper {

    /**
     * 批量插入StoreSnapshot数据
     * @param list 待插入的StoreSnapshot集合
     * @return 影响的行数
     */
    int batchInsert(List<StoreSnapshot> list);
}
