package com.ioes.lims.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextInitListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties props = new Properties(); 
        InputStream inputStream = null; 
        try { 
            inputStream = getClass().getResourceAsStream("/cfg.properties"); 
            props.load(inputStream);
            for(String prop:props.stringPropertyNames()){
            	if (System.getProperty(prop)==null){
            		System.setProperty(prop,props.getProperty(prop));
            	}
            }             
            System.out.println(System.getProperty("sd.Interval")+"+++++++++++++++++++++++++++++");
            System.out.println(System.getProperty("sd.scantime")+"+++++++++++++++++++++++++++++");
            
        } catch (IOException ex) { 
            ex.printStackTrace(); 
        } 
		
	}

}
