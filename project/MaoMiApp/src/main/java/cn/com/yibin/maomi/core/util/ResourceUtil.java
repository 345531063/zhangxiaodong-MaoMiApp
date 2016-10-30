package cn.com.yibin.maomi.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ResourceUtil 
{
   public static void init(){
	   //获得tablexml文件路径
	   tablesDataSourceDirectoryPath =getWebConfigValue("tables_dataDirectory");
	   //获得chartxml文件路径
	   chartsDataSourceDirectoryPath =getWebConfigValue("charts_dataDirectory");
	   //获得chartflt文件路径
	   chartsFltSourceDirectoryPath =getWebConfigValue("charts_fltDirectory");
   };
   //private static  String  CONFIG_FILE_NAME = "framework-config.properties";
   private static  String  CONFIG_FILE_NAME = "quartz.properties";
   private static  Log log = LogFactory.getLog(ResourceUtil.class);
   private static  final Map<String,String> configMap = new HashMap<String,String>();
   public static String getConfigValue(String propertyName)
   {
		   if(0 == configMap.size())
		   {
				 FileInputStream fis = null;
				 try 
				 {
					fis = new FileInputStream(URLDecoder.decode(Thread.currentThread()
							.getContextClassLoader().getResource(CONFIG_FILE_NAME)
							.getFile(), "UTF-8"));
					Properties pro = new Properties();
					pro.load(fis);
					for(Object key : pro.keySet()){
						String valString = StringUtil.emptyOpt(pro.get(key)).trim();
						String keyString = StringUtil.emptyOpt(key).trim();
						configMap.put(keyString, valString);
						
				        if(log.isInfoEnabled())
				        {
				           log.info("###########加载配置文件属性：【"+keyString+" = "+valString+"】");
				        }
					}
				}
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				} finally{
					try {
						if(null != fis){
							fis.close();
							fis = null;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		   }

	   return configMap.get(propertyName);
   }
   //获得项目部署相对路径名称
   public static String getWebConfigValue(String propertyName)
   {
	   String value = WebUtil.getWebContextRealPath()+StringUtil.emptyOpt(getConfigValue(propertyName)).trim();
	   return value;
   }
   //获得是否处于系统开发阶段
   private static boolean isDebug = "TRUE".equals(StringUtil.emptyOpt(getConfigValue("isDebug")).toUpperCase());
   public  static void setDebug(boolean isDebugModel){
	   isDebug = isDebugModel;
   }
   public static boolean isDebug()
   {
	   isDebug = true;
	   return isDebug;
   }
   //远程RMI主机地址
   private static String localIp = getConfigValue("localIp");
   public  static String getLocalIp(){
	   return localIp;
   }
   //
   private static int gateWayTCPServerPort    = Integer.parseInt(getConfigValue("gateWayTCPServerPort"));
   public  static int getGateWayTCPServerPort(){
	   return gateWayTCPServerPort;
   }
   private static int gateWayUDPServerPort    = Integer.parseInt(getConfigValue("gateWayUDPServerPort"));
   public  static int getGateWayUDPServerPort(){
	   return gateWayUDPServerPort;
   }
   private static int userMobileTCPServerPort = Integer.parseInt(getConfigValue("userMobileTCPServerPort"));
   public  static int getUserMobileTCPServerPort(){
	   return userMobileTCPServerPort;
   }
   private static int userMobileUDPServerPort = Integer.parseInt(getConfigValue("userMobileUDPServerPort"));
   public  static int getUserMobileUDPServerPort(){
	   return userMobileUDPServerPort;
   }
   //远程RMI端口
   private static int RMILocalPort = Integer.parseInt(getConfigValue("RMILocalPort"));
   public  static int getRMILocalPort(){
	   return RMILocalPort;
   }
   //远程RMI服务名
   private static String RMILocalServiceName = getConfigValue("RMILocalServiceName");
   public  static String getRMILocalServiceName(){
	   return RMILocalServiceName;
   }
   //远程RMI集群
   private static  String       RMICluster    = getConfigValue("RMICluster").replaceAll("\\s","");
   private static  List<String> RMIClsterList = new ArrayList<String>();
   static {
	   String[]       rmiClusterArr  =  RMICluster.split("\\|");
	   for( String rmiCluster : rmiClusterArr ){
		   RMIClsterList.add(rmiCluster);
	   }
   }
   public  static List<String> getRMIClusterList(){
	   return RMIClsterList;
   }
   private static String rmiServiceName = getConfigValue("rmiServiceName");
   public  static String getRmiServiceName(){
	   return rmiServiceName;
   }
   private static  String       keepAlivedCheckedIps    = getConfigValue("keepAlivedCheckedIps").replaceAll("\\s","");
   private static  List<String> keepAlivedCheckedIpList = new ArrayList<String>();
   static {
	   String[]    keepAlivedCheckedIpArr  =  keepAlivedCheckedIps.split("\\|");
	   for( String keepAlivedCheckedIp : keepAlivedCheckedIpArr ){
		   keepAlivedCheckedIpList.add(keepAlivedCheckedIp);
	   }
   }
   public  static List<String> getKeepAlivedCheckedIpList(){
	   return keepAlivedCheckedIpList;
   }
   //密码复杂度
   private static int minPasswordLength = Integer.parseInt(StringUtil.emptyOpt(getConfigValue("minPasswordLength"), "6"));
   public  static int getMinPasswordLength(){
	   return minPasswordLength;
   }
   private static String passwordComplexityJSRegex = getConfigValue("passwordComplexityJSRegex");
   public  static String getMinPasswordComplexityJSRegex(){
	   return passwordComplexityJSRegex;
   }
   //获得数据库类型
   private static String DBTYPE = getConfigValue("org.quartz.dataSource.myDS.URL").toUpperCase();
   public static String getDBType()
   {
	   if(DBTYPE.indexOf("ORACLE")>-1){
		   DBTYPE = "ORACLE";
	   }
	   else if(DBTYPE.indexOf("SQLSERVER")>-1){
		   DBTYPE = "SQLSERVER";
	   }
	   else if(DBTYPE.indexOf("MYSQL")>-1){
		   DBTYPE = "MYSQL";
	   }
	   return DBTYPE;
   }
   //获得tablexml文件路径
   private static String tablesDataSourceDirectoryPath = null;
   public static String getTablesDataSourceDirectoryPath()
   {
	   return tablesDataSourceDirectoryPath;
   }
   //获得chartxml文件路径
   private static String chartsDataSourceDirectoryPath = null;
   public static String getChartsDataSourceDirectoryPath()
   {
	   return chartsDataSourceDirectoryPath;
   }
   //获得chartflt文件路径
   private static String chartsFltSourceDirectoryPath = null;
   public static String getFltSourceDirectoryPath()
   {
	   return chartsFltSourceDirectoryPath;
   }
   
   //获取菜单的装载方式
   private static String MENUTYPE = getConfigValue("MENUTYPE").toUpperCase();
   public static String getMenuType() 
   {
	  return MENUTYPE;
   }
   ////获取upload文件上传路径
   private static String fileUploadDataPath = getConfigValue("uploads_store_path");
   public static String getFileUploadDataPath()
   {
	   return fileUploadDataPath;
   }
   //（集群或者本地模式）
   private static final String  uploadsStoreType = getConfigValue("uploads_store_type").toUpperCase();
   public static String getUploadsStoreType()
   {
	   return uploadsStoreType;
   }
   private static final String  uploadsStoreCharset = getConfigValue("uploads_store_charset");
   public static String getUploadsStoreCharset()
   {
	   return uploadsStoreCharset;
   }
   private static final String  uploadsStoreHost = getConfigValue("uploads_store_host");
   public static String getUploadsStoreHost()
   {
	   return uploadsStoreHost;
   }
   private static final String  port_ = getConfigValue("uploads_store_port");
   private static int   uploadsStorePort = port_.isEmpty() ?  21 : Integer.parseInt(port_);
   public static int getUploadsStorePort()
   {
	   return uploadsStorePort;
   }
   private static final String  uploadsStoreUsername = getConfigValue("uploads_store_username");
   public static String getUploadsStoreUsername()
   {
	   return uploadsStoreUsername;
   }
   private static final String  uploadsStorePassword = getConfigValue("uploads_store_password");
   public static String getUploadsStorePassword()
   {
	   return uploadsStorePassword;
   }
   //设置更新密码的时间
   private static int updatePasswordDays = Integer.parseInt(getConfigValue("updatePasswordDays"));
   public static int getUpdatePasswordDays()
   {
	   return updatePasswordDays;
   }
   private static boolean isNeedUpdatePermissionCache = false;
   public static void setNeedUpdatePermissionCache(boolean isNeed)
   {
	   isNeedUpdatePermissionCache = isNeed;
   }
   public static boolean isNeedUpdatePermissionCache()
   {
	   return isNeedUpdatePermissionCache ;
   }
   //sso
   private static String casServer = getConfigValue("sso.casServer");
   public  static String getCasServer(){
	   return casServer;
   }
   private static String localClient = getConfigValue("sso.localClient");
   public  static String getLocalClient(){
	   return localClient;
   }
}
