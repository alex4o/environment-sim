package components;

import com.badlogic.ashley.core.Component;

public class Life implements Component {
	boolean alive = true;

	public Life() {
	}

	public Life(boolean alive) {
		this.alive = alive;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive() {
		this.alive = true;
	}

	public void  setDead(){
		this.alive = false;
	}

	@Override
	public String toString() {
		return "Life{" +
				"alive=" + alive +
				'}';
	}
}
