package components;

import com.badlogic.ashley.core.Component;
import main.Randomizer;

public class Hunger implements Component {
	private int level;
	private int maxLevel;

	public Hunger(int level, int maxLevel) {
		this.level = level;
		this.maxLevel = maxLevel;
	}

	public void feed(int level){
		if(this.level + level > maxLevel){
			this.level = maxLevel;
		}else{
			this.level += level;
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void decrement(){
		level -= 1;
	}

	@Override
	public String toString() {
		return "Hunger{" +
				"level=" + level +
				", maxLevel=" + maxLevel +
				'}';
	}
}
