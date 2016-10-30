package cn.com.yibin.maomi.core.repository.dao;

import java.util.Map;

import cn.com.yibin.maomi.core.constant.ConditionEnum;
import cn.com.yibin.maomi.core.constant.DBQueryEnum;
import cn.com.yibin.maomi.core.constant.OrderByEnum;
import cn.com.yibin.maomi.core.jpa.SQLFilterEnum;

/**
 * 
 * 
 * 
 * 
 * Description: 生成查询数量的SQL
 */

public class SQLGenerator {

	/**
	 * 生成sequece查询下一个数值sql
	 * @param seq
	 * @return
	 */
	public static String sqlFetchSeqNextVal(String seq) {
	   return "SELECT ".concat(seq).concat(".nextval FROM DUAL");	
	}
	public static String sqlFindAll(String columnFieldAliasSql,String tableName){
		StringBuilder sql_bulder = new  StringBuilder();
		sql_bulder.append("SELECT ")
				  .append(columnFieldAliasSql)
				  .append(" FROM ")
				  .append(tableName);
		return sql_bulder.toString();
	}
	public static String sqlDeleteAll(String tableName){
		StringBuilder sql_bulder = new  StringBuilder();
		sql_bulder.append("DELETE ")
		.append(" FROM ")
		.append(tableName);
		return sql_bulder.toString();
	}
	private static String sqlColumnFieldFilterSql(Map<String,String> fieldColumnMap,Map<String,?> filterMap,ConditionEnum conditionEmun,DBQueryEnum dbQueryEnum){
		if(null == filterMap)return "";
		StringBuilder sql_bulder = new  StringBuilder();
		int count = 0;
		for(String fieldName : filterMap.keySet()){
			Object filterValue = filterMap.get(fieldName);
			if(0 < (count++)){
				 String connectKey  = "";
				 switch(conditionEmun){
			      case AND:{
			    	  connectKey = " AND ";
			    	  break;
			      }
			      case OR:{
			    	  connectKey = " OR ";
			    	  break;
			      }
			   }
				sql_bulder.append(connectKey);
			}
			String columnName = fieldColumnMap.get(fieldName);
			if(SQLFilterEnum.FILTER_EMPTY_RECORDS.getValue().equals(filterValue)){
				sql_bulder.append(columnName +" IS NULL ");
			}
			else if(SQLFilterEnum.FILTER_NOT_EMPTY_RECORDS.getValue().equals(filterValue)){
				sql_bulder.append(columnName +" IS NOT NULL ");
			}
			else{
				 String operUnionStr = "";
				 switch(dbQueryEnum){
			      case EQUALS:{
			    	  operUnionStr = columnName+  " = :"    + fieldName;
			    	  break;
			      }
			      case CASE_INSENSITIVE:{
			    	  operUnionStr = " UPPER( "+columnName +" ) = UPPER(:"+fieldName+" ) ";
			    	  break;
			      }
			      case LIKE:{
			    	  operUnionStr = columnName+  " LIKE :" + fieldName;
			    	  break;
			      }
			      case INSTR:{
			    	  operUnionStr = " INSTR("+columnName +",:"+fieldName+" ) > 0 ";
			    	  break;
			      }
				}
				sql_bulder.append(operUnionStr);
			}
		}
		return sql_bulder.toString();
	}
	private static String sqlColumnOrderBy(Map<String,String> fieldColumnMap,Map<String,OrderByEnum> orderByMap){
		if(null == orderByMap)return "";
		StringBuilder sql_bulder = new  StringBuilder();
		int count = 0;
		for(String fieldName : orderByMap.keySet()){
			if(0 < (count++)){
				sql_bulder.append(" , ");
			}
			String columnName = fieldColumnMap.get(fieldName);
			switch(orderByMap.get(fieldName)){
			    case ASC:{
			    	sql_bulder.append(columnName+" ASC ");
			       break;
			    }
			    case DESC:{
			    	sql_bulder.append(columnName+" DESC ");
			    	break;
			    }
			    default:{
			    	sql_bulder.append(columnName+" ASC ");
			    }
			}
		}
		return sql_bulder.toString();
	}
	public static String sqlFindOneById(Boolean in,String columnFieldAliasSql,String tableName,String columnPkName,String fieldPkName) throws Exception{
		String findAllSql = sqlFindAll(columnFieldAliasSql,tableName);
		StringBuilder sql_bulder = new  StringBuilder(findAllSql);
		sql_bulder.append(" WHERE ")
				  .append(columnPkName)
				  .append(in ? " IN( " : " = ")
				  .append(":"+fieldPkName)
				  .append(in ? " ) " : " ")
				  ;
		return sql_bulder.toString();
	}
	public static String sqlFindByFilter(String columnFieldAliasSql,Map<String,String> fieldColumnMap,String tableName,Map<String,?> filterMap,ConditionEnum conditionEnum,DBQueryEnum dbQueryEnum) throws Exception{
		String findAllSql = sqlFindAll(columnFieldAliasSql,tableName);
		StringBuilder sql_bulder = new  StringBuilder(findAllSql);
		String findFilterSql = sqlColumnFieldFilterSql(fieldColumnMap,filterMap,conditionEnum,dbQueryEnum);
		if(!findFilterSql.isEmpty()){
			sql_bulder.append(" WHERE ").append(findFilterSql);
		}
		return sql_bulder.toString();
	}
	public static String sqlFindByFilterPage(String columnFieldAliasSql,Map<String,String> fieldColumnMap,String tableName,Map<String,?> filterMap,ConditionEnum conditionEnum,DBQueryEnum dbQueryEnum,int start , int limit) throws Exception{
		String        findFilterSql  = sqlFindByFilter(columnFieldAliasSql, fieldColumnMap, tableName, filterMap, conditionEnum, dbQueryEnum);
		StringBuffer  sql_bulder     = new StringBuffer();
		sql_bulder.append(" select OUTER_PAGE_RS.* from(")
        .append("    select rownum as rn_column,INNER_PAGE_RS.* from(")
        .append("          "+findFilterSql)
		.append("    )INNER_PAGE_RS ")
        .append(" )OUTER_PAGE_RS " )
        .append(" WHERE OUTER_PAGE_RS.rn_column > " +start)
        .append(" AND   OUTER_PAGE_RS.rn_column <= "+(start+limit));
		return sql_bulder.toString();
	}
	public static String sqlFindByIDArray(String columnFieldAliasSql,String tableName,String columnPkName,String fieldPkName,int arrayIdsLen) throws Exception{
		String findAllSql = sqlFindAll(columnFieldAliasSql,tableName);
		StringBuilder sql_bulder = new  StringBuilder(findAllSql);
		String sqlFilterByIdArray = sqlFilterByIdArray(columnPkName,fieldPkName,arrayIdsLen);
		sql_bulder.append(" WHERE ").append(sqlFilterByIdArray);
		return sql_bulder.toString();
	}
		
