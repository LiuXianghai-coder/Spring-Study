package com.example.demo.entity;

/**
 * @author lxh
 */
public class InsuredEntity {
    private String policy;
    private String expiry;
    private String location;
    private String region;
    private String insuredValue;
    private String construction;
    private String businessType;
    private String earthquake;
    private String flood;

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getInsuredValue() {
        return insuredValue;
    }

    public void setInsuredValue(String insuredValue) {
        this.insuredValue = insuredValue;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getEarthquake() {
        return earthquake;
    }

    public void setEarthquake(String earthquake) {
        this.earthquake = earthquake;
    }

    public String getFlood() {
        return flood;
    }

    public void setFlood(String flood) {
        this.flood = flood;
    }

    @Override
    public String toString() {
        return "InsuredEntity{" +
                "policy='" + policy + '\'' +
                ", expiry='" + expiry + '\'' +
                ", location='" + location + '\'' +
                ", region='" + region + '\'' +
                ", insuredValue='" + insuredValue + '\'' +
                ", construction='" + construction + '\'' +
                ", businessType='" + businessType + '\'' +
                ", earthquake='" + earthquake + '\'' +
                ", flood='" + flood + '\'' +
                '}';
    }
}
