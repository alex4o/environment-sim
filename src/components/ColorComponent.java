package components;

import com.badlogic.ashley.core.Component;

import java.awt.*;

public class ColorComponent implements Component {
	private Color color;

	public ColorComponent(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "ColorComponent{" +
				"color=" + color +
				'}';
	}
}
