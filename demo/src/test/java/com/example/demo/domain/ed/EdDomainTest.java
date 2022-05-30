package com.example.demo.domain.ed;

import com.example.demo.domain.ed.entity.EdProjectInfo;
import com.example.demo.domain.ed.entity.ProjectInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

public class EdDomainTest {
    @Test
    public void test() {
        ProjectInfo info = EdProjectInfo.EXAMPLE;
        EdProjectInfo obj = new EdProjectInfo();
        BeanUtils.copyProperties(info, obj);
        System.out.println(info);
        System.out.println(obj);
    }
}
