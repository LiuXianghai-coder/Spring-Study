package com.example.demo.jasypt;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import com.ulisesbocchio.jasyptspringboot.exception.DecryptionException;
import com.ulisesbocchio.jasyptspringboot.properties.JasyptEncryptorConfigurationProperties;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.iv.StringFixedIvGenerator;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component("jasyptPropertiesPropertyResolver")
public class PropertiesPropertyResolver
        implements EncryptablePropertyResolver {

    private final static Logger log = LoggerFactory.getLogger(PropertiesPropertyResolver.class);

    private final static String IV_GEN_TEXT = "这是一个向量生成器";

    private final static String SALT_TEXT = "我能够吞下玻璃而不伤身体";

    private final Environment environment;

    private final PooledPBEStringEncryptor encryptor;

    private final EncryptablePropertyDetector detector;

    @Autowired
    public PropertiesPropertyResolver(ConfigurableEnvironment env,
                                      @Qualifier("jasyptPropertiesDetector") EncryptablePropertyDetector detector) {
        this.environment = env;
        this.encryptor = new PooledPBEStringEncryptor();
        this.detector = detector;

        // 复用原有 Jasypt 的配置属性
        JasyptEncryptorConfigurationProperties props = JasyptEncryptorConfigurationProperties.bindConfigProps(env);
        String password = props.getPassword();
        if (Objects.isNull(password) || password.trim().isEmpty()) {
            return;
        }

        SimpleStringPBEConfig config = getSimpleStringPBEConfig(password);
        this.encryptor.setConfig(config);
    }

    private static SimpleStringPBEConfig getSimpleStringPBEConfig(String password) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPasswordCharArray(password.toCharArray());
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize(1);
        config.setProviderName("SunJCE");
        config.setSaltGenerator(new StringFixedSaltGenerator(SALT_TEXT)); // 替换默认的盐值生成器
        config.setIvGenerator(new StringFixedIvGenerator(IV_GEN_TEXT)); // 替换默认的向量生成器
        config.setStringOutputType("base64");
        return config;
    }

    @Override
    public String resolvePropertyValue(String value) {
        return Optional.ofNullable(value)
                .map(environment::resolvePlaceholders)
                .filter(detector::isEncrypted)
                .map(resolvedValue -> {
                    try {
                        String unwrappedProperty = detector.unwrapEncryptedValue(resolvedValue.trim());
                        String resolvedProperty = environment.resolvePlaceholders(unwrappedProperty);
                        String decrypt = encryptor.decrypt(resolvedProperty);
                        log.info("from {} to {}", resolvedProperty, decrypt);
                        return decrypt;
                    } catch (EncryptionOperationNotPossibleException e) {
                        throw new DecryptionException("Unable to decrypt property: "
                                + value + " resolved to: " + resolvedValue + ". Decryption of Properties failed,  make sure encryption/decryption " +
                                "passwords match", e);
                    }
                })
                .orElse(value);
    }
}
