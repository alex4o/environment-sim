
package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.rits.cloning.Cloner;
import components.*;
import main.Field;
import main.Randomizer;
import main.Simulator;

import java.util.List;
import java.util.Random;


public class BreedSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private ComponentMapper<Age> am = ComponentMapper.getFor(Age.class);
	private ComponentMapper<Life> lm = ComponentMapper.getFor(Life.class);
	private ComponentMapper<Breed> bm = ComponentMapper.getFor(Breed.class);
	private ComponentMapper<Move> mm = ComponentMapper.getFor(Move.class);
	private Cloner cloner = new Cloner();

	Field field;
	Engine engine;
	public BreedSystem(Field field, Engine engine) {
		this.field = field;
		this.engine = engine;
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Age.class, Life.class, Move.class, Breed.class).get());
	}

	public void update(float deltaTime) {
		for(Entity entity: entities){
			Age age = am.get(entity);
			Life life = lm.get(entity);
			Breed breed = bm.get(entity);
			Move move = mm.get(entity);
			Random random = Randomizer.getRandom();


			if(age.getAge() >= breed.getBreedAge() && life.isAlive()){
				if(random.nextDouble() < breed.getProbability()){
					int births = random.nextInt( breed.getMaxLittle()) + 1;
					List<Location> free = field.getFreeAdjacentLocations(move.getCurrent());

					for(int b = 0; b < births && free.size() > 0; b++) {
						Location loc = free.remove(0);

						Entity child = new Entity();
						for(Component component : entity.getComponents()){
							if(component instanceof Parent) continue;
							child.add(cloner.deepClone(component));
						}

						am.get(child).setAge(0);
						lm.get(child).isAlive();
						mm.get(child).setCurrent(loc);
						Food food = child.getComponent(Food.class);
						food.setLevel(9);
						child.add(new Parent(entity));

						Simulator.newEntities.add(child);

					}

				}
			}
		}
	}
}
