package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import components.Location;
import components.Move;
import main.Field;

import java.util.Optional;

public class MovementSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private ComponentMapper<Move> mm = ComponentMapper.getFor(Move.class);
//	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

	private Field field;

	public MovementSystem(Field field) {
		this.field = field;
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Move.class).get());
	}

	public void update(float deltaTime) {
		for(Entity entity : entities) {
			Move move = mm.get(entity);

			Location next = move.getNext().orElse(field.freeAdjacentLocation(move.getCurrent()));
			if(next != null && next.getCol() >= 0 && next.getRow() >= 0) { // stay where you are if the location is negative
				if(field.getObjectAt(next) == null){
					field.clear(move.getCurrent());
					field.place(entity, next);
					move.setCurrent(next);
					move.setNext(Optional.empty());
				}else{
					System.out.println("You are trying to move over sth!!!");
				}

			}
		}
	}
}