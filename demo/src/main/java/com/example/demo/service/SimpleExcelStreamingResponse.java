package com.example.demo.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.example.demo.entity.BigColsSchema;
import com.example.demo.mapper.BigColsSchemaMapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *@author lxh
 */
public class SimpleExcelStreamingResponse
        implements StreamingResponseBody {

    private final static Logger logger = LoggerFactory.getLogger(SimpleExcelStreamingResponse.class);

    private final ApplicationContext context;

    public SimpleExcelStreamingResponse(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        BigColsSchemaMapper schemaMapper = context.getBean(BigColsSchemaMapper.class);

        BigColsSchemaIterator iterator = new BigColsSchemaIterator(schemaMapper);
        ExcelWriter writer = EasyExcel.write(out, BigColsSchema.class)
                .registerConverter(new OffsetTimeConvert())
                .excelType(ExcelTypeEnum.XLSX).inMemory(Boolean.FALSE)
                .autoCloseStream(Boolean.FALSE)
                .build();
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(null, 1, true, false);
        writer.writeContext().writeWorkbookHolder().setCachedWorkbook(sxssfWorkbook);
        writer.writeContext().writeWorkbookHolder().setWorkbook(sxssfWorkbook);

        WriteSheet writeSheet = EasyExcel.writerSheet("Big_Cols_Schema")
                .build();
        List<BigColsSchema> buffer = new ArrayList<>();
        while (iterator.hasNext()) {
            buffer.add(iterator.next());
            if (buffer.size() >= 1000) {
                logger.info("Write buffer to excel.....");
                writer.write(buffer, writeSheet);
                buffer.clear();
            }
        }
        if (!CollUtil.isEmpty(buffer)) {
            writer.write(buffer, writeSheet);
            buffer.clear();
        }
        writer.finish();
    }

    private static class OffsetTimeConvert implements Converter<OffsetDateTime> {

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
}
