package com.example.demo.app;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xhliu2
 * @create 2021-09-06 16:19
 **/
public class Application {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Application app = new Application();
        String msg = "abcdefghijklmopqrstuvwxyz";
        KeyPair pair = app.genKeyPair();
        System.out.println(new String(msg.getBytes()));
        byte[] encode = app.encode(msg, pair.getPrivateKey().toByteArray(), pair.getN().toByteArray());
        System.out.println(Base64.getEncoder().encodeToString(encode));
        byte[] decode = app.decode(encode, pair.getPublicKey().toByteArray(), pair.getN().toByteArray());
        System.out.println(new String(decode));
    }

    public byte[] encode(String msg, byte[] _e, byte[] _n) {
        BigInteger m = new BigInteger(msg.getBytes(StandardCharsets.UTF_8));
        BigInteger n = new BigInteger(_n);
        BigInteger e = new BigInteger(_e);
        return m.modPow(e, n).toByteArray();
    }

    public byte[] decode(byte[] content, byte[] _d, byte[] _n) {
        BigInteger c = new BigInteger(content);
        BigInteger d = new BigInteger(_d);
        BigInteger n = new BigInteger(_n);
        return c.modPow(d, n).toByteArray();
    }

    public KeyPair genKeyPair() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        BigInteger p = BigInteger.probablePrime(1024, random);
        BigInteger q = BigInteger.probablePrime(1024, random);
        BigInteger n = p.multiply(q);
        while (p.compareTo(q) == 0) {
            q = BigInteger.probablePrime(1024, random);
        }
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = phi.subtract(BigInteger.ONE);
        while (e.gcd(phi).compareTo(BigInteger.ONE) != 0) {
            e = e.subtract(BigInteger.ONE);
        }
        BigInteger d = e.modInverse(phi);
        return new KeyPair(e, d, n);
    }

    static class KeyPair {
        private final BigInteger privateKey;
        private final BigInteger publicKey;
        private final BigInteger n;

        KeyPair(BigInteger privateKey, BigInteger publicKey, BigInteger n) {
            this.privateKey = privateKey;
            this.publicKey = publicKey;
            this.n = n;
        }

        public BigInteger getPrivateKey() {
            return privateKey;
        }

        public BigInteger getPublicKey() {
            return publicKey;
        }

        public BigInteger getN() {
            return n;
        }
    }
}
