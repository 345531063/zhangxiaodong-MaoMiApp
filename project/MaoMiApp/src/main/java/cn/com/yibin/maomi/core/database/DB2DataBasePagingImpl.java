package cn.com.yibin.maomi.core.database;

public class DB2DataBasePagingImpl implements DataBasePaging {

	@Override
	public String getPagingSql(String sqlNoPaging, int start, int limit) {
		String sqlPaging = sqlNoPaging + " limit "+start+","+limit;
		return sqlPaging;
	}
}
