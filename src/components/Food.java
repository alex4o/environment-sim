package components;

import com.badlogic.ashley.core.Component;

public class Food implements Component{
	private int level;
	private int maxLevel;
	private FoodType eat;

	public Food(int level, int maxLevel, FoodType type) {
		this.level = level;
		this.maxLevel = maxLevel;
		this.eat = type;
	}

	public Food(int maxLevel, FoodType type) {
		this(0, maxLevel, type);
	}

	public void feed(int level){
		if(this.level + level > maxLevel){
			this.level = maxLevel;
		}else{
			this.level += level;
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public FoodType getEatType() {
		return eat;
	}

	public void setEatType(FoodType eat) {
		this.eat = eat;
	}

	@Override
	public String toString() {
		return "Food{" +
				"level=" + level +
				", maxLevel=" + maxLevel +
				", eat=" + eat +
				'}';
	}
}
