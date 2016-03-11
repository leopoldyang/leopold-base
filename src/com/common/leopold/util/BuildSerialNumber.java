package com.common.leopold.util;

import java.util.Random;

/**
 * 简单获取编号
 *
 * Created by IDEA
 * User:Leopold
 * Email:ylp_boy@126.com
 * Date:2015/11/26
 * Time:0:57
 */
public class BuildSerialNumber {
    private final static char[] numberArry={'0','1','2','3',
            '4','5','6','7','8','9'};
    private final static Random random=new Random();
    public static String getCurrentTime(){
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 获取编号
     * @param length 编号长度
     * @return
     */
    public static String getSimpleNo(int length){
        StringBuilder stringBuilder=new StringBuilder(getCurrentTime());
        for(int i=0;i<length-13;i++){
            stringBuilder.append(numberArry[random.nextInt(10)]);
        }
        return stringBuilder.toString();
    }
}
