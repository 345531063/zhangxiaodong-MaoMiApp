package cn.com.yibin.maomi.core.constant;

public enum DataBaseEnum
{
		MYSQL("com.mysql.jdbc.Driver","MySqlDataBasePagingImpl","MySqlDBBinaryStreamManagerImpl"),
	    ORACLE("oracle.jdbc.OracleDriver","OracleDataBasePagingImpl","OracleDBBinaryStreamManagerImpl"), 
		SQLSERVER("net.sourceforge.jtds.jdbc.Driver","SqlServerDataBasePagingImpl","SqlServerDBBinaryStreamManagerImpl"), 
		DB2("com.ibm.db2.jcc.DB2Driver","DB2DataBasePagingImpl",""),
		SYBASE("com.sybase.jdbc2.jdbc.SybDriver","SyBaseDataBasePagingImpl","");
	
	   private  final  String DBDriverName;
	   private  final  String DBPagerClassName;
	   private  static final String BASE_PACKAGE = "com.andsonap.core.database.";
	   private  final  String DBBinaryStreamManagerClassName;
	   private  DataBaseEnum(String DBDriverName, String DBPagerClassName,String DBBinaryStreamManagerClassName) 
	   {
			this.DBDriverName = DBDriverName;
			this.DBPagerClassName = DBPagerClassName;
			this.DBBinaryStreamManagerClassName = DBBinaryStreamManagerClassName;
	   }
	   public String getDBDriverName() 
	   {
			return DBDriverName;
	   }
	   public String getDBPagerClassName() 
	   {
			return BASE_PACKAGE+DBPagerClassName;
	   }
	   public String getDBBinaryStreamManagerClassName() 
	   {
		   return BASE_PACKAGE+DBBinaryStreamManagerClassName;
	   }
	   public static String getDBPagerByDriverClassName(String driverClassName)
	   {
		   DataBaseEnum[] dbPagers = DataBaseEnum.values();
		   
		   for(int i=0;i<dbPagers.length;i++ )
		   {
			   DataBaseEnum dbPager = dbPagers[i];
			   if(driverClassName.equals(dbPager.getDBDriverName()))
			   {
				   return BASE_PACKAGE+dbPager.getDBPagerClassName();
			   }
		   }
		   return null;
	   }
	   public static String getDBBinaryStreamManagerClassName(String driverClassName)
	   {
		   DataBaseEnum[] dbPagers = DataBaseEnum.values();
		   
		   for(int i=0;i<dbPagers.length;i++ )
		   {
			   DataBaseEnum dbPager = dbPagers[i];
			   if(driverClassName.equals(dbPager.getDBDriverName()))
			   {
				   return BASE_PACKAGE+dbPager.getDBBinaryStreamManagerClassName();
			   }
		   }
		   return null;
	   }
}
