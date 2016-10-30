/*
 * created by tracywindy 2014-03-04
 * version 1.0
 */

package cn.com.yibin.maomi.core.tx.service.impl;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import cn.com.yibin.maomi.core.constant.OrderByEnum;
import cn.com.yibin.maomi.core.jpa.JPASupport;
import cn.com.yibin.maomi.core.repository.dao.RepositoryDao;
import cn.com.yibin.maomi.core.tx.service.TxService;
import cn.com.yibin.maomi.core.util.WebUtil;

/**
 * @author Administrator
 * 
 */
public abstract class AbstractTxServiceImpl implements TxService{
	private   RepositoryDao repositoryDao;
	protected RepositoryDao getRepositoryDao() throws Exception {
		if(null == repositoryDao){
			repositoryDao = (RepositoryDao) WebUtil.getRepositoryDao();
		}
		return repositoryDao;
	}
    @Override
	public <T extends JPASupport> Long saveEntity(T t) throws Exception {
		return getRepositoryDao().saveEntity(t);
	}
	@Override
	public <T extends JPASupport> Long saveOrModifyEntity(T t) throws Exception {
		return getRepositoryDao().saveOrModifyEntity(t);
	}
	@Override
	public <T extends JPASupport> int[] saveEntitiesBatch(List<T> tList)
			throws Exception {
		return getRepositoryDao().saveEntitiesBatch(tList);
	}
	@Override
	public <T extends JPASupport> int[] saveOrModifyEntitiesBatch(List<T> tList)
			throws Exception {
		return getRepositoryDao().saveEntitiesBatch(tList);
	}
	@Override
	public <T extends JPASupport> Long modifyEntity(T t) throws Exception {
		return getRepositoryDao().modifyEntity(t);
	}
	@Override
	public <T extends JPASupport> int[] modifyEntitiesBatch(List<T> tList)
			throws Exception {
		return getRepositoryDao().modifyEntitiesBatch(tList);
	}
	@Override
	public <T extends JPASupport> T findEntityById(Class<T> tClazz, Long id)
			throws Exception {
		return getRepositoryDao().findEntityById(tClazz,id);
	}
	@Override
	public <T extends JPASupport> List<T> findEntities(Class<T> tClazz)
			throws Exception {
		return getRepositoryDao().findEntities(tClazz);
	}
	@Override
	public <T extends JPASupport> List<T> findEntitiesByIdArray(
			Class<T> tClazz, Long[] ids) throws Exception {
		return getRepositoryDao().findEntitiesByIdArray(tClazz,ids);
	}
	@Override
	public <T extends JPASupport> List<T> findEntitiesWithOrderBy(
			Class<T> tClazz, LinkedHashMap<String, OrderByEnum> orderByMap)
			throws Exception {
		return getRepositoryDao().findEntitiesWithOrderBy(tClazz,orderByMap);
	}
	@Override
	public <T extends JPASupport> List<T> findEntitiesByFilterMap(
			Class<T> tClazz, LinkedHashMap<String, ?> filterMap)
			throws Exception {
		return getRepositoryDao().findEntitiesByFilterMap(tClazz,filterMap);
	}
	@Override
	public <T extends JPASupport> List<T> findEntitiesByFilterMapWithOrderBy(final Class<T> tClazz,LinkedHashMap<String,?> filterMap,LinkedHashMap<String,OrderByEnum> orderByMap) throws Exception{
		return getRepositoryDao().findEntitiesByFilterMapWithOrderBy(tClazz, filterMap, orderByMap);
	}
	@Override
	public <T extends JPASupport> T findEntityByFilterMap(Class<T> tClazz,
			LinkedHashMap<String, ?> filterMap) throws Exception {
		return getRepositoryDao().findEntityByFilterMap(tClazz,filterMap);
	}
	public <T extends JPASupport> List<T> findEntitiesByFilterMapPage(final Class<T> tClazz,LinkedHashMap<String,?> filterMap,int start , int limit) throws Exception{
		return  getRepositoryDao().findEntitiesByFilterMapPage(tClazz, filterMap, start, limit);
	}
	public <T extends JPASupport> List<T> findEntitiesByFilterMapWithOrderByPage(final Class<T> tClazz,LinkedHashMap<String,?> filterMap,LinkedHashMap<String,OrderByEnum> orderByMap,int start , int limit) throws Exception{
		return  getRepositoryDao().findEntitiesByFilterMapWithOrderByPage(tClazz, filterMap, orderByMap, start, limit);
	}
	
