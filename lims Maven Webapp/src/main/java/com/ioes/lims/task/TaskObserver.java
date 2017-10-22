package com.ioes.lims.task;

import java.util.Observable;
import java.util.Observer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * ReadOrderTask的观察者，发现逾期Task线程关闭就启动他
 * 
 * @author 周喜平
 * 2015-10-15
 */
public class TaskObserver implements Observer {
	private static final Log log = LogFactory.getLog(TaskObserver.class);
	@Override
	public void update(Observable o, Object arg) {
		 log.debug("overdue scan task dead!");
		 ReadOrderTask oldtask=(ReadOrderTask)o;
		 //shutdown service task
		 if (oldtask.Service!=null) oldtask.Service.shutdown();	
		 //start new service task
	     ReadOrderTask run = new ReadOrderTask(oldtask.interval,oldtask.sc);
	     run.setAttime(oldtask.getAttime());
	     run.addObserver(this);
	     run.start();
	     log.debug("overdue scan task restart...");
		
	}
	
}
