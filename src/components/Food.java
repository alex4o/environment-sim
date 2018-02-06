package components;

import com.badlogic.ashley.core.Component;
import main.Randomizer;

public class Food implements Component{

	private FoodType eat;

	public Food(FoodType type) {
		this.eat = type;
	}

//	public Food(int maxLevel, FoodType type) {
//		this(0, maxLevel, type);
//	}



	public void setEatType(FoodType eat) {
		this.eat = eat;
	}

	public FoodType getEatType() {
		return eat;
	}

	@Override
	public String toString() {
		return "Food{" +
				"eat=" + eat +
				'}';
	}
}
