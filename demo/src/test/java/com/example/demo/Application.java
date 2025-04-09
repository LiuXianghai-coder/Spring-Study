package com.example.demo;

import cn.hutool.core.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author xhliu2
 **/
public class Application {


    public static void main(String[] args) throws Throwable {
        Application app = new Application();
        String s = "package org.xhliu.mapper;\n" +
                "\n" +
                "import org.apache.ibatis.annotations.Mapper;\n" +
                "import org.xhliu.entity.UserInfo;\n" +
                "\n" +
                "@Mapper\n" +
                "public interface UserInfoMapper extends tk.mybatis.mapper.common.Mapper<UserInfo> {\n" +
                "}\n";

        for (int i = 0; i < 500; i++) {
            FileUtil.writeString(s.replaceAll("UserInfoMapper", "UserInfoMapper" + (i + 1)),
                    new File("c:/tmp/UserInfoMapper" + (i + 1) + ".java"),
                    StandardCharsets.UTF_8
            );
        }
    }
}
