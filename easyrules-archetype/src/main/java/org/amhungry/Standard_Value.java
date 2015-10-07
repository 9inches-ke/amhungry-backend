package org.amhungry;

public class Standard_Value {
	
	private static double std_price = 80; //baht
	private static double std_distance = 1; //km
	private static double max_vote = 5; //5 stars
	private static String filter_type;
	
	public static double getSTD_price(){
		return std_price;
	}
	public static void setSTD_price(double price){
		std_price = price;
	}
	
	public static double getSTD_distance(){
		return std_distance;
	}
	public static void setSTD_distance(double distance){
		std_distance = distance;
	}
	
	public static double getMax_vote(){
		return max_vote;
	}
	
	public static void setFilter_type(String type){
		filter_type = type;
	}
	public static String getFilter_type(){
		return filter_type;
	}
}
