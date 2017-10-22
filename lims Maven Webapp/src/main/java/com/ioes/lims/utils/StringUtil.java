package com.ioes.lims.utils;

public class StringUtil {
	/**
	 * 指定数字字串前，不够len位补0
	 *
	 * @param str
	 * @param len
	 * @return 2017年6月24日 by 周喜平
	 */
	public static String StrtoFullstr(String str,int len){
		String reString="";
		if (str.length()>=len){
			return str;
		}
		for(int i=0;i<len-str.length();i++){
			reString+="0";
		}
		return reString+str;
	}
	/**
	 * d convert to 2 point,转变成小数点后2位
	 *
	 * @param d
	 * @return 2017年7月6日 by 周喜平
	 */
	public static double fix2(double d){
		
		 return Math.round(d*100)/100d;
	}
}
