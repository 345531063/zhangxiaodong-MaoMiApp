package cn.com.yibin.maomi.core.util;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerUtil 
{
	private static Configuration cfg = new Configuration();
	public static void init()
	{
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
		cfg.setNumberFormat("#");
		cfg.setEncoding(Locale.getDefault(),"UTF-8");
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.clearTemplateCache();
	}
	public static Configuration getConfiguration(String fltSourceDirectoryPath) 
	{
		
		if(ResourceUtil.isDebug())
		{
		   init();
		}
		try {
			cfg.setDirectoryForTemplateLoading(new File(FileUtil.getFilePathString(fltSourceDirectoryPath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	   return cfg;
	}
}
