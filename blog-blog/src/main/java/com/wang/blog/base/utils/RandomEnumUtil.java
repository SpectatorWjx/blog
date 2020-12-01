package com.wang.blog.base.utils;

import java.util.Random;

/**
 * @author wjx
 * @date 2019/08/13
 */
public class RandomEnumUtil {
    private static int random=(int)(Math.random()*10);// 生成种子
    private static Random rand = new Random(random);

    public static <T extends Enum<T>> T random(Class<T> ec) {
        return random(ec.getEnumConstants());
    }

    public static <T> T random(T[] values) {
        return values[rand.nextInt(values.length)];
    }

    public static <T extends Enum<T>> T num(Class<T> ec, Integer num) {
        return num(ec.getEnumConstants(), num);
    }

    public static <T> T num(T[] values, Integer num) {
        if(num < values.length){
            return values[num];
        }
        return values[values.length-1];
    }
}