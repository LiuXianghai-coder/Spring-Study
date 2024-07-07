package com.example.demo.jasypt;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.detector.DefaultPropertyDetector;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component("jasyptPropertiesDetector")
public class PropertiesDetector
        extends DefaultPropertyDetector
        implements EncryptablePropertyDetector {

    private String prefix = "ENC(";

    public PropertiesDetector() {
        super();
    }

    public PropertiesDetector(String prefix, String suffix) {
        super(prefix, suffix);
        this.prefix = prefix;
    }

    @Override
    public boolean isEncrypted(String property) {
        if (super.isEncrypted(property)) {
            return true;
        }
        // 如果是以 Base64 的方式进行的编码，则我们认为它是被加密的
        return property.length() >= 64 && Base64.isBase64(property);
    }

    @Override
    public String unwrapEncryptedValue(String property) {
        if (property.contains(prefix)) {
            return super.unwrapEncryptedValue(property);
        }
        return property;
    }
}
