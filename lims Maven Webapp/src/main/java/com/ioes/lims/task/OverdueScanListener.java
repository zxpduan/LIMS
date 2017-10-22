package com.ioes.lims.task;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sias.util.SerialUtil;

/**
 * 启动ReadOrderTask线程任务
 * 
 * @author 周喜平 2015-10-15
 */
public class OverdueScanListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(OverdueScanListener.class);
	Thread readTaskthread;
	ServletContext sc;

	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("ReadOrderListener contextDestroyed starting");
		if (readTaskthread != null) {
			readTaskthread.interrupt();
			readTaskthread = null;
		}
		logger.info("ReadOrderListener contextDestroyed ends");

	}

	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		// int interval=Integer.parseInt(sc.getInitParameter("sendInterval"));
		// int totalCount =Integer.parseInt(sc.getInitParameter("totalCount"));
		long interval = Long.parseLong(System.getProperty("sd.Interval"));
		String attime=System.getProperty("sd.scantime");
		
		ReadOrderTask run = new ReadOrderTask(interval,sc);
		run.setAttime(attime);
		
		TaskObserver listen = new TaskObserver();
		run.addObserver(listen);
		run.start();
		System.out.println("********start up overdue scan task!*********");

	}

}
