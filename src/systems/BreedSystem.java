
package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.rits.cloning.Cloner;
import components.*;
import main.*;

import java.util.List;
import java.util.Random;


public class BreedSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private static final boolean wantToUseReflection = true;

	private ComponentMapper<Age> am = ComponentMapper.getFor(Age.class);
	private ComponentMapper<Life> lm = ComponentMapper.getFor(Life.class);
	private ComponentMapper<Breed> bm = ComponentMapper.getFor(Breed.class);
	private ComponentMapper<Location> locm = ComponentMapper.getFor(Location.class);
	private Cloner cloner = new Cloner();

	Field field;
	Engine engine;
	public BreedSystem(Engine engine) {
		this.field = Field.getInstance();
		this.engine = engine;
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Age.class, Life.class, Location.class, Breed.class).get());
	}

	private boolean canBreed(Age age, Breed breed) {
		return age.getAge() >= breed.getBreedAge();
	}

	public void update(float deltaTime) {
		for(Entity entity: entities){
			Age age = am.get(entity);
			Life life = lm.get(entity);
			Breed breed = bm.get(entity);
			Location location = locm.get(entity);
			Random random = Randomizer.getRandom();


			if(canBreed(age, breed) && life.isAlive()){
				if(random.nextDouble() <= breed.getProbability()){
					int births = random.nextInt(breed.getMaxLittle()) + 1;
					List<Location> free = field.getFreeAdjacentLocations(location);

					for(int b = 0; b < births && free.size() > 0; b++) {
						Location loc = free.remove(0);
						Entity child = new Entity();

						if(wantToUseReflection){
							child = Models.createAnimal(entity.getComponent(Name.class).getName(), false, loc);
						}else {
							for (Component component : entity.getComponents()) {
								if (component instanceof Parent) continue;
								child.add(cloner.deepClone(component));
							}

							am.get(child).setAge(0);
							lm.get(child).isAlive();
							locm.get(child).set(loc);
							Hunger hunger = child.getComponent(Hunger.class);
							if (hunger != null) {
								hunger.setLevel(9);
							}
						}
//						child.add(new Parent(entity));

						engine.addEntity(child);
//						Simulator.newEntities.add(child);

					}

				}
			}
		}
	}
}
