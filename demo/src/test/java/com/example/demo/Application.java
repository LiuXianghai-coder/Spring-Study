package com.example.demo;

/**
 * @author xhliu2
 **/
public class Application {

    public static void main(String[] args) {
        System.out.println("local value = struct.pack('dLc0', tonumber(ARGV[2]), string.len(ARGV[3]), ARGV[3]);"
                + "redis.call('zadd', KEYS[2], ARGV[1], value);"
                + "redis.call('rpush', KEYS[3], value);"
                // if new object added to queue head when publish its startTime
                // to all scheduler workers
                + "local v = redis.call('zrange', KEYS[2], 0, 0); "
                + "if v[1] == value then "
                + "redis.call('publish', KEYS[4], ARGV[1]); "
                + "end;");
    }
}