	@Override
	public <T extends JPASupport> List<T> findEntitiesByFilterMapInstr(
			Class<T> tClazz, LinkedHashMap<String, ?> filterMap)
			throws Exception {
		return getRepositoryDao().findEntitiesByFilterMapInstr(tClazz,filterMap);
	}
	@Override
	public <T extends JPASupport> List<T> findEntitiesByFilterMapLike(
			Class<T> tClazz, LinkedHashMap<String, ?> filterMap)
			throws Exception {
		return getRepositoryDao().findEntitiesByFilterMapLike(tClazz,filterMap);
	}
	
	public <T extends JPASupport> List<T> findEntitiesByFilterMapCaseInsensitive(
			Class<T> tClazz, LinkedHashMap<String, ?> filterMap)
					throws Exception {
		return getRepositoryDao().findEntitiesByFilterMapCaseInsensitive(tClazz,filterMap);
	}
	@Override
	public <T extends JPASupport> T findEntityByFilterMapCaseInsensitive(Class<T> tClazz,LinkedHashMap<String,?> filterMap) throws Exception{
		return getRepositoryDao().findEntityByFilterMapCaseInsensitive(tClazz,filterMap);
	}
	@Override
	public <T extends JPASupport> List<T> findEntitiesByAnyOne(Class<T> tClazz,
			LinkedHashMap<String, ?> anyOneMap) throws Exception {
		return getRepositoryDao().findEntitiesByAnyOne(tClazz,anyOneMap);
	}
	@Override
	public <T extends JPASupport> List<T> findEntitiesByAnyOneInstr(
			Class<T> tClazz, LinkedHashMap<String, ?> anyOneMap)
			throws Exception {
		return getRepositoryDao().findEntitiesByAnyOneInstr(tClazz,anyOneMap);
	}
	@Override
	public <T extends JPASupport> List<T> findEntitiesByAnyOneLike(
			Class<T> tClazz, LinkedHashMap<String, ?> anyOneMap)
			throws Exception {
		return getRepositoryDao().findEntitiesByAnyOneLike(tClazz,anyOneMap);
	}
	@Override
	public <T extends JPASupport> List<T> findEntitiesByAnyOneCaseInsensitive(
			Class<T> tClazz, LinkedHashMap<String, ?> anyOneMap)
					throws Exception {
		return getRepositoryDao().findEntitiesByAnyOneCaseInsensitive(tClazz,anyOneMap);
	}
	@Override
	public <T extends JPASupport> int deleteEntityById(Class<T> tClazz, Long id)
			throws Exception {
		return getRepositoryDao().deleteEntityById(tClazz,id);
	}
	@Override
	public <T extends JPASupport> int deleteEntitiesByIdArray(Class<T> tClazz,
			Long[] ids) throws Exception {
		return getRepositoryDao().deleteEntitiesByIdArray(tClazz,ids);
	}
	@Override
	public <T extends JPASupport> int deleteAllEntities(Class<T> tClazz)
			throws Exception {
		return getRepositoryDao().deleteAllEntities(tClazz);
	}
	@Override
	public <T extends JPASupport> int deleteEntity(T t) throws Exception {
		return getRepositoryDao().deleteEntity(t);
	}
	@Override
	public <T extends JPASupport> int[] deleteEntitiesBatch(List<T> tList)
			throws Exception {
	    return getRepositoryDao().deleteEntitiesBatch(tList);
	}
	@Override
	public <T extends JPASupport> int deleteEntitiesByFilterMap(
			Class<T> tClazz, LinkedHashMap<String, ?> filterMap)
			throws Exception {
		return getRepositoryDao().deleteEntitiesByFilterMap(tClazz,filterMap);
	}
	@Override
	public int updateBySql(String sql, Map<String, ?> paramMap)
			throws Exception {
		return getRepositoryDao().updateBySql(sql,paramMap);
	}
	@Override
	public int updateBySql(String sql) throws Exception {
		return getRepositoryDao().updateBySql(sql);
	}
	@Override
	public <T> List<T> queryForObjectList(String sql, Map<String, ?> paramMap,
			Class<T> clazz) throws Exception {
		return getRepositoryDao().queryForObjectList(sql,paramMap,clazz);
	}
	@Override
	public <T> List<T> queryForObjectList(String sql, Map<String, ?> paramMap,
			RowMapper<T> rowMapper) throws Exception {
		return getRepositoryDao().queryForObjectList(sql,paramMap,rowMapper);
	}
	@Override
	public List<Map<String, Object>> queryForMapList(String sql,
			Map<String, ?> paramMap) throws Exception {
		return getRepositoryDao().queryForMapList(sql,paramMap);
	}
	@Override
	public <T> T queryForObject(String sql, Map<String, ?> paramMap,
			Class<T> clazz) throws Exception {
		return getRepositoryDao().queryForObject(sql,paramMap,clazz);
	}
	@Override
	public <T> T queryForObject(String sql, Map<String, ?> paramMap,
			RowMapper<T> rowMapper) throws Exception {
		return getRepositoryDao().queryForObject(sql,paramMap,rowMapper);
	}
	@Override
	public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap)
			throws Exception {
		return getRepositoryDao().queryForMap(sql,paramMap);
	}
	@Override
	public long queryForLong(String sql, Map<String, ?> paramMap)
			throws Exception {
		return getRepositoryDao().queryForLong(sql,paramMap);
	}
	@Override
	public int queryForInt(String sql, Map<String, ?> paramMap)
			throws Exception {
		return getRepositoryDao().queryForInt(sql,paramMap);
	}
	@Override
	public void commitChanges() throws Exception {
		getRepositoryDao().commitChanges();		
	}
