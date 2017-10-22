package com.ioes.lims.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.mapping.Array;

import com.ioes.lims.beans.Permoney;

public class CalcuInterest {
	double cost;//cost,本金
	double ratio;//interest利率
	int instalment;//分期付款的期数，value is 12,24,36,48,60
	String startDate;//format is yyyy-MM-dd
	public CalcuInterest(double cost,double ratio,int instalment,String startDate){
		this.cost=cost;
		this.ratio=ratio;
		this.instalment=instalment;
		this.startDate=startDate;
	}	
	
	/**	  
	 java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");   
  	double   d=3.14159;   
  	System.out.println(df.format(d));
	
	 */
	public List<Permoney> methodTow(){
		if (instalment==0) return null;
		double amount;//repay amount,每月应还款金额
		double tem=Math.pow((1+ratio), instalment);
		amount=cost*ratio*tem/(tem-1);
		return cal(cost,amount,instalment,ratio);
	}
	public List<Permoney> cal(double cost,double amount,int instalment,double ratio){
		List<Permoney> relist=new ArrayList<Permoney>();
		double Fi=0.0;//Interest per issue
		double Ei=0.0;//cost per issue
		double SumEi=0.0;//sum of cost		
		double tem=Math.pow((1+ratio), instalment);
		amount=Math.round(amount);
		List<String> permonthList=DateUtil.nextDay(startDate, instalment);
		for (int i = 1; i <= instalment; i++) {
			Permoney p=new Permoney();
			p.setInstalmentno(i);
			SumEi+=Ei;
			Fi=Math.round((cost-SumEi)*ratio);			
			p.setInterest(Fi);
			Ei=amount-Fi;
			p.setCost(Ei);
			p.setAmount(amount);
			p.setPayDay(permonthList.get(i-1));
			relist.add(p);
		}
		return relist;
	}
	public List<Permoney> methodOne(){
		if (instalment==0) return null;
		double amount=0.0;//repay amount,每月应还款金额
		List<Permoney> relist=new ArrayList<Permoney>();
		switch (instalment) {
		case 12:	
			amount=cost*(1+0.066)/instalment;
			break;
		case 24:	
			amount=cost*(1+0.13)/instalment;
			break;
		case 36:	
			amount=cost*(1+0.2)/instalment;
			break;
		case 48:	
			amount=cost*(1+0.264)/instalment;
			break;
		case 60:	
			amount=cost*(1+0.33)/instalment;
			break;	
		default:
			break;
		}
		return cal(cost,amount,instalment,ratio);
	}
	/**
	 * calculate the overdue interest
	 *
	 * @param overamount 逾期金额
	 * @param overday 逾期天数
	 * @param ratiomon 月利率
	 * @return 2017年7月6日 by 周喜平
	 */
	public static double calOverInteresst(double overamount,int overday,double ratiomon){
		//DecimalFormat   df=new  DecimalFormat("#.##");
		double d=overamount*(ratiomon/30)*1.5*overday;
		//System.out.println(d);
		return Math.round(d*100)/100d;
	}
	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	/**
	 * @return the ratio
	 */
	public double getRatio() {
		return ratio;
	}
	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	/**
	 * @return the instalment
	 */
	public int getInstalment() {
		return instalment;
	}
	/**
	 * @param instalment the instalment to set
	 */
	public void setInstalment(int instalment) {
		this.instalment = instalment;
	}
	
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public static void main(String args[]){
		CalcuInterest calcuInterest=new CalcuInterest(100000, 0.01, 60,"2018-3-10");
		List<Permoney> list=calcuInterest.methodTow();
		double t=0,t2=0;
		for (Permoney permoney : list) {
			t+=permoney.getCost();
			t2+=permoney.getInterest();
			System.out.println(permoney.getAmount()+":"+permoney.getInstalmentno()+"cost:"+permoney.getCost()+" interest:"+permoney.getInterest()+permoney.getPayDay());
		}
		System.out.println(t+","+t2);
		System.out.println(CalcuInterest.calOverInteresst(1007,7,0.24));
		double d1=StringUtil.fix2(123.2342342342);
		double d2=StringUtil.fix2(123.2332342342);
		System.out.println(d1==d2);
	}
}
