package com.example.springsecurity.Entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "user_role")
public class UserRole implements UserDetails, Serializable {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Basic
    @Column(name = "user_name")
    private String username;

    @Basic
    @Column(name = "user_password")
    private String userPassword;

    @Basic
    @Column(name = "user_role")
    private String userRole;

    @Basic
    @Column(name = "is_enabled")
    private Boolean enabled = true;

    // 用户角色信息集合, 这个不是对应表的列， 因此添加 @Transient 注解
    @Transient
    private Collection<SimpleGrantedAuthority> grantedAuthorityCollection;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorityCollection;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 默认为 true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 默认为 true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 默认为 true
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
