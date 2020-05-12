package com.powernow.cdt.utils;

import com.alibaba.fastjson.JSON;
import com.powernow.cdt.config.JedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.Map;


@Component
public class JedisPoolUtils {

	private static JedisPool pool;

	@Autowired
	private JedisConfig jedisConfig;

	/**
	 * JedisPool 无法通过@Autowired注入，可能由于是方法bean的原因，此处可以先注入RedisConfig，
	 * 然后通过@PostConstruct初始化的时候将factory直接赋给jedisPool
	 */
	@PostConstruct
	public void init() {
		pool = jedisConfig.redisPoolFactory();
	}




	/**
	 * 静态方法获取Jedis对象 获取前进行非空判断 pool如果为空，则返回null
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		if (pool != null) {
			return pool.getResource();
		}
		return null;
	}

	/**
	 * 回收Jedis 回收前进行非空判断 final Jedis jedis确保传进来的Jedis对象与returnJedis(Jedis
	 * jedis)方法内Jedis对象一致
	 * 
	 * @param jedis
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void returnJedis(final Jedis jedis) {
		if (jedis != null) {
			pool.returnResourceObject(jedis);
		}
	}


	public static JedisPool getPool() {
		return pool;
	}

	/************************************************
	 * String Key 类型
	 *******************************************/

	/**
	 * 向缓存中设置字符串内容 失败返回0 不覆盖 成功 返回1
	 * 
	 * @param key   key
	 * @param value value
	 * @return
	 * @throws Exception
	 */
	public static long setnx(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.setnx(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}

		return 0;
	}

	/**
	 * 成功返回 OK 向缓存中设置对象(自动把对象转换成json数据存储到缓层中)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static long setnx(String key, Object value) {
		Jedis jedis = null;
		try {
			String objectJson = JSON.toJSONString(value);
			jedis = pool.getResource();
			return jedis.setnx(key, objectJson);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}

		return 0;
	}

	/**
	 * 删除缓存中得对象，根据key
	 * 
	 * @param key
	 * @return
	 */
	public static boolean del(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.del(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedis.close();
		}

	}

	/**
	 * 根据key 获取内容
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			Object value = jedis.get(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedis.close();
		}

	}

	/**
	 * 根据key 获取对象
	 * 
	 * @param key
	 * @return
	 */
	public static <T> T get(String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String value = jedis.get(key);
			return JSON.parseObject(value, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}
	}

	/***
	 * 检查key是否存在
	 * 
	 * @param key
	 * @return true 存在 false 不存在
	 */
	public static boolean checkExists(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedis.close();
		}

	}

	/***
	 * 往指定的key追加内容，key不在则添加key
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean appendStr(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.append(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			jedis.close();
		}
	}

	/***
	 * 批量获取key的value值
	 * 
	 * @param keys
	 * @return
	 */
	public static Object bathKey(String[] keys) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.mget(keys);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}

	}

	/***************************************
	 * hashes(哈希)类型
	 *********************************************************/

	/**
	 * 设置hash field 如果存在不会设置返回0
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return 成功返回1,失败 0
	 */
	public static long hsetnx(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hsetnx(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			jedis.close();
		}
		return 0;

	}
	
	/**
	 * 设置hash field 如果存在不会设置返回0
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return 成功返回1,失败 0
	 */
	public static long hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hset(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			jedis.close();
		}
		return 0;

	}

	/**
	 * hget取值(value)
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static Object hget(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hget(key, field);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}
	}

	/**
	 * hmset 批量设置值
	 * 
	 * @param key
	 * @param hashmap
	 * @return 成功返回OK
	 */
	public static String hmset(String key, Map<String, String> hashmap) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hmset(key, hashmap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * hmget 批量取值(value)
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public static Object hmget(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hmget(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}
	}

	/**
	 * @param key
	 * @return 返回所有的key和value
	 */
	public static Map<String, String> hgetall(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}
	}
	/**
	 * hdel 批量删除值
	 * 
	 * @param key
	 * @param fields
	 * @return 成功返回OK
	 */
	public static long hdel(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hdel(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return 0;
	}
	/***************************************
	 * list(列表)
	 *********************************************************/

	/**
	 * lpush 设置值 从头部压入一个元素
	 * 
	 * @param key
	 * @param strings
	 * @return 成功返回成员的数量 失败返回0
	 */
	public static long lpush(String key, String... strings) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lpush(key, strings);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return 0;
	}

	/**
	 * list列表取值(lrange)
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return start=0 end=-1(代表从开始到结束)
	 */
	public static Object lrange(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return 0;
	}

	public static String rpoplpush(String key, String dstkey) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.rpoplpush(key, dstkey);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	public static String rpop(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.rpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	public static String set(String key, String value, int time) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.set(key, value, "NX", "EX", time);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}

		return "";
	}

	public static Long setExpire(String key, int time) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.expire(key, time);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return 0L;
	}
	
	
	public static String upateExpire(String key, String value,int time) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			Long ttl = jedis.ttl(key);
			int expire = time/2;
			if(ttl!=null&&ttl.longValue()<expire) {//当key的剩余时间小于time一半值，再更新
				jedis.del(key);
				return jedis.set(key, value, "NX", "EX", time);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return "";
	}

}
