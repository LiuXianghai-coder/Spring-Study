package com.example.demo;

/*
 * ProtocolTest.java
 *
 * Author: Dr. Ho
 *
 * YXSmart - Java test for SE
 *
 */
public class ProtocolTest {

    private final static byte STL = 0x02;
    private final static byte ETX = 0x03;
    private final static byte DLE = 0x10;

    private int start, end, lrc;

    public ProtocolTest() {
    }

    public void refresh(byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("校验的数据流不能为 null");
        }
        resetIndex(); // 重置当前的索引位置
        int curStart = start, cueEnd, curLrc = 0;
        int n = data.length;
        boolean flag = false; // 当前位置的数据是否已经被转义
        for (int i = 0; i < n; ++i) {
            if (flag) {
                curLrc ^= data[i];
                flag = false;
                continue;
            }
            if (DLE == data[i]) {
                flag = true;
                continue;
            }

            if (STL == data[i]) {
                curStart = i;
                curLrc = 0;
            } else if (ETX == data[i]) {
                cueEnd = i;
                curLrc ^= data[i];

                // 当前窗口已经是一个有效的数据体
                if (i < n - 1 && curStart >= 0 && curLrc == data[i + 1]) {
                    start = curStart;
                    end = cueEnd;
                    i += 1;

                    // 重置当前窗口位置
                    curStart = -1;
                    curLrc = 0;
                }
            } else {
                curLrc ^= data[i];
            }
        }
    }

    private void resetIndex() {
        start = end = -1;
    }

    public boolean isValid() {
        return start >= 0;
    }

    public int getSTX() {
        return start;
    }

    public int getETX() {
        return end;
    }
}