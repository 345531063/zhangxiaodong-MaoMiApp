package cn.com.yibin.maomi.core.util;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public class QueryUtil {
	private static FilterTextManipulator filterTextManipulator = null;
	private static TokenReplaceTextManipulator tokenReplaceTextManipulator = null;
	static {
		filterTextManipulator = new FilterTextManipulator();
		tokenReplaceTextManipulator = new TokenReplaceTextManipulator();
	}
    public static boolean isAjaxRequest(HttpServletRequest request){
    	 String requestType = request.getHeader("X-Requested-With");  
		 if((null != requestType)&&("XMLHttpRequest".equalsIgnoreCase(requestType)))
		 {
		    return true;
		 }
		 return false;
    }
	public static String getQueryStringNoAjax(HttpServletRequest request, String text) throws Exception {
		Map<String, String> model = getRequestParameterMapNoAjax(request);
		return getQueryString(model, text);
	}

	public static String getQueryStringByAjax(HttpServletRequest request, String text) throws Exception {
		Map<String, String> model = getRequestParameterMapByAjax(request);
		return getQueryString(model, text);
	}
    public static Map<String,String> getSessionAttributesMap(HttpServletRequest request){
    	Map<String, String> model = new HashMap<String, String>();
		HttpSession session = request.getSession(false);
		if(null == session)return model;
		Enumeration<?> attributeNamesEnum = session.getAttributeNames();
		while (attributeNamesEnum.hasMoreElements()) {
			Object keyObj = attributeNamesEnum.nextElement();
			if (keyObj instanceof String) {
				String key = StringUtil.emptyOpt(keyObj);
				if (!key.isEmpty()) {
					Object valueObj = session.getAttribute(key);
					String value = StringUtil.emptyOpt(valueObj);
					if (!value.isEmpty()) {
						model.put(key, value);
					}
				}
			}
		}
		return  model;
    }
    @SuppressWarnings("rawtypes")
	public static Map<String,Object> getQueryStringWithSqlFilter(Map<String, String> model, String text,boolean isSqlFilter){
    	Map<String,Object> returnMap = new HashMap<String,Object>();
//    	if (null == text)
//			return returnMap;
//    	
//    	//new List[]{replaceKeyList,replaceValueList}
//		StringBuffer sb_text = new StringBuffer(text);
//		filterTextManipulator.manipulate(sb_text, model);
//		if(isSqlFilter){
//			List[] keyValueListArr = tokenReplaceTextManipulator.manipulateString(sb_text, model, isSqlFilter);
//			returnMap.put("transferedKeyValueListArr", keyValueListArr);
//		}else{
//			tokenReplaceTextManipulator.manipulate(sb_text, model);
//		}
//		
//		String reText = sb_text.toString();
//		if ("ORACLE".equals(ResourceUtil.getDBType())) {
//			Matcher p = Matcher.compile("ISNULL\\s*\\(", Matcher.CASE_INSENSITIVE | Pattern.MULTILINE);
//			reText = p.(reText).replaceAll("NVL(");
//			/*
//			 * p = Pattern.compile("\\+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
//			 * reText = p.matcher(reText).replaceAll("||");
//			 */
//		} else if ("SQLSERVER".equals(ResourceUtil.getDBType())) {
//			Pattern p = M.compile("NVL\\s*\\(", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
//			reText = p.matcher(reText).replaceAll("ISNULL(");
//			p = Pattern.compile("\\|\\|", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
//			reText = p.matcher(reText).replaceAll("+");
//		}
//		returnMap.put("transferedText", reText.toString());
		return returnMap;
    }
	public static String getQueryString(Map<String, String> model, String text) throws Exception {
		return StringUtil.emptyOpt(getQueryStringWithSqlFilter(model,text,false).get("transferedText"));
	}
	public static Map<String, String> getRequestParameterMapNoAjax(HttpServletRequest request) throws Exception {
		Map<String,String> model = getSessionAttributesMap(request);
		request.setCharacterEncoding("UTF-8");
		model.putAll(getRequestParameterMapNoAjax((Map<String, String[]>) request.getParameterMap()));
		return model;
	}

	public static Map<String, String> getRequestParameterMapByAjax(HttpServletRequest request) throws Exception {
		Map<String,String> model = getSessionAttributesMap(request);
		model.putAll(getRequestParameterMapByAjax(request, null));
		return model;
	}
	public static Map<String, String> getRequestParameterMapByAjaxNoSessionAttributes(HttpServletRequest request) throws Exception {
		return getRequestParameterMapByAjax(request, null);
	}

	public static Map<String, String> getRequestParameterMapByAjax(HttpServletRequest request, String fieldPrefixRemove) throws Exception {
		Map<String,String> model = getSessionAttributesMap(request);
		model.putAll(getRequestParameterMapByAjax(request, fieldPrefixRemove, true));
		return model;
	}

	public static Map<String, String> getRequestParameterMapByAjax(HttpServletRequest request, String fieldPrefixRemove, boolean decodeURL)
			throws Exception {
		Map<String,String> model = getSessionAttributesMap(request);
		model.putAll(getRequestParameterMapByAjax((Map<String, String[]>) request.getParameterMap(), fieldPrefixRemove, decodeURL));
		return model;
	}

	public static Map<String, String> getRequestParameterMapNoAjax(Map<String, String[]> requestParameterMap) {

		if (requestParameterMap == null) {
			return new HashMap<String, String>();
		}

		Map<String, String> parameters = new HashMap<String, String>(requestParameterMap.size());

		for (Iterator<String> keys = requestParameterMap.keySet().iterator(); keys.hasNext();) {
			String key = keys.next();
			if (StringUtils.isBlank(key)) {
				continue;
			}
			String[] values;
			Object valuesObj = requestParameterMap.get(key);
			if (valuesObj instanceof String) {
				values = new String[] { (String) valuesObj };
			} else {
				values = (String[]) valuesObj;
			}
			Object value = ((values == null) ? null
					: (values.length == 1 ? (Object) values[0] : ("[" + StringUtil.join(values, ",") + "]")/* (Object) values */));

			String strValue = StringUtil.emptyOpt(value);
			if (strValue.isEmpty()) {
				parameters.put(key, null);
				continue;
			}
			parameters.put(key, strValue);
		}
		return parameters;
	}

	public static Map<String, String> getRequestParameterMapByAjax(Map<String, String[]> requestParameterMap) {
		return getRequestParameterMapByAjax(requestParameterMap, null);
	}

	public static Map<String, String> getRequestParameterMapByAjax(Map<String, String[]> requestParameterMap, String fieldPrefixRemove) {
		return getRequestParameterMapByAjax(requestParameterMap, fieldPrefixRemove, true);
	}

	public static Map<String, String> getRequestParameterMapByAjax(Map<String, String[]> requestParameterMap, String fieldPrefixRemove,
			boolean decodeURL) {
		String prefixRemove = "";
		if (fieldPrefixRemove != null && !"".equals(fieldPrefixRemove))
			prefixRemove = fieldPrefixRemove;

		if (requestParameterMap == null) {
			return new HashMap<String, String>();
		}

		Map<String, String> parameters = new HashMap<String, String>(requestParameterMap.size());

		for (Iterator<String> keys = requestParameterMap.keySet().iterator(); keys.hasNext();) {
			String key = keys.next();

			if (StringUtils.isBlank(key)) {
				continue;
			}
			String entityFieldName = key;
			if (!"".equals(prefixRemove) && entityFieldName.startsWith(prefixRemove)) {
				entityFieldName = key.substring(prefixRemove.length());
			}
			String[] values;
			Object valuesObj = requestParameterMap.get(key);
			if (valuesObj instanceof String) {
				values = new String[] { (String) valuesObj };
			} else {
				values = (String[]) valuesObj;
			}
			Object value = ((values == null) ? null
					: (values.length == 1 ? (Object) values[0] : ("[" + StringUtil.join(values, ",") + "]")/* (Object) values */));
			String strValue = StringUtil.emptyOpt(value);
			if (strValue.isEmpty()) {
				parameters.put(entityFieldName, null);
				continue;
			}
			try {

				if (decodeURL) {
					//parameters.put(entityFieldName, URLDecoder.decode(strValue, "UTF-8"));
					parameters.put(entityFieldName, strValue);
				} else {
					parameters.put(entityFieldName, strValue);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return parameters;
	}

	public static String getFilenameAssociateBrowser(String brower, String fileName) throws Exception {
		if ("firefox".equalsIgnoreCase(brower)) {
			fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
		} else if ("chrome".equalsIgnoreCase(brower)) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else if ("safari".equalsIgnoreCase(brower)) {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		} else {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		}
		return fileName;
	}
}
