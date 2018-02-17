package main;

import java.util.Random;

public class Weather {

	boolean isCloudy = false;

	public boolean isRainging() {
		Random random = Randomizer.getRandom();
		if(random.nextDouble() > 0.5 && isCloudy){
			return true;
		}else{
			return false;
		}

	}

}
