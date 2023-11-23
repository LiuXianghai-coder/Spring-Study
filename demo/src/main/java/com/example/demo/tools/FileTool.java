package com.example.demo.tools;

import org.apache.ibatis.io.Resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author lxh
 */
public abstract class FileTool {

    public static String loadParamJson() {
        try (BufferedReader reader = new BufferedReader(Resources.getResourceAsReader("param.json"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadResultJson() {
        try (BufferedReader reader = new BufferedReader(Resources.getResourceAsReader("result.json"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
