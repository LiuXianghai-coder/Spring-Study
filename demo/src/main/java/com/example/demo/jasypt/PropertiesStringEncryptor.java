package com.example.demo.jasypt;

import cn.hutool.core.lang.Pair;
import com.example.demo.tools.RSATools;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Base64;

@Component("jasyptPropertiesStringEncryptor")
public class PropertiesStringEncryptor
        implements StringEncryptor {

    private final static Logger log = LoggerFactory.getLogger(PropertiesStringEncryptor.class);

    /*
        假设通过某种工具，生成了 RSA 对应的密钥对
     */
    private final static String N = "ALMI7iWKmnHB2RwLlSKXJ4Ws98gNfL0d4Mt4zBYUXo65yHwpqWq9m1BJW3nI0Coh1PlNZpfBgL0OZ1oNw1LF4YTnr/Tb8ouOp43B96uNr6yxoYzy9xd8THFS4nF8EQgI5Hhp9dvDCypssHAOsvKAhT5ZuH1V33YnZCkl1K2bMmrgqjt1nwSmCT5VbPqI1LEB0Oct5T+0tcr3AN8C5FCchHWNWCNS1labJzF68RuFTYOFhVo9C0ULNbZFO3RdgLpMg7fRcQ80iiOUmCOctLjU8Sd9grI/gPYDyJaPHam5CRSvTJiN9bBzs5tZnl/z/jGYhZGLF/gSdAtqhQpxJ8B7Huc=";
    private final static String E = "AI4e8RWXMkBg4J5lpRjJeerVtXt1NcKdGRapjx2L8lfZOutp1xbNIOYDs6n6mXXdzjEBn0frLh6K9+Erw1b63cc=";
    private final static String D = "EbtQsNV5vZxCsz0zfVtrhw6AJklEBNoOnEvTF+MGt3Kp7NvIBWoCZV4odfIJGivhDHE/612zvkFd3wzHqbLlGwYXAcqzzCanr7HpNm8Q5G3V5caz6jRSErxp5FFyXMMLVCEwkpn/X3SCKNh/SeYJX9++1hZivNMmm0NKOBG+h2vuTkaIgpUhWw0jJY2YuHE9x1LNUlxpPwrYZqMhN2A99eGxOXAD3FaWtMQbVz0xf640TkgiQt2WF+9cuvTKY+1PUFVbgxCaNjG1XatWQ8p4UOg9EQnf073nagWdFjzbRNGQWYPJY63EQt1ff9mSZHp374v/kwiugYFtIcJbEW32nQ==";
    // 密钥对结束

    private final BigInteger n;

    private final BigInteger e;

    private final BigInteger d;

    public PropertiesStringEncryptor() {
        this.n = new BigInteger(Base64.getDecoder().decode(N));
        this.e = new BigInteger(Base64.getDecoder().decode(E));
        this.d = new BigInteger(Base64.getDecoder().decode(D));
    }

    @Override
    public String encrypt(String message) {
        String s = new String(RSATools.encode(message, Pair.of(n.toByteArray(), e.toByteArray())));
        log.info("{} encode to {}", message, s);
        return s;
    }

    @Override
    public String decrypt(String encryptedMessage) {
        String s = new String(RSATools.decode(Base64.getDecoder().decode(encryptedMessage), Pair.of(n.toByteArray(), d.toByteArray())));
        log.info("{} decode to {}", encryptedMessage, s);
        return s;
    }
}
