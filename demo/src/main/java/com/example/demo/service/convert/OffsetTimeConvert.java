package com.example.demo.service.convert;

import cn.hutool.core.date.DateTime;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.OffsetDateTime;

/**
 *@author lxh
 */
public class OffsetTimeConvert implements Converter<OffsetDateTime> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return OffsetDateTime.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(OffsetDateTime value,
                                               ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration
    ) throws Exception {
        return new WriteCellData<>(new DateTime(value));
    }
}
