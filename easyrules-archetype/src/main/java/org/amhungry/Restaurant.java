package org.amhungry;

import java.time.LocalTime;

public class Restaurant implements Comparable{
	
	private String name;
	private double price; //This price is average in Baht
	private double distance; //meters
	private LocalTime open_time;
	private LocalTime close_time;
	private double vote;
	private String type;
	private double pos_x, pos_y; //Position of restaurant on GPS
	private double rc_point; //Restaurant choice point
	private int id;
	
	public Restaurant(int id,String name, double price, double distance, LocalTime open_time, LocalTime close_time, double vote, String type) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.distance = distance;
		this.open_time = open_time;
		this.close_time = close_time;
		this.vote = vote;
		this.type = type;
	} 
	
	public String getName() { return name; }
	public double getPrice() { return price; }
	public LocalTime getOpen_time() { return open_time; }
	public LocalTime getClose_time() { return close_time; }
	
	public void setRC(double d){ this.rc_point = d; }
	public double getRC(){ return rc_point; }
	
	//Using later
	public double getPos_x() { return pos_x; }
	public double getPos_y() { return pos_y; }
	
	public double getDistance() { return distance; }
	public void setDistance(double distance) { this.distance = distance; }
	
	public int getId(){
		return this.id;
	}
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	
	public double getVote() { return vote; }
	public void setVote(double vote) { this.vote = vote; }
	
	public String toString() {
		return getId() + " " + getName() + " " + getPrice() + " " +getDistance() + " " + getRC() + " " + getOpen_time() + " " + getClose_time();
	}
	public String toStringID() {
		return getId()+",";
	}
	public int compareTo(Object comparestu) {
		double compareage=((Restaurant)comparestu).getRC();
		int a = (int)(compareage*10000000);
		int b = (int)(this.rc_point*10000000);
        /* For Ascending order*/
        return b - a;
	}
	
}
