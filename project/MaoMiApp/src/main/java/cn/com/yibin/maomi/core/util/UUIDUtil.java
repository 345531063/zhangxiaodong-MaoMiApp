package cn.com.yibin.maomi.core.util;

import java.util.UUID;
  /**
 *@author tracywindy
 *@createDate 2010-3-6 下午01:52:08 
 **/
public class UUIDUtil
{
	public static String getUUID()
	{
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", ""); 
	}
}
