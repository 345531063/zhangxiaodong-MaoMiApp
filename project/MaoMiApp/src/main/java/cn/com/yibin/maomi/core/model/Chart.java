package cn.com.yibin.maomi.core.model;

import java.util.List;
import java.util.Map;

import cn.com.yibin.maomi.core.util.QueryUtil;

public class Chart 
{
   private    String chartId;
   private    String chartTitle;
   private String fltName;
   private String dataSourceName;
   private String sourceSql;
   private List<List<String>> datas;
   private String chartAttributs; 
   private String fltLoadingDirectoryFilePath;
   private String chartXmlLoadingDirectoryFilePath;
   private String chartXmlChartFileNameOrPath;
   private Map<String,String> model;
   private String other;
   private String showSql;
   
	public String getFltName() {
		return fltName;
	}
	public void setFltName(String fltName) {
		this.fltName = fltName;
	}
	public String getSourceSql() {
		return sourceSql;
	}
	public void setSourceSql(String sourceSql) {
		this.sourceSql = sourceSql;
	}
	public String getTargetSql() 
	{
		try 
		{
			return QueryUtil.getQueryString(model, this.sourceSql);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "";
	}
	public List<List<String>> getDatas() {
		return datas;
	}
	public void setDatas(List<List<String>> datas) {
		this.datas = datas;
	}
	public String getChartAttributs() {
		return chartAttributs;
	}
	public void setChartAttributs(String chartAttributs) {
		this.chartAttributs = chartAttributs;
	}
	public String getFltLoadingDirectoryFilePath() {
		return fltLoadingDirectoryFilePath;
	}
	public void setFltLoadingDirectoryFilePath(String fltLoadingDirectoryFilePath) {
		this.fltLoadingDirectoryFilePath = fltLoadingDirectoryFilePath;
	}
	public String getChartXmlLoadingDirectoryFilePath() {
		return chartXmlLoadingDirectoryFilePath;
	}
	public void setChartXmlLoadingDirectoryFilePath(
			String chartXmlLoadingDirectoryFilePath) {
		this.chartXmlLoadingDirectoryFilePath = chartXmlLoadingDirectoryFilePath;
	}
	public String getChartXmlChartFileNameOrPath() {
		return chartXmlChartFileNameOrPath;
	}
	public void setChartXmlChartFileNameOrPath(String chartXmlChartFileNameOrPath) {
		this.chartXmlChartFileNameOrPath = chartXmlChartFileNameOrPath;
	}
	public String getXmlChartFileFullPathWithoutFileSeparator()
	{
		return (this.chartXmlLoadingDirectoryFilePath+this.chartXmlChartFileNameOrPath).replaceAll("(\\\\)|(/)|(%5C)","").replaceAll("\\s","");
	}
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	public String getDataSourceName() {
		return dataSourceName;
	}
	public void setModel(Map<String,String> model) {
		this.model = model;
	}
	public Map<String,String> getModel() {
		return model;
	}
	
	 /**
	 * @param other the other to set
	 **/
	
	public void setOther(String other) {
		this.other = other;
	}
	
	 /**
	 * other
	 * @return the other
	 * @since 1.0.0
	 **/
	
	public String getOther() {
		return other;
	}
	
	 /**
	 * @param showSql the showSql to set
	 **/
	
	public void setShowSql(String showSql) {
		this.showSql = showSql;
	}
	
	 /**
	 * showSql
	 * @return the showSql
	 * @since 1.0.0
	 **/
	
	public String getShowSql() {
		return showSql;
	}
	public boolean isShowSql()
	{
		return "true".equalsIgnoreCase(this.showSql);
	}
	public String getChartId() {
		return chartId;
	}
	public void setChartId(String chartId) {
		this.chartId = chartId;
	}
	public String getChartTitle() {
		return chartTitle;
	}
	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}
	
}
