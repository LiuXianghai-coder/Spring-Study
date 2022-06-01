package com.example.demo.domain.ed;

import com.example.demo.domain.ed.entity.EdProjectInfo;
import com.example.demo.tools.PropertiesTool;
import org.junit.jupiter.api.Test;

public class EdDomainTest {
    @Test
    public void test() {
        EdProjectInfo info = EdProjectInfo.EXAMPLE;
        EdProjectInfo obj = new EdProjectInfo(2);
        PropertiesTool.copyProperties(info, obj);
        System.out.println(info);
        System.out.println(obj);
//        System.out.println(int.class);
    }
}
