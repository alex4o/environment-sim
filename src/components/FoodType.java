package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodType {
	public enum FoodEnum {
		ANIMAL,
		PLANT,
		MUSHROOM,
		CORPSE,
		GROUND,
		WATER;

		public FoodType getType(String ... things) {
			return new FoodType(this, things);
		}
	}

	public FoodEnum type;
	public List<String> things;
	private FoodType(){

	}

	private FoodType(FoodEnum type, String ... things){
		this.things = Arrays.asList(things);
		this.type = type;
	}

//	public static from()
}
