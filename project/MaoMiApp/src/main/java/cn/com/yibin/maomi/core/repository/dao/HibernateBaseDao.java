/*
 * created by tracywindy 2014-03-04
 * version 1.0
 */

package cn.com.yibin.maomi.core.repository.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author Administrator
 * 
 */
public interface HibernateBaseDao {
	
	/**
	 * jdbc
	 * @return
	 * @throws Exception
	 */
	public  NamedParameterJdbcTemplate   getNamedParameterJdbcTemplate()      throws Exception;
	/**
	 * mybaties
	 * @return
	 * @throws Exception
	 */
	public  SqlSessionTemplate            getSqlSessionTemplate()      throws Exception;
	/**
	 * hibernate 
	 * @return
	 * @throws Exception
	 */
	public  HibernateTemplate            getHibernateTemplate()               throws Exception;
	
	// basic  hibernate update operation
	public <T> void  saveHibernateEntity(T entity) throws Exception;

	public <T> void  saveHibernateAllEntities(Collection<T> entities) throws Exception;

	public <T> void  updateHibernateEntity(T entity) throws Exception;

	public <T> void  updateHibernateAllEntities(Collection<T> entities) throws Exception;

	public <T> void  saveOrUpdateHibernateEntity(T entity) throws Exception;

	public <T> void  saveOrUpdateHibernateAllEntities(Collection<T> entities) throws Exception;

	public <T> void  removeHibernateEntity(T entity) throws Exception;

	public <T> void  removeHibernateEntityById(Class<T> entityClass, String id) throws Exception;

	public <T> void  removeHibernateAllEntites(Collection<T> entities) throws Exception;
	
	public     int   updateHibernateByHSQL(String hsql, Object... values) throws Exception;
	
	public     int   updateHibernateByNamedParamHSQL(String hsql, String[] paramNames, Object[] values) throws Exception;
	
	//update by jdbc
	
	public     int   updateJdbcBySQL(String hsql, Object... values) throws Exception;
	
	//manual synchronize hibernate with database
	
	public void hibernateSessionFlush() throws Exception;
	
	//query  hibernate entity 
	
	public <T> T        findHibernateEntityByID(Class<T> entityClass, String id) throws Exception;
	
	public <T> List<T>  findHibernateEntityByIDArray(Class<T> entityClass, String[] ids) throws Exception;
	
	public <T> List<T>  findHibernateEntities(Class<T> entityClass) throws Exception;

	public <T> List<T>  findHibernateEntityByProperties(Class<T> entityClass, Map<String, Object> propertiesMap) throws Exception;
	
	public <T> List<T>  findHibernateResultsByHSQL(String hsql, Object... values) throws Exception;

	public <T> List<T>  findResultsByNamedParamHSQL(String hsql, String[] paramNames, Object[] values) throws Exception;
	
	//query  hibernate by page
	
	public <T> List<T>  findHibernateEntitiesPage(Class<T> entityClass, final int start, final int limit) throws Exception;
	
	public <T> List<T>  findHibernateEntityByPropertiesPage(Class<T> entityClass, Map<String, Object> propertiesMap, int start, int limit)throws Exception;
	
	public <T> List<T>  findResultsByHSQLPage(String hsql, int start, int limit, Object... values) throws Exception;
	
	public <T> List<T>  findHibernateResultsByNamedParamHSQLPage(String hsql, String[] paramNames, Object[] values, int start, int limit) throws Exception;
	
	//query hibernate row count
	public <T> Integer  getHibernateEntitiesRowCount(Class<T> entityClass) throws Exception;
	
	public <T> Integer  getHibernateEntityByPropertiesRowCount(Class<T> entityClass, Map<String, Object> propertiesMap) throws Exception;
	
	public     Integer  getHibernateResultsByHSQLRowCount(String countHSql, Object... values) throws Exception;
	
	public     Integer  getHibernateResultsByNamedParamHSQLRowCount(String countHsql, String[] paramNames, Object[] values) throws Exception;
	
	//query by jdbc
	public List<Map<String,Object>> queryJdbcListBySql(String sql, Object... values) throws Exception;
}