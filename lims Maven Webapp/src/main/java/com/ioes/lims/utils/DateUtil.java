package com.ioes.lims.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sias.util.dateUtils;

public class DateUtil extends dateUtils{
	/**
	 * 根据startDate算出下个月同一天的日期
	 * startDate format is yyyy-MM-dd
	 * return format is  yyyy-MM-dd
	 *
	 * @param startDate
	 * @return 2017年7月2日 by 周喜平
	 */
	public static List<String> nextDay(String startDate,int count){
		List<String> reList=new ArrayList<String>();
		try {			
			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date=dateFormat.parse(startDate);
			cal.setTime(date);
			int tem1=cal.get(Calendar.DAY_OF_MONTH);
			String nextd="";
			int inputMon = cal.get(Calendar.MONTH);		
			for (int i = 1; i <= count; i++) {
				//cal.set(Calendar.MONTH, inputMon+i);
				cal.add(Calendar.MONTH,i);
				nextd=dateFormat.format(cal.getTime());				
				String lastDay=dateUtils.getEndDateString(nextd);
				int tem2=Integer.parseInt((lastDay.split("-"))[2]);
				if (tem1>tem2){
					reList.add(lastDay);
				}else{
					reList.add(nextd);
				}
				cal.setTime(date);
			}
			return reList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	/**
	 * get network date time
	 *
	 * @param webUrl
	 * @return 2017年7月11日 by 周喜平
	 */
	public static String getNetworkTime(String webUrl) {
        try {
            URL url = new URL(webUrl);
            URLConnection conn = url.openConnection();
            conn.connect();
            long dateL = conn.getDate();
            Date date = new Date(dateL);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
	/**
	 * get locale date time
	 *
	 * @param webUrl
	 * @return 2017年7月11日 by 周喜平
	 */
	public static String getLocalTime() {
        try {
            
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(date);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return "";
    }
	public static void main(String args[])  {
		/////////////////////////get network time
        String webUrl2 = "http://www.baidu.com";// 百度
        String webUrl3 = "http://www.taobao.com";// 淘宝
        String webUrl4 = "http://www.ntsc.ac.cn";// 中国科学院国家授时中心
        String webUrl5 = "http://www.360.cn";// 360
        String webUrl6 = "http://www.beijing-time.org";// beijing-time
        String webUrl7 = "http://www.163.com/";// 网易
        String webUrl8 = "https://www.tmall.com/";// 天猫
        String webUrl9 = "http://218.198.176.244";// etses
        
        System.out.println(getNetworkTime(webUrl2) + " [百度]");
        System.out.println(getNetworkTime(webUrl3) + " [淘宝]");
        System.out.println(getNetworkTime(webUrl4) + " [中国科学院国家授时中心]");
        System.out.println(getNetworkTime(webUrl5) + " [360安全卫士]");
        System.out.println(getNetworkTime(webUrl6) + " [beijing-time]");
        System.out.println(getNetworkTime(webUrl7) + " [网易]");
        System.out.println(getNetworkTime(webUrl8) + " [天猫]");
        System.out.println(getNetworkTime(webUrl9) + " [etses]");
        System.out.println(getLocalTime() + " [local]");
        ///////////end get network time
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		
		try {
		
			
			String t="23:07:01;03:03:03;05:05:05";//;03:03:03;05:05:05
			String ts[]=t.split(";");
			System.out.println(ts.length);
			System.out.println(dateFormat.format(dateFormat.parse("22:56:20")).equals(dateFormat.format(new Date())));
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
        List<String> tt=DateUtil.nextDay("2017-1-29", 10);
		for (int i=0;i<tt.size();i++) {
			System.out.println(tt.get(i));
		}
	}
}
