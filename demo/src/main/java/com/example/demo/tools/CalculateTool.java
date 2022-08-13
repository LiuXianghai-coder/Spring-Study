package com.example.demo.tools;

import com.example.demo.common.IfExtract;

/**
 * @author lxh
 * @date 2022/8/9-下午10:51
 */
@IfExtract(name = "CalTool")
public class CalculateTool {
    public long multiply(int x, int y) {
        return (long) x * y;
    }

    private long add(int x, int y) {
        return x + y;
    }
}
