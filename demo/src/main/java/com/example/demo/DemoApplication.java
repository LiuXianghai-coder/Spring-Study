package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {


    static Object invokeMethod(String methodName, Object target, Object... args) {
        Method[] methods = target.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                try {
                    method.setAccessible(true);
                    Object res = method.invoke(target, args);
                    method.setAccessible(false);
                    return res;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    static int cal() {
        int a = 10, b = 5;
        try {
            a += 10;
            throw new RuntimeException();
        } catch (Exception e) {
            a += 3;
            return a + b;
        } finally {
            a += 5;
//            return a + b;
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File("D:/data/tmp/tmp.txt");
        try (
                FileChannel fc = FileChannel.open(file.toPath(), StandardOpenOption.READ);
                AsynchronousFileChannel afc = AsynchronousFileChannel.open(file.toPath(), StandardOpenOption.READ);
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            Charset charset = Charset.defaultCharset();
            while (fc.read(buffer) > 0) {
                buffer.flip();
                System.out.println(charset.decode(buffer));
                buffer.rewind();
            }

//            afc.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
//                @Override
//                public void completed(Integer result, ByteBuffer attachment) {
//                    attachment.rewind();
//                    System.out.println(charset.decode(attachment));
//                    attachment.flip();
//                }
//
//                @Override
//                public void failed(Throwable exc, ByteBuffer attachment) {
//                    exc.printStackTrace();
//                }
//            });
        }
        System.out.println("Hello World!!!!");
    }
}
