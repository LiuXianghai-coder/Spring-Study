package org.xhliu.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Arrays;

/**
 * @author xhliu
 * @time 2022-01-22-下午2:44
 */
@Entity
@Table(name = "customer")
@JsonIgnoreProperties(value = {"basicInfo"})
public class CustomerInfo {
    @Id
    @Column(name = "customer_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gender")
    private int gender;

    @Column(name = "email")
    private String email;

    @Column(name = "basic_info")
    private byte[] basicInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(byte[] basicInfo) {
        this.basicInfo = basicInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerInfo that = (CustomerInfo) o;
        return gender == that.gender
                && Objects.equal(id, that.id)
                && Objects.equal(email, that.email)
                && Objects.equal(basicInfo, that.basicInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, gender, email, basicInfo);
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
                "id=" + id +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", basicInfo=" + Arrays.toString(basicInfo) +
                '}';
    }
}
