package cn.com.yibin.maomi.cache.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import cn.com.yibin.maomi.core.redis.ShardedJedisSentinelPool;

public class ShardedJedisTemplate {
    /* */
	private final ShardedJedisSentinelPool shardedJedisSentinelPool;

	public ShardedJedisTemplate(ShardedJedisSentinelPool shardedJedisSentinelPool) {
		this.shardedJedisSentinelPool = shardedJedisSentinelPool;
	}

	private ShardedJedis getShardedJedis() throws Exception {
		return ShardedJedisUtil.getShardedJedis(shardedJedisSentinelPool);
	}
	public void releaseShardedJedis(boolean success,ShardedJedis shardedJedis) throws Exception {
		ShardedJedisUtil.releaseShardedJedis(success,shardedJedisSentinelPool,shardedJedis);
	}
	public void releaseBrokenShardedJedis(boolean success,ShardedJedis shardedJedis) throws Exception {
		ShardedJedisUtil.releaseBrokenShardedJedis(success,shardedJedisSentinelPool,shardedJedis);
	}
   
	/*
	private final ShardedJedisPool shardedJedisPool;

	public ShardedJedisTemplate(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	private ShardedJedis getShardedJedis() throws Exception {
		return ShardedJedisUtil.getShardedJedis(shardedJedisPool);
	}
	
	public void releaseShardedJedis(boolean success,ShardedJedis shardedJedis) throws Exception {
		ShardedJedisUtil.releaseShardedJedis(success,shardedJedisPool,shardedJedis);
	}
	public void releaseBrokenShardedJedis(boolean success,ShardedJedis shardedJedis) throws Exception {
		ShardedJedisUtil.releaseBrokenShardedJedis(success,shardedJedisPool,shardedJedis);
	}*/
    private void manageShardedJedis(boolean success,ShardedJedis shardedJedis){
    	try {
			releaseShardedJedis(success,shardedJedis);
		} catch (Exception e) {
			e.printStackTrace();
		}
    } 
    
	public Set<String> getAllKeys(String pattern) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			Set<String> keys = new HashSet<String>();
			shardedJedis     = getShardedJedis();
			for (Jedis j : shardedJedis.getAllShards()) {
				keys.addAll(j.keys(pattern));
			}
			return keys;
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
	public int deleteKeysByPattern(String pattern) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis     = getShardedJedis();
			Set<String> keys = new HashSet<String>();
			for (Jedis j : shardedJedis.getAllShards()) {
				keys.addAll(j.keys(pattern));
			}
			int delCount = 0;
			for(String key : keys){
//				for (Jedis j : shardedJedis.getAllShards()) {
//					j.del(key);
//				}
				shardedJedis.del(key);
				++delCount;
			}
			return delCount;
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
	public String set(String key, String value) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis     = getShardedJedis();
			return shardedJedis.set(key, value);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
	public long ttl(String key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis     = getShardedJedis();
			return shardedJedis.ttl(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
	public long ttl(byte[] key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis     = getShardedJedis();
			return shardedJedis.ttl(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
	public String get(String key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.get(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public String set(byte[] key, byte[] value) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.set(key, value);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public byte[] get(byte[] key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.get(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long delete(String key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.del(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long delete(byte[] key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.del(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long expire(String key, int seconds) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.expire(key, seconds);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long expire(byte[] key, int seconds) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.expire(key, seconds);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public boolean exists(String key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.exists(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public boolean exists(byte[] key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.exists(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long setMap(String key, String entryKey, String entryValue) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hset(key, entryKey, entryValue);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long setMap(byte[] key, byte[] entryKey, byte[] entryValue) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hset(key, entryKey, entryValue);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public String setMap(String key, Map<String, String> map) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hmset(key, map);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public String setMap(byte[] key, Map<byte[], byte[]> map) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hmset(key, map);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Map<String, String> getMap(String key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hgetAll(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Map<byte[], byte[]> getMap(byte[] key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hgetAll(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long deleteMap(String key, String... removedEntryKeys) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hdel(key, removedEntryKeys);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long deleteMap(byte[] key, byte[]... removedEntryKeys) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hdel(key, removedEntryKeys);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
	public String mapGet(String key,String entryKey) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hget(key,entryKey);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
	public boolean mapContains(String key, String entryKey) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hexists(key, entryKey);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public boolean mapContains(byte[] key, byte[] entryKey) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hexists(key, entryKey);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long setList(String key, String... values) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.lpush(key, values);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long setList(byte[] key, byte[]... values) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.lpush(key, values);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long setList(String key, List<String> values) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			Long returnValue = 0L;
			for (String value : values) {
				returnValue += shardedJedis.lpush(key, value);
			}
			return returnValue;
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long setList(byte[] key, List<byte[]> values) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			Long returnValue = 0L;
			for (byte[] value : values) {
				returnValue += shardedJedis.lpush(key, value);
			}
			return returnValue;
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public List<byte[]> getList(byte[] key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.lrange(key, 0, -1);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public List<String> getList(String key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.lrange(key, 0, -1);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public List<String> getList(String key, String... entryKeys) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hmget(key, entryKeys);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public List<byte[]> getList(byte[] key, byte[]... entryKeys)
			throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.hmget(key, entryKeys);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long deleteList(String key, String removedMember) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.lrem(key, 0, removedMember);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
	public Long deleteList(String key, String... removedMembers) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			Long returnValue = 0L;
			for(String removedMember : removedMembers){
				returnValue +=  shardedJedis.lrem(key, 0, removedMember);
			}
			 return returnValue;
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long deleteList(byte[] key, byte[] removedMember) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.lrem(key, 0, removedMember);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public String listGet(String key, long index) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.lindex(key, index);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public byte[] listGet(byte[] key, long index) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.lindex(key, index);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public boolean listContains(byte[] key,byte[] member) throws Exception {
		List<byte[]> l = getList(key);
		return l.contains(member);
	}

	public boolean listContains(String key,String member) throws Exception {
		List<String> l = getList(key);
		return l.contains(member);
	}

	public Long setSet(String key, String members) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.sadd(key, members);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long setSet(byte[] key, byte[] members) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.sadd(key, members);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long setSet(String key, Set<String> members) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			Long returnValue = 0L;
			for (String member : members) {
				returnValue += shardedJedis.sadd(key, member);
			}
			return returnValue;
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long setSet(byte[] key, Set<byte[]> members) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			Long returnValue = 0L;
			for (byte[] member : members) {
				returnValue += shardedJedis.sadd(key, member);
			}
			return returnValue;
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Set<String> getSet(String key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.smembers(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Set<byte[]> getSet(byte[] key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.smembers(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long deleteSet(String key, String... removedMembers) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.srem(key, removedMembers);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public Long deleteSet(byte[] key, byte[]... removedMembers) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.srem(key, removedMembers);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public boolean setContains(String key, String member) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.sismember(key, member);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}

	public boolean setContains(byte[] key, byte[] member) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.sismember(key, member);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
	public Long setAdd(String key,String... members) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.sadd(key, members);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
	public Long setAdd(byte[] key,byte[]... members) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.sadd(key, members);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
	public Long setIncr(String key) throws Exception {
		ShardedJedis shardedJedis = null;
		boolean      success      = true;
		try {
			shardedJedis = getShardedJedis();
			return shardedJedis.incr(key);
		} catch (Exception e) {
			success      = false;
			releaseBrokenShardedJedis(success,shardedJedis);
			throw e;
		} finally {
			manageShardedJedis(success,shardedJedis);
		}
	}
}
