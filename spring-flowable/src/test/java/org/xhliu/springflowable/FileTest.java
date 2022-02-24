package org.xhliu.springflowable;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author xhliu
 * @time 2022-02-23-下午8:38
 */
public class FileTest {
    @Test
    public void fileTest() throws IOException {
        Path path = Paths.get("/home/lxh/Git/Github/Test-Repo");
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult
            visitFile(
                    Path file,
                    BasicFileAttributes attrs
            ) throws IOException {
                System.out.println(file.getFileName());
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
