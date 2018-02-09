package components;

import com.badlogic.ashley.core.Component;

public class FoodValue implements Component{
	private int value;
	private int max;

	public FoodValue(int value, int max) {
		this.value = value;
		this.max = max;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public String toString() {
		return "FoodValue{" +
				"value=" + value +
				", max=" + max +
				'}';
	}


}
