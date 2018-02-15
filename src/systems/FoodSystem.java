package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import components.*;
import main.Field;
import components.Location;
import main.Models;

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

	public FoodSystem() {
		this.field = Field.getInstance();
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Location.class, Food.class, Life.class, Hunger.class).get());
	}

	public void update(float deltaTime) {
		for(Entity entity : entities) {
			Location location = locm.get(entity);
			Food food = fm.get(entity);
			Life life = lm.get(entity);
			Hunger hunger = hm.get(entity);
			List<String> things = food.getEatType().things; // the names of the animals that this likes eating
			switch (food.getEatType().type) {
				case ANIMAL:
					if(life.isAlive()){
						if(mm.has(entity)) {
							Move move = mm.get(entity);
							move.setNext(findFood(location, hunger));
						}
					}
					break;
				case PLANT:
					break;
				case MUSHROOM:
					break;
				case CORPSE:
					break;
			}
		}
	}

	private Optional<Location> findFood(Location location, Hunger hunger)
	{
		List<Location> adjacent = field.adjacentLocations(location);
		Iterator<Location> it = adjacent.iterator();
		for(Location where : adjacent){
			Entity entity = (Entity) field.getObjectAt(where);
			if(entity == null){
				continue;
			}

			if(entity.getComponent(Name.class).getName().equals("Rabbit")) {
				if(lm.get(entity).isAlive()) {
					lm.get(entity).setDead();
					hunger.setLevel(9);

					return Optional.ofNullable(where);
				}
			}
		}
		return Optional.empty();
	}
}