package cn.com.yibin.maomi.core.database;

public class SyBaseDataBasePagingImpl implements DataBasePaging {

	@Override
	public String getPagingSql(String sqlNoPaging, int start, int limit) {
		return sqlNoPaging;
	}
}
