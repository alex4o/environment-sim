package main;

import components.Location;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single animal.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class Field
{
    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();
    
    // The depth and width of the field.
    private int depth, width;
    // Storage for the animals.
    private Object[][] field;
    private Tile[][] map;

    private static Field instance;
    public static Field getInstance() {
    	if(instance == null){
    		instance = new Field(100, 100);
		}

		return instance;
	}

	public static Field newInstance(int depth, int width){
		instance = new Field(depth, width);
		return instance;
	}

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    private Field(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
        field = new Object[depth][width];
        map = new Tile[depth][width];
    }

    public void setTile(int row, int col, Tile tile){
		map[row][col] = tile;
	}

	public Tile getTile(int row, int col){
		return map[row][col];
	}
	public Tile getTile(Location location){
		return map[location.getRow()][location.getCol()];
	}

    /**
     * Empty the field.
     */
    public void clear()
    {
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }
    
    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    public void clear(Location location)
    {
        field[location.getRow()][location.getCol()] = null;
    }
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Object animal, int row, int col)
    {
        place(animal, new Location(row, col));
    }
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param location Where to place the animal.
     */
    public void place(Object animal, Location location)
    {
        field[location.getRow()][location.getCol()] = animal;
    }
    
    /**
     * Return the animal at the given location, if any.
     * @param location Where in the field.
     * @return The animal at the given location, or null if there is none.
     */
    public <T> T  getObjectAt(Location location)
    {
        return (T)getObjectAt(location.getRow(), location.getCol());
    }
    
    /**
     * Return the animal at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The animal at the given location, or null if there is none.
     */
    public <T> T getObjectAt(int row, int col)
    {
        return (T)field[row][col];
    }
    
    /**
     * Generate a random location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location randomAdjacentLocation(Location location)
    {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }
    
    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null && getTile(next.getRow(), next.getCol()).isWalkable()) {
                free.add(next);
            }
        }
        return free;
    }
    
    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location)
    {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

	/**
	 * The same as the previous one but not shuffled
	 * @param location The location from which to generate adjacencies.
	 * @param size The number of blocks from the center
	 * @return A list of locations adjacent to that given.
	 */
	public List<Location> adjacentLocations(Location location, int size, boolean empty)
	{
		assert location != null : "Null location passed to adjacentLocations";
		// The list of locations to be returned.
		List<Location> locations = new LinkedList<>();

		int row = location.getRow();
		int col = location.getCol();

		for(int r = row - size; r <= row + size; r++){
			for(int c = col - size; c <= col + size; c++){
				Location next = new Location(r, c);
				if(inBounds(next) && !next.equals(location)){
					if(!empty && this.getObjectAt(next) == null){
						continue;
					}
					locations.add(next);
				}
			}
		}


		return locations;
	}

	public List<Location> adjacentLocations(Location location, int size){
		return adjacentLocations(location, size, true);
	}


	/**
	 * The same as the previous one but not shuffled
	 * @param location The location from which to generate adjacencies.
	 * @param size The number of blocks from the center
	 * @return A list of locations adjacent to that given.
	 */
	public List<Location> plusLocations(Location location, int size)
	{
		assert location != null : "Null location passed to adjacentLocations";
		// The list of locations to be returned.
		List<Location> locations = new LinkedList<>();
		int row = location.getRow();
		int col = location.getCol();

		for(int r = row - size; r <= row + size; r++){
			Location next = new Location(r, col);
			if(inBounds(next) && !next.equals(location)){
				locations.add(next);
			}
		}

		for(int c = col - size; c <= col + size; c++){
			Location next = new Location(row, c);
			if(inBounds(next) && !next.equals(location)){
				locations.add(next);
			}
		}

		return locations;
	}


	/**
	 * @param location the location that will be checked
	 * @return Is the location in the boundaries of the field
	 */
	private boolean inBounds(Location location) {
		int row = location.getRow();
		int col = location.getCol();

		if(row >= this.depth){
			return false;
		}else if(col >= this.width){
			return false;
		}else if(col < 0 || row < 0){
			return false;
		}else {
			return true;
		}
	}

    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth()
    {
        return width;
    }
}
