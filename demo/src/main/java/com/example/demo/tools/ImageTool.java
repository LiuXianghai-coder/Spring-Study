package com.example.demo.tools;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author xhliu
 */
public class ImageTool {
    private static final String path = "/tmp/images";

    public static void resizeImage(int n) throws IOException {
        createPath(path);
        File file = new File(path);
        File[] files = file.listFiles();
        for (File f : files) {
            BufferedImage image = ImageIO.read(f);
            BufferedImage resize = Scalr.resize(image, n);
            String name = "00" + f.getName();
            ImageIO.write(resize, "jpg", createFile("D:\\tmp\\result" + "\\" + name));
        }
    }

    private static void createPath(String path) {
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
    }

    private static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static void main(String[] args) throws IOException {
        resizeImage(1024);
    }
}
