package com.example.demo.tools;

import java.util.Arrays;

/**
 * @author lxh
 */
public class MutableBigInteger {
    private int[] value;  // 使用int数组表示大整数，低位在前，高位在后
    private boolean isNegative; // 标记是否为负数

    public MutableBigInteger(int size) {
        value = new int[size];
        isNegative = false;
    }

    // 以指定的整数初始化
    public MutableBigInteger(long initialValue) {
        this.isNegative = initialValue < 0;
        initialValue = Math.abs(initialValue);
        this.value = new int[2];
        this.value[0] = (int) (initialValue & 0xFFFFFFFF);
        this.value[1] = (int) (initialValue >>> 32);
    }

    // 复制构造函数
    public MutableBigInteger(MutableBigInteger other) {
        this.value = Arrays.copyOf(other.value, other.value.length);
        this.isNegative = other.isNegative;
    }

    // 加法运算（只实现正数的简单加法）
    public void add(MutableBigInteger other) {
        if (other.value.length > this.value.length) {
            this.expandTo(other.value.length);
        }
        long carry = 0;
        for (int i = 0; i < this.value.length; i++) {
            long sum = ((long) this.value[i] & 0xFFFFFFFFL) +
                    ((long) (i < other.value.length ? other.value[i] : 0) & 0xFFFFFFFFL) + carry;
            this.value[i] = (int) sum;
            carry = sum >>> 32;
        }
        if (carry != 0) {
            this.expandTo(this.value.length + 1);
            this.value[this.value.length - 1] = (int) carry;
        }
    }

    public void subtract(MutableBigInteger other) {
        if (this.compareTo(other) < 0) {
            throw new IllegalArgumentException("Subtraction would result in a negative value.");
        }

        long borrow = 0;
        for (int i = 0; i < this.value.length; i++) {
            long diff = ((long) this.value[i] & 0xFFFFFFFFL) -
                    ((long) (i < other.value.length ? other.value[i] : 0) & 0xFFFFFFFFL) - borrow;
            if (diff < 0) {
                diff += (1L << 32);
                borrow = 1;
            } else {
                borrow = 0;
            }
            this.value[i] = (int) diff;
        }
    }

    // 比较操作（假设只比较非负整数，this 与 other）
    public int compareTo(MutableBigInteger other) {
        if (this.value.length != other.value.length) {
            return Integer.compare(this.value.length, other.value.length);
        }
        for (int i = this.value.length - 1; i >= 0; i--) {
            if (this.value[i] != other.value[i]) {
                return Integer.compare(this.value[i], other.value[i]);
            }
        }
        return 0;
    }

    // 位左移操作
    public void shiftLeft(int bits) {
        int shiftInts = bits / 32; // 要左移的int数
        int shiftBits = bits % 32; // int内部的位移
        int[] newValue = new int[this.value.length + shiftInts + 1];

        for (int i = 0; i < this.value.length; i++) {
            newValue[i + shiftInts] |= (this.value[i] << shiftBits);
            if (shiftBits != 0 && i + shiftInts + 1 < newValue.length) {
                newValue[i + shiftInts + 1] |= (this.value[i] >>> (32 - shiftBits));
            }
        }
        this.value = newValue;
    }

    // 位右移操作
    public void shiftRight(int bits) {
        int shiftInts = bits / 32;
        int shiftBits = bits % 32;
        int[] newValue = new int[this.value.length - shiftInts];

        for (int i = this.value.length - 1; i >= shiftInts; i--) {
            newValue[i - shiftInts] |= (this.value[i] >>> shiftBits);
            if (shiftBits != 0 && i - shiftInts - 1 >= 0) {
                newValue[i - shiftInts - 1] |= (this.value[i] << (32 - shiftBits));
            }
        }
        this.value = newValue;
    }

    // 按位与操作
    public void and(MutableBigInteger other) {
        int minLength = Math.min(this.value.length, other.value.length);
        for (int i = 0; i < minLength; i++) {
            this.value[i] &= other.value[i];
        }
        for (int i = minLength; i < this.value.length; i++) {
            this.value[i] = 0;
        }
    }

    // 按位或操作
    public void or(MutableBigInteger other) {
        if (other.value.length > this.value.length) {
            this.expandTo(other.value.length);
        }
        for (int i = 0; i < other.value.length; i++) {
            this.value[i] |= other.value[i];
        }
    }

    // 按位异或操作
    public void xor(MutableBigInteger other) {
        if (other.value.length > this.value.length) {
            this.expandTo(other.value.length);
        }
        for (int i = 0; i < other.value.length; i++) {
            this.value[i] ^= other.value[i];
        }
    }

    // 扩展数组大小
    private void expandTo(int newSize) {
        if (newSize > this.value.length) {
            int[] newValue = new int[newSize];
            System.arraycopy(this.value, 0, newValue, 0, this.value.length);
            this.value = newValue;
        }
    }

    // 覆盖toString方法，用于显示值（十六进制格式）
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isNegative) {
            sb.append("-");
        }
        for (int i = value.length - 1; i >= 0; i--) {
            sb.append(String.format("%08X", value[i]));
        }
        return sb.toString();
    }

    // 示例测试
    public static void main(String[] args) {
        MutableBigInteger a = new MutableBigInteger(123456789012345L);
        MutableBigInteger b = new MutableBigInteger(987654321L);

        System.out.println("Initial a: " + a);
        System.out.println("Initial b: " + b);

        a.add(b);
        System.out.println("a + b: " + a);

        a.shiftLeft(4);
        System.out.println("a << 4: " + a);

        a.shiftRight(2);
        System.out.println("a >> 2: " + a);

        a.and(b);
        System.out.println("a & b: " + a);

        a.or(b);
        System.out.println("a | b: " + a);

        a.xor(b);
        System.out.println("a ^ b: " + a);
    }
}
