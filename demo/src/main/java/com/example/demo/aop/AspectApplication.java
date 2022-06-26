package com.example.demo.aop;

import com.example.demo.entity.Book;
import com.example.demo.tools.DiffTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lxh
 * @date 2022/6/25-上午7:33
 */
public class AspectApplication {
    private final static Logger log = LoggerFactory.getLogger(AspectApplication.class);

    public static void main(String[] args) {
        Book book1 = new Book.Builder(123456, "apue").build();
        Book book2 = new Book.Builder(356849, "simple").build();

        DiffTool tool = DiffTool.DiffToolBuilder.aDiffTool()
                .withUseCache(true)
                .build();
        log.info("================ 分割线 ==================");
        tool.compObject(book1, book2);
        log.info("================ 结束 ==================");
    }
}
