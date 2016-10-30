package cn.com.yibin.maomi.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogUtil {
	public static void trace(Class<?> currentLogClass,Object logTrace){
    	Log log = LogFactory.getLog(currentLogClass);
    	if(log.isTraceEnabled()){
    		log.trace(logTrace);
    	}
    	log = null;
	}
    public static void debug(Class<?> currentLogClass,Object logDebug){
    	Log log = LogFactory.getLog(currentLogClass);
    	if(log.isDebugEnabled()){
    		log.debug(logDebug);
    	}
    	log = null;
    }
    public static void info(Class<?> currentLogClass,Object logInfo){
    	Log log = LogFactory.getLog(currentLogClass);
    	if(log.isInfoEnabled()){
    		log.info(logInfo);
    	}
    	log = null;
    }
    public static void warn(Class<?> currentLogClass,Object logWarn){
    	Log log = LogFactory.getLog(currentLogClass);
    	if(log.isWarnEnabled()){
    	    log.warn(logWarn);
    	}
    	log = null;
    }
    public static void error(Class<?> currentLogClass,Object logError){
    	Log log = LogFactory.getLog(currentLogClass);
    	if(log.isErrorEnabled()){
    		log.error(logError);
    	}
    	log = null;
    }
    public static void fatal(Class<?> currentLogClass,Object logFatal){
    	Log log = LogFactory.getLog(currentLogClass);
    	if(log.isFatalEnabled()){
    		log.fatal(logFatal);
    	}
    	log = null;
    }
    public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }

}
