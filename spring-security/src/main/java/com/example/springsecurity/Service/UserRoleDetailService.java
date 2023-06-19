package com.example.springsecurity.Service;

import com.example.springsecurity.Entity.UserRole;
import com.example.springsecurity.Repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserRoleDetailService implements UserDetailsService {
    private final UserRoleRepository roleRepository;

    @Autowired
    public UserRoleDetailService(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // 定义一个集合， 用于存储用户角色（即权限）的集合。该集合是为了存储用户角色的类型
        Collection<SimpleGrantedAuthority> collection = new ArrayList<>();
        // 遍历所有的用户， 将他们的角色信息添加到集合中
        for (UserRole role: roleRepository.findAll())
            collection.add(new SimpleGrantedAuthority(role.getUserRole()));

        // 使用流过滤掉相同的元素
        collection = collection.stream()
                .distinct()
                .collect(Collectors.toList());

        // 通过用户名查找对应的用户信息
        UserRole userRole = roleRepository.findUserRoleByUsername(username);

        if (null == userRole) return new UserRole();

        // 将存在的用户角色集合注入到用户信息对象中
        userRole.setGrantedAuthorityCollection(collection);

        return userRole;
    }
}
