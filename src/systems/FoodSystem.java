package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import components.*;
import main.Field;
import components.Location;
import main.Models;
import main.Simulator;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class FoodSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private ComponentMapper<Location> locm = ComponentMapper.getFor(Location.class);
	private ComponentMapper<Move> mm = ComponentMapper.getFor(Move.class);
	private ComponentMapper<Life> lm = ComponentMapper.getFor(Life.class);
	private ComponentMapper<Food> fm = ComponentMapper.getFor(Food.class);
	private ComponentMapper<Hunger> hm = ComponentMapper.getFor(Hunger.class);

	private Field field;

	private Engine engine;

	public FoodSystem() {
		this.field = Field.getInstance();
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Location.class, Food.class, Life.class, Hunger.class).get());
		this.engine = engine;
	}

	public void update(float deltaTime) {
		for (Entity entity : entities) {
			Location location = locm.get(entity);
			Food food = fm.get(entity);
			Life life = lm.get(entity);
			Hunger hunger = hm.get(entity);


			if (life.isAlive()) {
				switch (food.getEatType().type) {
					case ANIMAL:

						if (mm.has(entity)) {
							Move move = mm.get(entity);
							move.setNext(findFood(location, hunger, food.getEatType().things));

						}
						break;
					case PLANT:
						if (mm.has(entity)) {
							Move move = mm.get(entity);
							move.setNext(findFood(location, hunger));

							}
							break;
					case MUSHROOM:
						break;
					case CORPSE:
						break;
				}
			}
		}
	}

	private Optional<Location> findFood(Location location, Hunger hunger, List<String> thingsThatEat)
	{
		List<Location> adjacent = field.adjacentLocations(location);
		Iterator<Location> it = adjacent.iterator();
		for(Location where : adjacent){
			Entity entity = field.getObjectAt(where);
			if(entity == null){
				continue;
			}

			if(thingsThatEat.contains(entity.getComponent(Name.class).getName())) {
				if(lm.get(entity).isAlive()) {
					lm.get(entity).setDead();
					hunger.setLevel(9);

					return Optional.ofNullable(where);
				}
			}
		}
		return Optional.empty();
	}


	private Optional<Location> findFood(Location location, Hunger hunger)
	{
		List<Location> adjacent = field.adjacentLocations(location);
		Iterator<Location> it = adjacent.iterator();
		for(Location where : adjacent){
			Entity entity = field.getObjectAt(where);
			if(entity == null){
				continue;
			}

			if(!fm.has(entity)) {
				FoodValue fValue = entity.getComponent(FoodValue.class);
				hunger.setLevel(fValue.getValue());

				engine.removeEntity(entity);

				return Optional.ofNullable(where);
			}

		}
		return Optional.empty();
	}
}
