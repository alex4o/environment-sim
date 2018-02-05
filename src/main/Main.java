package main;

import com.badlogic.ashley.core.Engine;
import systems.*;

public class Main {

	public static void main(String[] args){
		Engine engine = new Engine();

		Simulator simulator = new Simulator(engine, 89/2, 160/2);

		engine.addSystem(new FoodSystem(simulator.getField()));
		engine.addSystem(new AgeSystem());
		engine.addSystem(new MovementSystem(simulator.getField()));
		engine.addSystem(new BreedSystem(simulator.getField(), engine));
		engine.addSystem(new DeadSystem(engine));


	}
}
