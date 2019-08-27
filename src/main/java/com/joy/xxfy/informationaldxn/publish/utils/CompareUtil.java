package com.joy.xxfy.informationaldxn.publish.utils;

/**
 * 比较工具
 */
public class CompareUtil {
    // 获取三个数中最大的一个
    public static int getMaxNumber(int a , int b, int c){
        int max = a;
        if(b > max){
            max = b;
        }
        return max > c? max:c;
    }

    public static void main(String[] args) {
        System.out.println(getMaxNumber(1,4,3));
    }
}
