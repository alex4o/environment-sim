package main;

import com.badlogic.ashley.core.Engine;
import systems.*;

public class Main {

	public static void main(String[] args){
		Engine engine = new Engine();

		Simulator simulator = new Simulator(engine, 89/2, 160/2);
		engine.addSystem(new AgeSystem());
		engine.addSystem(new HungerSystem());
		engine.addSystem(new BreedSystem(engine));

		engine.addSystem(new FoodSystem());

		engine.addSystem(new MovementSystem());
		engine.addSystem(new DeadSystem(engine));


	}
}
