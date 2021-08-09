package com.example.hateoas.Controller;

import com.example.hateoas.Assembler.UserInfoAssembler;
import com.example.hateoas.Entity.UserInfo;
import com.example.hateoas.Repository.UserInfoRepository;
import com.example.hateoas.Resource.UserInfoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * 用户信息控制器类， 处理相关的请求
 */
@RestController // 声明这是一个 REST 风格的控制器
@RequestMapping(path = "/user") // 该控制器的基础访问路径
public class UserInfoController {
    // 通过构造函数注入 UserInfoRepository 接口对象， 实现对数据库的基本访问
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoController(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @GetMapping(path = "/userInfos")
    public CollectionModel<UserInfoResource>
    userInfoCollection() {
        // 通过Pageable对象查找前三个 UserInfo 对象
        List<UserInfo> userInfoList = userInfoRepository
                .findAll(PageRequest.of(0, 3));

        /*
            定义的UserInfoResource对象列表，通过遍历 UserInfo 列表，
            添加相关的超链接属性
         */
        List<UserInfoResource> resources = new ArrayList<>();

        for (UserInfo info: userInfoList) {
            UserInfoResource resource = new UserInfoResource(info);
            /*
                WebMvcLinkBuilder.linkTo 链接到对应的地址，
                在这里使用 WebMvcLinkBuilder.methodOn() 方法， 可以访问到对象的方法名，
                从而得到方法对应的地址， 避免硬编码。

                当然， 也可以使用 linkTo(***Controller.class).slash()
                方法来设置路径， 但是这样的话会导致硬编码

                withRel：这个链接对应的链接名称， 这里定义为 Info1Address
             */
            resource.add(linkTo(methodOn(UserInfoController.class)
                    .infoResource(info.getUserId())).withRel("infoAddress"));

            resources.add(resource);
        }

        // 将得到的 UserInfoResource 包装到资源集合中
        CollectionModel<UserInfoResource> models
                = CollectionModel.of(resources);

        // 这个资源集合的访问超链接
        models.add(linkTo(methodOn(UserInfoController.class).userInfoCollection())
                .withRel("userInfos"));

        return models;
    }

    /**
     * 通过 userId 查找对应的 UserInfo 对象
     * @param userId ： 查找的 userId
     * @return ： 查找到的 UserInfo 对象
     */
    @GetMapping(path = "/userInfo/{userId}")
    public EntityModel<UserInfoResource> infoResource(
            @PathVariable(name = "userId") Long userId) {
        UserInfo userInfo = userInfoRepository.findUserInfoByUserId(userId);

        return new EntityModel<>(new UserInfoResource(userInfo));
    }

    /**
     * 使用 Assembler 对象转变为对应的集合
     * @return ： 转变后的对象集合
     */
    @GetMapping(path = "/userAssembler")
    public CollectionModel<UserInfoResource>
    userAssembler(){
        // 通过Pageable对象查找前三个 UserInfo 对象
        List<UserInfo> userInfoList = userInfoRepository
                .findAll(PageRequest.of(0, 3));

        // 将得到的 UserInfo 对象列表转变为对应的集合
        CollectionModel<UserInfoResource> models
                = new UserInfoAssembler().toCollectionModel(userInfoList);
        // 添加当前集合的超链接
        models.add(linkTo(methodOn(UserInfoController.class)
                .userAssembler()).withRel("userAssembler"));

        return models;
    }
}
