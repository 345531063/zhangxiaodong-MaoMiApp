package cn.com.yibin.maomi.init.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import org.springframework.web.context.support.WebApplicationContextUtils;


public class InitListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("..............初始化数据参数.........");
		ServletContext  sc  = servletContextEvent.getServletContext();
	}
}
