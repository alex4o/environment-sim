package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import components.Age;
import components.Food;
import components.Life;
import components.Move;
import main.Field;


public class AgeSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private ComponentMapper<Age> am = ComponentMapper.getFor(Age.class);
	private ComponentMapper<Life> lm = ComponentMapper.getFor(Life.class);


	/**
	 * Sets the animal as dead when it reaches the maxAge
	 */
	public AgeSystem() {
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Age.class, Life.class).get());
	}

	public void update(float deltaTime) {
		for(Entity entity: entities){
			Age age = am.get(entity);
			Life life = lm.get(entity);

			age.setAge(age.getAge() + 1);
			if(age.getAge() >= age.getMaxAge()){
				life.setDead();
			}
		}
	}
}
