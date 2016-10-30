
/**
 * 项目名称：    系统名称
 * 包名：              com.business.dao
 * 文件名：         AbstractJedisManager.java
 * 版本信息：    1.0.0
 * 创建日期：     2013-9-8-上午10:20:15
 * Copyright：2013XX公司-版权所有
 **/

package cn.com.yibin.maomi.cache.redis;


import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
import cn.com.yibin.maomi.core.util.ExceptionUtil;
import cn.com.yibin.maomi.core.util.LogUtil;

public abstract class AbstractRedisCacheManagerImpl {
	protected abstract JedisPool getJedisPool();
	protected abstract JedisDataType getJedisDataType();
	public Jedis getJedis(){
		return this.getJedisPool().getResource();
	}
	protected void returnResource(Jedis jedis){
		this.getJedisPool().returnResource(jedis);
	}
	public byte[] getValueByKey(byte[] byteKey ){
		Jedis jedis = this.getJedis();
		try {
			return jedis.get(byteKey);
		} catch (JedisException e) {
			LogUtil.error(AbstractRedisCacheManagerImpl.class,"获取byteKey为" + String.valueOf(byteKey)+ "的value失败"+ExceptionUtil.getExceptionPrintInfo(e));
		} finally {
			this.returnResource(jedis);
		}
		return null;
	}
	public void saveValueByKey(byte[] byteKey,byte[] byteValue){
		Jedis jedis = this.getJedis();
		try {
			jedis.set(byteKey, byteValue);
		} catch (JedisException e) {
			LogUtil.error(AbstractRedisCacheManagerImpl.class,"保存byteKey为" + String.valueOf(byteKey)+ "保存byteValue为："+String.valueOf(byteValue)+ExceptionUtil.getExceptionPrintInfo(e));
		} finally {
			this.returnResource(jedis);
		}
	}
	public void deleteByKeysPattern(byte[] keysPattern ){
		Jedis jedis = this.getJedis();
		try {
			Set<byte[]> byteKeys = jedis.keys(keysPattern);
			if (byteKeys != null && byteKeys.size() > 0) {
				for (byte[] bs : byteKeys) {
					jedis.del(bs);
				}
			}
		} catch (JedisException e) {
			LogUtil.error(AbstractRedisCacheManagerImpl.class,"删除byteKey为" + String.valueOf(keysPattern)+ "失败"+ExceptionUtil.getExceptionPrintInfo(e));
		} finally {
			this.returnResource(jedis);
		}
	}
	public void deleteByKey(byte[] byteKey){
		Jedis jedis = this.getJedis();
		try {
			jedis.del(byteKey);
		} catch (JedisException e) {
			LogUtil.error(AbstractRedisCacheManagerImpl.class, "删除byteKey为" + String.valueOf(byteKey)+ "失败"+ ExceptionUtil.getExceptionPrintInfo(e));
		} finally {
			this.returnResource(jedis);
		}
	}
	public Set<byte[]> getKeysByKeysPattern(byte[] keysPattern){
		Jedis jedis = this.getJedis();
		try {
			Set<byte[]> byteKeys = jedis.keys(keysPattern);
			return byteKeys;
		} catch (JedisException e) {
			LogUtil.error(AbstractRedisCacheManagerImpl.class,"获取byteKey为" + String.valueOf(keysPattern)
					+ "失败"+ExceptionUtil.getExceptionPrintInfo(e));
		} finally {
			this.returnResource(jedis);
		}
		return null;
	}
}
