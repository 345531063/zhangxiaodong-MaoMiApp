package cn.com.yibin.maomi.core.database;

public class SqlServerDataBasePagingImpl implements DataBasePaging {

	@Override
	public String getPagingSql(String sqlNoPaging, int start, int limit) {
		   String noSpaceSql = sqlNoPaging.toUpperCase().replaceAll("\\s", "");
		   if(!noSpaceSql.startsWith("SELECTTOP100PERCENT"))
		   {
			   sqlNoPaging = "SELECT TOP 100 PERCENT "+sqlNoPaging.trim().substring(6);
		   }
		StringBuffer pageSql_sb = new StringBuffer("");
		pageSql_sb.append(" select OUTER_PAGE_RS.* from(")
		          .append("    select ROW_NUMBER() OVER(ORDER BY GETDATE()) as rn_column,INNER_PAGE_RS.* from(")
		          .append("          "+sqlNoPaging)
		          .append("    )INNER_PAGE_RS ")
		          .append(" )OUTER_PAGE_RS " )
		          .append(" WHERE OUTER_PAGE_RS.rn_column > " +start)
		          .append(" AND   OUTER_PAGE_RS.rn_column <= "+(start+limit));
		return pageSql_sb.toString();
	}
}
