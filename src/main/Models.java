package main;


import com.badlogic.ashley.core.Entity;
import components.*;

import java.awt.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Random;

public class Models {
	public static Age createAge(boolean random, int maxAge){
		Random rand = Randomizer.getRandom();
		int age = 0;
		if(random) {
			age = rand.nextInt(maxAge);
		}
		return new Age(age, maxAge);
	}

	public static Hunger createHunger(boolean random, int maxLevel){
		int level = maxLevel;

		if(random) {
			level = Randomizer.getRandom().nextInt(maxLevel);
		}
		return new Hunger(level, maxLevel);

	}

	public static Entity createFox(boolean randomAge, Location location) {
		Entity fox = new Entity();
		fox.add(createAge(randomAge, 150));
		fox.add(createHunger(randomAge, 9));
		fox.add(new Food(FoodType.ANIMAL));
		fox.add(new Move());
		fox.add(location);
		fox.add(new ColorComponent(Color.RED));
		fox.add(new Life());
		fox.add(new Breed(15, 0.08, 2));
		fox.add(new Name("Fox"));
		return fox;
	}

	public static Entity createRabbit(boolean randomAge, Location location) {
		Entity rabbit = new Entity();
		rabbit.add(createAge(randomAge, 40));
		rabbit.add(new Food(FoodType.PLANT));
		rabbit.add(location);
		rabbit.add(new Move());
		rabbit.add(new ColorComponent(Color.ORANGE));
		rabbit.add(new Life());
		rabbit.add(new Breed(5, 0.06, 4));
		rabbit.add(new Name("Rabbit"));

		return rabbit;
	}

	public static Entity createPlant(boolean random ,Location location) {
		Entity plant = new Entity();
		plant.add(new ColorComponent(new Color(120,200,125,150)));
		plant.add(new Name("Plant"));
		plant.add(location);

//		plant.add(new FoodValue(5, 10));
		return plant;
	}

	public static Entity createMoss(boolean random ,Location location) {
		Entity plant = new Entity();
		plant.add(new ColorComponent(new Color(120,255,125,100)));
		plant.add(new Name("Moss"));
		plant.add(location);

//		plant.add(new FoodValue(5, 10));
		return plant;
	}

	/**
	 * @param name the name of the animal to e crated
	 * @param random do you want randomization
	 * @param location where should the animal be located in the field
	 * @return the "minted" animal
	 */
	public static Entity createAnimal(String name, boolean random, Location location) {
		MethodHandles.Lookup lookup = MethodHandles.lookup();

		try {
			MethodHandle handle = lookup.findStatic(Models.class, "create" + name, MethodType.methodType(Entity.class, new Class[] { boolean.class, Location.class }));
			return (Entity) handle.invokeWithArguments(random, location);

		} catch (NoSuchMethodException e) {
			System.out.println("Fix your names!!");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return null;
	}
}
