package com.example.demo.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class OaPenetrateData {
    private String id;
    private String vcReportDate;
    private String vcSecurityCode;
    private String vcAssetCode;
    private String vcSecurityType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVcReportDate() {
        return vcReportDate;
    }

    public void setVcReportDate(String vcReportDate) {
        this.vcReportDate = vcReportDate;
    }

    public String getVcSecurityCode() {
        return vcSecurityCode;
    }

    public void setVcSecurityCode(String vcSecurityCode) {
        this.vcSecurityCode = vcSecurityCode;
    }

    public String getVcAssetCode() {
        return vcAssetCode;
    }

    public void setVcAssetCode(String vcAssetCode) {
        this.vcAssetCode = vcAssetCode;
    }

    public String getVcSecurityType() {
        return vcSecurityType;
    }

    public void setVcSecurityType(String vcSecurityType) {
        this.vcSecurityType = vcSecurityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OaPenetrateData that = (OaPenetrateData) o;
        return Objects.equals(id, that.id)
                && Objects.equals(vcReportDate, that.vcReportDate)
                && Objects.equals(vcSecurityCode, that.vcSecurityCode)
                && Objects.equals(vcAssetCode, that.vcAssetCode)
                && Objects.equals(vcSecurityType, that.vcSecurityType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vcReportDate, vcSecurityCode,
                vcAssetCode, vcSecurityType);
    }

    @Override
    public String toString() {
        return "OaPenetrateData{" +
                "id='" + id + '\'' +
                ", vcReportDate='" + vcReportDate + '\'' +
                ", vcSecurityCode='" + vcSecurityCode + '\'' +
                ", vcAssetCode='" + vcAssetCode + '\'' +
                ", vcSecurityType='" + vcSecurityType + '\'' +
                '}';
    }

    private static long timestamp(LocalDateTime time) {
        return time.toEpochSecond(ZoneOffset.UTC);
    }

    private static String toReportDate(long time) {
        LocalDateTime t = LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.UTC);
        return t.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String randomReportDate() {
        return toReportDate(ThreadLocalRandom.current()
                .nextLong(946684800, 4102444800L));
    }

    public static OaPenetrateData randomPenetrateData() {
        OaPenetrateData data = new OaPenetrateData();
        data.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int assetCode = random.nextInt(0, 10000);
        String assetCodeStr = "ID" + String.format("%6s", assetCode).replaceAll(" ", "0");
        data.setVcAssetCode(assetCodeStr);
        data.setVcSecurityCode(assetCodeStr + "YY");
        data.setVcReportDate(toReportDate(random.nextLong(946684800, 4102444800L)));
        data.setVcSecurityType(String.valueOf(random.nextInt(1, 30)));
        return data;
    }
}
