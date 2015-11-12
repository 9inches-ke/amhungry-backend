package org.amhungry;


import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

@Rule(name = "Type rule", description = "Select restaurant from type of restaurant")
public class TypeRule {
	
	private Restaurant restaurant;
	
	public void setInput(Restaurant restaurant){
		this.restaurant = restaurant;
	}
	
	@Condition
    public boolean isNotOpen() {
		System.out.println("Type Rule");
        return !(restaurant.getType().equals(Standard_Value.getFilter_type()));
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
