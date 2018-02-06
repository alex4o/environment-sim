package components;

import com.badlogic.ashley.core.Component;

import java.util.Optional;

public class Move implements Component {
	Optional<Location> next;

	// ideas
	int speed;
	int energy;

	public Move(Location next) {
		this.next = Optional.of(next);
	}

	public Move() {
		this.next = Optional.empty();
	}

	public Optional<Location> getNext() {
		return next;
	}

	public void setNext(Location next) {
		this.setNext(Optional.ofNullable(next));
	}

	public void setNext(Optional<Location> next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "Move{" +
				"next=" + next +
				'}';
	}
}
