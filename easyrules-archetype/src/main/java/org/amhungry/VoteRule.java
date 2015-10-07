package org.amhungry;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

@Rule(name = "Vote rule", description = "Select restaurant more than user vote")
public class VoteRule {
	
	private Restaurant restaurant;
	private double filter_vote;
	
	public void setInput(Restaurant restaurant){
		this.restaurant = restaurant;
	}
	
	public void setFilterVote(double filter_vote){
		this.filter_vote = filter_vote;
	}
	
	@Condition
    public boolean isNotPopular() {
//		System.out.println(restaurant.getOpen_time().compareTo(LocalTime.now()) <= 0 && restaurant.getClose_time().compareTo(LocalTime.now()) > 0);
//		System.out.println(restaurant.getOpen_time() + " " + restaurant.getClose_time() + " " + LocalTime.now());
		System.out.println("Vote Rule");
        return !(restaurant.getVote() >= filter_vote);
    }
	
	@Action
	public void removeRestaurant(){
		System.out.println("Vote Rule - Remove");
		Launcher.removeRestaurant(restaurant);
	}
	
	@Priority
	public int getPriority(){
		return 4;
	}
	
}
