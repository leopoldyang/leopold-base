package com.leopold.framework;


import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Base64工具类
 *
 * Created by IDEA
 * User:Leopold
 * Email:ylp_boy@126.com
 * Date:2015/11/26
 * Time:0:57
 */
public class Base64Util {
	private static final String PREFIX="encrypt_";
	private static final String UNICODE="UTF-8";

	private Base64Util() {
	}
 
	
	//解密
	public static String decrypt(String data) {
		try {
			if (StringTools.isEmpty(data)) {
				return data;
			} else if (data.startsWith(PREFIX)) {
				return new String(Base64.decodeBase64(data.replaceFirst(PREFIX, "")),UNICODE) ;
			}
		}catch (Exception e) {
			return data;
		}
		return data;
	}

	//加密
	public static String encrypt(String data) {
		try {
			if (StringTools.isEmpty(data)) {
				return data;
			} else {
				return PREFIX + Base64.encodeBase64String(data.getBytes(UNICODE));
			}
		}catch (Exception e) {
			return data;
		}
	}

	/**
	 * 获取字符串md5加密之后的值
	 * @param value
	 * @return
	 */
	public static String toMD5String(String value){
		String result=null;
		try {
			MessageDigest messageDigest=MessageDigest.getInstance("MD5");
			BASE64Encoder base64Encoder=new BASE64Encoder();
			result=base64Encoder.encode(messageDigest.digest(value.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
