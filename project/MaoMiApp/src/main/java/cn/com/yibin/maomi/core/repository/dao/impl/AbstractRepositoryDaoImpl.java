package cn.com.yibin.maomi.core.repository.dao.impl;
/*
 * created by zxd 2015-12-23
 * version 0
 */

import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import cn.com.yibin.maomi.cache.redis.RedisClientDao;
import cn.com.yibin.maomi.core.constant.ConditionEnum;
import cn.com.yibin.maomi.core.constant.DBQueryEnum;
import cn.com.yibin.maomi.core.constant.DBUpdateTypeEnum;
import cn.com.yibin.maomi.core.constant.OrderByEnum;
import cn.com.yibin.maomi.core.jpa.JPASupport;
import cn.com.yibin.maomi.core.repository.dao.RepositoryDao;
import cn.com.yibin.maomi.core.repository.dao.SQLGenerator;
import cn.com.yibin.maomi.core.util.ReflectionUtil;
import cn.com.yibin.maomi.core.util.StringUtil;
import cn.com.yibin.maomi.core.util.WebUtil;
/**
 * @author Administrator
 */
public abstract class AbstractRepositoryDaoImpl implements RepositoryDao {
	
	private  NamedParameterJdbcTemplate     namedParameterJdbcTemplate;
	private  SqlSessionTemplate             sqlSessionTemplate;
	private  RedisClientDao                 redisClientDao;
    
	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() throws Exception {
		if(null == namedParameterJdbcTemplate){
			namedParameterJdbcTemplate = (NamedParameterJdbcTemplate) WebUtil.getApplicationContext().getBean("namedParameterJdbcTemplate");
		}
		return namedParameterJdbcTemplate;
	}
	protected SqlSessionTemplate getSqlSessionTemplate() throws Exception {
		if(null == sqlSessionTemplate){
			sqlSessionTemplate = (SqlSessionTemplate) WebUtil.getApplicationContext().getBean("sqlSessionTemplate");
		}
		return sqlSessionTemplate;
    }
//	protected ShardedJedisTemplate getShardedJedisTemplate() throws Exception {
//		if(null == shardedJedisTemplate){
//			shardedJedisTemplate = (ShardedJedisTemplate) WebUtil.getApplicationContext().getBean("shardedJedisTemplate");
//		}
//		return shardedJedisTemplate;
//	}
	protected RedisClientDao getShardedJedisTemplate() throws Exception {
		if(null == redisClientDao){
			redisClientDao = (RedisClientDao) WebUtil.getApplicationContext().getBean("redisClientDao");
		}
		return redisClientDao;
	}
   private <T  extends JPASupport> Long getSeqNextVal(Class<T> tClazz) throws Exception {
	    String className = tClazz.getName();
		String seq = JPASupport.getClassTableSequenceNameCachedMap().get(className);
		String sql = SQLGenerator.sqlFetchSeqNextVal(seq);
		HashMap<String, String> hm = null;
		Long nextval = getNamedParameterJdbcTemplate().queryForObject(sql,hm, Long.class);
		return nextval;
	}
   @SuppressWarnings("unchecked")
   private <T extends JPASupport> Long updateEntity(T t,DBUpdateTypeEnum updateType) throws Exception{
		 Class<T>     tClazz    = (Class<T>)t.getClass();
    	 String className = tClazz.getName();
    	 String updatePreparedSql = null;
    	 SqlParameterSource paramSource = null;
    	 String fieldPKName       = null;
    	 KeyHolder 	keyHolder	  = null; 
       	 if(DBUpdateTypeEnum.INSERTORMODIFY.equals(updateType)){
       			fieldPKName = JPASupport.getClassTableFieldPKNameCachedMap().get(className);
       			Object fieldPKValue = ReflectionUtil.invokeGetterMethod(t, fieldPKName);
       			if(null == fieldPKValue){
       				updateType = DBUpdateTypeEnum.INSERT;
       			}else{
       				updateType = DBUpdateTypeEnum.MODIFY;
       			}
       	 }
    	 switch(updateType){
	    	 case INSERT:{
	    		 if(null == fieldPKName){
	    			 fieldPKName = JPASupport.getClassTableFieldPKNameCachedMap().get(className);
	    		 }
//	        	 Long   fieldPKValue      = getSeqNextVal(tClazz);
//	        	 ReflectionUtil.invokeSetterMethod(t, fieldPKName, fieldPKValue);
	        	 updatePreparedSql = JPASupport.getClassTableInsertPreparedSqlCachedMap().get(className);
	        	 paramSource 	   = new BeanPropertySqlParameterSource(t);
	        	 keyHolder   	   = new GeneratedKeyHolder();
	        	 break;
	    	 }
	    	 case MODIFY:{
	    		 updatePreparedSql =  JPASupport.getClassTableUpdatePreparedSqlCachedMap().get(className);
	    		 paramSource = new BeanPropertySqlParameterSource(t);
	    		 break;
	    	 }
	    	 case DELETE:{
	    		 String fieldPkName       = JPASupport.getClassTableFieldPKNameCachedMap().get(className);
	    		 String columnPkName      = JPASupport.getClassTableColumnPKNameCachedMap().get(className);
	    		 String tableName         = JPASupport.getClassTableNameCachedMap().get(className);
	    		 Object idValue           = ReflectionUtil.invokeGetterMethod(t, fieldPkName);
	    		 paramSource = new MapSqlParameterSource().addValue(fieldPkName,idValue);
	    		 updatePreparedSql = SQLGenerator.sqlDeleteOneById(false, tableName, columnPkName, fieldPkName);
	    		 break;
	    	 }
	    	 default:{
	    		 
	    	 }
    	 }
    	 if(keyHolder == null){
    		 int returnI = getNamedParameterJdbcTemplate().update(updatePreparedSql, paramSource);
    		 return (long)returnI;
    	 }else{
    		 getNamedParameterJdbcTemplate().update(updatePreparedSql, paramSource,keyHolder);
    		 if(keyHolder.getKey() == null ) return 0l;
    		 Long 		id 			= keyHolder.getKey().longValue();
    		 ReflectionUtil.invokeSetterMethod(t, fieldPKName, id);
    		 return id;
    	 }
    }
   @SuppressWarnings("unchecked")
   private <T extends JPASupport> int[] updateEntitiesBatch(List<T> tList,DBUpdateTypeEnum updateType) throws Exception{
     if((null == tList) || (tList.isEmpty())){
    	 return new int[]{};
     }
     Class<T>     tClazz    = null;
     String       className = null; 
     int tListLen = tList.size();
     SqlParameterSource[] paramSourceArray = new SqlParameterSource[tListLen];
     String updatePreparedSql = "";
   	 for(int i= 0;i < tListLen; i++ ){
   		 T   t 		   = tList.get(i);
   		 if(0 == i ){
   			 tClazz    = (Class<T>)t.getClass();
   			 className = tClazz.getName();
   		}
   		SqlParameterSource paramSource = null;
   		String fieldPKName       = null;
   		if(DBUpdateTypeEnum.INSERTORMODIFY.equals(updateType)){
   			fieldPKName = JPASupport.getClassTableFieldPKNameCachedMap().get(className);
   			Object fieldPKValue = ReflectionUtil.invokeGetterMethod(t, fieldPKName);
   			if(null == fieldPKValue){
   				updateType = DBUpdateTypeEnum.INSERT;
   			}else{
   				updateType = DBUpdateTypeEnum.MODIFY;
   			}
   		}
    	switch(updateType){
	    	 case INSERT:{
	    		 if(null == fieldPKName){
	    			    fieldPKName       = JPASupport.getClassTableFieldPKNameCachedMap().get(className);
	    		 }
	        	 Long   fieldPKValue      = getSeqNextVal(tClazz);
	        	 ReflectionUtil.invokeSetterMethod(t, fieldPKName, fieldPKValue);
	        	 if(0 == i){
	        		    updatePreparedSql =  JPASupport.getClassTableInsertPreparedSqlCachedMap().get(className);
	        	 }
	        	 		paramSource		  = new BeanPropertySqlParameterSource(t);
	    		break;
	    	 }
	    	 case MODIFY:{
	        	 if(0 == i){
	        		    updatePreparedSql =  JPASupport.getClassTableUpdatePreparedSqlCachedMap().get(className);
	        	 }
	        	 		paramSource		  = new BeanPropertySqlParameterSource(t);
	    		 break;
	    	 }
	    	 case DELETE:{
	    		 String fieldPkName       = JPASupport.getClassTableFieldPKNameCachedMap().get(className);
	    		 String columnPkName      = JPASupport.getClassTableColumnPKNameCachedMap().get(className);
	    		 String tableName         = JPASupport.getClassTableNameCachedMap().get(className);
	    		 Object idValue           = ReflectionUtil.invokeGetterMethod(t, fieldPkName);
	    		 		paramSource       = new MapSqlParameterSource().addValue(fieldPkName,idValue);
	    		 		updatePreparedSql = SQLGenerator.sqlDeleteOneById(false, tableName, columnPkName, fieldPkName);
	    		 break;
	    	 }
	    	 default:{
	    		 break;
	    	 }
	    }
   		paramSourceArray[i] = paramSource;
   	 }
   	 return getNamedParameterJdbcTemplate().batchUpdate(updatePreparedSql, paramSourceArray);
   }
   private <T extends JPASupport> T wrapEntityByResultSet(Class<T> tClazz,ResultSet rs) throws Exception{
	    T      				    tInstance                   = tClazz.newInstance();
	    String				    className 					= tClazz.getName();
	    Map<String,String>	    columnFieldMap				= JPASupport.getClassColumnFieldCachedMap().get(className);
		ResultSetMetaData 	    rsmd 						= rs.getMetaData();
		for(int i = 1 ;i<= rsmd.getColumnCount();i++){
			String 				columnName     				= StringUtil.emptyOpt(rsmd.getColumnName(i).toUpperCase(), rsmd.getColumnLabel(i).toUpperCase());
			if(filterColumnName(columnName)){
				continue; 
			}
			String 				fieldName     			    = columnFieldMap.get(columnName);
			String 				columnTypeName 				= rsmd.getColumnTypeName(i).toUpperCase();
			Object 				value          				= null;
			switch(columnTypeName){
			   case "BLOB":
			   case "IMAGE":{
				   value          = rs.getBytes(i);
				   break;
			   }
			   case "CLOB":
			   case "TEXT":
			   case "LONGTEXT":{
				    Reader reader = null;
	    			try{
		    			   reader = rs.getCharacterStream(i);
			    		   if(null != reader  ){
					    		StringBuilder result = new StringBuilder( 4096 );
					    		char[] charbuf = new char[4096];
					    		for ( int ii = reader.read( charbuf ); ii > 0 ; ii = reader.read( charbuf ) ) 
					    		{
					    		   result.append( charbuf, 0, ii );
					    		}
					    		value = result.toString();
			    		   }
	    			}finally{
	    				if(null != reader){
	    					reader.close();
	    				}
	    			}
				   break;
			   }
			   case "DATE":{
				   value     = rs.getDate(i);
				   break;
			   }
			   case "TIME":
			   case "DATETIME":{
				   value     = rs.getTime(i);
				   break;
			   }
			   case "TIMESTAMP":
			   case "TIMESTAMP(6)":{
				   value     = rs.getTimestamp(i);
				   break;
			   }
			   default:{
				   value     = rs.getObject(i);
				   break;
			   }
			}
			ReflectionUtil.invokeSetterMethod(tInstance, fieldName, value);
		}
		return tInstance;
   }
   private boolean filterColumnName(String filterColumnName){
	   boolean returnFlag  = false;
	   switch(filterColumnName){
	   case  "RN_COLUMN":
	   {
		   returnFlag = true;
		   break; 
	   }
	   }
	   return returnFlag;
   }
   @Override
   public <T extends JPASupport> Long saveEntity(T t) throws Exception{
    	return updateEntity(t,DBUpdateTypeEnum.INSERT);
   }
   @Override
   public <T extends JPASupport> Long saveOrModifyEntity(T t) throws Exception{
	   return updateEntity(t,DBUpdateTypeEnum.INSERTORMODIFY);
   }
   @Override
   public <T extends JPASupport> int[] saveEntitiesBatch(List<T> tList) throws Exception{
	   return updateEntitiesBatch(tList,DBUpdateTypeEnum.INSERT);
   }
   @Override
   public <T extends JPASupport> int[] saveOrModifyEntitiesBatch(List<T> tList) throws Exception{
	   return updateEntitiesBatch(tList,DBUpdateTypeEnum.INSERTORMODIFY);
	   
   }
   public <T extends JPASupport> Long modifyEntity(T t) throws Exception{
	   return updateEntity(t,DBUpdateTypeEnum.MODIFY);
   }
   @Override
   public <T extends JPASupport> int[] modifyEntitiesBatch(List<T> tList) throws Exception{
	  return updateEntitiesBatch(tList,DBUpdateTypeEnum.MODIFY);
   }
   @Override
   public <T extends JPASupport> T findEntityById(final Class<T> tClazz,Long id) throws Exception{
	   
	   String       	className    		= tClazz.getName();
	   String       	tableName    		= JPASupport.getClassTableNameCachedMap().get(className);
	   String       	columnPkName 		= JPASupport.getClassTableColumnPKNameCachedMap().get(className);
	   String       	fieldPkName  		= JPASupport.getClassTableFieldPKNameCachedMap().get(className);
	   String       	columnFieldAliasSql = JPASupport.getClassTableColumnFieldAliasSqlCachedMap().get(className);
	   
	   String 		    sql 				= SQLGenerator.sqlFindOneById(false,columnFieldAliasSql, tableName, columnPkName, fieldPkName);
	   Map<String,Long> paramMap 			= new HashMap<String,Long>();
	   paramMap.put(fieldPkName, id);
	   List<T> list = getNamedParameterJdbcTemplate().query(sql,paramMap,new RowMapper<T>(){
			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				try {
					return wrapEntityByResultSet(tClazz,rs);
				} catch (Exception e) {
					e.printStackTrace();
					throw new SQLException("wrap Entity by ResultSet Exception");
				}
			}
		    
	   });
	   if(list.size()>0){
		   return list.get(0);
	   }
	  return null;
   }
   @Override
   public <T extends JPASupport> List<T> findEntities(final Class<T> tClazz) throws Exception
   {
	   String 			className 			= tClazz.getName();
	   String       	tableName 			= JPASupport.getClassTableNameCachedMap().get(className);
	   String       	columnFieldAliasSql = JPASupport.getClassTableColumnFieldAliasSqlCachedMap().get(className);
	   String 			findAllSql  		= SQLGenerator.sqlFindAll(columnFieldAliasSql, tableName);
	   Map<String,Long> paramMap 			= null;
	   return getNamedParameterJdbcTemplate().query(findAllSql,paramMap,new RowMapper<T>(){
			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				try {
					return wrapEntityByResultSet(tClazz,rs);
				} catch (Exception e) {
					e.printStackTrace();
					throw new SQLException("wrap Entity by ResultSet Exception");
				}
			}
	   });
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByIdArray(final Class<T> tClazz,Long[] ids) throws Exception
   {
	   String 		className 			 = tClazz.getName();
	   String       tableName 			 = JPASupport.getClassTableNameCachedMap().get(className);
	   String       columnPkName 		 = JPASupport.getClassTableColumnPKNameCachedMap().get(className);
	   String       fieldPkName  		 = JPASupport.getClassTableFieldPKNameCachedMap().get(className);
	   String       columnFieldAliasSql  = JPASupport.getClassTableColumnFieldAliasSqlCachedMap().get(className);
	   int 			idsLen 				 = ids.length;
	   String  		sql 				 = SQLGenerator.sqlFindByIDArray(columnFieldAliasSql,tableName, columnPkName, fieldPkName, idsLen);
	   MapSqlParameterSource paramSource = new MapSqlParameterSource();
	   for(int i =0;i<idsLen;i++){
		   paramSource.addValue(fieldPkName+(1+i),ids[i]);
	   }
	   
	   return getNamedParameterJdbcTemplate().query(sql,paramSource,new RowMapper<T>(){
			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				try {
					return wrapEntityByResultSet(tClazz,rs);
				} catch (Exception e) {
					e.printStackTrace();
					throw new SQLException("wrap Entity by ResultSet Exception");
				}
			}
		    
	   });
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesWithOrderBy(final Class<T> tClazz,LinkedHashMap<String,OrderByEnum> orderByMap) throws Exception
   {
	   return this.findEntitiesByFilterMapWithOrderBy(tClazz, null, orderByMap);
   }
   
   private <T extends JPASupport> List<T> findEntitiesByConditonMap(final Class<T> tClazz,LinkedHashMap<String,?> filterMap,ConditionEnum conditionEmun,DBQueryEnum dbQueryEnum) throws Exception
   {
	   String 				className 			 = tClazz.getName();
	   String       		tableName 			 = JPASupport.getClassTableNameCachedMap().get(className);
	   String       		columnFieldAliasSql  = JPASupport.getClassTableColumnFieldAliasSqlCachedMap().get(className);
	   Map<String,String>   fieldColumnMap 		 = JPASupport.getClassFieldColumnCachedMap().get(className);
	   final String 		filterSql 			 = SQLGenerator.sqlFindByFilter(columnFieldAliasSql, fieldColumnMap, tableName, filterMap,conditionEmun,dbQueryEnum);
	   return getNamedParameterJdbcTemplate().query(filterSql, filterMap,new RowMapper<T>(){
			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				try {
					return wrapEntityByResultSet(tClazz,rs);
				} catch (Exception e) {
					e.printStackTrace();
					throw new SQLException("wrap Entity by ResultSet Exception");
				}
			}
		    
	   });
   }
   private <T extends JPASupport> List<T> findEntitiesByConditonMapPage(final Class<T> tClazz,LinkedHashMap<String,?> filterMap,ConditionEnum conditionEmun,DBQueryEnum dbQueryEnum,int start , int limit) throws Exception
   {
	   String 				className 			 = tClazz.getName();
	   String       		tableName 			 = JPASupport.getClassTableNameCachedMap().get(className);
	   String       		columnFieldAliasSql  = JPASupport.getClassTableColumnFieldAliasSqlCachedMap().get(className);
	   Map<String,String>   fieldColumnMap 		 = JPASupport.getClassFieldColumnCachedMap().get(className);
	   final String 		filterSql 			 = SQLGenerator.sqlFindByFilterPage(columnFieldAliasSql, fieldColumnMap, tableName, filterMap,conditionEmun,dbQueryEnum,start,limit);
	   return getNamedParameterJdbcTemplate().query(filterSql, filterMap,new RowMapper<T>(){
		   @Override
		   public T mapRow(ResultSet rs, int rowNum) throws SQLException {
			   try {
				   return wrapEntityByResultSet(tClazz,rs);
			   } catch (Exception e) {
				   e.printStackTrace();
				   throw new SQLException("wrap Entity by ResultSet Exception");
			   }
		   }
	   });
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByFilterMap(Class<T> tClazz,LinkedHashMap<String,?> filterMap) throws Exception{
	   return findEntitiesByConditonMap(tClazz,filterMap,ConditionEnum.AND,DBQueryEnum.EQUALS);
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByFilterMapPage(final Class<T> tClazz,LinkedHashMap<String,?> filterMap,int start , int limit) throws Exception{
	   return findEntitiesByConditonMapPage(tClazz,filterMap,ConditionEnum.AND,DBQueryEnum.EQUALS,start,limit);
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByFilterMapWithOrderByPage(final Class<T> tClazz,LinkedHashMap<String,?> filterMap,LinkedHashMap<String,OrderByEnum> orderByMap,int start , int limit) throws Exception{
	   String 				className 			 	= tClazz.getName();
	   String       		tableName 			 	= JPASupport.getClassTableNameCachedMap().get(className);
	   String       		columnFieldAliasSql  	= JPASupport.getClassTableColumnFieldAliasSqlCachedMap().get(className);
	   Map<String,String>   fieldColumnMap 			= JPASupport.getClassFieldColumnCachedMap().get(className);
	   String 				findAllSqlAndOrderBy  	= SQLGenerator.sqlFindByFilterAndOrderWithPage(columnFieldAliasSql, fieldColumnMap, tableName, filterMap, orderByMap,ConditionEnum.AND,DBQueryEnum.EQUALS,start,limit);
	   return getNamedParameterJdbcTemplate().query(findAllSqlAndOrderBy,filterMap,new RowMapper<T>(){
			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				try {
					return wrapEntityByResultSet(tClazz,rs);
				} catch (Exception e) {
					e.printStackTrace();
					throw new SQLException("wrap Entity by ResultSet Exception");
				}
			}
	   });
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByFilterMapWithOrderBy(final Class<T> tClazz,LinkedHashMap<String,?> filterMap,LinkedHashMap<String,OrderByEnum> orderByMap) throws Exception
   {
	   String 				className 			 	= tClazz.getName();
	   String       		tableName 			 	= JPASupport.getClassTableNameCachedMap().get(className);
	   String       		columnFieldAliasSql  	= JPASupport.getClassTableColumnFieldAliasSqlCachedMap().get(className);
	   Map<String,String>   fieldColumnMap 			= JPASupport.getClassFieldColumnCachedMap().get(className);
	   String 				findAllSqlAndOrderBy  	= SQLGenerator.sqlFindByFilterAndOrder(columnFieldAliasSql, fieldColumnMap, tableName, filterMap, orderByMap,ConditionEnum.AND,DBQueryEnum.EQUALS);
	   return getNamedParameterJdbcTemplate().query(findAllSqlAndOrderBy,filterMap,new RowMapper<T>(){
			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				try {
					return wrapEntityByResultSet(tClazz,rs);
				} catch (Exception e) {
					e.printStackTrace();
					throw new SQLException("wrap Entity by ResultSet Exception");
				}
			}
	   });
   }
   
   @Override
   public <T extends JPASupport> T findEntityByFilterMap(Class<T> tClazz,LinkedHashMap<String,?> filterMap) throws Exception{
      List<T> l = findEntitiesByFilterMap(tClazz,filterMap);
      if(0 < l.size()){
    	  return l.get(0);
      }
      return null;
   }
   @Override
   public <T extends JPASupport> T findEntityByFilterMapCaseInsensitive(Class<T> tClazz,LinkedHashMap<String,?> filterMap) throws Exception{
	   List<T> l = findEntitiesByFilterMapCaseInsensitive(tClazz,filterMap);
	   if(0 < l.size()){
		   return l.get(0);
	   }
	   return null;
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByFilterMapInstr(Class<T> tClazz,LinkedHashMap<String,?> filterMap) throws Exception{
	   return findEntitiesByConditonMap(tClazz,filterMap,ConditionEnum.AND,DBQueryEnum.LIKE);
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByFilterMapLike(Class<T> tClazz,LinkedHashMap<String,?> filterMap) throws Exception{
	   return findEntitiesByConditonMap(tClazz,filterMap,ConditionEnum.AND,DBQueryEnum.LIKE);
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByFilterMapCaseInsensitive(Class<T> tClazz,LinkedHashMap<String,?> filterMap) throws Exception{
	   return findEntitiesByConditonMap(tClazz,filterMap,ConditionEnum.AND,DBQueryEnum.CASE_INSENSITIVE);
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByAnyOne(Class<T> tClazz,LinkedHashMap<String,?> anyOneMap) throws Exception{
	   return findEntitiesByConditonMap(tClazz,anyOneMap,ConditionEnum.OR,DBQueryEnum.EQUALS);
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByAnyOneLike(Class<T> tClazz,LinkedHashMap<String,?> anyOneMap) throws Exception{
	   return findEntitiesByConditonMap(tClazz,anyOneMap,ConditionEnum.OR,DBQueryEnum.LIKE);
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByAnyOneInstr(Class<T> tClazz,LinkedHashMap<String,?> anyOneMap) throws Exception{
	   return findEntitiesByConditonMap(tClazz,anyOneMap,ConditionEnum.OR,DBQueryEnum.LIKE);
   }
   @Override
   public <T extends JPASupport> List<T> findEntitiesByAnyOneCaseInsensitive(Class<T> tClazz,LinkedHashMap<String,?> anyOneMap) throws Exception{
	   return findEntitiesByConditonMap(tClazz,anyOneMap,ConditionEnum.OR,DBQueryEnum.CASE_INSENSITIVE);
   }
   @Override
   public <T extends JPASupport> int deleteEntityById(Class<T> tClazz,Long id) throws Exception{
	   String className = tClazz.getName();
	   String       tableName = JPASupport.getClassTableNameCachedMap().get(className);
	   String       columnPkName = JPASupport.getClassTableColumnPKNameCachedMap().get(className);
	   String       fieldPkName  = JPASupport.getClassTableFieldPKNameCachedMap().get(className);
	   String  deleteSql = SQLGenerator.sqlDeleteOneById(false,tableName, columnPkName, fieldPkName);
	   Map<String,Long> paramMap = new HashMap<String,Long>();
	   paramMap.put(fieldPkName, id);
	   return getNamedParameterJdbcTemplate().update(deleteSql, paramMap);
   }
   @Override
   public <T extends JPASupport> int deleteEntitiesByIdArray(Class<T> tClazz,Long[] ids) throws Exception{
	   String className = tClazz.getName();
	   String       tableName = JPASupport.getClassTableNameCachedMap().get(className);
	   String       columnPkName = JPASupport.getClassTableColumnPKNameCachedMap().get(className);
	   String       fieldPkName  = JPASupport.getClassTableFieldPKNameCachedMap().get(className);
	   int idsLen = ids.length;
	   String  deleteSql = SQLGenerator.sqlDeleteByIDArray(tableName, columnPkName, fieldPkName, idsLen);
	   MapSqlParameterSource paramSource = new MapSqlParameterSource();
	   for(int i =0;i<idsLen;i++){
		   paramSource.addValue(fieldPkName+(1+i),ids[i]);
	   }
	   
	   return getNamedParameterJdbcTemplate().update(deleteSql, paramSource);
   }
   @Override
   public <T extends JPASupport> int deleteAllEntities(Class<T> tClazz) throws Exception{
	   String className = tClazz.getName();
	   String       tableName = JPASupport.getClassTableNameCachedMap().get(className);
	   String  deleteAllSql = SQLGenerator.sqlDeleteAll(tableName);
	   Map<String,Long> paramMap = null;
	   return getNamedParameterJdbcTemplate().update(deleteAllSql, paramMap);
   }
   @Override
   @SuppressWarnings("unchecked")
   public <T extends JPASupport> int deleteEntity(T t) throws Exception{
	   Class<T> tClazz = (Class<T>)t.getClass();
	   String className = tClazz.getName();
	   String   fieldPkName  = JPASupport.getClassTableFieldPKNameCachedMap().get(className);
	   Long id = (Long) ReflectionUtil.invokeGetterMethod(t, fieldPkName);
	   return deleteEntityById(tClazz, id);
   }
   @Override
   public <T extends JPASupport> int[] deleteEntitiesBatch(List<T> tList) throws Exception{
	   return updateEntitiesBatch(tList, DBUpdateTypeEnum.DELETE);
   }
   @Override
   public <T extends JPASupport> int deleteEntitiesByFilterMap(Class<T> tClazz,LinkedHashMap<String,?> filterMap) throws Exception{
	   String className = tClazz.getName();
	   String       tableName = JPASupport.getClassTableNameCachedMap().get(className);
	   Map<String,String> fieldColumnMap = JPASupport.getClassFieldColumnCachedMap().get(className);
	   String filterSql = SQLGenerator.sqlDeleteByFilter(fieldColumnMap, tableName, filterMap,ConditionEnum.AND,DBQueryEnum.EQUALS);
	   return getNamedParameterJdbcTemplate().update(filterSql, filterMap);
   }
   @Override
   public int updateBySql(String sql,Map<String,?> paramMap) throws Exception{
	   return getNamedParameterJdbcTemplate().update(sql, paramMap);
   }
   @Override
   public int updateBySql(String sql) throws Exception{
	   return updateBySql(sql,null);
   }
   @Override
   public <T> List<T> queryForObjectList(String sql ,Map<String,?> paramMap, Class<T> clazz) throws Exception{
	   return  getNamedParameterJdbcTemplate().queryForList(sql, paramMap, clazz);
   }
   @Override
   public List<Map<String, Object>> queryForMapList(String sql ,Map<String,?> paramMap) throws Exception{
	   return   getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
   }
   @Override
   public <T> T queryForObject(String sql ,Map<String,?> paramMap, Class<T> clazz) throws Exception{
	   return  getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, clazz);
   }
   @Override
   public Map<String, Object>  queryForMap(String sql ,Map<String,?> paramMap) throws Exception{
	   return  getNamedParameterJdbcTemplate().queryForMap(sql, paramMap);
   }
   @Override
   public long  queryForLong(String sql ,Map<String,?> paramMap) throws Exception{
//	   return  getNamedParameterJdbcTemplate().queryForLong(sql, paramMap);
	   return 0l;
   }
   @Override
   public int  queryForInt(String sql ,Map<String,?> paramMap) throws Exception{
	 //  return  getNamedParameterJdbcTemplate().queryForInt(sql, paramMap);
	   return 0;
   }
   @Override
   public <T> T queryForObject(String sql ,Map<String,?> paramMap,RowMapper<T> rowMapper) throws Exception{
	   return  getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, rowMapper);
   }
   @Override
   public <T> List<T> queryForObjectList(String sql ,Map<String,?> paramMap,RowMapper<T> rowMapper) throws Exception{
	   return  getNamedParameterJdbcTemplate().query(sql, paramMap, rowMapper);
   }

	@Override
	public void commitChanges() throws Exception {
		getNamedParameterJdbcTemplate().getJdbcOperations().execute(
			new ConnectionCallback<String>() {
					@Override
					public String doInConnection(Connection conn) throws SQLException, DataAccessException {
						boolean oldCommitStatus = conn.getAutoCommit();
						conn.commit();
						boolean newCommitStatus = conn.getAutoCommit();
						if(oldCommitStatus != newCommitStatus){
							conn.setAutoCommit(oldCommitStatus);
						}
						return null;
					}
			});
	}
	////mybaties 操作
	@Override
	public <T>  T mybatiesSelectOne(String sqlMapperId) throws Exception{
		return getSqlSessionTemplate().selectOne(sqlMapperId);
	}
	@Override
	public <T,P> T mybatiesSelectOne(String sqlMapperId,P parameter) throws Exception{
		return getSqlSessionTemplate().selectOne(sqlMapperId, parameter);
	}
	@Override
	public <T> int mybatiesUpdate(String sqlMapperId) throws Exception{
		return getSqlSessionTemplate().update(sqlMapperId);
	}
	@Override
	public <P> int   mybatiesUpdate(String sqlMapperId,P parameter) throws Exception{
		return getSqlSessionTemplate().update(sqlMapperId, parameter);
	}
	@Override
	public <T> List<T>   mybatiesSelectList(String sqlMapperId) throws Exception{
		return getSqlSessionTemplate().selectList(sqlMapperId);
	}
	@Override
	public <T,P> List<T> mybatiesSelectList(String sqlMapperId,P parameter) throws Exception{
		return this.getSqlSessionTemplate().selectList(sqlMapperId, parameter);
	}
//	///redis operations
//	//释放jedis 连接
//	@Override
//	public void                redisRelease() throws Exception{
//		getShardedJedisTemplate().releaseShardedJedis();
//	}
	//基本jedis操作
	@Override
	public String              redisSet(String key ,String value,Integer expireSeconds) throws Exception{
		String res = getShardedJedisTemplate().set(key, value);
		redisExpireBySeconds(key,expireSeconds);
		return res;
	}
	@Override
	public String              redisGet(String key) throws Exception{
		return getShardedJedisTemplate().get(key);
	}
	@Override
	public String              redisSet(byte[] key ,byte[] value,Integer expireSeconds) throws Exception{
		String res = getShardedJedisTemplate().set(key, value);
		redisExpireBySeconds(key,expireSeconds);
		return res;
	}
	@Override
	public byte[]              redisGet(byte[] key) throws Exception{
		return getShardedJedisTemplate().get(key);
	}
	@Override
	public Set<String>         redisGetAllKeys(String pattern)  throws Exception{
		return getShardedJedisTemplate().getAllKeys(pattern);
	}
	@Override
	public Long                redisExpireBySeconds(String key ,Integer expireSeconds) throws Exception{
		if((null != expireSeconds) && ( !Integer.valueOf(-1) .equals(expireSeconds) ) &&  ( !Integer.valueOf(0) .equals(expireSeconds) )){
			return getShardedJedisTemplate().expire(key, expireSeconds);
		}
		return 0L;
	}
	@Override
	public Long                redisExpireBySeconds(byte[] key ,Integer expireSeconds) throws Exception{
		if((null != expireSeconds) && ( !Integer.valueOf(-1) .equals(expireSeconds) ) &&  ( !Integer.valueOf(0) .equals(expireSeconds) )){
			return getShardedJedisTemplate().expire(key, expireSeconds);
		}
		return 0L;
	}
	@Override
	public Long                redisDelete(String key) throws Exception{
		return getShardedJedisTemplate().delete(key);
	}
	@Override
	public Long                redisDelete(byte[] key) throws Exception{
		return getShardedJedisTemplate().delete(key);
	}
	@Override
	public int                 redisDeleteKeysByPattern(String pattern) throws Exception{
		return getShardedJedisTemplate().deleteKeysByPattern(pattern);
	}
	@Override
	public boolean             redisExistsKey(String key) throws Exception{
		return getShardedJedisTemplate().exists(key);
	}
	//Map 操作
	@Override
	public String              redisSetMap(String key ,Map<String,String> map,Integer expireSeconds) throws Exception{
		String res = getShardedJedisTemplate().setMap(key, map);
		redisExpireBySeconds(key,expireSeconds);
		return res;
	}
	@Override
	public Long                redisSetMapProperty(String key ,String entryKey,String entryValue) throws Exception{
		return getShardedJedisTemplate().setMap(key, entryKey, entryValue);
	}
	@Override
	public Map<String,String>  redisGetMap(String key) throws Exception{
		return getShardedJedisTemplate().getMap(key);
	}
	@Override
	public String              redisGetMapProperty(String key,String entryKey) throws Exception{
		return getShardedJedisTemplate().mapGet(key, entryKey);
	}
	@Override
	public List<String>        redisGetMapProperties(String key,String... entryKeys) throws Exception{
		return getShardedJedisTemplate().getList(key, entryKeys);
	}
	@Override
	public Long                redisDeleteMapProperty(String key ,String... removedEntryKeys) throws Exception{
		return getShardedJedisTemplate().deleteMap(key, removedEntryKeys);
	}
	@Override
	public boolean             redisExistsMapEntryKey(String key ,String entryKey) throws Exception{
		return getShardedJedisTemplate().mapContains(key, entryKey);
	}
	//list 操作
	@Override
	public Long                redisSetList(String key ,List<String> list,Integer expireSeconds) throws Exception{
		Long res = getShardedJedisTemplate().setList(key, list);
		redisExpireBySeconds(key,expireSeconds);
		return res;
	}
	@Override
	public List<String>        redisGetList(String key) throws Exception{
		return getShardedJedisTemplate().getList(key);
	}
	@Override
	public String        redisGetListMemberByIndex(String key,int index) throws Exception{
		return getShardedJedisTemplate().listGet(key, index);
	}
	@Override
	public Long                redisDeleteListMember(String key,String... removedListMembers) throws Exception{
		return getShardedJedisTemplate().deleteList(key, removedListMembers);
	}
	@Override
	public boolean             redisExistsListMember(String key,String member) throws Exception{
		return getShardedJedisTemplate().listContains(key,member);
	}
	//set操作
	@Override
	public Long                redisSetSet(String key ,Set<String> set,Integer expireSeconds) throws Exception{
		Long res = getShardedJedisTemplate().setSet(key, set);
		redisExpireBySeconds(key,expireSeconds);
		return res;
	}
	@Override
	public Set<String>        redisGetSet(String key) throws Exception{
		return getShardedJedisTemplate().getSet(key);
	}
	@Override
	public Long                redisDeleteSetMember(String key,String... removedSetMembers) throws Exception{
		return getShardedJedisTemplate().deleteSet(key, removedSetMembers);
	}
	@Override
	public boolean            redisExistsSetMember(String key,String member) throws Exception{
		return getShardedJedisTemplate().setContains(key, member);
	}
	@Override
	public Long                redisSetAdd(String key ,String... members) throws Exception{
		return getShardedJedisTemplate().setAdd(key, members);
	}
	@Override
	public Long redisIncr(String key)throws Exception{
		return getShardedJedisTemplate().setIncr(key);
	}
}
