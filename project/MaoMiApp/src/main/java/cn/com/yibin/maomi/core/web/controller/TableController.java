package cn.com.yibin.maomi.core.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.yibin.maomi.core.tx.service.TableService;

@Controller(value = "tableController")
@RequestMapping(value = "/**/table")
public class TableController  {

//	@Resource(name = "tableService")
//	private TableService tableService;
//	@Override
//	public TableService getTxService() {
//		return tableService;
//	}
//
//	@RequestMapping(value = "/getTableData.action")
//	public String getTableData(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map<String, String> model = QueryUtil.getRequestParameterMapByAjax(request);
//		String pageIndexStr = model.get("pageIndex");
//		if(null != pageIndexStr){
//			String pageSizeStr = model.get("limit");
//			model.put("start",Integer.parseInt(pageIndexStr)*Integer.parseInt(pageSizeStr)+"");
//		}
//		String xmlFileNameOrPath = model.get("xmlFileName");
//		String jsonData = this.tableService.getTableJsonData(xmlFileNameOrPath, model);
//		this.ajaxReturn(response, jsonData);
//		return null;
//	}
//
//	@RequestMapping(value = "/importExcel.action")
//	public String importExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map<String, String> modelData = QueryUtil.getRequestParameterMapNoAjax(request);
//		String currentTableId = modelData.get("currentTableId");
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		MultipartFile multipartFile = multipartRequest.getFile("tableImportExcel");
//		String returnInfo = this.tableService.importExcel(multipartFile, modelData);
//		String ajaxCallBackScript = "<script type='text/javascript'>window.parent.importExcelFormCallBack('" + returnInfo + "','" + currentTableId
//				+ "');</script>";
//		this.htmlReturn(response, ajaxCallBackScript);
//		return null;
//	}
//
//	@RequestMapping(value = "/getTreeData.action")
//	public String getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map<String, String> model = QueryUtil.getRequestParameterMapByAjax(request);
//		String xmlFileNameOrPath = model.get("xmlFileName");
//		String jsonData = this.tableService.getTreeJsonData(xmlFileNameOrPath, model);
//		this.ajaxReturn(response, jsonData);
//		return null;
//	}
//
//	@RequestMapping(value = "/getExcelExportData.action")
//	public String getExcelExportData(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map<String, String> model = QueryUtil.getRequestParameterMapNoAjax(request);
//		String xmlFileNameOrPath = model.get("xmlFileName");
//		String fileTitleName = model.get("excelTitleName");
//		String exportExcelVersion = StringUtil.emptyOpt(model.get("exportExcelVersion"), "2007");
//		String extent = ".xlsx";
//		if (exportExcelVersion.trim().equals("2003")) {
//			extent = ".xls";
//		}
//		fileTitleName += "_" + DateUtil.getSystemDateTime().replaceAll("\\s", "-").replaceAll(":", "-") + extent;
//		response.reset();
//		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//		String browserType = model.get("BrowserType");
//		fileTitleName = QueryUtil.getFilenameAssociateBrowser(browserType, fileTitleName);
//		response.setHeader("Content-disposition", "attachment; filename=" + fileTitleName);
//		boolean isExportTitle = !("false".equals(model.get("isExportTitle")));
//		boolean isOnlyExportByXmlFile = "true".equalsIgnoreCase(StringUtil.emptyOpt(model.get("isOnlyExportByXmlFile")).trim());
//		this.tableService.doExportExcel(xmlFileNameOrPath, model, isExportTitle, response.getOutputStream(), isOnlyExportByXmlFile);
//		return null;
//	}
//	@RequestMapping(value = "/addRemoteOper.action")
//	@ResponseBody
//	public JsonReturnResult addRemoteOper(HttpServletRequest request, HttpServletResponse response) throws Exception 
//	{
//		Map<String, String>   model          =  QueryUtil.getRequestParameterMapByAjaxNoSessionAttributes(request);
//		JsonReturnResult      returnResult   =  new JsonReturnResult(JsonReturnResultTypeEnum.SUCCESS,"");
//		try {
//			this.tableService.updateRemoteOper(model, OperTypeEnum.ADD);
//		} catch (Exception e) {
//			e.printStackTrace();
//			returnResult.setReturnType(JsonReturnResultTypeEnum.FAILURE);
//			returnResult.setContent(e.getMessage());
//		}
//		return returnResult;
//	}
//	@RequestMapping(value = "/editRemoteOper.action")
//	@ResponseBody
//	public JsonReturnResult editRemoteOper(HttpServletRequest request, HttpServletResponse response) throws Exception 
//	{
//		Map<String, String>     model          =  QueryUtil.getRequestParameterMapByAjaxNoSessionAttributes(request);
//		JsonReturnResult        returnResult   =  new JsonReturnResult(JsonReturnResultTypeEnum.SUCCESS,"");
//		try {
//			this.tableService.updateRemoteOper(model, OperTypeEnum.EDIT);
//		} catch (Exception e) {
//			e.printStackTrace();
//			returnResult.setReturnType(JsonReturnResultTypeEnum.FAILURE);
//			returnResult.setContent(e.getMessage());
//		}
//		return returnResult;
//	}
//	@RequestMapping(value = "/copyRemoteOper.action")
//	@ResponseBody
//	public JsonReturnResult copyRemoteOper(HttpServletRequest request, HttpServletResponse response) throws Exception 
//	{
//		Map<String, String>      model           =  QueryUtil.getRequestParameterMapByAjaxNoSessionAttributes(request);
//		JsonReturnResult         returnResult    =  new JsonReturnResult(JsonReturnResultTypeEnum.SUCCESS,"");
//		try {
//			this.tableService.updateRemoteOper(model, OperTypeEnum.COPY);
//		} catch (Exception e) {
//			e.printStackTrace();
//			returnResult.setReturnType(JsonReturnResultTypeEnum.FAILURE);
//			returnResult.setContent(e.getMessage());
//		}
//		return returnResult;
//	}
//	@RequestMapping(value = "/removeRemoteOper.action")
//	@ResponseBody
//	public JsonReturnResult removeRemoteOper(HttpServletRequest request, HttpServletResponse response) throws Exception 
//	{
//		Map<String, String>  model         =  QueryUtil.getRequestParameterMapByAjaxNoSessionAttributes(request);
//		JsonReturnResult     returnResult  =  new JsonReturnResult(JsonReturnResultTypeEnum.SUCCESS,"");
//		try {
//			this.tableService.updateRemoteOper(model, OperTypeEnum.REMOVE);
//		} catch (Exception e) {
//			e.printStackTrace();
//			returnResult.setReturnType(JsonReturnResultTypeEnum.FAILURE);
//			returnResult.setContent(e.getMessage());
//		}
//		return returnResult;
//	}
}
