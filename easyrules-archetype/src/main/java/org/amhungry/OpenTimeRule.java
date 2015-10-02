package org.amhungry;

import java.time.LocalTime;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule(name = "AmHungry rule", description = "Find the best restaurant")
public class OpenTimeRule {
	
	private Restaurant restaurant;
	
	public OpenTimeRule(Restaurant restaurant){
		this.restaurant = restaurant;
	}
	
	@Condition
    public boolean isOpen() {
        return restaurant.getOpen_time().compareTo(LocalTime.now()) >= 0 && restaurant.getClose_time().compareTo(LocalTime.now()) < 0;
    }
	
}
