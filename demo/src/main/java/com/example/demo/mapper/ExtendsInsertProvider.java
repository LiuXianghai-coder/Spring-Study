package com.example.demo.mapper;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SelectKeyHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lxh
 */
public class ExtendsInsertProvider
        extends BaseInsertProvider {
    public ExtendsInsertProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    protected final static String VAR_REGEX = "#\\{(.+)}";

    public String saveAll(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        EntityColumn logicDeleteColumn = SqlHelper.getLogicDeleteColumn(entityClass);
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        sql.append("<trim prefix=\"VALUES \" suffixOverrides=\",\">");
        sql.append("<foreach collection=\"collection\" item=\"item\" separator=\",\" >");
        processKey(sql, entityClass, ms, columnList);
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (EntityColumn column : columnList) {
            if (!column.isInsertable()) {
                continue;
            }
            if (logicDeleteColumn != null && logicDeleteColumn == column) {
                sql.append(SqlHelper.getLogicDeletedValue(column, false)).append(",");
                continue;
            }
            String tmp;
            //优先使用传入的属性值,当原属性property!=null时，用原属性
            //自增的情况下,如果默认有值,就会备份到property_cache中,所以这里需要先判断备份的值是否存在
            //其他情况值仍然存在原property中
            if (column.isIdentity()) {
                tmp = SqlHelper.getIfCacheNotNull(column,
                        column.getColumnHolder(null, "_cache", ","));
                sql.append(tmp);
            } else {
                //其他情况值仍然存在原property中
                tmp = SqlHelper.getIfNotNull(column,
                        column.getColumnHolder(null, null, ","), isNotEmpty());
                sql.append(replaceByRegex(tmp, VAR_REGEX, "item.", true));
            }
            // tmp = SqlHelper.getIfNotNull(column,
            //        column.getColumnHolder(null, null, ","), isNotEmpty());

            //当属性为null时，如果存在主键策略，会自动获取值，如果不存在，则使用null
            //当null的时候，如果不指定jdbcType，oracle可能会报异常，指定VARCHAR不影响其他
            if (column.isIdentity()) {
                tmp = SqlHelper.getIfCacheIsNull(column, column.getColumnHolder() + ",");
                sql.append(replaceByRegex(tmp, "#\\{(.+})", "item.", true));
            } else {
                //当null的时候，如果不指定jdbcType，oracle可能会报异常，指定VARCHAR不影响其他
                tmp = SqlHelper.getIfIsNull(column,
                        column.getColumnHolder(null, null, ","), isNotEmpty());
                sql.append(replaceByRegex(tmp, VAR_REGEX, "item.", true));
            }
        }
        sql.append("</trim>");
        sql.append("</foreach>");
        sql.append("</trim>");
        return sql.toString();
    }

    public String mysqlUpdateAll(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder(saveAll(ms));
        sql.append(" AS new ON DUPLICATE KEY UPDATE ");

        sql.append("<trim suffixOverrides=\",\">");
        //获取全部列
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnSet) {
            if (!column.isInsertable()) {
                continue;
            }
            if (column.isId()) {
                continue;
            }
            sql.append(column.getColumn()).append("=new.")
                    .append(column.getColumn())
                    .append(",");
        }
        sql.append("</trim>");

        return sql.toString();
    }

    protected void processKey(StringBuilder sql, Class<?> entityClass,
                              MappedStatement ms, Set<EntityColumn> columnList) {
        //Identity列只能有一个
        boolean hasIdentityKey = false;
        //先处理cache或bind节点
        for (EntityColumn column : columnList) {
            if (column.isIdentity()) {
                //这种情况下,如果原先的字段有值,需要先缓存起来,否则就一定会使用自动增长
                //这是一个bind节点
                String tmp = SqlHelper.getBindCache(column);
                sql.append(replaceByRegex(tmp, "value=\"(.+\")", "item.", true));
                //如果是Identity列，就需要插入selectKey
                //如果已经存在Identity列，抛出异常
                if (hasIdentityKey) {
                    //jdbc类型只需要添加一次
                    if (column.getGenerator() != null && "JDBC".equals(column.getGenerator())) {
                        continue;
                    }
                    throw new MapperException(ms.getId() + "对应的实体类"
                            + entityClass.getName() + "中包含多个MySql的自动增长列,最多只能有一个!");
                }
                //插入selectKey
                SelectKeyHelper.newSelectKeyMappedStatement(ms, column, entityClass, isBEFORE(), getIDENTITY(column));
                hasIdentityKey = true;
            } else if (column.getGenIdClass() != null) {
                sql.append("<bind name=\"").append(column.getColumn())
                        .append("GenIdBind\" value=\"@tk.mybatis.mapper.genid.GenIdUtil@genId(");
                sql.append("item").append(", '").append(column.getProperty()).append("'");
                sql.append(", @").append(column.getGenIdClass().getName()).append("@class");
                sql.append(", '").append(tableName(entityClass)).append("'");
                sql.append(", '").append(column.getColumn()).append("')");
                sql.append("\"/>");
            }
        }
    }

    public static String replaceByRegex(String rawStr, String regex,
                                        String content, boolean et) {
        if (rawStr == null || rawStr.isEmpty()) {
            return rawStr;
        }
        if (!et) {
            return rawStr.replaceAll(regex, content);
        }

        Pattern pat = Pattern.compile(regex);
        Matcher matcher = pat.matcher(rawStr);
        while (matcher.find()) {
            if (matcher.groupCount() >= 1) {
                String group = matcher.group(1);
                rawStr = rawStr.replace(group, content + group);
            }
        }
        return rawStr;
    }
}
