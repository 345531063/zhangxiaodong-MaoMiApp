package cn.com.yibin.maomi.core.tx.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.web.multipart.MultipartFile;

import cn.com.yibin.maomi.core.constant.ExcelVersionEnum;
import cn.com.yibin.maomi.core.constant.OperTypeEnum;
import cn.com.yibin.maomi.core.model.Table;

public interface TableService extends TxService
{
	public String getTableJsonData(String xmlFileNameOrPath, Map<String, String> model) throws Exception;
	public String getTreeJsonData(String xmlFileNameOrPath, Map<String, String> model) throws Exception;
	public void readTabletXmlInfo(Table table, boolean isOnlyExportByXmlFile) throws Exception;
	public void doExportExcel(String xmlFileNameOrPath, Map<String, String> model, boolean isExportTitle, OutputStream os, boolean isOnlyExportByXmlFile) throws Exception;
	public String importExcel(MultipartFile multipartFile, Map<String, String> modelData) throws Exception;
	public void dealExcelDatas(ExcelVersionEnum excelVersionEnum, String complexHeadersStr, String columnsStr, List<Map<String, String>> datas, Map<String, String> dataTypesMapping, String fileTitleName, boolean isExportTitle, OutputStream os) throws Exception;
	public JSONArray getJsonArrayData(String xmlFileNameOrPath, Map<String, String> model) throws Exception;
	public void updateRemoteOper(Map<String, String> model, OperTypeEnum operType) throws Exception;
}
