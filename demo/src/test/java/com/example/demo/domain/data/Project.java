package com.example.demo.domain.data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author xhliu
 * @create 2022-04-27-16:21
 **/
class Project {
    private String id;

    private String name;

    private String industryType;

    private Date createdDate;

    private short delFlag;

    private BigDecimal investScale;

    private Integer stock;

    static Project EXAMPLE_ONE = new Builder()
            .withCreatedDate(new Date())
            .withIndustryType("computer")
            .withDelFlag((short) 0)
            .withName("Project 1")
            .withInvestScale(BigDecimal.ONE)
            .withId("eeffa1as")
            .withStock(129)
            .build();

    static Project EXAMPLE_TWO = new Builder()
            .withCreatedDate(new Date())
            .withIndustryType("soccer")
            .withDelFlag((short) 1)
            .withName("Project 2")
            .withInvestScale(BigDecimal.TEN)
            .withId("jokan1al")
            .withStock(300)
            .build();

    static Project EXAMPLE_THREE = new Builder()
            .withCreatedDate(new Date())
            .withIndustryType("math")
            .withDelFlag((short) 1)
            .withName("Project 3")
            .withInvestScale(BigDecimal.valueOf(300L))
            .withId("jklai1l")
            .withStock(300)
            .build();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public short getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(short delFlag) {
        this.delFlag = delFlag;
    }

    public BigDecimal getInvestScale() {
        return investScale;
    }

    public void setInvestScale(BigDecimal investScale) {
        this.investScale = investScale;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public static final class Builder {
        private String id;
        private String name;
        private String industryType;
        private Date createdDate;
        private short delFlag;
        private BigDecimal investScale;
        private Integer stock;

        private Builder() {
        }

        public static Builder aProject() {
            return new Builder();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withIndustryType(String industryType) {
            this.industryType = industryType;
            return this;
        }

        public Builder withCreatedDate(Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder withDelFlag(short delFlag) {
            this.delFlag = delFlag;
            return this;
        }

        public Builder withInvestScale(BigDecimal investScale) {
            this.investScale = investScale;
            return this;
        }

        public Builder withStock(Integer stock) {
            this.stock = stock;
            return this;
        }

        public Project build() {
            Project project = new Project();
            project.setId(id);
            project.setName(name);
            project.setIndustryType(industryType);
            project.setCreatedDate(createdDate);
            project.setDelFlag(delFlag);
            project.setInvestScale(investScale);
            project.setStock(stock);
            return project;
        }
    }
}