	private static String sqlFilterByIdArray(String columnPkName,String fieldPkName,int arrayIdsLen){
		StringBuilder sql_bulder = new  StringBuilder(columnPkName+" IN ( ");
		for(int i= 0; i < arrayIdsLen;i++){
			if(0 < i){
				sql_bulder.append(",");
			}
			sql_bulder.append(":"+fieldPkName+(1+i));
		}
		sql_bulder.append(" ) ");
		return sql_bulder.toString();
	} 
	public static String sqlDeleteByIDArray(String tableName,String columnPkName,String fieldPkName,int arrayIdsLen){
		String sqlDeleteAll = sqlDeleteAll(tableName);
		StringBuilder sql_bulder = new StringBuilder(sqlDeleteAll);
		String sqlFilterByIdArray = sqlFilterByIdArray(columnPkName,fieldPkName,arrayIdsLen);
		sql_bulder.append(" WHERE ").append(sqlFilterByIdArray);
		return sql_bulder.toString();
	}
	public static String sqlFindByFilterAndOrder(String columnFieldAliasSql,Map<String,String> fieldColumnMap,String tableName,Map<String,?> filterMap,Map<String,OrderByEnum> orderByMap,ConditionEnum conditionEnum,DBQueryEnum dbQueryEnum) throws Exception{
		String filterMapSql = SQLGenerator.sqlFindByFilter(columnFieldAliasSql, fieldColumnMap, tableName, filterMap,conditionEnum, dbQueryEnum);
		StringBuilder sql_bulder = new  StringBuilder(filterMapSql);
		String orderBySql = sqlColumnOrderBy(fieldColumnMap,orderByMap);
		if(!orderBySql.isEmpty()){
			sql_bulder.append(" ORDER BY ").append(orderBySql);
		}
		return sql_bulder.toString();
	}
	public static String sqlFindByFilterAndOrderWithPage(String columnFieldAliasSql,Map<String,String> fieldColumnMap,String tableName,Map<String,?> filterMap,Map<String,OrderByEnum> orderByMap,ConditionEnum conditionEnum,DBQueryEnum dbQueryEnum,int start , int limit) throws Exception{
		String          findFilterSql  = sqlFindByFilter(columnFieldAliasSql, fieldColumnMap, tableName, filterMap, conditionEnum, dbQueryEnum);
		StringBuffer    sql_bulder     = new StringBuffer();
		String 			orderBySql     = sqlColumnOrderBy(fieldColumnMap,orderByMap);
		if(!orderBySql.isEmpty()){
			findFilterSql +=" ORDER BY "+orderBySql;
		}
		sql_bulder.append(" select OUTER_PAGE_RS.* from(")
        .append("    select rownum as rn_column,INNER_PAGE_RS.* from(")
        .append("          "+findFilterSql)
		.append("    )INNER_PAGE_RS ")
        .append(" )OUTER_PAGE_RS " )
        .append(" WHERE OUTER_PAGE_RS.rn_column > " +start)
        .append(" AND   OUTER_PAGE_RS.rn_column <= "+(start+limit));
		return sql_bulder.toString();
	}
	public static String sqlDeleteOneById(Boolean in,String tableName,String columnPkName,String fieldPkName) throws Exception{
		String deleteAllSql = sqlDeleteAll(tableName);
		StringBuilder sql_bulder = new  StringBuilder(deleteAllSql);
		sql_bulder.append(" WHERE ")
				  .append(columnPkName)
				  .append(in ? " IN( " : " = ")
				  .append(":"+fieldPkName)
				  .append(in ? " ) " : " ")
				  ;
		return sql_bulder.toString();
	}
	public static String sqlDeleteByFilter(Map<String,String> fieldColumnMap,String tableName,Map<String,?> filterMap,ConditionEnum conditionEnum,DBQueryEnum dbQueryEnum) throws Exception{
		String deleteAllSql = sqlDeleteAll(tableName);
		StringBuilder sql_bulder = new  StringBuilder(deleteAllSql);
		String deleteFilterSql = sqlColumnFieldFilterSql(fieldColumnMap,filterMap,conditionEnum,dbQueryEnum);
		if(!deleteFilterSql.isEmpty()){
			sql_bulder.append(" WHERE ").append(deleteFilterSql);
		}
		return sql_bulder.toString();
	}
}
