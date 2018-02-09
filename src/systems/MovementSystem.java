package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import components.Life;
import components.Location;
import components.Move;
import main.Field;
import main.Models;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MovementSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private ComponentMapper<Move> mm = ComponentMapper.getFor(Move.class);
	private ComponentMapper<Life> lifem = ComponentMapper.getFor(Life.class);
	private ComponentMapper<Location> locm = ComponentMapper.getFor(Location.class);

	private Field field;

	public MovementSystem() {
		this.field = Field.getInstance();
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Location.class, Move.class).get());
	}

	public List<Location> getFreeAdjacentLocations(Location location) {
		List<Location> free = new LinkedList<>();
		List<Location> adjacent = field.adjacentLocations(location);
		for(Location next : adjacent) {
			Entity entitiy = field.getObjectAt(next);

			if(entitiy == null){
				if(field.getTile(next.getRow(), next.getCol()).isWalkable()) {
					free.add(next);
				}
			}else {
				Optional<Boolean> alive = Models.get(entitiy, lifem).map(l -> l.isAlive());
				if (!alive.orElse(true)){
					free.add(next);
				}

			}
	}
		return free;
	}

	private boolean canWalkOver(Entity e) {
		if(e != null){
			if(lifem.has(e)){
				Life life = lifem.get(e);
				if(life.isAlive()){
					return false; // if it is alive we continue
				}
			}else{
				return false; // if it does not have life we continue
			}
			return true;
		}
		return true;

	}

	public void update(float deltaTime) {
		for(Entity entity : entities) {
			Move move = mm.get(entity);
			Location current = locm.get(entity);
			if(lifem.has(entity)){
				Life life = lifem.get(entity);
				if(!life.isAlive()){
					continue;
				}
			}

			List<Location> free = getFreeAdjacentLocations(current);

			Location next = move.getNext().orElseGet(() -> free.size() > 0 ? free.get(0): null);
			if(next != null) { // stay where you are if the location is negative
				Entity adj = field.getObjectAt(next);


				if(!canWalkOver(adj)){
					if(free.size() > 0) {
						next = free.get(0);
					}else{
						if(lifem.has(entity)){
							Life life = lifem.get(entity);
							life.setDead();
						}
					}
				}

				// we step on the next tile if only it is empty
				// or the entity there is dead
				field.clear(next);
				field.clear(current);
				field.place(entity, next);
				current.set(next);
				move.setNext(Optional.empty());

			}else{
				if(lifem.has(entity)){
					Life life = lifem.get(entity);
					life.setDead();
				}
			}
		}
	}
}