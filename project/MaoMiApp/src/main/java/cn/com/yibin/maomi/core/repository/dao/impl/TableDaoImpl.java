package cn.com.yibin.maomi.core.repository.dao.impl;


import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.com.yibin.maomi.core.constant.DataBaseEnum;
import cn.com.yibin.maomi.core.database.DataBasePager;
import cn.com.yibin.maomi.core.database.DataBasePaging;
import cn.com.yibin.maomi.core.model.Table;
import cn.com.yibin.maomi.core.repository.dao.TableDao;
import cn.com.yibin.maomi.core.util.ResourceUtil;
import cn.com.yibin.maomi.core.util.WebUtil;

@Repository(value="tableDao")
public class TableDaoImpl  extends AbstractRepositoryDaoImpl  implements TableDao 
{
	   @Override
       public void getTableInfo(final Table table,final boolean isLoadTreeData) throws Exception
       {
		    if(isLoadTreeData)
		    {
		      this.getTableExportExcelInfo(table,isLoadTreeData);
			  return; 
		    }
		   
    	    DataBasePager dataBasePager = new DataBasePager(){
			@Override
			public DataBasePaging getDataBasePaging() {
				String dbType = ResourceUtil.getDBType();
				String DataBasePagingImplClassName = DataBaseEnum.valueOf(dbType).getDBPagerClassName();
				try {
					return (DataBasePaging)Class.forName(DataBasePagingImplClassName).newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
				try {
					return ("dataSource".equals(table.getDataSourceName())) ? TableDaoImpl.this.getNamedParameterJdbcTemplate() : (new NamedParameterJdbcTemplate((DataSource)WebUtil.getApplicationContext().getBean(table.getDataSourceName())));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public int getLimit() {
				return table.getLimit();
			}

			@Override
			public String getSourceSqlWithoutPaging() {
				return table.getTargetSql();
			}

			@Override
			public int getStart() {
				return table.getStart();
			}

			@Override
			public List<String> getReplaceKeyList() {
				return table.getReplaceKeyList();
			}

			@Override
			public List<String> getReplaceValueList() {
				return table.getReplaceValueList();
			}
    	   };
    	   table.setTotalCount(dataBasePager.getPagingDatas(false));
    	   table.setDatas(dataBasePager.getDatas());
    	   table.setColumnTypesMapping(dataBasePager.getColumnTypesMapping());
    	   table.setColumnsJsonArray(dataBasePager.getColumnsJsonArray());
       }
	   @Override
       public void getTableExportExcelInfo(final Table table,final boolean isLoadTreeData) throws Exception
       {
    	    DataBasePager dataBasePager = new DataBasePager(){
    			@Override
    			public DataBasePaging getDataBasePaging() {
    				String dbType = ResourceUtil.getDBType();
    				String DataBasePagingImplClassName = DataBaseEnum.valueOf(dbType).getDBPagerClassName();
    				try {
    					return (DataBasePaging)Class.forName(DataBasePagingImplClassName).newInstance();
    				} catch (InstantiationException e) {
    					e.printStackTrace();
    				} catch (IllegalAccessException e) {
    					e.printStackTrace();
    				} catch (ClassNotFoundException e) {
    					e.printStackTrace();
    				}
    				return null;
    			}

    			@Override
    			public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
    				try {
    					return ("dataSource".equals(table.getDataSourceName())) ? TableDaoImpl.this.getNamedParameterJdbcTemplate() : (new NamedParameterJdbcTemplate((DataSource)WebUtil.getApplicationContext().getBean(table.getDataSourceName())));
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				return null;
    			}

			@Override
			public int getLimit() {
				return table.getLimit();
			}

			@Override
			public String getSourceSqlWithoutPaging() {
				if(isLoadTreeData)
				{
					return table.getTargetSql();
				}
				return table.getExcelTargetSql();
			}
			@Override
			public List<String> getReplaceKeyList() {
				return table.getReplaceKeyList();
			}

			@Override
			public List<String> getReplaceValueList() {
				return table.getReplaceValueList();
			}
			@Override
			public int getStart() {
				return table.getStart();
			}
    	   };
    	   table.setTotalCount(dataBasePager.getPagingDatas(true));
    	   table.setDatas(dataBasePager.getDatas());
    	   table.setColumnTypesMapping(dataBasePager.getColumnTypesMapping());
    	   table.setColumnsJsonArray(dataBasePager.getColumnsJsonArray());
       }
}
