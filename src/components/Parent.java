package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;


public class Parent implements Component{
	Entity parent;

	public Parent(Entity parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(Component c : parent.getComponents()){
			builder.append("\t");
			builder.append(c);
			builder.append("\n");

		}

		return "Parent{\n" +
				builder +
				'}';
	}
}
