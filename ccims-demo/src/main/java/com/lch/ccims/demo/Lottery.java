package com.lch.ccims.demo;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

/**
 * 抽奖实例demo
 */
public class Lottery {

    // 参与抽奖手机号码
    static String[] phone = {"13144800360","13144800361","13144800362","13144800363","13144800364","13144800365"
            ,"13144800366","13144800367","13144800368","13144800369","13144800376","13144800386","13144800396"
            ,"13144800466","13144800566","13144800666","13144800766","13144800866","13144800966","13144800066"};

    // 中奖人数
    static int num = 2;

    /**
     * 开始抽奖方法
     * @return
     */
    public static Set<String> start(){
        Set<String> set = new HashSet<>();
        for (; ; ){
            if (set.size() == num){
                break;
            }
            int random = new SecureRandom().nextInt(phone.length);
            set.add(phone[random]);
        }
        return set;
    }

    public static void main(String[] args) {
        System.out.println(start());
    }
}
