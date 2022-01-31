package org.xhliu.demo;

import org.xhliu.demo.entity.CustomerInfo;
import org.xhliu.demo.rdo.CustomerBasic;

/**
 * @author xhliu
 * @time 2022-01-22-下午3:03
 */
public class ProtobufTest {
    public static void main(String[] args) {
        CustomerInfo info = new CustomerInfo();
        CustomerBasic basic = CustomerBasic.getDefaultInstance();
        info.setBasicInfo(basic.toByteArray());

        System.out.println(info.toString());
    }
}
