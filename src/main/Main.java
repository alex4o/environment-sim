package main;

import com.badlogic.ashley.core.Engine;
import systems.*;

public class Main {

	/**
	 *  Adds the different Systems which implements the logic of the simulation
	 *
	 */

	public static void main(String[] args){
		Engine engine = new Engine();

		Simulator simulator = new Simulator(engine, (int)(89), (int)(160));
		engine.addSystem(new AgeSystem());
		engine.addSystem(new HungerSystem());
		engine.addSystem(new BreedSystem(engine));
		engine.addSystem(new PlantBreedSystem(engine));
		engine.addSystem(new DiseaseSystem());
		engine.addSystem(new UndeseasedSystem());
		engine.addSystem(new SleepSystem());

		engine.addSystem(new FoodSystem());

		engine.addSystem(new MovementSystem());
		engine.addSystem(new DeadSystem(engine));



	}
}
