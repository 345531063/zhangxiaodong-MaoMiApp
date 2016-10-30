package cn.com.yibin.maomi.core.jpa;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import cn.com.yibin.maomi.core.annotation.ApIgnore;
import cn.com.yibin.maomi.core.annotation.ApPrimaryKey;
import cn.com.yibin.maomi.core.annotation.ApTable;
import cn.com.yibin.maomi.core.annotation.ApTableColumn;
import cn.com.yibin.maomi.core.util.FileUtil;
import cn.com.yibin.maomi.core.util.StringUtil;

public abstract class JPASupport {
	/**
  	 * 
  	 * 作cache 结构{T类的镜像,{数据库列名,实体字段名}}
  	 */
    private static final Map<String,String>                classTableInsertPreparedSqlCachedMap     = new HashMap<String,String>();
    private static final Map<String,String>                classTableUpdatePreparedSqlCachedMap     = new HashMap<String,String>();
    private static final Map<String,String>                classTableColumnFieldAliasSqlCachedMap   = new HashMap<String,String>();
    private static final Map<String,Map<String,String>>    classColumnTypeCachedMap                 = new HashMap<String,Map<String,String>>();
    private static final Map<String,Map<String,String>>    classFieldTypeCachedMap                  = new HashMap<String,Map<String,String>>();
    private static final Map<String,Map<String,String>>    classColumnFieldCachedMap                = new HashMap<String,Map<String,String>>();
    private static final Map<String,Map<String,String>>    classFieldColumnCachedMap     		    = new HashMap<String,Map<String,String>>();
    private static final Map<String,String>                classTableNameCachedMap      		    = new HashMap<String,String>();
    private static final Map<String,String>                classTableFieldPKNameCachedMap           = new HashMap<String,String>();
    private static final Map<String,String>                classTableColumnPKNameCachedMap          = new HashMap<String,String>();
    private static final Map<String,String>                classTableSequenceNameCachedMap          = new HashMap<String,String>();
    private static final Map<String,List<String>>          classFieldsCachedListMap                 = new HashMap<String,List<String>>();
    private static final Map<String,List<String>>          classColumnsCachedListMap                = new HashMap<String,List<String>>();
	
