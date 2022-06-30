package com.example.demo.tools;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.math.BigInteger.ONE;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author xhliu
 * @create 2022-06-30-13:55
 **/
public class RSAEncoder {
    private final static Random rand = ThreadLocalRandom.current();
    private final static int BIT_LENGTH = 1024;

    private final static BigInteger P = BigInteger.probablePrime(BIT_LENGTH, rand);
    private final static BigInteger Q = BigInteger.probablePrime(BIT_LENGTH, rand);

    private final static BigInteger N = P.multiply(Q);
    private final static BigInteger R = P.subtract(ONE).multiply(Q.subtract(ONE));

    private static BigInteger E = BigInteger.valueOf(2L);
    private static BigInteger D;

    static {
        while (E.compareTo(R) < 0) {
            if (E.gcd(R).intValue() == 1) break;
            E.add(ONE);
        }

        D = E.modInverse(R);
    }

    public String encoder(String msg) {
        BigInteger msgNum = new BigInteger(msg.getBytes(UTF_8));
        BigInteger enc = msgNum.modPow(E, N);

        return new String(enc.toByteArray());
    }

    public String decoder(String msg) {
        BigInteger dec = new BigInteger(msg.getBytes(UTF_8));
        dec = dec.modPow(D, N);
        return new String(dec.toByteArray(), UTF_8);
    }

    public String publisherKey() {
        return E.toString();
    }

    public String privateKey() {
        return D.toString();
    }

    static void test(String text) {
        int BIT_LENGTH = 2048;

        Random rand = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(BIT_LENGTH / 2, rand);
        BigInteger q = BigInteger.probablePrime(BIT_LENGTH / 2, rand);
        // 计算 N
        BigInteger n = p.multiply(q);

        // 计算 r
        BigInteger phi = p.subtract(ONE)
                .multiply(q.subtract(ONE));

        BigInteger e = BigInteger.valueOf(2L);

        // 找到合适的 e
        while (e.compareTo(phi) < 0) {
            if (e.gcd(phi).intValue() == 1) break;
            e = e.add(ONE);
        }

        BigInteger d = e.modInverse(phi); // 获得 e 的模反元素

        BigInteger msg = new BigInteger(text.getBytes(UTF_8)); // 将消息转换为对应的整数
        BigInteger enc = msg.modPow(e, n); // 相当于对 msg 做 e 次乘法，再对 n 求模

        System.out.println("raw=" + text);
        System.out.println("enc=" + enc);
        BigInteger dec = enc.modPow(d, n);
        System.out.println("dec=" + new String(dec.toByteArray(), UTF_8));
    }

    public static void main(String[] args) {
        String msg = "This is simple text";
        test(msg);
    }
}
