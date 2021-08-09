package com.example.hateoas.Assembler;

import com.example.hateoas.Controller.UserInfoController;
import com.example.hateoas.Entity.UserInfo;
import com.example.hateoas.Resource.UserInfoResource;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * 定义一个 Assembler 类， 实现 RepresentationModelAssembler 接口，
 * 可以避免每次都需要添加对应的超链接
 */
public class UserInfoAssembler implements
        RepresentationModelAssembler<UserInfo, UserInfoResource> {
    @Override
    public UserInfoResource toModel(UserInfo userInfo) {
        return new UserInfoResource(userInfo,
                linkTo(methodOn(UserInfoController.class)
                        .infoResource(userInfo.getUserId()))
                        .withRel("infoAddress"));
    }
}
