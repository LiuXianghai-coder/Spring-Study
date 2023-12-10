package com.example.demo.entity;

import com.example.demo.tools.IdTool;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lxh
 */
@Entity
@Table(name = "big_cols_schema")
public class BigColsSchema
        extends AbstractEntity {

    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "scheme_name")
    private String schemeName;
    @Basic
    @Column(name = "schema_address")
    private String schemaAddress;
    @Basic
    @Column(name = "schema_status")
    private String schemaStatus;
    @Basic
    @Column(name = "schema_type")
    private String schemaType;
    @Basic
    @Column(name = "schema_rate")
    private BigDecimal schemaRate;
    @Basic
    @Column(name = "schema_rate_work_date")
    private OffsetDateTime schemaRateWorkDate;
    @Basic
    @Column(name = "schema_rate_source")
    private String schemaRateSource;
    @Basic
    @Column(name = "schema_author_type")
    private String schemaAuthorType;
    @Basic
    @Column(name = "schema_author_date")
    private OffsetDateTime schemaAuthorDate;
    @Basic
    @Column(name = "schema_author_seq")
    private String schemaAuthorSeq;
    @Basic
    @Column(name = "schema_author_name")
    private String schemaAuthorName;
    @Basic
    @Column(name = "schema_repay_type")
    private String schemaRepayType;
    @Basic
    @Column(name = "schema_repay_sum")
    private BigDecimal schemaRepaySum;
    @Basic
    @Column(name = "schema_repay_date")
    private OffsetDateTime schemaRepayDate;
    @Basic
    @Column(name = "schema_expense_type")
    private String schemaExpenseType;
    @Basic
    @Column(name = "schema_expense_val")
    private BigDecimal schemaExpenseVal;
    @Basic
    @Column(name = "schema_expense_name")
    private String schemaExpenseName;
    @Basic
    @Column(name = "schema_expense_lump_type")
    private String schemaExpenseLumpType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSchemaAddress() {
        return schemaAddress;
    }

    public void setSchemaAddress(String schemaAddress) {
        this.schemaAddress = schemaAddress;
    }

    public String getSchemaStatus() {
        return schemaStatus;
    }

    public void setSchemaStatus(String schemaStatus) {
        this.schemaStatus = schemaStatus;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public void setSchemaType(String schemaType) {
        this.schemaType = schemaType;
    }

    public BigDecimal getSchemaRate() {
        return schemaRate;
    }

    public void setSchemaRate(BigDecimal schemaRate) {
        this.schemaRate = schemaRate;
    }

    public OffsetDateTime getSchemaRateWorkDate() {
        return schemaRateWorkDate;
    }

    public void setSchemaRateWorkDate(OffsetDateTime schemaRateWorkDate) {
        this.schemaRateWorkDate = schemaRateWorkDate;
    }

    public String getSchemaRateSource() {
        return schemaRateSource;
    }

    public void setSchemaRateSource(String schemaRateSource) {
        this.schemaRateSource = schemaRateSource;
    }

    public String getSchemaAuthorType() {
        return schemaAuthorType;
    }

    public void setSchemaAuthorType(String schemaAuthorTye) {
        this.schemaAuthorType = schemaAuthorTye;
    }

    public OffsetDateTime getSchemaAuthorDate() {
        return schemaAuthorDate;
    }

    public void setSchemaAuthorDate(OffsetDateTime schemaAuthorDate) {
        this.schemaAuthorDate = schemaAuthorDate;
    }

    public String getSchemaAuthorSeq() {
        return schemaAuthorSeq;
    }

    public void setSchemaAuthorSeq(String schemaAuthorSeq) {
        this.schemaAuthorSeq = schemaAuthorSeq;
    }

    public String getSchemaAuthorName() {
        return schemaAuthorName;
    }

    public void setSchemaAuthorName(String schemaAuthorName) {
        this.schemaAuthorName = schemaAuthorName;
    }

    public String getSchemaRepayType() {
        return schemaRepayType;
    }

    public void setSchemaRepayType(String schemaRepayType) {
        this.schemaRepayType = schemaRepayType;
    }

    public BigDecimal getSchemaRepaySum() {
        return schemaRepaySum;
    }

    public void setSchemaRepaySum(BigDecimal schemaRepaySum) {
        this.schemaRepaySum = schemaRepaySum;
    }

    public OffsetDateTime getSchemaRepayDate() {
        return schemaRepayDate;
    }

    public void setSchemaRepayDate(OffsetDateTime schemaRepayDate) {
        this.schemaRepayDate = schemaRepayDate;
    }

    public String getSchemaExpenseType() {
        return schemaExpenseType;
    }

    public void setSchemaExpenseType(String schemaExpenseType) {
        this.schemaExpenseType = schemaExpenseType;
    }

    public BigDecimal getSchemaExpenseVal() {
        return schemaExpenseVal;
    }

    public void setSchemaExpenseVal(BigDecimal schemaExpenseVal) {
        this.schemaExpenseVal = schemaExpenseVal;
    }

    public String getSchemaExpenseName() {
        return schemaExpenseName;
    }

    public void setSchemaExpenseName(String schemaExpenseName) {
        this.schemaExpenseName = schemaExpenseName;
    }

    public String getSchemaExpenseLumpType() {
        return schemaExpenseLumpType;
    }

    public void setSchemaExpenseLumpType(String schemaExpenseLumpTyp) {
        this.schemaExpenseLumpType = schemaExpenseLumpTyp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BigColsSchema that = (BigColsSchema) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(schemeName, that.schemeName)) return false;
        if (!Objects.equals(schemaAddress, that.schemaAddress))
            return false;
        if (!Objects.equals(schemaStatus, that.schemaStatus)) return false;
        if (!Objects.equals(schemaType, that.schemaType)) return false;
        if (!Objects.equals(schemaRate, that.schemaRate)) return false;
        if (!Objects.equals(schemaRateWorkDate, that.schemaRateWorkDate))
            return false;
        if (!Objects.equals(schemaRateSource, that.schemaRateSource))
            return false;
        if (!Objects.equals(schemaAuthorType, that.schemaAuthorType))
            return false;
        if (!Objects.equals(schemaAuthorDate, that.schemaAuthorDate))
            return false;
        if (!Objects.equals(schemaAuthorSeq, that.schemaAuthorSeq))
            return false;
        if (!Objects.equals(schemaAuthorName, that.schemaAuthorName))
            return false;
        if (!Objects.equals(schemaRepayType, that.schemaRepayType))
            return false;
        if (!Objects.equals(schemaRepaySum, that.schemaRepaySum))
            return false;
        if (!Objects.equals(schemaRepayDate, that.schemaRepayDate))
            return false;
        if (!Objects.equals(schemaExpenseType, that.schemaExpenseType))
            return false;
        if (!Objects.equals(schemaExpenseVal, that.schemaExpenseVal))
            return false;
        if (!Objects.equals(schemaExpenseName, that.schemaExpenseName))
            return false;
        return Objects.equals(schemaExpenseLumpType, that.schemaExpenseLumpType);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (schemeName != null ? schemeName.hashCode() : 0);
        result = 31 * result + (schemaAddress != null ? schemaAddress.hashCode() : 0);
        result = 31 * result + (schemaStatus != null ? schemaStatus.hashCode() : 0);
        result = 31 * result + (schemaType != null ? schemaType.hashCode() : 0);
        result = 31 * result + (schemaRate != null ? schemaRate.hashCode() : 0);
        result = 31 * result + (schemaRateWorkDate != null ? schemaRateWorkDate.hashCode() : 0);
        result = 31 * result + (schemaRateSource != null ? schemaRateSource.hashCode() : 0);
        result = 31 * result + (schemaAuthorType != null ? schemaAuthorType.hashCode() : 0);
        result = 31 * result + (schemaAuthorDate != null ? schemaAuthorDate.hashCode() : 0);
        result = 31 * result + (schemaAuthorSeq != null ? schemaAuthorSeq.hashCode() : 0);
        result = 31 * result + (schemaAuthorName != null ? schemaAuthorName.hashCode() : 0);
        result = 31 * result + (schemaRepayType != null ? schemaRepayType.hashCode() : 0);
        result = 31 * result + (schemaRepaySum != null ? schemaRepaySum.hashCode() : 0);
        result = 31 * result + (schemaRepayDate != null ? schemaRepayDate.hashCode() : 0);
        result = 31 * result + (schemaExpenseType != null ? schemaExpenseType.hashCode() : 0);
        result = 31 * result + (schemaExpenseVal != null ? schemaExpenseVal.hashCode() : 0);
        result = 31 * result + (schemaExpenseName != null ? schemaExpenseName.hashCode() : 0);
        result = 31 * result + (schemaExpenseLumpType != null ? schemaExpenseLumpType.hashCode() : 0);
        return result;
    }

    public static BigColsSchema sample() {
        BigColsSchema schema = new BigColsSchema();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        schema.setId(String.valueOf(IdTool.snowFlake()));

        schema.setSchemeName(IdTool.randomName(random, 32));
        schema.setSchemaAddress(IdTool.randomName(random, 256));
        schema.setSchemaStatus(String.valueOf(random.nextInt(0, 6)));
        schema.setSchemaType(String.valueOf(random.nextInt(0, 6)));
        schema.setSchemaRate(BigDecimal.valueOf(random.nextDouble(0, 100)));
        schema.setSchemaRateWorkDate(OffsetDateTime.now());
        schema.setSchemaRateSource(String.valueOf(random.nextInt(0, 6)));

        schema.setSchemaAuthorType(String.valueOf(random.nextInt(0, 6)));
        schema.setSchemaAuthorDate(OffsetDateTime.now());
        schema.setSchemaAuthorSeq("OP" + String.format("%10s", random.nextInt(1, 1000000))
                .replaceAll(" ", "0"));
        schema.setSchemaAuthorName(IdTool.randomName(random, 10));

        schema.setSchemaRepayType(String.valueOf(random.nextInt(0, 6)));
        schema.setSchemaRepaySum(BigDecimal.valueOf(random.nextDouble(1, 10000000)));
        schema.setSchemaRepayDate(OffsetDateTime.now());

        schema.setSchemaExpenseName(IdTool.randomName(random, 16));
        schema.setSchemaExpenseType(String.valueOf(random.nextInt(0, 6)));
        schema.setSchemaRepaySum(BigDecimal.valueOf(random.nextDouble(1, 1000000)));
        schema.setSchemaExpenseLumpType(String.valueOf(random.nextInt(0, 6)));

        schema.initFiled();
        return schema;
    }
}
