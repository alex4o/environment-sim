package components;

import com.badlogic.ashley.core.Component;

public class Breed implements Component {
	private int breedAge;
	private double probability = 0.05;
	private int maxLittle = 2;

	public Breed(int breedAge, double probability, int maxLittle) {
		this.breedAge = breedAge;
		this.probability = probability;
		this.maxLittle = maxLittle;
	}

	public int getBreedAge() {
		return breedAge;
	}

	public void setBreedAge(int breedAge) {
		this.breedAge = breedAge;
	}

	public double getProbability() {

		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public int getMaxLittle() {
		return maxLittle;
	}

	public void setMaxLittle(int maxLittle) {
		this.maxLittle = maxLittle;
	}

	@Override
	public String toString() {
		return "Breed{" +
				"breedAge=" + breedAge +
				", probability=" + probability +
				", maxLittle=" + maxLittle +
				'}';
	}
}
