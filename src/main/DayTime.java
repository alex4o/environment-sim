package main;

public enum DayTime {
	Morning(0),
	Midday(1),
	Noon(2),
	Evening(3),
	Night(4);


	int value;
	DayTime(int value){
		this.value = value;
	}

	public DayTime next(){
		switch (this) {
			case Morning:
				return Midday;
			case Midday:
				return Noon;
			case Noon:
				return Evening;
			case Evening:
				return Night;
			case Night:
				return Morning;
		}
		return null;
	}
}
