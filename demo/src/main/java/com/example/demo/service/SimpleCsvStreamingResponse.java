package com.example.demo.service;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.demo.entity.BigColsSchema;
import com.example.demo.mapper.BigColsSchemaMapper;
import com.example.demo.service.convert.OffsetTimeConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *@author lxh
 */
public class SimpleCsvStreamingResponse
        implements StreamingResponseBody {

    private final static Logger logger = LoggerFactory.getLogger(SimpleCsvStreamingResponse.class);

    private final ApplicationContext context;

    public SimpleCsvStreamingResponse(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        BigColsSchemaMapper schemaMapper = context.getBean(BigColsSchemaMapper.class);

        BigColsSchemaIterator iterator = new BigColsSchemaIterator(schemaMapper);
        ExcelWriter writer = EasyExcel.write(out, BigColsSchema.class)
                .registerConverter(new OffsetTimeConvert())
                .excelType(ExcelTypeEnum.CSV).inMemory(Boolean.FALSE)
                .autoCloseStream(Boolean.FALSE)
                .build();

        WriteSheet sheet1 = EasyExcel.writerSheet(0, "Sheet_1")
                .build();
        WriteSheet sheet2 = EasyExcel.writerSheet(1, "Sheet_2")
                .build();
        List<BigColsSchema> buffer = new ArrayList<>();
        while (iterator.hasNext()) {
            buffer.add(iterator.next());
            if (buffer.size() >= 1000) {
                logger.info("Write buffer to csv.....");
                writer.write(buffer, sheet1);
                writer.write(buffer, sheet2);
                buffer.clear();
            }
        }
        if (!CollUtil.isEmpty(buffer)) {
            writer.write(buffer, sheet1);
            writer.write(buffer, sheet2);
            buffer.clear();
        }
        writer.finish();
    }
}
