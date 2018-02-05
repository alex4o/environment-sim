import java.awt.*;

public class Tile {

	private Color color;
	private double elevation;
	private boolean walkable = true;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	@Override
	public String toString() {
		return "Tile{" +
				"color=" + color +
				", elevation=" + elevation +
				", walkable=" + walkable +
				'}';
	}
}
