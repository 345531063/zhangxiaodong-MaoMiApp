package cn.com.yibin.maomi.core.util;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Locale;



public class StringUtil {
	public static boolean isEmpty(Object obj){
		return emptyOpt(obj).isEmpty();
	}
	public static boolean isBlank(Object obj){
		return emptyOpt(obj).isEmpty();
	}
	public static boolean isNotBlank(Object obj){
		return !isBlank(obj);
	}
	public static String  capitalize(String str){
		return str.substring(0,1).toUpperCase(Locale.US) + str.substring(1);
	}
	public static String emptyOpt(final Object obj, String... defaultValue) {
		String returnStr = "";
		if (obj != null) {
			Class<?> clazz = obj.getClass();
			if (clazz.isEnum()) {
				returnStr = ((Enum<?>) obj).name();
			} else {
				if(clazz.getSimpleName().equals("Integer")){
					returnStr = String.valueOf(obj);
				}else if(clazz.getSimpleName().equals("Double")){
					DecimalFormat df = new DecimalFormat("0.00"); 
					returnStr = df.format(obj);
				}else{
					returnStr =  obj.toString();
				}  
			}
		}
		if(returnStr.isEmpty()){
			for (String v : defaultValue) {
				returnStr = v;
			}
		}
		return returnStr;
	}

	public static String reverse(String orig) {
		char[] s = orig.toCharArray();
		int n = s.length - 1;
		int halfLength = n / 2;
		for (int i = 0; i <= halfLength; i++) {
			char temp = s[i];
			s[i] = s[n - i];
			s[n - i] = temp;
		}
		return new String(s); // 知道 char数组和String相互转化
	}

	@SuppressWarnings("unchecked")
	public static String join(Object items, String splitChar) {
		StringBuffer sb = new StringBuffer();
		if (items instanceof Collection) {
			Collection<Object> collectionItems = (Collection<Object>) items;
			int index = 0;
			for (Object collectionItem : collectionItems) {
				if (++index > 1) {
					sb.append(splitChar);
				}
				sb.append(StringUtil.emptyOpt(collectionItem));
			}
		} else {
			Object[] arrayItems = (Object[]) items;
			for (int i = 0; i < arrayItems.length; i++) {
				if (i > 0) {
					sb.append(splitChar);
				}
				sb.append(StringUtil.emptyOpt(arrayItems[i]));
			}
		}
		return sb.toString();
	}
	public static String getJsonString(Object str) {
		return emptyOpt(str).replaceAll("\"", "'").replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n").replaceAll("\\\\", "\\\\\\\\");
	}
	public static String getClearWhereSQL(String table_sql_temp) {
		/*
		 * Pattern p = Pattern.compile("\\s*where\\s*1\\s*=\\s*1\\s*and?", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		 * table_sql_temp = p.matcher(table_sql_temp).replaceAll(" _W_H_E_R_E_ ");
		 * p = Pattern.compile("\\s*where\\s*1\\s*=\\s*1\\s*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		 * table_sql_temp = p.matcher(table_sql_temp).replaceAll(" ");
		 * p = Pattern.compile(" _W_H_E_R_E_ ", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		 * table_sql_temp = p.matcher(table_sql_temp).replaceAll(" WHERE ");
		 */
		return table_sql_temp;
	}
}
