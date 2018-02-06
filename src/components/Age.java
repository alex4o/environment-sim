package components;

import com.badlogic.ashley.core.Component;
import main.Randomizer;

import java.util.Random;

public class Age implements Component {
	private int age;
	private int maxAge;

	public Age(int age, int maxAge) {
		this.age = age;
		this.maxAge = maxAge;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getMaxAge() {
		return maxAge;
	}

	@Override
	public String toString() {
		return "Age{" +
				"age=" + age +
				", maxAge=" + maxAge +
				'}';
	}
}
