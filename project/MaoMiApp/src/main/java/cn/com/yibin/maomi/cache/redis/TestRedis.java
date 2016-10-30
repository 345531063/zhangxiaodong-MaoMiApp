//package cn.com.yibin.maomi.cache.redis;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//
//import redis.clients.jedis.JedisShardInfo;
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.ShardedJedisPool;
//
//import cn.com.yibin.maomi.mina.handler.util.RedisKeyUtil;
//
//public class TestRedis {
//   public static void main(String[] args) {
//	   TestRedis.test();
//   }
//   public static void test() {
//        //Jedis redis = new Jedis("127.0.0.1", 6379);// 连接redis
//        //redis.auth("redis");// 验证密码
//        /*
//         * ----------------------------------------------------------------------
//         * -------------------------------------
//         */
//        /** KEY操作 **/
//        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
//        config.setMaxTotal(200);
//        config.setMaxIdle(50);
//        config.setMaxWaitMillis(10000);
//        config.setTestOnBorrow(true);
//		/*List<String> masters = new ArrayList<String>();
//		masters.add("master135");
//		masters.add("master136");
//		//masters.add("shard2");
//
//		Set<String> sentinels = new HashSet<String>();
//		sentinels.add("192.168.2.200:16379");
//		sentinels.add("192.168.2.200:26379");
//		//sentinels.add("192.168.109.215:26379");
//        String password = "yibin.maomi123";
//		ShardedJedisSentinelPool pool = new ShardedJedisSentinelPool(masters, sentinels, config, password);
//        */
//        /***
//    	<!-- jedis shard pool配置 -->
//    	<bean id="shardedJedisPool" class="redis.clients.jedis.ShardedJedisPool">
//    	    <constructor-arg index="0" ref="jedisPoolConfig" />
//    	    <constructor-arg index="1">
//    	        <list>
//    	            <bean class="redis.clients.jedis.JedisShardInfo">
//    				    <constructor-arg index="0" value="192.168.2.135" />
//    				    <constructor-arg index="1" value="6379" />
//    				    <property name="password" value="#{jedisProperties['redis.password']}" />
//    				</bean>
//    				<bean class="redis.clients.jedis.JedisShardInfo">
//    				    <constructor-arg index="0" value="192.168.2.136" />
//    				    <constructor-arg index="1" value="6379" />
//    				    <property name="password" value="#{jedisProperties['redis.password']}" />
//    				</bean>
//    	        </list>
//    	    </constructor-arg>
//    	</bean>
//    	<bean id="shardedJedisTemplate" class="com.yibin.maomi.cache.redis.ShardedJedisTemplate" >
//    	  <constructor-arg index="0" ref="shardedJedisPool"/>
//    	</bean>
//    	***/
//        /*****
//         *
//         * 	<bean id="shardedJedisPool" class="redis.clients.jedis.ShardedJedisPool">
//	    <constructor-arg index="0" ref="jedisPoolConfig" />
//	    <constructor-arg index="1">
//	        <list>
//	            <bean class="redis.clients.jedis.JedisShardInfo">
//				    <constructor-arg index="0" value="192.168.2.135" />
//				    <constructor-arg index="1" value="6379" />
//				    <property name="password" value="#{jedisProperties['redis.password']}" />
//				</bean>
//				<bean class="redis.clients.jedis.JedisShardInfo">
//				    <constructor-arg index="0" value="192.168.2.136" />
//				    <constructor-arg index="1" value="6379" />
//				    <property name="password" value="#{jedisProperties['redis.password']}" />
//				</bean>
//	        </list>
//	    </constructor-arg>
//	</bean>
//         *
//         *
//         */
//        List<JedisShardInfo>   jedisShardInfoList  = new ArrayList<JedisShardInfo>();
//        JedisShardInfo    jedisShardInfo1 =  new JedisShardInfo("192.168.2.135",6379);
//        jedisShardInfo1.setPassword("yibin.maomi123");
//        jedisShardInfoList.add(jedisShardInfo1);
//        JedisShardInfo    jedisShardInfo2 =  new JedisShardInfo("192.168.2.136",6379);
//        jedisShardInfo2.setPassword("yibin.maomi123");
//        jedisShardInfoList.add(jedisShardInfo2);
//        ShardedJedisPool pool = new ShardedJedisPool(config,jedisShardInfoList);
//
//
//		ShardedJedis redis = pool.getResource();
//
//
//		//String gateWayBindKey              = RedisKeyUtil.getUserMobileIdGateWayBindKey(75L);
//    	String             userMobileIdGateWayBindKey        = RedisKeyUtil.getUserMobileIdGateWayBindKey(75l);
//    	Set<String>        userIdMobileIdSet                 = redis.smembers(userMobileIdGateWayBindKey);
//		System.out.println(">>>"+userIdMobileIdSet);
//		for(String userIdMobileId : userIdMobileIdSet){
//			Long   userId     = Long.parseLong(userIdMobileId.split(":")[0]);
//			String mobileId   = userIdMobileId.split(":")[1];
//			String userIdMobileIdBindKey    = RedisKeyUtil.getUserIdMobileIdBindKey(userId, mobileId);
//			System.out.println("测试::"+userIdMobileIdBindKey);
//			System.out.println(">>>>>>>:::"+redis.get(userIdMobileIdBindKey));
//		}
//
//		String             gateWayBindkey        = RedisKeyUtil.getGateWayIdBindKey(75L);
//		System.out.println(">>>:"+gateWayBindkey);
//		System.out.println("测试::"+redis.getShardInfo(gateWayBindkey));
//		System.out.println(">>>>>>>:::"+redis.hgetAll(gateWayBindkey));
////		redis.del(gateWayBindKey);
////		redis.set(RedisKeyUtil.getSceneDoubleGoHomeUserGateWayBindKey(32L, 75L),"1");
////		redis.set(RedisKeyUtil.getSceneDoubleLeaveHomeUserGateWayBindKey(32L, 75L),"2");
//		//String                       gateWayDeviceTokenBindKey    = RedisKeyUtil.getUserMobileDeviceTokenGateWayBindKey(75L);
//        //redis.del(gateWayDeviceTokenBindKey);
//		//		// KEYS
////        //Set<String> keys = redis.keys("*");// 列出所有的key，查找特定的key如：redis.keys("foo")
////		Set<String> keys = new HashSet<String>();
//////		for(Jedis j : redis.getAllShards()){
//////			keys.addAll(j.keys("DEVICE:*"));
//////		}
//////		System.out.println(keys);
//////		Iterator<String> t1 = keys.iterator();
//////        while (t1.hasNext()) {
//////            Object obj1 = t1.next();
//////            System.out.println("::"+obj1+">>"+redis.hgetAll("hash"));
//////        }
////		//redis.set("GATEWAYF315D402004B1200:0E01010110000001", "abcd");
//////		System.out.println(redis.get("GATEWAYF315D402004B1200:0E01010110000001"));
//////		System.out.println(redis.hgetAll("DEVICE:ID:"+49+":"));
//////		System.out.println(redis.hgetAll("DEVICE"+49+":"));
//////		System.out.println(redis.hgetAll("DEVICE"+49));
//////		System.out.println(redis.hgetAll("DEVICE:"+49));
////		Map<String,String> testMap = new HashMap<String,String>();
////		testMap.put("a", "a");
////		redis.hmset("test", testMap);
////		System.out.println(redis.hgetAll("test"));
////		testMap = new HashMap<String,String>();
////		testMap.remove("a");
////		redis.hset("test", "b","b");
////		System.out.println(redis.hgetAll("test"));
////		redis.hset("test", "c","c");
////		System.out.println(redis.hgetAll("test"));
////		//ABBA120C00016FC42C17D402004B1200AFBE
////		//ABBA120C00016FC42C17D402004B1200AABB
////		//SocketUtils.hexStringToBytes("ABBA120C00016FC 42C17D402004B1200 AFBE")
////		//ABBA120C0001 6FC4 2C17D402004B1200 AABB
////		Set<String> ieeeKeys = new HashSet<String>();
////		for(Jedis j : redis.getAllShards()){
////			ieeeKeys.addAll(j.keys("DEVICE:IEEE:*"));
////		}
////		try{
////			redis.hgetAll("DEVICE:IEEE:2C17D402004B1200:");
////			//System.out.println(/*redis.hgetAll*/(RedisKeyUtil.getDeviceIeeeAddressBindKey("2C17D402004B1200")));//2C17D402004B1200
////			//redis.hmset(RedisKeyUtil.getDeviceIeeeAddressBindKey("2C17D402004B1200"), new HashMap<String,String>());
////		}catch(Exception e){
////			e.printStackTrace();
////		}
////		System.out.println(keys.size());
////		for(String ieeeKey : ieeeKeys){
////			try{
////				redis.hgetAll(ieeeKey);//2C17D402004B1200
////			}catch(Exception e){
////				redis.del(ieeeKey);
////				System.out.println(ieeeKey);
////			}
////		}
////		//String ieeeAddress = SocketUtils.bytesToHexString(Arrays.copyOfRange(SocketUtils.hexStringToBytes("42C17D402004B1200"), 8, 16));
////
////		//redis.del(RedisKeyUtil.getDeviceIeeeAddressBindKey(ieeeAddress));
////        pool.returnResource(redis);
////
//////        redis = pool.getResource();
//////        // DEL 移除给定的一个或多个key。如果key不存在，则忽略该命令。
//////        redis.del("name1");
//////
//////        // TTL 返回给定key的剩余生存时间(time to live)(以秒为单位)
//////        redis.ttl("foo");
//////
//////        // PERSIST key 移除给定key的生存时间。
//////        redis.persist("foo");
//////
//////        // EXISTS 检查给定key是否存在。
//////        redis.exists("foo");
//////        // MOVE key db
//////        // 将当前数据库(默认为0)的key移动到给定的数据库db当中。如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定key，或者key不存在于当前数据库，那么MOVE没有任何效果。
//////        redis.move("foo", 1);// 将foo这个key，移动到数据库1
//////
//////        // RENAME key newkey
//////        // 将key改名为newkey。当key和newkey相同或者key不存在时，返回一个错误。当newkey已经存在时，RENAME命令将覆盖旧值。
//////        /*try{redis.rename("foo", "foonew");}catch(Exception e){
//////        	System.out.println("不存在相应的key");
//////        }*/
//////
//////        // TYPE key 返回key所储存的值的类型。
//////        System.out.println(redis.type("foo"));// none(key不存在),string(字符串),list(列表),set(集合),zset(有序集),hash(哈希表)
//////        //有序集合
//////        redis.zadd("zhangsan", 30,"李四");
//////        redis.zadd("zhangsan", 10,"张三");
//////        redis.zadd("zhangsan", 20,"王五");
//////        System.out.println("设定顺序的set集合："+redis.zrange("zhangsan", 0, -1));
//////        // EXPIRE key seconds 为给定key设置生存时间。当key过期时，它会被自动删除。
//////        redis.expire("foo", 5);// 5秒过期
//////        // EXPIREAT
//////        // EXPIREAT的作用和EXPIRE一样，都用于为key设置生存时间。不同在于EXPIREAT命令接受的时间参数是UNIX时间戳(unix
//////        // timestamp)。
//////
//////        // 一般SORT用法 最简单的SORT使用方法是SORT key。
//////        redis.lpush("sort", "1");
//////        redis.lpush("sort", "4");
//////        redis.lpush("sort", "6");
//////        redis.lpush("sort", "3");
////        redis.lpush("sort", "0");
////
////        List<String> list = redis.sort("sort");// 默认是升序
////        for (int i = 0; i < list.size(); i++) {
////            System.out.println(list.get(i));
////        }
////
////        /*
////         * ----------------------------------------------------------------------
////         * -------------------------------------
////         */
////        /** STRING 操作 **/
////
////        // SET key value将字符串值value关联到key。
////        redis.set("name", "wangjun1");
////        redis.set("id", "123456");
////        redis.set("address", "guangzhou");
////
////        // SETEX key seconds value将值value关联到key，并将key的生存时间设为seconds(以秒为单位)。
////        redis.setex("foo", 5, "haha");
////
////        // MSET key value [key value ...]同时设置一个或多个key-value对。
////        //redis.mset("haha", "111", "xixi", "222");
////        redis.set("haha", "111");
////        redis.set("xixi", "222");
////        // redis.flushAll();清空所有的key
////        /****System.out.println(redis.dbSize());// dbSize是多少个key的个数****/
////
////        // APPEND key value如果key已经存在并且是一个字符串，APPEND命令将value追加到key原来的值之后。
////        redis.append("foo", "00");// 如果key已经存在并且是一个字符串，APPEND命令将value追加到key原来的值之后。
////
////        // GET key 返回key所关联的字符串值
////        redis.get("foo");
////
////        // MGET key [key ...] 返回所有(一个或多个)给定key的值
////        //list = redis.mget("haha", "xixi");
////        list.add(redis.get("haha"));
////        list.add(redis.get("xixi"));
////        for (int i = 0; i < list.size(); i++) {
////            System.out.println(list.get(i));
////        }
////        // DECR key将key中储存的数字值减一。
////        // DECRBY key decrement将key所储存的值减去减量decrement。
////        // INCR key 将key中储存的数字值增一。
////        // INCRBY key increment 将key所储存的值加上增量increment。
////
////        /*
////         * ----------------------------------------------------------------------
////         * -------------------------------------
////         */
////        /** Hash 操作 **/
////
////        // HSET key field value将哈希表key中的域field的值设为value。
////        redis.hset("website", "google", "www.google.cn");
////        redis.hset("website", "baidu", "www.baidu.com");
////        redis.hset("website", "sina", "www.sina.com");
////
////        // HMSET key field value [field value ...] 同时将多个field -
////        // value(域-值)对设置到哈希表key中。
////        Map<String, String> map = new HashMap<String, String>();
////        map.put("cardid", "123456");
////        map.put("username", "jzkangta");
////        redis.hmset("hash", map);
////        // HGET key field返回哈希表key中给定域field的值。
////        System.out.println(redis.hget("hash", "username"));
////
////        // HMGET key field [field ...]返回哈希表key中，一个或多个给定域的值。
////        list = redis.hmget("website", "google", "baidu", "sina");
////        for (int i = 0; i < list.size(); i++) {
////            System.out.println(list.get(i));
////        }
////
////        // HGETALL key返回哈希表key中，所有的域和值。
////        Map<String, String> maphash = redis.hgetAll("hash");
////        for (@SuppressWarnings("rawtypes")
////        Map.Entry entry : maphash.entrySet()) {
////            System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");
////        }
////
////        // HDEL key field [field ...]删除哈希表key中的一个或多个指定域。
////        // HLEN key 返回哈希表key中域的数量。
////        // HEXISTS key field查看哈希表key中，给定域field是否存在。
////        // HINCRBY key field increment为哈希表key中的域field的值加上增量increment。
////        // HKEYS key返回哈希表key中的所有域。
////        // HVALS key返回哈希表key中的所有值。
////
////        /*
////         * ----------------------------------------------------------------------
////         * -------------------------------------
////         */
////        /** LIST 操作 **/
////        // LPUSH key value [value ...]将值value插入到列表key的表头。
////        redis.lpush("list", "abc");
////        redis.lpush("list", "xzc");
////        redis.lpush("list", "erf");
////        redis.lpush("list", "bnh");
////
////        // LRANGE key start
////        // stop返回列表key中指定区间内的元素，区间以偏移量start和stop指定。下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。你也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。
////        list = redis.lrange("list", 0, -1);
////        for (int i = 0; i < list.size(); i++) {
////            System.out.println(list.get(i));
////        }
////
////        // LLEN key返回列表key的长度。
////        // LREM key count value根据参数count的值，移除列表中与参数value相等的元素。
////
////        /*
////         * ----------------------------------------------------------------------
////         * -------------------------------------
////         */
////        /** SET 操作 **/
////        // SADD key member [member ...]将member元素加入到集合key当中。
////        redis.sadd("testSet", "s1");
////        redis.sadd("testSet", "s2");
////        redis.sadd("testSet", "s3");
////        redis.sadd("testSet", "s4");
////        redis.sadd("testSet", "s5");
////
////        // SREM key member移除集合中的member元素。
////        redis.srem("testSet", "s5");
////        // SMEMBERS key返回集合key中的所有成员。
////        Set<String> set = redis.smembers("testSet");
////        Iterator<String> testSet = set.iterator();
////        while (testSet.hasNext()) {
////            Object obj1 = testSet.next();
////            System.out.println(obj1);
////        }
////
////        // SISMEMBER key member判断member元素是否是集合key的成员。是（true），否则（false）
////        System.out.println(redis.sismember("testSet", "s4"));
////        // SCARD key返回集合key的基数(集合中元素的数量)。
////        // SMOVE source destination member将member元素从source集合移动到destination集合。
////
////        // SINTER key [key ...]返回一个集合的全部成员，该集合是所有给定集合的交集。
////        // SINTERSTORE destination key [key
////        // ...]此命令等同于SINTER，但它将结果保存到destination集合，而不是简单地返回结果集
////        // SUNION key [key ...]返回一个集合的全部成员，该集合是所有给定集合的并集。
////        // SUNIONSTORE destination key [key
////        // ...]此命令等同于SUNION，但它将结果保存到destination集合，而不是简单地返回结果集。
////        // SDIFF key [key ...]返回一个集合的全部成员，该集合是所有给定集合的差集 。
////        // SDIFFSTORE destination key [key
////        // ...]此命令等同于SDIFF，但它将结果保存到destination集合，而不是简单地返回结果集。
//        // redis.close();
//		pool.returnResource(redis);
//        pool.destroy();
//    }
////    public static void main(String []args) throws Exception{
////        TestRedis t1 = new TestRedis();
////        t1.test1();
////    }
//}
