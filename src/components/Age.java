package components;

import com.badlogic.ashley.core.Component;
import main.Randomizer;

import java.util.Random;

public class Age implements Component {
	private int age;
	private int maxAge;

	public Age(int age) {
		this.age = age;
		this.maxAge = 100;
	}

	public Age(boolean random, int maxAge) {
		Random rand = Randomizer.getRandom();
		if(random) {
			this.age = rand.nextInt(maxAge);
		}else{
			this.age = 0;
		}
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
