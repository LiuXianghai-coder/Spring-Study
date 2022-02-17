package org.xhliu.demo.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xhliu.demo.entity.SysPermission;
import org.xhliu.demo.entity.SysRole;
import org.xhliu.demo.entity.UserInfo;
import org.xhliu.demo.repository.UserInfoRepo;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author xhliu
 * @time 2022-01-28-下午9:11
 */
@Component
public class SimpleRealm extends AuthorizingRealm {
    private final static Logger log = LoggerFactory.getLogger(SimpleRealm.class);

    private final UserInfoRepo userInfoRepo;

    public SimpleRealm(UserInfoRepo userInfoRepo, HashedCredentialsMatcher matcher) {
        this.userInfoRepo = userInfoRepo;
        setCredentialsMatcher(matcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        for (SysRole role : userInfo.getRoles()) {
            info.addRole(role.getName());
            for (SysPermission permission : role.getPermissions())
                info.addStringPermission(permission.getName());
        }

        return info;
    }

    @Override
    protected AuthenticationInfo
    doGetAuthenticationInfo(
            AuthenticationToken token
    ) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        token.getCredentials();
        log.info("get principal: {}", userName);

        Optional<UserInfo> optional = userInfoRepo.findByUserName(userName);
        if (optional.isEmpty()) return null;

        UserInfo userInfo = optional.get();

        return new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getPassword(),
                ByteSource.Util.bytes(userInfo.getSalt()),
                getName()
        );
    }
}
