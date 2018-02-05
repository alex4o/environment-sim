package components;

import com.badlogic.ashley.core.Component;

import java.util.Optional;

public class Move implements Component {
	Location current;
	Optional<Location> next;

	public Move(Location current, Location next) {
		this.current = current;
		this.next = Optional.of(next);
	}

	public Move(Location current) {
		this.current = current;
		this.next = Optional.empty();
	}

	public Location getCurrent() {
		return current;
	}

	public void setCurrent(Location current) {
		this.current = current;
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
				"current=" + current +
				", next=" + next +
				'}';
	}
}
