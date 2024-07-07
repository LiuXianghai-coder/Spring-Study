package com.example.demo.tools;

import cn.hutool.core.lang.Pair;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 用于生成 RSA 对以及加密、解密的工具类
 *
 * @author lxh
 */
public abstract class RSATools {

    public static Pair<byte[], Pair<byte[], byte[]>> genKeyPair() {
        return genKeyPair(1024);
    }

    public static Pair<byte[], Pair<byte[], byte[]>> genKeyPair(int bitCnt) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        BigInteger p = BigInteger.probablePrime(bitCnt, random);
        BigInteger q = BigInteger.probablePrime(bitCnt, random);
        while (p.compareTo(q) == 0) {
            q = BigInteger.probablePrime(bitCnt, random);
        }
        BigInteger n = p.multiply(q);
        BigInteger r = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.probablePrime(bitCnt / 2, random);
        while (e.gcd(r).compareTo(BigInteger.ONE) != 0) {
            e = BigInteger.probablePrime(bitCnt / 2, random);
        }
        BigInteger d = e.modInverse(r);
        return Pair.of(n.toByteArray(), Pair.of(e.toByteArray(), d.toByteArray()));
    }

    public static byte[] encode(String message, Pair<byte[], byte[]> privateKey) {
        BigInteger e = new BigInteger(privateKey.getValue());
        BigInteger n = new BigInteger(privateKey.getKey());
        BigInteger m = new BigInteger(message.getBytes(StandardCharsets.UTF_8));
        return m.modPow(e, n).toByteArray();
    }

    public static byte[] decode(byte[] encMsg, Pair<byte[], byte[]> publicKey) {
        BigInteger d = new BigInteger(publicKey.getValue());
        BigInteger n = new BigInteger(publicKey.getKey());
        BigInteger m = new BigInteger(encMsg);
        return m.modPow(d, n).toByteArray();
    }

    public static void main(String[] args) {
        String message = "123";
        Pair<byte[], Pair<byte[], byte[]>> keyPair = genKeyPair();
        String n = "ALMI7iWKmnHB2RwLlSKXJ4Ws98gNfL0d4Mt4zBYUXo65yHwpqWq9m1BJW3nI0Coh1PlNZpfBgL0OZ1oNw1LF4YTnr/Tb8ouOp43B96uNr6yxoYzy9xd8THFS4nF8EQgI5Hhp9dvDCypssHAOsvKAhT5ZuH1V33YnZCkl1K2bMmrgqjt1nwSmCT5VbPqI1LEB0Oct5T+0tcr3AN8C5FCchHWNWCNS1labJzF68RuFTYOFhVo9C0ULNbZFO3RdgLpMg7fRcQ80iiOUmCOctLjU8Sd9grI/gPYDyJaPHam5CRSvTJiN9bBzs5tZnl/z/jGYhZGLF/gSdAtqhQpxJ8B7Huc=";
        String e = "AOPgQq3PI5MubESilppWD2c93uwvy0s3/fbNSEPMgww9bsqi77DV/ypIOWK2xN+5++dHeNGoyX/gYi5zQTRR6y0=";
        String d = "EbtQsNV5vZxCsz0zfVtrhw6AJklEBNoOnEvTF+MGt3Kp7NvIBWoCZV4odfIJGivhDHE/612zvkFd3wzHqbLlGwYXAcqzzCanr7HpNm8Q5G3V5caz6jRSErxp5FFyXMMLVCEwkpn/X3SCKNh/SeYJX9++1hZivNMmm0NKOBG+h2vuTkaIgpUhWw0jJY2YuHE9x1LNUlxpPwrYZqMhN2A99eGxOXAD3FaWtMQbVz0xf640TkgiQt2WF+9cuvTKY+1PUFVbgxCaNjG1XatWQ8p4UOg9EQnf073nagWdFjzbRNGQWYPJY63EQt1ff9mSZHp374v/kwiugYFtIcJbEW32nQ==";

        System.out.println(Base64.getEncoder().encodeToString(keyPair.getKey()));
        System.out.println(Base64.getEncoder().encodeToString(keyPair.getValue().getKey()));
        System.out.println(Base64.getEncoder().encodeToString(keyPair.getValue().getValue()));

        byte[] encode = encode(message, Pair.of(Base64.getDecoder().decode(n.getBytes(StandardCharsets.UTF_8)),
                Base64.getDecoder().decode(e.getBytes(StandardCharsets.UTF_8))));
        System.out.println(Base64.getEncoder().encodeToString(encode));

        byte[] decode = decode(encode, Pair.of(Base64.getDecoder().decode(n.getBytes(StandardCharsets.UTF_8)),
                Base64.getDecoder().decode(d.getBytes(StandardCharsets.UTF_8))));
        System.out.println(new String(decode));
    }
}
