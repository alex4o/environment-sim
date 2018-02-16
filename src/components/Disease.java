package components;

import com.badlogic.ashley.core.Component;

public class Disease implements Component {
	public enum DiseaseType {
		Toxocariasis(10),
		Generic(20);
		public final int deterioration;

		DiseaseType(int deterioration){

			this.deterioration = deterioration;
		}
	}

	private DiseaseType type;
	private int deterioration;
	private int currentDeterioration;

	public Disease(DiseaseType type, int deterioration) {
		this.type = type;
		this.deterioration = deterioration;
	}

	public Disease(DiseaseType type) {

		this.type = type;
		this.deterioration = type.deterioration;
	}

	public DiseaseType getType() {
		return type;
	}

	public void setType(DiseaseType type) {
		this.type = type;
	}

	public int getDeterioration() {
		return deterioration;
	}

	public void setDeterioration(int deterioration) {
		this.deterioration = deterioration;
	}

	public int getCurrentDeterioration() {
		return currentDeterioration;
	}

	public void setCurrentDeterioration(int currentDeterioration) {
		this.currentDeterioration = currentDeterioration;
	}

	@Override
	public String toString() {
		return "Disease{" +
				"type=" + type +
				", deterioration=" + deterioration +
				", currentDeterioration=" + currentDeterioration +
				'}';
	}
}
