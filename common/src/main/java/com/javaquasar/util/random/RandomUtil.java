package com.javaquasar.util.random;

import java.util.Random;

/**
 * Created by Java Quasar on 07.09.17.
 */
public class RandomUtil {

    public static void main(String[] args) {
        System.out.println(RandomUtil.getRandomNumber(3000, 5000));
    }

    private static final Random rand = new Random();

    public static int getRandomNumber(int max) {
        return getRandomNumber(0, max);
    }

    public static int getRandomNumber(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

}
