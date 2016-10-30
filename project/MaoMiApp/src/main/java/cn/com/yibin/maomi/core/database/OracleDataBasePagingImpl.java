package cn.com.yibin.maomi.core.database;


public class OracleDataBasePagingImpl implements DataBasePaging 
{

	@Override
	public String getPagingSql(String sqlNoPaging, int start, int limit) 
	{
		StringBuffer pageSql_sb = new StringBuffer("");
		pageSql_sb.append(" select OUTER_PAGE_RS.* from(")
		          .append("    select rownum as rn_column,INNER_PAGE_RS.* from(")
		          .append("          "+sqlNoPaging)
		          .append("    )INNER_PAGE_RS ")
		          .append(" )OUTER_PAGE_RS " )
		          .append(" WHERE OUTER_PAGE_RS.rn_column > " +start)
		          .append(" AND   OUTER_PAGE_RS.rn_column <= "+(start+limit));
		return pageSql_sb.toString();
	}

}
