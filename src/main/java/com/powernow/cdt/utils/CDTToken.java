package com.powernow.cdt.utils;

import com.powernow.cdt.exception.DecodeVisitTokenException;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;


public class CDTToken {
	
	private static String TOKEN_KEY="_c";
	private static int token_expire = 30*24*60*60;
	
	
	/**
	 * 根据userid生成用于访问个人信息的token；
	 * @param userId
	 * @return
	 */
	public static String encodeVisitToken(int userId){
		String access_token = EncryUtil.encode(TOKEN_KEY+"_"+userId);
		return access_token;
	}
	
	/**
	 * 返回userid
	 * @param token
	 * @return
	 * @throws DecodeVisitTokenException 
	 */
	public static int decodeVisitToken(String token) throws DecodeVisitTokenException{
		if(token.startsWith("1000000")){
			return Integer.valueOf(token);
		}
		int userId = 0;
		try{
			String decodeToken = EncryUtil.decode(token);
			String userIdstr = decodeToken.split("_")[2];
			userId = Integer.valueOf(userIdstr);
		}catch(Exception e){
			throw new DecodeVisitTokenException("visit token出错");
		}
		return userId; 
	}

	private static String token_key = "api:token:";
	private static String token_hash_key = "api_token_hash";
	/**
	 * 生成Token Token：Nv6RRuGEVvmGjB+jimI/gw==
	 *
	 * @return
	 */
	public static String makeToken() { 
		String random = new Random().nextInt(999999999) + "";
		String token = UUID.randomUUID().toString().replaceAll("-", "") + random;
		// 数据指纹 128位长 16个字节 md5
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte md5[] = md.digest(token.getBytes());
			// base64编码--任意二进制编码明文字符 adfsdfsdfsf
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(md5);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String encodeToken2(int userId) {
		String token = makeToken();
		if(StringUtils.isNotBlank(token)) {
			String result = JedisPoolUtils.set(token_key+token, userId+"",token_expire);
			if(StringUtils.isNotBlank(result)) {
				Object object = JedisPoolUtils.hget(token_hash_key, userId+"");
				if(object!=null) {
					JedisPoolUtils.del(token_key+object.toString());
				}
				JedisPoolUtils.hset(token_hash_key, userId+"", token);
				return token;
			}else {
				//放入redis失败，递归调用，重新生产
				return encodeToken2(userId);
			}
//			JedisPoolUtils.setnx(token_key+token, userId+"");
		}else {
			//生成失败，递归调用，重新生产
			return encodeToken2(userId);
		}
	}

	public static int decodeToken(String token) {
		Object string = JedisPoolUtils.get(token_key+token);
		if(string!=null) {
			Object object = JedisPoolUtils.hget(token_hash_key, string.toString());
			if(object!=null) {
				if(token.equals(object.toString())) {
					JedisPoolUtils.upateExpire(token_key+token, string.toString(), token_expire);
					return Integer.valueOf(string.toString());
				}else {
					JedisPoolUtils.del(token_key+token);
				}
			}else {
				JedisPoolUtils.hset(token_hash_key, string.toString(), token);
				return Integer.valueOf(string.toString());
			}
		}
		return 0;
	}
	
	public static void delToken(String token) {
		Object string = JedisPoolUtils.get(token_key+token);
		if(string!=null) {
			JedisPoolUtils.hdel(token_hash_key, string.toString());
			JedisPoolUtils.del(token_key+token);
		}
	}
}
