package org.amhungry;

public class RCFormula {
	
	private double price;
	private double distance;
	private double vote;
	
	//Weight point
	private final double wp = 1.5; //weight of price
	private final double wd = 1.2; //weight of distance
	private final double wv = 1.0; //weight of vote
	
	//Standard value
	private double std_price = Standard_Value.getSTD_price();
	private double std_distance = Standard_Value.getSTD_dist();
	private double max_vote = Standard_Value.getMax_vote();
	
	public RCFormula (Restaurant restaurant){
		this.price = restaurant.getPrice();
		this.distance = restaurant.getDistance();
		this.vote = restaurant.getVote();
	}
	
	private double calculate_result(){
		return calculate_p() + calculate_d() + calculate_v();
	}
	
	private double calculate_p(){
		return (price/std_price)*wp;
	}
	private double calculate_d(){
		return (distance/std_distance)*wd;
	}
	
	private double calculate_v(){
		return (vote/max_vote)*wv;
	}
	
	public double getRC(){
		return calculate_result();
	}
}
