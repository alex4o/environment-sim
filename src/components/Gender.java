package components;

public class Gender {
	public enum GenderType {
		MALE,
		FEMALE,
		CIS,
		NONBINARY,
		APACHEHELICOPTER,
		VENN,
		POLY,
		PAN
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
}
