package main;

import actors.Animal;
import actors.Fox;
import actors.Rabbit;
import com.badlogic.ashley.core.*;
import com.flowpowered.noise.module.source.Perlin;
import components.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.09;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.20;
	// The probability that a coyote will be created in any given grid position.
	private static final double COYOTE_CREATION_PROBABILITY = 0.2;
	// The probability that a raccoon will be created in any given grid position.
	private static final double RACCOON_CREATION_PROBABILITY = 0.2;

    // List of animals in the field.
    private List<Animal> animals;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;

    private Engine engine;
    // A noise generator for the world map
    private Perlin perlin = new Perlin();
	private Timer timer;
	public DayTime time = DayTime.Midday;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(new Engine(), DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param heigth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(Engine engine, int heigth, int width)
    {
        if(width <= 0 || heigth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
			heigth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        animals = new ArrayList<>();
        field = Field.newInstance(heigth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(heigth, width, this);
        view.setColor(Rabbit.class, Color.ORANGE);
        view.setColor(Fox.class, Color.RED);

		perlin.setFrequency(10);
		perlin.setLacunarity(20);
		perlin.setOctaveCount(4);
		perlin.setSeed(234435);
		this.engine = engine;


		engine.addEntityListener(Family.all(Location.class, ColorComponent.class).get(), new EntityListener() {
			@Override
			public void entityAdded(Entity entity) {
				Location location = entity.getComponent(Location.class);
				field.place(entity, location);
			}

			@Override
			public void entityRemoved(Entity entity) {
				Location location = entity.getComponent(Location.class);

				if(field.getObjectAt(location) == entity) {
					field.clear(location);
				}else{
//					System.out.println("You are trying to remove something else!!");
				}
			}
		});
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }

    public void stop() {
		timer.stop();
	}

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
		final int endSteps = step + numSteps;
		timer = new Timer(33, (ActionEvent event) -> {
			if(step < endSteps) {
				simulateOneStep();
			}else{
				timer.stop();
			}

		});
//		timer.setInitialDelay(0);
		timer.setRepeats(true);
		timer.start();

//		for(int step = 1; step <= numSteps && view.isViable(field); step++) {
//            simulateOneStep();
//            view.repaint();
//            // delay(60);   // uncomment this to run more slowly
//        }
    }

    public static List<Entity> newEntities = new ArrayList<>();

	/**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for newborn animals.
        // Let all rabbits act.
//        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
//            Animal animal = it.next();
//
////            animal.act(newAnimals);
//            if(! animal.isAlive()) {
//                it.remove();
//            }
//        }


        engine.update(1);

        // Add the newly born foxes and rabbits to the main lists.
//        animals.addAll(newAnimals);
		for(Entity entity : newEntities){
			engine.addEntity(entity);
		}



		newEntities.clear();
        view.showStatus(step, field);
        time = time.next();
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
		buildWorld();


		populate();
        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    private void buildWorld(){
		for(int row = 0; row < field.getDepth(); row++) {
			for(int col = 0; col < field.getWidth(); col++) {
				double level = perlin.getValue(row / 100.0, col / 100.0, 10);

//				System.out.printf("Noise: %f %d %d\n", level, x, y);
				Tile tile = new Tile();

				if (level <= 0.89) {
					tile.setColor(new Color(0, 0, (int) ((level) * 255)));
					tile.setWalkable(false);
					tile.setType(Tile.TileType.Water);

				} else if (level >= 1) {
					double h = Math.abs(-level + 1);

					int color = (int)((h * 15 ) * 150) + 100;
					if (color > 255) color = 255;

					tile.setColor(new Color(color, color, color));
					tile.setWalkable(false);
					tile.setType(Tile.TileType.Rock);

				} else {
					if (level > 1) level = 1;

					tile.setColor(new Color(0, (int) (level * 255), 0));
					tile.setType(Tile.TileType.Grass);
				}
				tile.setElevation(level);
				field.setTile(row,col,tile);

//				g.fillRect(x * xScale, y * yScale, xScale, yScale);
			}
		}
	}
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
            	if(!field.getTile(row, col).isWalkable()){
            		if(field.getTile(row, col).getType() == Tile.TileType.Rock){
						if(rand.nextDouble() <= 0.5 && field.plusLocations(new Location(row, col), 1).stream().filter(location -> field.getTile(location).getType() == Tile.TileType.Grass).count() > 0) {
							Entity rabbit = Models.createMoss(true, new Location(row, col));
							engine.addEntity(rabbit);
						}
					}
            		continue;
				}

                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
					Entity fox = Models.createFox(true, new Location(row, col) );
//                    Fox fox = new Fox(true, field, location);
//                    animals.add(fox);
                	engine.addEntity(fox);
            	}
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
					Entity rabbit = Models.createRabbit(true, new Location(row, col));
					engine.addEntity(rabbit);

				} else if(rand.nextDouble() <= RACCOON_CREATION_PROBABILITY) {
					Entity entity = Models.createRaccoon(true, new Location(row, col));
					engine.addEntity(entity);

				} else if(rand.nextDouble() <= COYOTE_CREATION_PROBABILITY) {
					Entity entity = Models.createCoyote(true, new Location(row, col));
					engine.addEntity(entity);

				}else if(field.plusLocations(new Location(row, col), 1).stream().filter(location -> field.getTile(location).getType() == Tile.TileType.Water).count() > 1){
					if(rand.nextDouble() <= 0.5) {
						Entity rabbit = Models.createPlant(true, new Location(row, col));
						engine.addEntity(rabbit);
					}
				}
                // else leave the location empty.
            }
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
