package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import components.Age;
import components.Life;
import components.Move;

public class DeadSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private ComponentMapper<Life> lm = ComponentMapper.getFor(Life.class);

	Engine engine;
	public DeadSystem(Engine engine) {
		this.engine = engine;
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Life.class).get());
	}

	/**
	 * removes dead animals after every step
	 * @param deltaTime, time for each step
	 */
	public void update(float deltaTime) {
		for(Entity entity: entities){
			Life life = lm.get(entity);
			if(!life.isAlive()) {
				engine.removeEntity(entity);
			}
		}
	}
}


/*
	for(Entity entity : engine.getEntitiesFor(Family.all(Life.class, Move.class).get())){
			if(!entity.getComponent(Life.class).isAlive()) {
				engine.removeEntity(entity);
			}
		}
 */