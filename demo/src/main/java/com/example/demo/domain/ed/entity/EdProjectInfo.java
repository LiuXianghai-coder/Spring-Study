package com.example.demo.domain.ed.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class EdProjectInfo extends ProjectInfo {
    private String fundFor;

    private String fundType;

    private LocalDateTime payDate;

    private int[] arr;

    private Object[] objs;

    private final int val;

    private int[][] dirs;

    public EdProjectInfo(int val) {
        this.val = val;
    }

    public final static EdProjectInfo EXAMPLE = new EdProjectInfo(1);

    public int getVal() {
        return val;
    }

    public int[][] getDirs() {
        return dirs;
    }

    public void setDirs(int[][] dirs) {
        this.dirs = dirs;
    }

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }

    public Object[] getObjs() {
        return objs;
    }

    public void setObjs(Object[] objs) {
        this.objs = objs;
    }

    static {
        EXAMPLE.setProjId("DN00001");
        EXAMPLE.setName("xhliu");
        EXAMPLE.setUpdateTime(LocalDateTime.now());
        EXAMPLE.setFundFor("保险");
        EXAMPLE.setFundType("Invest");
        EXAMPLE.setPayDate(LocalDateTime.of(2021, 5, 26, 19, 0));
        EXAMPLE.setArr(new int[]{1, 2, 3});
        EXAMPLE.setObjs(new Object[]{
                LocalDate.of(2011, 10, 23),
                LocalDate.of(2013, 12, 14),
                LocalDate.of(2014, 2, 18)
        });
        EXAMPLE.setDirs(new int[][]{{0, 1},{0, -1}, {1, 0}, {-1, 0}});
    }

    public String getFundFor() {
        return fundFor;
    }

    public void setFundFor(String fundFor) {
        this.fundFor = fundFor;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDateTime payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "EdProjectInfo{" +
                "fundFor='" + fundFor + '\'' +
                ", fundType='" + fundType + '\'' +
                ", payDate=" + payDate +
                ", arr=" + Arrays.toString(arr) +
                ", objs=" + Arrays.toString(objs) +
                ", val=" + val +
                ", dirs=" + Arrays.deepToString(dirs) +
                '}';
    }
}
