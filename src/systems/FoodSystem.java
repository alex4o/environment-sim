package systems;

import actors.Rabbit;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import components.*;
import main.Field;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class FoodSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private ComponentMapper<Move> mm = ComponentMapper.getFor(Move.class);
	private ComponentMapper<Life> lm = ComponentMapper.getFor(Life.class);
	private ComponentMapper<Food> fm = ComponentMapper.getFor(Food.class);

	private Field field;

	public FoodSystem(Field field) {
		this.field = field;
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Move.class, Food.class, Life.class).get());
	}

	public void update(float deltaTime) {
		for(Entity entity : entities) {
			Move move = mm.get(entity);
			Food food = fm.get(entity);
			Life life = lm.get(entity);
			switch (food.getEatType()) {
				case ANIMAL:
					food.setLevel(food.getLevel() - 1);
					if(food.getLevel() <= 0){
						life.setDead();
					}

					findFood(move.getCurrent(), food);
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

	private Optional<Location> findFood(Location location, Food food)
	{
		List<Location> adjacent = field.adjacentLocations(location);
		Iterator<Location> it = adjacent.iterator();
		for(Location where : adjacent){
			Entity entity = (Entity) field.getObjectAt(where);
			if(entity == null){
				continue;
			}

			if(fm.get(entity).getEatType() == FoodType.PLANT) {
				if(lm.get(entity).isAlive()) {
					lm.get(entity).setDead();
					food.setLevel(9);

					return Optional.ofNullable(where);
				}
			}
		}
		return Optional.empty();
	}
}