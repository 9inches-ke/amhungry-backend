package org.amhungry;

import java.sql.Time;

public class RCFormula {
	
	private double rc_point;
	
	private double price;
	private double distance;
	
	//Weight point
	private final int wp = 2;
	private final int wd = 1;
	
	//Standard value
	private final double std_price = 80;
	private final double std_distance = 500;
	
	public RCFormula (Restaurant restaurant) {
		this.price = price;
		this.distance = restaurant.getDistance();
	}
	
	private void calculate(){
		rc_point = diff_p()*wp + diff_d()*wd;
	}
	
	private double diff_p(){
		return std_price - price;
	}
	private double diff_d(){
		return std_distance - distance;
	}
	
	public double getRC(){
		return rc_point;
	}
	
}
