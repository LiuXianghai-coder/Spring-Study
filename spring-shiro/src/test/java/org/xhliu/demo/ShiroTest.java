package org.xhliu.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author xhliu
 * @time 2022-01-23-下午7:54
 */
@SpringBootTest
public class ShiroTest {
    static Logger log = LoggerFactory.getLogger(ShiroTest.class);

    @Resource
    DataSource dataSource;

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
    JdbcRealm jdbcRealm = new JdbcRealm();

    static String USER_NAME = "xhliu";
    static String PASSWORD = "123456";

    @Test
    public void contextLoad() {
        log.info("dataSource=" + dataSource);
    }

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("xhliu", "123456", "admin", "user");
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setAuthenticationQuery("SELECT user_password FROM tb_user WHERE user_name=?");
        jdbcRealm.setUserRolesQuery("SELECT role_name\n" +
                "FROM tb_role\n" +
                "         JOIN\n" +
                "     (\n" +
                "         SELECT role_id\n" +
                "         FROM tb_user\n" +
                "                  JOIN user_role ON tb_user.user_id = user_role.user_id\n" +
                "         WHERE tb_user.user_name = ?\n" +
                "     ) AS role_ids\n" +
                "     ON tb_role.role_id = role_ids.role_id");
        jdbcRealm.setPermissionsQuery("SELECT permission\n" +
                "FROM data_scope\n" +
                "         JOIN\n" +
                "     (\n" +
                "         SELECT role_scope.scope_id\n" +
                "         FROM role_scope\n" +
                "                  JOIN tb_role ON role_scope.role_id = tb_role.role_id\n" +
                "         WHERE tb_role.role_name = ?\n" +
                "     ) AS scopes ON data_scope.scope_id = scopes.scope_id");
    }

    @Test
    public void testAuthentication() {
        // 1. 构建 SecurityManager（核心部分）
        DefaultSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2. 主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置当前 Shiro 上下文中的 SecurityManager

        // 3. 客户端请求对象
        Subject subject = SecurityUtils.getSubject(); // 获取当前 Shiro 上下文环境下的的 客户端请求对象
        UsernamePasswordToken token = new UsernamePasswordToken(USER_NAME, PASSWORD); // 通过解析请求得到的用户名和密码构成访问 Token

        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            log.info("用户帐号不存在");
            throw e;
        } catch (IncorrectCredentialsException e) {
            log.info("用户帐号或密码错误");
            throw e;
        }

        log.info("authenticated status: {}", subject.isAuthenticated());
        subject.logout();
        log.info("authenticated status: {}", subject.isAuthenticated());
    }

    @Test
    public void jdbcAuthTest() {
        // 1. 构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm); // 设置数据访问 Realm

        // 2. 设置当前 Shiro 上下文环境中的 SecurityManager
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        // 3. 获取当前登录会话的主题对象
        Subject subject = SecurityUtils.getSubject();

        // 根据当前的登录会话生成登录 token
        UsernamePasswordToken token = new UsernamePasswordToken(USER_NAME, PASSWORD);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            log.info("用户帐号不存在");
            throw e;
        } catch (IncorrectCredentialsException e) {
            log.info("用户帐号或密码错误");
            throw e;
        }

        log.info("authenticated status: {}", subject.isAuthenticated());
        log.info("check user has 'admin' and 'user' roles: {}", subject.hasRoles(Arrays.asList("admin", "user")));
    }

    @Resource
    private HashedCredentialsMatcher matcher;

    @Test
    public void hashTest() {
        String password = "123456";
        String salt = "123456";

        salt = ByteSource.Util.bytes(salt).toBase64();

        String genPwd = password;
        for (int i = 0; i < matcher.getHashIterations(); ++i)
            genPwd = getSecurePassword(genPwd, salt.getBytes());

        log.info("salt = {}", salt);
        log.info("password= {}", genPwd);

        log.info("encode = {}", new SimpleHash("SHA-256", password, salt, 10));
    }

    public static String getSecurePassword(String password, byte[] salt) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }

}
