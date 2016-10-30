package cn.com.yibin.maomi.core.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import cn.com.yibin.maomi.core.util.ResourceUtil;
import cn.com.yibin.maomi.core.util.StringUtil;

public abstract class DataBasePager 
{
   private final List<Map<String,String>> datas              = new ArrayList<Map<String,String>>();
   private final Map<String,String>       columnTypesMapping = new HashMap<String,String>();
   private final JSONArray                columnsJsonArray   = new JSONArray();
   
   public abstract NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();
   public abstract int    getStart();
   public abstract int    getLimit();
   public abstract String getSourceSqlWithoutPaging() ;
   public abstract List<String> getReplaceKeyList() ;
   public abstract List<String> getReplaceValueList() ;
   public abstract DataBasePaging getDataBasePaging() ;
   
   public int getPagingDatas(boolean isExportExcel) throws Exception
   {
	   int returnValue = 0;
	   final String sqlNoPaging = StringUtil.getClearWhereSQL(this.getSourceSqlWithoutPaging());
	   if(!isExportExcel)
	   {
		   String countSql = null;
		   if(ResourceUtil.getDBType().indexOf("SQLSERVER")>-1)
		   {
			   String noSpaceSql = sqlNoPaging.toUpperCase().replaceAll("\\s", "");
			   if(!noSpaceSql.startsWith("SELECTTOP100PERCENT"))
			   {
				   String  innerSql = "SELECT TOP 100 PERCENT "+sqlNoPaging.trim().substring(6);
				   countSql = " SELECT  COUNT(*) AS TOTALCOUNT from("+innerSql+")T_OUTER_COUNT ";
			   }
			   else
			   {
				   countSql = " SELECT  COUNT(*) AS TOTALCOUNT from("+sqlNoPaging+")T_OUTER_COUNT ";
			   }
		   }
		   else
		   {
			    countSql = " SELECT COUNT(*) as TOTALCOUNT from("+sqlNoPaging+")T_OUTER_COUNT ";
		   }
		   final List<String> replaceValueList = getReplaceValueList();
		   final String finalCountSql = countSql;
		   returnValue =  this.getNamedParameterJdbcTemplate().getJdbcOperations().execute(new ConnectionCallback<Integer>(){
				@Override
				public Integer doInConnection(Connection conn) throws SQLException,
						DataAccessException {
					PreparedStatement ps = conn.prepareStatement(finalCountSql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					for(int index = 0;index<replaceValueList.size();index++){
						ps.setString((index+1),replaceValueList.get(index));
					}
					ResultSet rs =null;
					try{
						rs = ps.executeQuery();	
						if(rs.next()){
							return Integer.valueOf(rs.getInt(1)) ;
						}
					}
					finally
					{
						 if(null!=rs)
						 {
							rs.close(); 
						 }
						 ps.close();
					}
					return 0;
				}
			});
		   //Map<String, Object>  map = this.getJdbcTemplate().queryForMap(countSql);
		   //returnValue = Integer.parseInt(map.get("totalcount").toString());
	   }
	   this.columnTypesMapping.clear();
	   this.datas.clear();
	   DataBasePaging dataBasePaging = this.getDataBasePaging();
	   final int    start     = this.getStart();
	   final int    limit     = this.getLimit();
	   final String pagingSql = dataBasePaging.getPagingSql(sqlNoPaging,start,limit);
	  // final List<String> replaceKeyList = getReplaceKeyList();
	   final List<String> replaceValueList = getReplaceValueList();
	   this.getNamedParameterJdbcTemplate().getJdbcOperations().execute(new ConnectionCallback<Object>(){
   
		@Override
		public Object doInConnection(Connection conn) throws SQLException,
				DataAccessException {
			PreparedStatement ps = conn.prepareStatement(pagingSql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			for(int index = 0;index<replaceValueList.size();index++){
				ps.setString((index+1),replaceValueList.get(index));
			}
			ResultSet rs = ps.executeQuery();
			int initIndex = 1;
			if("ORACLE".equals(ResourceUtil.getDBType())||"SQLSERVER".equals(ResourceUtil.getDBType())){
				initIndex = 2;
			}
			try
			{
				ResultSetMetaData resultSetMetaData = rs.getMetaData();
				int columnCount = resultSetMetaData.getColumnCount();
		        for(int i=initIndex;i<=columnCount;i++)
		        {
		        	String headerItem = StringUtil.emptyOpt(resultSetMetaData.getColumnLabel(i), resultSetMetaData.getColumnName(i)).toLowerCase();
		        	String columnType_name = resultSetMetaData.getColumnTypeName(i);
		        	JSONObject columnJson = new JSONObject();
		        	columnJson.put("header", headerItem);
		        	columnJson.put("name", headerItem);
		        	columnJson.put("mapping", headerItem);
		        	columnJson.put("hidden", false);
		        	columnJson.put("width", 100);
		        	columnJson.put("headerAlign", "center");
		        	columnJson.put("align", "left");
		        	DataBasePager.this.columnsJsonArray.add(columnJson);
		        	DataBasePager.this.columnTypesMapping.put(headerItem, columnType_name);
		        }
		 	   if(sqlNoPaging.equals(pagingSql))//逻辑分页
			   {
		 		   if(rs.absolute(start+1))
		 		   {
		 				do
		 				{
				        	Map<String,String> rowData = new LinkedHashMap<String,String>();
				        	for(int i=1;i<=columnCount;i++)
				        	{
				        		String headerItem = cn.com.yibin.maomi.core.util.StringUtil.emptyOpt(resultSetMetaData.getColumnLabel(i), resultSetMetaData.getColumnName(i)).toLowerCase();
					    		String columnType_name = DataBasePager.this.columnTypesMapping.get(headerItem);
					    		String value = "";
					    		if("CLOB".equalsIgnoreCase(columnType_name) || "TEXT".equalsIgnoreCase(columnType_name) || "LONGTEXT".equalsIgnoreCase(columnType_name))
				        		{
					    			Reader reader = null;
					    			try{
						    			reader = rs.getCharacterStream(i);
							    		if ( null != reader ){
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
				        		}
	                            else if("BLOB".equalsIgnoreCase(columnType_name) || "IMAGE".equalsIgnoreCase(columnType_name) )
				        		{
					    			InputStream is = rs.getBinaryStream(i);
					    		    if ( null != is  ) {
					    		    	StringBuilder sb = new StringBuilder();
					    		    	String line;
					    		    	BufferedReader reader = null;
					    		    	try {
					    		    		reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
						    		        while ((line = reader.readLine()) != null) {
						    		    	    sb.append(line);
						    		    	}
					    		    	} finally {
					    		    		if(null != reader){
					    		    			reader.close();
					    		    		}
					    		    		if(null != rs){
					    		    	      is.close();
					    		    		}
					    		    	}
					    		    	value = sb.toString();
					    		    }
				        		}
					    		else{
					    			value = rs.getString(i);
					    		}
				        		rowData.put(headerItem,value) ;
				        	}
				        	DataBasePager.this.datas.add(rowData);
				        	
		 					if(rs.getRow() == (start+limit))
		 					{
		 						break;
		 					}
		 				}while(rs.next());
		 			}
			   }
		 	   else//物理分页
		 	   {
			        while(rs.next())
			        {
			        	Map<String,String> rowData = new LinkedHashMap<String,String>();
			        	for(int i=initIndex;i<=columnCount;i++)
			        	{
			        		String headerItem = StringUtil.emptyOpt(resultSetMetaData.getColumnLabel(i), resultSetMetaData.getColumnName(i)).toLowerCase();
			        		String columnType_name = DataBasePager.this.columnTypesMapping.get(headerItem);
				    		String value = "";
				    		if("CLOB".equalsIgnoreCase(columnType_name) || "TEXT".equalsIgnoreCase(columnType_name) || "LONGTEXT".equalsIgnoreCase(columnType_name))
			        		{
				    			Reader reader = null;
				    			try{
					    			reader = rs.getCharacterStream(i);
						    		if ( null != reader){
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
			        		}
                            else if("BLOB".equalsIgnoreCase(columnType_name) || "IMAGE".equalsIgnoreCase(columnType_name) )
			        		{
				    			InputStream is = rs.getBinaryStream(i);
				    		    if (null != is ) {
				    		    	StringBuilder sb = new StringBuilder();
				    		    	String line;
				    		    	BufferedReader reader = null;
				    		    	try {
				    		    		reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					    		        while ((line = reader.readLine()) != null) {
					    		    	    sb.append(line);
					    		    	}
				    		    	} finally {
				    		    		if(null != reader){
				    		    			reader.close();
				    		    		}
				    		    		if(null != rs){
				    		    	      is.close();
				    		    		}
				    		    	}
				    		    	value = sb.toString();
				    		    }
			        		}
				    		else{
				    			value = rs.getString(i);
				    		}
			        		rowData.put(headerItem,value) ;
			        	}
			        	DataBasePager.this.datas.add(rowData);
			        }
		 	   }
			}catch(Exception e)
			{
				e.printStackTrace();
				throw new SQLException("异常"+pagingSql);
			}
			finally
			{
				 if(null!=rs)
				 {
					rs.close(); 
				 }
				 ps.close();
			}
			return null;
		}
		   
	   });
	   return returnValue;
   }
	
	public JSONArray getColumnsJsonArray() {
		return columnsJsonArray;
	}
	public List<Map<String, String>> getDatas() {
		return datas;
	}
	public Map<String, String> getColumnTypesMapping() {
		return columnTypesMapping;
	}
	
}