//	///redis operations
//	//释放jedis 连接
//	@Override
//	public void                redisRelease() throws Exception{
//		getRepositoryDao().redisRelease();
//	}
	//基本jedis操作
	@Override
	public String              redisSet(String key ,String value,Integer expireSeconds) throws Exception{
		return getRepositoryDao().redisSet(key ,value,expireSeconds);
	}
	@Override
	public Long  redisIncr(String key) throws Exception{
		return getRepositoryDao().redisIncr(key);
	}
	
	@Override
	public String              redisSet(byte[] key ,byte[] value,Integer expireSeconds) throws Exception{
		return getRepositoryDao().redisSet(key ,value,expireSeconds);
	}
	@Override
	public String              redisGet(String key) throws Exception{
		return getRepositoryDao().redisGet(key);
	}
	@Override
	public byte[]              redisGet(byte[] key) throws Exception{
		return getRepositoryDao().redisGet(key);
	}
	@Override
	public Set<String>         redisGetAllKeys(String pattern)  throws Exception{
		return getRepositoryDao().redisGetAllKeys(pattern);
	}
	@Override
	public Long                redisExpireBySeconds(String key,Integer expireSeconds) throws Exception{
		return getRepositoryDao().redisExpireBySeconds(key, expireSeconds);
	}
	@Override
	public Long                redisExpireBySeconds(byte[] key,Integer expireSeconds) throws Exception{
		return getRepositoryDao().redisExpireBySeconds(key, expireSeconds);
	}
	@Override
	public Long                redisDelete(String key) throws Exception{
		return getRepositoryDao().redisDelete(key);
	}
	@Override
	public Long                redisDelete(byte[] key) throws Exception{
		return getRepositoryDao().redisDelete(key);
	}
	@Override
	public int                redisDeleteKeysByPattern(String pattern) throws Exception{
		return getRepositoryDao().redisDeleteKeysByPattern(pattern);
	}
	@Override
	public boolean             redisExistsKey(String key) throws Exception{
		return getRepositoryDao().redisExistsKey(key);
	}
	//Map 操作
	@Override
	public String              redisSetMap(String key ,Map<String,String> map,Integer expireSeconds) throws Exception{
		return getRepositoryDao().redisSetMap(key, map, expireSeconds);
	}
	@Override
	public Long                redisSetMapProperty(String key ,String entryKey,String entryValue) throws Exception{
		return getRepositoryDao().redisSetMapProperty(key, entryKey, entryValue);
	}
	@Override
	public Map<String,String>  redisGetMap(String key) throws Exception{
		return getRepositoryDao().redisGetMap(key);
	}
	@Override
	public String              redisGetMapProperty(String key,String entryKey) throws Exception{
		return getRepositoryDao().redisGetMapProperty(key, entryKey);
	}
	@Override
	public List<String>        redisGetMapProperties(String key,String... entryKeys) throws Exception{
		return getRepositoryDao().redisGetMapProperties(key, entryKeys);
	}
	@Override
	public Long                redisDeleteMapProperty(String key ,String... removedEntryKeys) throws Exception{
		return getRepositoryDao().redisDeleteMapProperty(key, removedEntryKeys);
	}
	@Override
	public boolean             redisExistsMapEntryKey(String key ,String entryKey) throws Exception{
		return getRepositoryDao().redisExistsMapEntryKey(key, entryKey);
	}
	//list 操作
	@Override
	public Long                redisSetList(String key ,List<String> list,Integer expireSeconds) throws Exception{
		return getRepositoryDao().redisSetList(key, list, expireSeconds);
	}
	@Override
	public List<String>        redisGetList(String key) throws Exception{
		return getRepositoryDao().redisGetList(key);
	}
	@Override
	public String              redisGetListMemberByIndex(String key,int index) throws Exception{
		return getRepositoryDao().redisGetListMemberByIndex(key, index);
	}
	@Override
	public Long                redisDeleteListMember(String key,String... removedListMembers) throws Exception{
		return getRepositoryDao().redisDeleteListMember(key, removedListMembers);
	}
	@Override
	public boolean             redisExistsListMember(String key,String member) throws Exception{
		return getRepositoryDao().redisExistsListMember(key, member);
	}
	//Set操作                           
	@Override
	public Long                redisSetSet(String key ,Set<String> set,Integer expireSeconds) throws Exception{
		return getRepositoryDao().redisSetSet(key, set, expireSeconds);
	}
	@Override
	public Set<String>         redisGetSet(String key) throws Exception{
		return getRepositoryDao().redisGetSet(key);
	}
	@Override
	public Long                redisDeleteSetMember(String key,String... removedSetMembers) throws Exception{
		return getRepositoryDao().redisDeleteSetMember(key, removedSetMembers);
	}
	@Override
	public boolean             redisExistsSetMember(String key,String member) throws Exception{
		return getRepositoryDao().redisExistsSetMember(key, member);
	}
	@Override
	public Long                redisSetAdd(String key ,String... members) throws Exception{
		return getRepositoryDao().redisSetAdd(key, members);
	}
	@Override
	public <T>     T         mybatiesSelectOne(String sqlMapperId) throws Exception{
		return getRepositoryDao().mybatiesSelectOne(sqlMapperId);
	}
	@Override
	public <T,P>   T         mybatiesSelectOne(String sqlMapperId,P parameter) throws Exception{
		return getRepositoryDao().mybatiesSelectOne(sqlMapperId, parameter);
	}
	@Override
	public <T>     int       mybatiesUpdate(String sqlMapperId) throws Exception{
		return getRepositoryDao().mybatiesUpdate(sqlMapperId);
	}
	@Override
	public <P>     int       mybatiesUpdate(String sqlMapperId,P parameter) throws Exception{
		return getRepositoryDao().mybatiesUpdate(sqlMapperId, parameter);
	}
	@Override
	public <T>     List<T>   mybatiesSelectList(String sqlMapperId) throws Exception{
		return getRepositoryDao().mybatiesSelectList(sqlMapperId);
	}
	@Override
	public <T,P>   List<T>   mybatiesSelectList(String sqlMapperId,P parameter) throws Exception{
		return getRepositoryDao().mybatiesSelectList(sqlMapperId, parameter);
	}
}
