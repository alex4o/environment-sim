package main;

import java.awt.*;

public class Tile {

	public enum TileType {
		Water,
		Ground,
		Grass,
		Rock
	}

	private TileType type;
	private Color color;
	private Color statusColor;
	private double elevation;
	private boolean walkable = true;
	public int clearIn;

	public Object data;

	public Weather weather = new Weather();

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}



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

	public Color getStatusColor() {
		return statusColor;
	}

	public void setStatusColor(Color statusColor) {
		this.statusColor = statusColor;
	}
}
