package com.example.demo.encrypt;

import com.example.demo.DemoApplication;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.iv.StringFixedIvGenerator;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

@SpringBootTest(classes = {DemoApplication.class})
public class SimpleEncryptTest {

    private final static Logger log = LoggerFactory.getLogger(SimpleEncryptTest.class);

    private static EnvironmentStringPBEConfig pbeConfig() {
        String password = "1234567";
        String salt = "我能够吞下玻璃而不伤身体";
        String iv = "这是一个向量生成器";

        final EnvironmentStringPBEConfig config =
                new EnvironmentStringPBEConfig();

        config.setPassword(password); // 当前对称加密算法需要的基本密码，而不是需要转换的文本

        /*
            具体对称加密算法，目前系统提供了 com.sun.crypto.provider.PBEKeyFactory 子类相关的算法
         */
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");

        /*
            迭代计算次数，通过增加这个值可以提高加密效果的强度
         */
        config.setKeyObtentionIterations(1000);

        config.setSaltGenerator(new StringFixedSaltGenerator(salt)); // 具体的盐值生成器，也可以由自己重写对应的生成规则

        /*
            如果需要设置自定义的对称加密算法，那么这里可能需要设置成对应的算法提供对象，
            在一般情况下，系统提供的加密算法已经足够满足需求，因此可以设置为 null
         */
        config.setProvider(null);

        config.setStringOutputType("Base64"); // 处理时的字节表示形式

        config.setIvGenerator(new StringFixedIvGenerator(iv)); // 某些算法可能需要使用到的初始向量生成器
        return config;
    }

    @Test
    public void listAlgorithm() throws NoSuchAlgorithmException {
        Provider[] providers = Security.getProviders();
        SecretKeyFactory rsa = SecretKeyFactory.getInstance("PBEWithHmacSHA224AndAES_128");
        log.info("RSA={}", rsa);
        for (Provider provider : providers) {
            for (Provider.Service service : provider.getServices()) {
                if ("SecretKeyFactory".endsWith(service.getType())) {
                    log.info("{}", service.getAlgorithm());
                }
            }
        }

    }

    @Test
    public void encryptTest() {
        String message = "123";

        final EnvironmentStringPBEConfig config = pbeConfig();

        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setConfig(config);

        log.info("{}", encryptor.encrypt(message));
    }

    @Test
    public void decrypt() {
        String encMsg = "14IWTHIVetfPsQHm7M/uvOMZ7DNmXwlY4Ujtf/wY+uA=";

        final EnvironmentStringPBEConfig config = pbeConfig();

        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setConfig(config);
        log.info("{}", encryptor.decrypt(encMsg));
    }
}
