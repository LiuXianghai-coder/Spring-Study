package org.xhliu.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.xhliu.mybatis.vo.Message;

import java.util.Map;

/**
 * @author xhliu
 * @time 2022-03-02-下午9:12
 */
public interface MessageMapper {
    /*
        此时传入的 id 就是一个查询参数, @Param("id") 中的 id 表示会对应上 Mapper XML 文件中的参数 id
        @Param("id") 中的 id 表示会对应上 Mapper XML 文件中的参数 id
     */
    Message getMessageById(@Param("id") Long id);

    /*
        使用 Object 作为 V 可能不是一个很好的想法
     */
    Message getMessageByMap(Map<String, Object> map);

    /*
         使用 Message 实体对象存储查询参数
     */
    Message getMessageByEntity(Message message);

    /*
        插入一条 Message 数据
     */
    int insert(Message message);

    /*
        更新对应的一条 Message 记录
     */
    int update(Message message);

    /*
        删除一条 Message 记录（如果它存在的话）
     */
    int delete(Message message);

    /*
        级联测试接口
     */
    Message getMessageAndMessageDetail(@Param("id") long id);
}
