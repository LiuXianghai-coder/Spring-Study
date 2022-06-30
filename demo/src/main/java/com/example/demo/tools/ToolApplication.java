package com.example.demo.tools;

/**
 * @author xhliu
 * @create 2022-06-23-19:47
 **/
public class ToolApplication {
    public static void main(String[] args) {
        int[] o1 = new int[]{1, 2, 3, 4};
        int[] o2 = new int[]{4, 5, 6, 7};

        DiffTool tool = DiffTool.DiffToolBuilder.aDiffTool()
                .withDeep(true)
                .build();

        tool.compObject(o1, o2);
    }
}
