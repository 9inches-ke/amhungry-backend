package org.amhungry;


import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

@Rule(name = "Type rule", description = "Select restaurant from type of restaurant")
public class TypeRule {
	
	private Restaurant restaurant;
	private String std_type;
	
	public void setInput(Restaurant restaurant){
		this.restaurant = restaurant;
	}
	
	public void setFilterType(Object object){
		std_type.equals(object);
	}
	
	@Condition
    public boolean isNotOpen() {
		System.out.println("Type Rule");
        return !(restaurant.getType().equals(std_type));
    }
	
	@Action
	public void removeRestaurant(){
		System.out.println("Type Rule - Remove");
		Launcher.removeRestaurant(restaurant);
	}
	
	@Priority
	public int getPriority(){
		return 0;
	}
	
}
