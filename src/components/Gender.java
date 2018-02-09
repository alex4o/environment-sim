package components;

import com.badlogic.ashley.core.Component;

public class Gender implements Component {
	public enum GenderType {
		Male,
		Female
	}

	GenderType gender;

	public Gender(GenderType gender) {
		this.gender = gender;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Gender{" +
				"gender=" + gender +
				'}';
	}
}
