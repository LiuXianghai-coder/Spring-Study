package com.example.hateoas.Resource;

import com.example.hateoas.Entity.UserInfo;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * 定义的用户信息资源类， 包含所需要展现的属性以及从 RepresentationModel
 * 类继承的 Link 等链接相关的属性
 */
/*
    @Relation: 消除 JSON 字段名与 Java 类属性之间的耦合
    value： 在JSON中引用一个单体对象时的单体对象名称
    collectionRelation： 当有多个对象时， 将其值设置为对应的JSON字段名
 */
@Relation(value = "userInfo", collectionRelation = "userInfos")
public class UserInfoResource extends RepresentationModel<UserInfoResource> {
    @Getter
    // 用户名属性
    private final String userName;

    @Getter
    // 用户性别
    private final String userGender;

    /*
        不含超链接属性的构造函数
     */
    public UserInfoResource(UserInfo userInfo) {
        this.userName       =       userInfo.getUserName();
        this.userGender     =       userInfo.getUserGender();
    }

    /*
        添加对应的链接对象的构造函数，
        因此能够在创建对象时就可以添加想对应的超链接
     */
    public UserInfoResource(UserInfo userInfo, Link link) {
        // 调用父类构造函数添加对应链接
        super(link);

        this.userName       =   userInfo.getUserName();
        this.userGender     =   userInfo.getUserGender();
    }
}
