package cn.com.yibin.maomi.cache.redis;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import cn.com.yibin.maomi.core.redis.ShardedJedisSentinelPool;

public class ShardedJedisUtil {
//	private static final ThreadLocal<ShardedJedis> tl_sj = new ThreadLocal<ShardedJedis>();
	
	//private static final ThreadLocal<List<RedisCommand>> tl_rc = new ThreadLocal<List<RedisCommand>>();
	//private  static Lock                                  lock                               = new ReentrantLock();
	private static ShardedJedis bindShardedJedis(ShardedJedisSentinelPool shardedJedisSentinelPool){
//		ShardedJedis shardedJedis = tl_sj.get();
//		if(null == shardedJedis){
//			shardedJedis =  shardedJedisSentinelPool.getResource();
//			tl_sj.set(shardedJedis);
//		}
//		return shardedJedis;
		//lock.lock();
		try{
		    return shardedJedisSentinelPool.getResource();
		}catch(JedisConnectionException e){
			e.printStackTrace();
			shardedJedisSentinelPool.reInitPool();
		}
		return shardedJedisSentinelPool.getResource();
	}
	public static void releaseShardedJedis(boolean success,ShardedJedisSentinelPool shardedJedisSentinelPool,ShardedJedis shardedJedis){
		try{
			if(success && (null != shardedJedis)){
				shardedJedisSentinelPool.returnResource(shardedJedis);
				//tl_sj.remove();
			}
		}finally{
			//lock.unlock();
		}
	}
	public static void releaseBrokenShardedJedis(boolean success,ShardedJedisSentinelPool shardedJedisSentinelPool,ShardedJedis shardedJedis){
		try{
			if(null != shardedJedis){
			   shardedJedisSentinelPool.returnBrokenResource(shardedJedis);
			}
		}finally{
			//lock.unlock();
		}
		//tl_sj.remove();
	}
	
	public static ShardedJedis getShardedJedis(ShardedJedisSentinelPool shardedJedisSentinelPool){
//		ShardedJedis shardedJedis = tl_sj.get();
//		if(null == shardedJedis){
//			shardedJedis = bindShardedJedis(shardedJedisSentinelPool);
//		}
//		return shardedJedis;
		return bindShardedJedis(shardedJedisSentinelPool);
	}
	private static ShardedJedis bindShardedJedis(ShardedJedisPool shardedJedisPool){
//		ShardedJedis shardedJedis = tl_sj.get();
//		if(null == shardedJedis){
//			shardedJedis =  shardedJedisSentinelPool.getResource();
//			tl_sj.set(shardedJedis);
//		}
//		return shardedJedis;
		//lock.lock();
		return shardedJedisPool.getResource();
	}
	public static void releaseShardedJedis(boolean success,ShardedJedisPool shardedJedisPool,ShardedJedis shardedJedis){
		try {
			if(success && (null != shardedJedis)){
				shardedJedisPool.returnResource(shardedJedis);
				//tl_sj.remove();
			}
		} finally{
			//lock.unlock();
		}
	}
	public static void releaseBrokenShardedJedis(boolean success,ShardedJedisPool shardedJedisPool,ShardedJedis shardedJedis){
		try {
			if(null != shardedJedis){
			   shardedJedisPool.returnBrokenResource(shardedJedis);
			}
			//tl_sj.remove();
		} finally{
			//lock.unlock();
		}
	}
	
	public static ShardedJedis getShardedJedis(ShardedJedisPool shardedJedisPool){
//		ShardedJedis shardedJedis = tl_sj.get();
//		if(null == shardedJedis){
//			shardedJedis = bindShardedJedis(shardedJedisPool);
//		}
//		return shardedJedis;
		return bindShardedJedis(shardedJedisPool);
	}
//	public static ShardedJedis getCurrentShardedJedis(){
//		ShardedJedis shardedJedis = tl_sj.get();
//		return shardedJedis;
//	}

}
