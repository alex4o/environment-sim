package main;


import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import components.*;

import java.awt.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Optional;
import java.util.Random;

public class Models {

	/**
	 * Sets the age randomly for the different animals
	 * @param age, can set a specific age for the animals
	 * @param maxAge, sets the maxAge which the animal can be
	 * @return
	 */


	public static Age createAge(Optional<Integer> age, int maxAge){
		Random rand = Randomizer.getRandom();
		return age.map(integer -> new Age(integer, maxAge)).orElseGet(() -> new Age(rand.nextInt(maxAge), maxAge));
	}

	/**
	 * Sets the Hunger level of the animals between upto the maxLevel
	 * @param random, set true if you want the  the animals to have a random hunger level
	 * @param maxLevel, sets the maximum hunger levels
	 * @return
	 */

	public static Hunger createHunger(boolean random, int maxLevel){
		int level = maxLevel;

		if(random) {
			level = Randomizer.getRandom().nextInt(maxLevel);
		}
		return new Hunger(level, maxLevel);
	}

	/**
	 *
	 * @param type, can specify what type of disease you want the animal to have.
	 * @param lasting
	 * @return
	 */

	public static Disease createDisease(Optional<Disease.DiseaseType> type, int lasting){
		Random rand = Randomizer.getRandom();
		return type.map(Disease::new).orElseGet(() -> new Disease(Disease.DiseaseType.values()[rand.nextInt(Disease.DiseaseType.values().length)]));
	}


	/**
	 *
	 * @param prob, sets the probability for creating the different Genders
	 * @return
	 */
	public static Gender createGender(double prob){
		return new Gender(Randomizer.getRandom().nextDouble() <= prob ? Gender.GenderType.Female : Gender.GenderType.Male);
	}

	/**
	 *
	 * @param random, specify whether you want a random age and hunger level assigned
	 * @param location, the tile that the animal is on
	 * @return
	 */

	public static Entity createFox(boolean random, Location location) {
		Entity fox = new Entity();
		fox.add(createAge(random ? Optional.empty() : Optional.of(0), 150 * 5));
		fox.add(createHunger(random, 90*5));
		fox.add(new Food(FoodType.FoodEnum.ANIMAL.getType("Rabbits")));
		fox.add(new Move());
		fox.add(location);
		fox.add(new ColorComponent(Color.RED));
		fox.add(new Life());
		fox.add(new Breed(1, 0.9, 2));
		fox.add(new Name("Fox"));
		fox.add(createGender(0.5));

		if(Randomizer.getRandom().nextDouble() < 0.3) {
			// TODO: Make the ground clean after some amount of time
			fox.add(createDisease(Optional.of(Disease.DiseaseType.Toxocariasis), 10));
		}

		return fox;
	}

	/**
	 *
	 * @param random, specify whether you want a random age and hunger level assigned
	 * @param location, the tile that the animal is on
	 * @return
	 */

	public static Entity createRabbit(boolean random, Location location) {
		Entity rabbit = new Entity();
		rabbit.add(createAge(random ? Optional.empty() : Optional.of(0), 20 * 5));
		rabbit.add(createHunger(random, 400*5));

		rabbit.add(new Food(FoodType.FoodEnum.PLANT.getType()));
		rabbit.add(location);
		rabbit.add(new Move());
		rabbit.add(new ColorComponent(Color.ORANGE));
		rabbit.add(new Life());
		rabbit.add(new Breed(1, 0.70, 4));
		rabbit.add(new Name("Rabbit"));
		rabbit.add(createGender(0.5));

		return rabbit;
	}


	/**
	 *
	 * @param random, specify whether you want a random age and hunger level assigned
	 * @param location, the tile that the animal is on
	 * @return
	 */

	public static Entity createCoyote(boolean random, Location location) {
		Entity coyote = new Entity();

		coyote.add(createAge(random ? Optional.empty() : Optional.of(0), 10 * 5));
		coyote.add(createHunger(random, 80*5));
		coyote.add(new Food(FoodType.FoodEnum.ANIMAL.getType("Raccoon", "Rabbit" ,"Fox")));
		coyote.add(location);
		coyote.add(new Move());
		coyote.add(new ColorComponent(Color.BLACK));
		coyote.add(new Life());
		coyote.add(new Breed(1, 0.80, 4));
		coyote.add(new Name("Coyote"));
		coyote.add(createGender(0.5));

		if(Randomizer.getRandom().nextDouble() < 0.5) {
			// TODO: Make the ground clean after some amount of time
			coyote.add(createDisease(Optional.of(Disease.DiseaseType.Generic), 10));
		}

		return coyote;
	}

	/**
	 *
	 * @param random, specify whether you want a random age and hunger level assigned
	 * @param location, the tile that the animal is on
	 * @return
	 */

	public static Entity createRaccoon(boolean random, Location location) {
		Entity raccoon = new Entity();

		raccoon.add(createAge(random ? Optional.empty() : Optional.of(0), 60 * 5));
		raccoon.add(createHunger(random, 100*5));
		raccoon.add(new Food(FoodType.FoodEnum.PLANT.getType("Plant")));
		raccoon.add(location);
		raccoon.add(new Move());
		raccoon.add(new ColorComponent(Color.PINK));
		raccoon.add(new Life());
		raccoon.add(new Breed(1, 0.90, 4));
		raccoon.add(new Name("Raccoon"));
		raccoon.add(createGender(0.5));


		return raccoon;
	}

	/**
	 * @param location, the tile that the animal is on
	 * @return
	 */
	public static Entity createPlant(boolean random ,Location location) {
		Entity plant = new Entity();
		plant.add(new ColorComponent(new Color(120,200,125,150)));
		plant.add(new Name("Plant"));
		plant.add(location);
		plant.add(createAge(Optional.empty(), 200*5));
		plant.add(new Breed(10, 0.80, 4));

		plant.add(new FoodValue(5, 10));
		return plant;
	}

	/**
	 *
	 * @param location, the tile that the animal is on
	 * @return
	 */
	public static Entity createMoss(boolean random ,Location location) {
		Entity plant = new Entity();
		plant.add(new ColorComponent(new Color(120,255,125,100)));
		plant.add(new Name("Moss"));
		plant.add(location);
		plant.add(createAge(Optional.empty(), 200*5));
		plant.add(new Breed(10, 0, 4));

		plant.add(new FoodValue(5, 10));
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

	public static <T extends Component> Optional<T> get(Entity entity, ComponentMapper<T> mapper){
		if(mapper.has(entity)) {
			return Optional.ofNullable(mapper.get(entity));
		}else{
			return Optional.empty();
		}
	}
}
