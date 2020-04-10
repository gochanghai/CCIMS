package com.lch.ccims.demo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.BitSet;

/**
 * redis工具类
 * 统计活跃用户的数量
 * 获取第一次签到和第一次未签到的时间
 */
@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String activeName = "activeUser:";

    /**
     * 标记为活跃用户
     * @param userid
     */
    public void setActiveUserCount(Long userid) {
        //Date currentTime = new Date();
        LocalDate currentStr = LocalDateTime.now().toLocalDate();
        String key = activeName + currentStr;
        //redisTemplate.opsForValue().setBit(key, id, true);
        redisTemplate.execute((RedisCallback<Boolean>) con -> con.setBit(key.getBytes(), userid, true));
    }

    /**
     * 统计每日活跃用户数等类似的场景
     * @param key
     * @return
     */
    public Long bitCount(String key) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    /**
     * 统计7天内活跃的用户数 方法1
     * @return
     */
    public long getActiveUserCount() {
        LocalDate currentTime = LocalDate.now();
        BitSet all = new BitSet();
        for(int i=0;i<7;i++){
            //获得前一天的日期
            currentTime= currentTime.minusDays(i);
            // 组件key
            String key = activeName+currentTime;
            //获得这一天日期的活跃用户字节数组
            byte[] loginByte = (byte[]) redisTemplate.execute((RedisCallback<byte[]>) con -> con.get(key.getBytes()));
            //有可能当天没有一个用户登录
            if(loginByte!=null){
                BitSet user = BitSet.valueOf(loginByte);
                //取其并集
                all.or(user);
            }
        }
        //统计人数
        long count = all.cardinality();
        return count;
    }

    /**
     * 统计7天内活跃的用户数 方法2
     * @return
     */
    public Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        byte[][] bytes = new byte[desKey.length][];
        for (int i = 1; i < desKey.length; i++) {
            bytes[i] = (activeName + desKey[i]).getBytes();
        }
        return (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(op, saveKey.getBytes(), bytes));
    }

    /**
     * 用来查找指定范围内出现的第一个 0 或 1
     * 获取第一次签到和第一次未签到的时间
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long bitCount(String key, int start, int end) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
    }

    public boolean setLock(){
        return true;
    }

    public static String charCount(String str) {
        StringBuilder cuntStr = new StringBuilder();
        while (str.length()>0){
            // 字符串的长度
            int strLength = str.length();
            // 获取字符串的第一个字符
            char at = str.charAt(0);
            // 字符串里的 at 字符替换为空字符串""
            str = str.replace(""+ at +"", "");
            // 计算字符出现的次数
            int cunt = strLength - str.length();
            // 添加到返回的字符串里面
            cuntStr.append(","+ at +":" + cunt);
        }
        String result = "{" + cuntStr.substring(1) + "}";
        return result;
    }

    /**
     * 有一个字符串A 有一个字符串B 想要从A转换到B，只能一次一次转换，每次转换要把字符串A中的一个字符全部转换成另一个字符，
     * 求字符串A能不能转换成字符串B。例如 "abc" -- "bbc" --- "ddc" 判断转换是否成立
     *
     */
    public static boolean isConvert(String A, String B, int index) {
        // 字符串转换为字符数组
        char[] c11 = A.toCharArray();
        char[] c21 = B.toCharArray();
        // 获取要替换的字符
        char m = c21[index];
        // 获取被替换的字符
        char f = c11[index];
        // 遍历原字符数组
        for (int i = 0; i < c11.length; i++) {
            // 如果是和需要替换的字符相同
            if (c11[i] == f) {
                c11[i] = m;
            }
        }
        // 字符数组转换为String类型
        A = arrayToString(c11);
        B = arrayToString(c21);

        // 判断是否到了最后一位
        if ((index == A.length() - 1)) {
            if (A.trim().equals(B.trim())) {
                return true;
            } else {
                return false;
            }
        }
        index++;
        // 递归判断
        return isConvert(A, B, index);

    }

    public static String arrayToString(char[] c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < c.length; i++) {
            sb.append(c[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(charCount("aaaBBBaaA23"));

        //System.out.println(isConvert("abc", "ddc", 0));
    }
}
