package cn.com.yibin.maomi.core.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class ExceptionUtil {
   public static String getExceptionPrintInfo(Throwable e) {
	   StringWriter sw = null;
	   String exceptionPrintInfo = "";
	   try{
		   sw = new StringWriter();
		   e.printStackTrace(new PrintWriter(sw, true));  
		   exceptionPrintInfo = sw.toString();  
	   }finally{
		   if(null != sw){
		     try {
				sw.close();
			} catch (IOException e1) {
				sw = null;
				e1.printStackTrace();
			}
		   }
		   sw = null;
	   }
	   return exceptionPrintInfo;
   }
}
