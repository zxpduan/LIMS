package com.ioes.lims.beans;

public class Permoney {
	double cost;
	double interest;
	int instalmentno;//期数序号
	double amount;//repay for monthly月供
	String payDay;//format is yyyy-MM-dd
	
	/**
	 * @return the payDay
	 */
	public String getPayDay() {
		return payDay;
	}
	/**
	 * @param payDay the payDay to set
	 */
	public void setPayDay(String payDay) {
		this.payDay = payDay;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the instalmentno
	 */
	public int getInstalmentno() {
		return instalmentno;
	}
	/**
	 * @param instalmentno the instalmentno to set
	 */
	public void setInstalmentno(int instalmentno) {
		this.instalmentno = instalmentno;
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
	 * @return the interest
	 */
	public double getInterest() {
		return interest;
	}
	/**
	 * @param interest the interest to set
	 */
	public void setInterest(double interest) {
		this.interest = interest;
	}	
	
}