    public static Map<String, String> getClassTableInsertPreparedSqlCachedMap() {
		return classTableInsertPreparedSqlCachedMap;
	}
	public static Map<String, String> getClassTableUpdatePreparedSqlCachedMap() {
		return classTableUpdatePreparedSqlCachedMap;
	}
	public static Map<String, String> getClassTableColumnFieldAliasSqlCachedMap() {
		return classTableColumnFieldAliasSqlCachedMap;
	}
	public static Map<String, Map<String, String>> getClassColumnTypeCachedMap() {
		return classColumnTypeCachedMap;
	}
	public static Map<String, Map<String, String>> getClassFieldTypeCachedMap() {
		return classFieldTypeCachedMap;
	}
	public static Map<String, Map<String, String>> getClassColumnFieldCachedMap() {
		return classColumnFieldCachedMap;
	}
	public static Map<String, Map<String, String>> getClassFieldColumnCachedMap() {
		return classFieldColumnCachedMap;
	}
	public static Map<String, String> getClassTableNameCachedMap() {
		return classTableNameCachedMap;
	}
	public static Map<String, String> getClassTableFieldPKNameCachedMap() {
		return classTableFieldPKNameCachedMap;
	}
	public static Map<String, String> getClassTableColumnPKNameCachedMap() {
		return classTableColumnPKNameCachedMap;
	}
	public static Map<String, String> getClassTableSequenceNameCachedMap() {
		return classTableSequenceNameCachedMap;
	}
	public static Map<String, List<String>> getClassFieldsCachedListMap() {
		return classFieldsCachedListMap;
	}
	public static Map<String, List<String>> getClassColumnsCachedListMap() {
		return classColumnsCachedListMap;
	}
	public static void init(){
		Set<Class<?>> classes = FileUtil.getClasses("cn.com.yibin.maomi");
		Class<JPASupport> jpaClazz = JPASupport.class;
		for(Class<?> clazz : classes){
			if(jpaClazz.isAssignableFrom(clazz)){
				initJPACached(clazz);
			}
		}
	}
    private static void  initJPACached(Class<?> jpaEntityClass){
    	
    	if(!jpaEntityClass.isAnnotationPresent(ApTable.class)){
    		return;
    	}
    	String className  = jpaEntityClass.getName();
        ApTable table     = jpaEntityClass.getAnnotation(ApTable.class);
        String  tableName = table.name().toUpperCase();
        String  seqName   = StringUtil.emptyOpt(table.sequenceName(), "SEQ_"+tableName);
        classTableNameCachedMap.put(className, tableName);
        classTableSequenceNameCachedMap.put(className, seqName);
        
    	// 作cache
        Field[] fields     = jpaEntityClass.getDeclaredFields();
        String  idName     = null;
        String  pkName     = null;
        String  fieldName  = null;
        String  columnName = null; 
        
        
        Map<String,String> fieldTypeCachedMap = new HashMap<String,String>();
        classFieldTypeCachedMap.put(className, fieldTypeCachedMap);
        
        Map<String,String> columnTypeCachedMap = new HashMap<String,String>();
        classColumnTypeCachedMap.put(className, columnTypeCachedMap);
        
        Map<String,String> columnFieldCachedMap = new HashMap<String,String>();
        classColumnFieldCachedMap.put(className, columnFieldCachedMap);
        
        Map<String,String> fieldColumnCachedMap = new HashMap<String,String>();
        classFieldColumnCachedMap.put(className, fieldColumnCachedMap);
        
        List<String> fieldsCachedList = new ArrayList<String>();
        classFieldsCachedListMap.put(className, fieldsCachedList);
        
        List<String> columnsCachedList = new ArrayList<String>();
        classColumnsCachedListMap.put(className, columnsCachedList);
        
        StringBuilder tableInsertPreparedSqlPrefix = new StringBuilder();
        StringBuilder tableInsertPreparedSqlSuffix = new StringBuilder();
        StringBuilder tableUpdatePreparedSql = new StringBuilder("UPDATE "+tableName+" SET ");
        StringBuilder tableColumnFieldAliasSql = new StringBuilder();
        int index = 0;
        
        for (Field field : fields) {
        	if (!field.isAnnotationPresent(ApTableColumn.class) || field.isAnnotationPresent(ApIgnore.class)) {
 	       		continue;
 	       	}
 	       	fieldName = field.getName();
 	       	ApTableColumn tableColumn = field.getAnnotation(ApTableColumn.class);
 	       	if (null != tableColumn) {
 	       		columnName = tableColumn.name().toUpperCase();
 	       	} else {
 	       		columnName = null;
 	       	}
 	        // 如果未标识特殊的列名，默认取字段名
 	        columnName = (StringUtils.isEmpty(columnName) ? StringUtils.upperCase(fieldName) : columnName);
 	        
 	        String typeName	= field.getType().getSimpleName();
 	        fieldTypeCachedMap.put(fieldName, typeName);
 	        columnTypeCachedMap.put(columnName, typeName);
 	        columnFieldCachedMap.put(columnName, fieldName);
 	        fieldColumnCachedMap.put(fieldName,columnName );
 	        fieldsCachedList.add(fieldName);
 	        columnsCachedList.add(columnName);
 	        
  	        if (field.isAnnotationPresent(ApPrimaryKey.class)) {
  	        	idName = fieldName;
  	        	pkName = columnName;
	       		// 取得ID的列名
	            classTableFieldPKNameCachedMap.put(className,idName);
	            classTableColumnPKNameCachedMap.put(className,pkName);
	            continue;
	       	}
        	tableInsertPreparedSqlPrefix.append(","+columnName);
        	tableInsertPreparedSqlSuffix.append(",:"+fieldName);
        	tableColumnFieldAliasSql.append(","+columnName+" ");
  	     	if( 0 < (index++)){
        		tableUpdatePreparedSql.append(",");
        	}
        	tableUpdatePreparedSql.append(columnName+" = :"+fieldName);
        }
        tableInsertPreparedSqlPrefix.append(") ");
        tableInsertPreparedSqlSuffix.append(") ");
        tableInsertPreparedSqlPrefix.insert(0, "INSERT INTO "+tableName+"("+pkName);
        tableInsertPreparedSqlSuffix.insert(0, " VALUES(:"+idName);
        tableColumnFieldAliasSql.insert(0, " "+pkName+" ");
        tableUpdatePreparedSql.append(" WHERE "+pkName+" = :"+idName);
        
        StringBuilder tableInsertPreparedSql = new StringBuilder();
        tableInsertPreparedSql.append(tableInsertPreparedSqlPrefix);
        tableInsertPreparedSql.append(tableInsertPreparedSqlSuffix);
        
        classTableInsertPreparedSqlCachedMap.put(className, tableInsertPreparedSql.toString());
        classTableUpdatePreparedSqlCachedMap.put(className, tableUpdatePreparedSql.toString());
        classTableColumnFieldAliasSqlCachedMap.put(className, tableColumnFieldAliasSql.toString());
    }    
}
