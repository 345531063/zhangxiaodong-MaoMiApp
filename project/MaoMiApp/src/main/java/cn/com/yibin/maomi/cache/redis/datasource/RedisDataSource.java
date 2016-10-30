package cn.com.yibin.maomi.cache.redis.datasource;

import redis.clients.jedis.ShardedJedis;

/**获取redis客户端*/
public interface RedisDataSource {
    public abstract ShardedJedis getRedisClient();
    public void returnResource(ShardedJedis shardedJedis, boolean broken);
}
