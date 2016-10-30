package cn.com.yibin.maomi.cache.redis.datasource;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Repository("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource {

    private static final Logger log = LoggerFactory.getLogger(RedisDataSourceImpl.class);

    @Resource(name="shardedJedisPool")
    private ShardedJedisPool    shardedJedisPool;

    public ShardedJedis getRedisClient() {
    	ShardedJedis shardJedis = null;
        try {
        	shardJedis = shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            log.error("getRedisClent error", e);
        }
        return shardJedis;
    }

    @SuppressWarnings("deprecation")
	public void returnResource(ShardedJedis shardedJedis, boolean broken) {
       shardedJedisPool.returnResourceObject(shardedJedis);
    }
}