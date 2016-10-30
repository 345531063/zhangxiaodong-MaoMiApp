package cn.com.yibin.maomi.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
public class TokenReplaceTextManipulator
{
	private String startToken = "{";
	private String endToken = "}";
	private String nullValue = "";
	/**
	 * @see net.mlw.vlh.adapter.util.TextManipulator#manipulate(String, Object)
	 */
	public  StringBuffer manipulate(StringBuffer text, Map<String,String> model)
	{
         manipulateString(text,model,false);
         return text;
	}
    @SuppressWarnings("rawtypes")
	public List[] manipulateString(StringBuffer text, Map model,boolean isSqlFilter){
    	List<String> replaceKeyList = new ArrayList<String>(); 
    	List<String> replaceValueList = new ArrayList<String>(); 
		if (model == null)
		{
			model = Collections.EMPTY_MAP;
		}
        int textLen = text.length();
		//Replace any [key] with the value in the whereClause Map.
		for (int end = 0, start; ((start = text.toString().indexOf(startToken, end)) >= 0);)
		{
			end = text.toString().indexOf(endToken, start);
			String key = text.substring(start + 1, end);
			Object modelValue = model.get(key);
			String value = (modelValue == null) ? nullValue : modelValue.toString();
			if(isSqlFilter){
				//text.replace(start, end + 1, ":"+key);
				boolean isFound = false;
				char leftOneChar = ' ',rightOneChar = ' ';
				//char leftTwoChar = ' ',rightTwoChar = ' ';
				String valueLeftAdd= "",valueRightAdd ="";
				
				if(0 <= (start - 1 )){
					leftOneChar = text.charAt(start-1);
				}
				if(textLen >= (end + 1 + 1) ){
					rightOneChar = text.charAt(end+1);
				}
				/*if(0 <= (start - 1 - 1 )){
					leftTwoChar = text.charAt(start - 1 - 1);
				}
				if(textLen >= (end + 1 + 1 + 1) ){
					rightTwoChar = text.charAt(end + 1 + 1);
				}*/
				if(('\''== leftOneChar) && ('\'' == rightOneChar))//'{key}'
				{
					text.replace(start - 1, end + 1+1, " ? ");
					isFound = true;
				}
				else if( /*('\''== leftTwoChar)  && */('%'== leftOneChar) && ('%' == rightOneChar) /*&& ('\'' == rightTwoChar)*/){//'%{key}%'
					text.replace(start - 1 /* - 1*/, end + 1 + 1 /*+ 1*/, " ? ");
					isFound = true;
					valueLeftAdd = "%";
					valueRightAdd = "%";
				}
				else if( /*('\''== leftTwoChar)  && */('%'== leftOneChar)/* && ('\'' == rightOneChar)*/){//'%{key}'
					text.replace(start - 1/* - 1*/, end + 1 /*+ 1*/, " ? ");
					isFound = true;
					valueLeftAdd = "%";
				}
				else if(/* ('\''== leftOneChar) && */('%' == rightOneChar) /*&& ('\'' == rightTwoChar)*/){//'{key}%'
					text.replace(start - 1 , end + 1 + 1 /*+ 1*/, " ? ");
					isFound = true;
					valueRightAdd = "%";
				}
				value=(valueLeftAdd+value+valueRightAdd);
				if(!isFound) {
				   text.replace(start, end + 1, " ? ");
				}
				replaceKeyList.add(key);
				replaceValueList.add(value);
				/*text.replace(start, end + 1, value);*/
				/******
				text.replace(start, end + 1, "?");
				replaceKeyList.add(key);
				replaceValueList.add(value);
				***/
			}else{
			   text.replace(start, end + 1, value);
			}
			end -= (key.length() + 2);
		}

		return new List[]{replaceKeyList,replaceValueList};
    }
	/**
	 * @param endToken The endToken to set.
	 */
	public void setEndToken(String endToken)
	{
		this.endToken = endToken;
	}

	/**
	 * @param startToken The startToken to set.
	 */
	public void setStartToken(String startToken)
	{
		this.startToken = startToken;
	}
	
	/**
	 * @param nullValue The nullValue to set.
	 */
	public void setNullValue(String nullValue)
	{
		this.nullValue = nullValue;
	}
}

