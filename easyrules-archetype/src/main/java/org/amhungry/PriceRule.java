package org.amhungry;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

@Rule(name = "OpenTime rule", description = "Select opening restaurant in current time")
public class PriceRule {
	
	private Restaurant restaurant;
	private double std_price = Standard_Value.getSTD_price();
	public void setInput(Restaurant restaurant){
		this.restaurant = restaurant;
	}
	
	public void setSTD_price(Object object){
		this.std_price = (Double) object;
	}

	@Condition
    public boolean isNotCheap() {
		System.out.println("Price rule");
        return restaurant.getPrice() <= std_price;
    }
	
	@Action
	public void removeRestaurant(){
		Launcher.removeRestaurant(restaurant);
	}
	
	@Priority
	public int getPriority(){
		return 2;
	}
	
}
