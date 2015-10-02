package org.amhungry;

public class RCFormula {
	
	private double price;
	private double distance;
	
	//Weight point
	private final double wp = 1.5;
	private final double wd = 1;
	
	//Standard value
	private double std_price = 80;
	private double std_distance = 0.5;
	
	public RCFormula (Restaurant restaurant) {
		this.price = restaurant.getPrice();
		this.distance = restaurant.getDistance();
	}
	
	private double calculate_result(){
		return calculate_p() + calculate_d();
	}
	
	private double calculate_p(){
		return (price/std_price)*wp;
	}
	private double calculate_d(){
		return (distance/std_distance)*wd;
	}
	
	public double getRC(){
		return calculate_result();
	}
	
}
