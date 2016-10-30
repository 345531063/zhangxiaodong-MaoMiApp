package cn.com.yibin.maomi.core.repository.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import cn.com.yibin.maomi.core.constant.OrderByEnum;
import cn.com.yibin.maomi.core.jpa.JPASupport;

/**
 * @author zxd
 * 
 */
public interface RepositoryDao {
	public <T extends JPASupport> Long saveEntity(T t) throws Exception;
	public <T extends JPASupport> Long saveOrModifyEntity(T t) throws Exception;
	public <T extends JPASupport> int[] saveEntitiesBatch(List<T> tList) throws Exception;
	public <T extends JPASupport> int[] saveOrModifyEntitiesBatch(List<T> tList) throws Exception;
	public <T extends JPASupport> Long modifyEntity(T t) throws Exception;
	public <T extends JPASupport> int[] modifyEntitiesBatch(List<T> tList) throws Exception;
	public <T extends JPASupport> T findEntityById(Class<T> tClazz, Long id) throws Exception;
	public <T extends JPASupport> List<T> findEntities(Class<T> tClazz) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByIdArray(Class<T> tClazz, Long[] ids) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesWithOrderBy(Class<T> tClazz, LinkedHashMap<String, OrderByEnum> orderByMap) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByFilterMap(Class<T> tClazz, LinkedHashMap<String, ?> filterMap) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByFilterMapPage(final Class<T> tClazz, LinkedHashMap<String, ?> filterMap, int start, int limit) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByFilterMapWithOrderBy(final Class<T> tClazz, LinkedHashMap<String, ?> filterMap, LinkedHashMap<String, OrderByEnum> orderByMap) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByFilterMapWithOrderByPage(final Class<T> tClazz, LinkedHashMap<String, ?> filterMap, LinkedHashMap<String, OrderByEnum> orderByMap, int start, int limit) throws Exception;
	public <T extends JPASupport> T findEntityByFilterMap(Class<T> tClazz, LinkedHashMap<String, ?> filterMap) throws Exception;
	public <T extends JPASupport> T findEntityByFilterMapCaseInsensitive(Class<T> tClazz, LinkedHashMap<String, ?> filterMap) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByFilterMapInstr(Class<T> tClazz, LinkedHashMap<String, ?> filterMap) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByFilterMapLike(Class<T> tClazz, LinkedHashMap<String, ?> filterMap) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByFilterMapCaseInsensitive(Class<T> tClazz, LinkedHashMap<String, ?> filterMap) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByAnyOne(Class<T> tClazz, LinkedHashMap<String, ?> anyOneMap) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByAnyOneInstr(Class<T> tClazz, LinkedHashMap<String, ?> anyOneMap) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByAnyOneLike(Class<T> tClazz, LinkedHashMap<String, ?> anyOneMap) throws Exception;
	public <T extends JPASupport> List<T> findEntitiesByAnyOneCaseInsensitive(Class<T> tClazz, LinkedHashMap<String, ?> anyOneMap) throws Exception;
	public <T extends JPASupport> int deleteEntityById(Class<T> tClazz, Long id) throws Exception;
	public <T extends JPASupport> int deleteEntitiesByIdArray(Class<T> tClazz, Long[] ids) throws Exception;
	public <T extends JPASupport> int deleteAllEntities(Class<T> tClazz) throws Exception;
	public <T extends JPASupport> int deleteEntity(T t) throws Exception;
	public <T extends JPASupport> int[] deleteEntitiesBatch(List<T> tList) throws Exception;
	public <T extends JPASupport> int deleteEntitiesByFilterMap(Class<T> tClazz, LinkedHashMap<String, ?> filterMap) throws Exception;
	public int updateBySql(String sql, Map<String, ?> paramMap) throws Exception;
	public int updateBySql(String sql) throws Exception;
	public <T> List<T> queryForObjectList(String sql, Map<String, ?> paramMap, Class<T> clazz) throws Exception;
	public <T> List<T> queryForObjectList(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws Exception;
	public List<Map<String, Object>> queryForMapList(String sql, Map<String, ?> paramMap) throws Exception;
	public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> clazz) throws Exception;
	public <T> T queryForObject(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws Exception;
	public Map<String, Object>  queryForMap(String sql, Map<String, ?> paramMap) throws Exception;
	public long  queryForLong(String sql, Map<String, ?> paramMap) throws Exception;
	public int   queryForInt(String sql, Map<String, ?> paramMap) throws Exception;
	public void  commitChanges() throws Exception;
	///mybaties 操作
	public <T>     T         mybatiesSelectOne(String sqlMapperId) throws Exception;
	public <T,P>   T         mybatiesSelectOne(String sqlMapperId, P parameter) throws Exception;
	public <T>     int       mybatiesUpdate(String sqlMapperId) throws Exception;
	public <P>     int       mybatiesUpdate(String sqlMapperId, P parameter) throws Exception;
	public <T>     List<T>   mybatiesSelectList(String sqlMapperId) throws Exception;
	public <T,P>   List<T>   mybatiesSelectList(String sqlMapperId, P parameter) throws Exception;
	///redis operations
	//释放jedis 连接
//	public void                redisRelease() throws Exception;
	//基本jedis操作
	public String              redisSet(String key, String value, Integer expireSeconds) throws Exception;
	public String              redisSet(byte[] key, byte[] value, Integer expireSeconds) throws Exception;
	public String              redisGet(String key) throws Exception;
	public byte[]              redisGet(byte[] key) throws Exception;
	public Set<String>         redisGetAllKeys(String pattern)  throws Exception;
	public Long                redisExpireBySeconds(String key, Integer expireSeconds) throws Exception;
	public Long                redisExpireBySeconds(byte[] key, Integer expireSeconds) throws Exception;
	public Long                redisDelete(String key) throws Exception;
	public Long                redisDelete(byte[] key) throws Exception;
	public int                 redisDeleteKeysByPattern(String pattern) throws Exception;
	public boolean             redisExistsKey(String key) throws Exception;
	//Map 操作
	public String              redisSetMap(String key, Map<String, String> map, Integer expireSeconds) throws Exception;
	public Long                redisSetMapProperty(String key, String entryKey, String entryValue) throws Exception;
	public Map<String,String>  redisGetMap(String key) throws Exception;
	public String              redisGetMapProperty(String key, String entryKey) throws Exception;
	public List<String>        redisGetMapProperties(String key, String... entryKeys) throws Exception;
	public Long                redisDeleteMapProperty(String key, String... removedEntryKeys) throws Exception;
	public boolean             redisExistsMapEntryKey(String key, String entryKey) throws Exception;
	//list 操作
	public Long                redisSetList(String key, List<String> list, Integer expireSeconds) throws Exception;
	public List<String>        redisGetList(String key) throws Exception;
	public String              redisGetListMemberByIndex(String key, int index) throws Exception;
	public Long                redisDeleteListMember(String key, String... removedListMembers) throws Exception;
	public boolean             redisExistsListMember(String key, String member) throws Exception;
	//Set操作                           
	public Long                redisSetSet(String key, Set<String> set, Integer expireSeconds) throws Exception;
	public Long                redisSetAdd(String key, String... members) throws Exception;
	public Set<String>         redisGetSet(String key) throws Exception;
	public Long                redisDeleteSetMember(String key, String... removedSetMembers) throws Exception;
	public boolean             redisExistsSetMember(String key, String member) throws Exception;
	public Long			       redisIncr(String key)throws Exception;
}
