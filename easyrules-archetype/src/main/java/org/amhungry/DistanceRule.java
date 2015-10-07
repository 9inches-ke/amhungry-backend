package org.amhungry;

import java.time.LocalTime;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

@Rule(name = "Distance rule", description = "Select restaurant in distance")

public class DistanceRule {
	private Restaurant restaurant;
	private double std_dist = Standard_Value.getSTD_dist();
	
	
	public void setInput(Restaurant restaurant){
		this.restaurant = restaurant;
	}
	
	public void setSTD_distance(Object object){
		this.std_dist = (Double) object;
	}
	
	@Condition
    public boolean isNotNear() {
		System.out.println("Dist rule");
		System.out.println(restaurant.getDistance() + " " + std_dist);
        return !(restaurant.getDistance() <= std_dist);
    }
	
	@Action
	public void removeRestaurant(){
		System.out.println("Dist rule - Remove");
		Launcher.removeRestaurant(restaurant);
	}
	
	@Priority
	public int getPriority(){
		return 3;
	}
}
