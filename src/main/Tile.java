package main;

import java.awt.*;

public class Tile {

	enum TileType {
		Water,
		Ground,
		Grass,
		Rock
	}

	private TileType type;
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

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}
}
