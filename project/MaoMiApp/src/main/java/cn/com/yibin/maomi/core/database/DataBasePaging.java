package cn.com.yibin.maomi.core.database;

public interface DataBasePaging 
{
	public abstract String getPagingSql(String sqlNoPaging, int start, int limit);
}
