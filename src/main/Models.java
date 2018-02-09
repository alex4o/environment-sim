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


	public static Age createAge(Optional<Integer> age, int maxAge){
		Random rand = Randomizer.getRandom();
		if(age.isPresent()) {
			return new Age(age.get(), maxAge);

		}else{
			return new Age(rand.nextInt(maxAge), maxAge);
		}
	}

	public static Hunger createHunger(boolean random, int maxLevel){
		int level = maxLevel;

		if(random) {
			level = Randomizer.getRandom().nextInt(maxLevel);
		}
		return new Hunger(level, maxLevel);
	}

	public static Disease createDisease(Optional<Disease.DiseaseType> type, int lasting){
		Random rand = Randomizer.getRandom();
		if(type.isPresent()) {
			return new Disease(type.get(), lasting);

		}else{
			return new Disease(Disease.DiseaseType.values()[rand.nextInt(Disease.DiseaseType.values().length)], lasting);
		}
	}

	public static Gender createGender(double prob){
		return new Gender(Randomizer.getRandom().nextDouble() <= prob ? Gender.GenderType.Female : Gender.GenderType.Male);
	}

	public static Entity createFox(boolean random, Location location) {
		Entity fox = new Entity();
		fox.add(createAge(random ? Optional.empty() : Optional.of(0), 150 * 5));
		fox.add(createHunger(random, 9));
		fox.add(new Food(FoodType.FoodEnum.ANIMAL.getType("Rabbits")));
		fox.add(new Move());
		fox.add(location);
		fox.add(new ColorComponent(Color.RED));
		fox.add(new Life());
		fox.add(new Breed(15, 0.1, 2));
		fox.add(new Name("Fox"));
		fox.add(createGender(0.5));

		if(Randomizer.getRandom().nextDouble() < 0.3) {
			// TODO: Make the ground clean after some amount of time
			fox.add(createDisease(Optional.of(Disease.DiseaseType.Toxocariasis), 10));
		}

		return fox;
	}

	public static Entity createRabbit(boolean random, Location location) {
		Entity rabbit = new Entity();
		rabbit.add(createAge(random ? Optional.empty() : Optional.of(0), 40 * 5));
		rabbit.add(new Food(FoodType.FoodEnum.PLANT.getType()));
		rabbit.add(location);
		rabbit.add(new Move());
		rabbit.add(new ColorComponent(Color.ORANGE));
		rabbit.add(new Life());
		rabbit.add(new Breed(5, 0.60, 4));
		rabbit.add(new Name("Rabbit"));
		rabbit.add(createGender(0.5));

		return rabbit;
	}

	public static Entity createCoyote(boolean random, Location location) {
		Entity coyote = new Entity();

		coyote.add(createAge(random ? Optional.empty() : Optional.of(0), 100 * 5));
		coyote.add(createHunger(random, 4*5));
		coyote.add(new Food(FoodType.FoodEnum.ANIMAL.getType("Raccoon", "Rabbit" ,"Fox")));
		coyote.add(location);
		coyote.add(new Move());
		coyote.add(new ColorComponent(Color.BLACK));
		coyote.add(new Life());
		coyote.add(new Breed(10, 0.60, 4));
		coyote.add(new Name("Coyote"));
		coyote.add(createGender(0.5));

		if(Randomizer.getRandom().nextDouble() < 0.3) {
			// TODO: Make the ground clean after some amount of time
			coyote.add(createDisease(Optional.of(Disease.DiseaseType.Generic), 10));
		}

		return coyote;
	}

	public static Entity createRaccoon(boolean random, Location location) {
		Entity raccoon = new Entity();

		raccoon.add(createAge(random ? Optional.empty() : Optional.of(0), 60 * 5));
		raccoon.add(createHunger(random, 5*5));
		raccoon.add(new Food(FoodType.FoodEnum.PLANT.getType("Plant")));
		raccoon.add(location);
		raccoon.add(new Move());
		raccoon.add(new ColorComponent(Color.PINK));
		raccoon.add(new Life());
		raccoon.add(new Breed(6, 0.60, 4));
		raccoon.add(new Name("Raccoon"));
		raccoon.add(createGender(0.5));


		return raccoon;
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

	public static <T extends Component> Optional<T> get(Entity entity, ComponentMapper<T> mapper){
		if(mapper.has(entity)) {
			return Optional.ofNullable(mapper.get(entity));
		}else{
			return Optional.empty();
		}
	}
}
