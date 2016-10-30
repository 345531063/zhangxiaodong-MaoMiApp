package cn.com.yibin.maomi.core.util;//package cn.com.yibin.maomi.core.util;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.sun.java.util.jar.pack.Attribute.Layout.Element;
//import com.sun.xml.internal.txw2.Document;
//
//import freemarker.core.Environment.Namespace;
//
//public class XMLDataUtil 
//{
//   public static Map<String,Map<String,String>> getAllXMLTable() throws Exception
//   {
//	   return getAllXML("table");
//   }
//   public static Map<String,Map<String,String>> getAllXMLChart() throws Exception
//   {
//	   return getAllXML("chart");
//   }
//   public static Map<String,Map<String,String>> getAllXML(String flag) throws Exception
//   {
//	   Map<String,Map<String,String>> allXML = new HashMap<String,Map<String,String>>();
//	   String fileDir = null;
//	   if("table".equals(flag))
//	   {
//		   fileDir = ResourceUtil.getTablesDataSourceDirectoryPath();
//	   }
//	   else if("chart".equals(flag))
//	   {
//		   fileDir = ResourceUtil.getChartsDataSourceDirectoryPath();
//	   }
//	   File listDirFile = new File(FileUtil.getFilePathString(fileDir));
//	   XMLDataUtil.recursionFiles(listDirFile,allXML,flag);
//	   XmlUtil.closeLocalResources();
//	   return allXML;
//   }
//   public static void recursionFiles(File listDirFile,Map<String,Map<String,String>> allXML,String flag) throws Exception
//   {
//	   String xmlFilePath   = listDirFile.getPath();
//	   if(!(xmlFilePath.toLowerCase().endsWith(".svn"))&& !(xmlFilePath.toLowerCase().endsWith(".cvs")))
//	   {
//		   if(listDirFile.isDirectory())
//		   {
//			   File[] chidrenFiles = listDirFile.listFiles();
//			   for(int index=0;index<chidrenFiles.length;index++)
//			   {
//				  File file  = chidrenFiles[index];
//				  XMLDataUtil.recursionFiles(file, allXML,flag);
//			   }
//		   }
//		   else 
//		   {
//			  if(xmlFilePath.toLowerCase().endsWith(".xml"))
//			  {
//				  String  fileFullPath  = xmlFilePath.replaceAll("(\\\\)|(/)|(%5C)","").replaceAll("\\s","");
//				  if("table".equals(flag))
//				  {
//					  allXML.put(fileFullPath, XMLDataUtil.readTableInfoFromXmlFile(xmlFilePath));
//				  }
//				  else if("chart".equals(flag))
//				  {
//					  allXML.put(fileFullPath,XMLDataUtil.readChartInfoFromXmlFile(xmlFilePath));
//				  }
//				  
//			  }
//		   }
//	   }
//   }
//   public static Map<String,String> readTableInfoFromXmlFile(String xmlFilePath) throws Exception
//	{
//		Map<String,String> m = new HashMap<String,String>();
//		Document document = null;
//		try {
//			document = XmlUtil.readXML(xmlFilePath);
//		} catch (Exception e) {
//			LogUtil.error(SerializeUtil.class,"格式不正确的xml文件路径:"+xmlFilePath);
//		}
//		Element  root = document.getRootElement();
//		Namespace ns = root.getNamespace();
//		Element  table = root.getChild("table",ns);
//		if(null == table)return null;
//		Element  data = table.getChild("data",ns);
//		//数据sql
//		String table_sql = null;
//		//modify by tracywindy 2013-08-21
//		String dbType = ResourceUtil.getDBType();
//		Element dbTypeData = data.getChild(dbType.toLowerCase(),ns);
//		if(null == dbTypeData){
//			dbTypeData = data.getChild("alldb",ns);
//		}
//		Element tableSql = dbTypeData.getChild("table_sql",ns);
//		//Element tableSql = data.getChild("table_sql",ns);
//		if(null!=tableSql)
//		{
//			table_sql =  tableSql.getTextTrim();
//		}
//		m.put("table_sql",table_sql);
//		//导出sql
//		String excel_sql = null;
//		Element excelSql = dbTypeData.getChild("excel_sql",ns);
//		//Element excelSql = data.getChild("excel_sql",ns);
//		if(null!=excelSql)
//		{
//			excel_sql =  excelSql.getTextTrim();
//		}
//		excel_sql = StringUtil.emptyOpt(excel_sql, table_sql);
//		m.put("excel_sql",excel_sql);
//		//数据源
//		String dataSource = null;
//		Element dataSourceChild = data.getChild("dataSource",ns);
//		if(null!=dataSourceChild)
//		{
//			dataSource =  dataSourceChild.getTextTrim();
//		}
//		dataSource = StringUtil.emptyOpt(dataSource, "dataSource");
//		m.put("dataSource",dataSource);
//		//是否在控制台打印sql
//		Element  showSql = data.getChild("show_sql",ns);
//		String   showSqlText = "false";
//		if(null!=showSql)
//		{
//			showSqlText =   showSql.getTextTrim();
//			if("true".equalsIgnoreCase(showSqlText))
//			{
//				showSqlText = "true";
//			}
//		}
//		m.put("show_sql",showSqlText);
//		//是否是预编译sql
//		Element  isPrepared = data.getChild("isPrepared",ns);
//		String   isPreparedText = "true";
//		if(null!=isPrepared)
//		{
//			isPreparedText =   isPrepared.getTextTrim();
//			if("false".equalsIgnoreCase(isPreparedText))
//			{
//				isPreparedText = "false";
//			}
//		}
//		m.put("isPrepared",isPreparedText);
//		//销毁资源
//		document = null;
//		return m;
//	}
//   //图形读写
//   public static Map<String,String> readChartInfoFromXmlFile(String xmlFilePath) throws Exception
//	{
//		Map<String,String> m = new HashMap<String,String>();
//		Document document = XmlUtil.readXML(xmlFilePath);
//		Element  root = document.getRootElement();
//		Namespace ns = root.getNamespace();
//		Element  chartElement = root.getChild("chart",ns);
//		if(null == chartElement)return null;
//		Element  data = chartElement.getChild("data",ns);
//		String chartAttributs = XmlUtil.getAttributesString(chartElement);
//		//modify by tracywindy 2013-08-21
//		String dbType = ResourceUtil.getDBType();
//		Element dbTypeData = data.getChild(dbType.toLowerCase(),ns);
//		if(null == dbTypeData){
//			dbTypeData = data.getChild("alldb",ns);
//		}
//		String sql = dbTypeData.getChildText("sql",ns);
//		//String sql = data.getChildText("sql",ns);
//		String flt = data.getChildText("flt",ns).trim();
//		String dataSource = StringUtil.emptyOpt(data.getChildText("dataSource"),"dataSource").trim();
//		//是否在控制台打印sql
//		Element  showSql = data.getChild("show_sql",ns);
//		String   showSqlText = "false";
//		if(null!=showSql)
//		{
//			showSqlText =   showSql.getTextTrim();
//			if("true".equalsIgnoreCase(showSqlText))
//			{
//				showSqlText = "true";
//			}
//		}
//		//是否是预编译sql
//		Element  isPrepared = data.getChild("isPrepared",ns);
//		String   isPreparedText = "true";
//		if(null!=isPrepared)
//		{
//			isPreparedText =   isPrepared.getTextTrim();
//			if("false".equalsIgnoreCase(isPreparedText))
//			{
//				isPreparedText = "false";
//			}
//		}
//		m.put("isPrepared",isPreparedText);
//		
//		//其他chart特性
//		String other     = "";
//		Element otherChild = chartElement.getChild("other",ns);
//		if(null!=otherChild)
//		{
//			other =  otherChild.getText();
//		}
//		m.put("dataSource",dataSource);
//		m.put("chartAttributs", chartAttributs);
//		m.put("sql", sql);
//		m.put("flt", flt);
//		m.put("dataSource", dataSource);
//		m.put("show_sql",showSqlText);
//		m.put("other", other);
//		//销毁资源
//		document = null;
//		return m;
//	}
//}
