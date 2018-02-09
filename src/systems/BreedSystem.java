
package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.rits.cloning.Cloner;
import components.*;
import main.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class BreedSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private static final boolean wantToUseReflection = true;

	private ComponentMapper<Age> am = ComponentMapper.getFor(Age.class);
	private ComponentMapper<Life> lm = ComponentMapper.getFor(Life.class);
	private ComponentMapper<Breed> bm = ComponentMapper.getFor(Breed.class);
	private ComponentMapper<Location> locm = ComponentMapper.getFor(Location.class);
	private ComponentMapper<Gender> gm = ComponentMapper.getFor(Gender.class);
	private ComponentMapper<Name> nm = ComponentMapper.getFor(Name.class);
	private Cloner cloner = new Cloner();

	Field field;
	Engine engine;
	public BreedSystem(Engine engine) {
		this.field = Field.getInstance();
		this.engine = engine;
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Name.class, Age.class, Life.class, Location.class, Breed.class, Gender.class).get());
	}

	private boolean canBreed(Age age, Breed breed) {
		return age.getAge() >= breed.getBreedAge();
	}

	public void update(float deltaTime) {
		Random random = Randomizer.getRandom();

		for(Entity entity: entities){
			Age age = am.get(entity);
			Life life = lm.get(entity);
			Breed breed = bm.get(entity);
			Location location = locm.get(entity);
			Gender gender = gm.get(entity);
			Name name = nm.get(entity);

			List<Entity> breedable =  field.adjacentLocations(location, 1, false).stream()
					.map(l -> (Entity)field.getObjectAt(l))
					.filter(e -> Models.get(e, nm).map(nc -> nc.getName().equals(name.getName())).orElse(false))
					.filter(e -> Models.get(e, gm).map(nc -> nc.getGender().equals(gender.getGender())).orElse(false))
					.collect(Collectors.toList());

			if(breedable.size() > 0) {
				Entity partner = breedable.get(Randomizer.getRandom().nextInt(breedable.size()));



				if (canBreed(age, breed) && canBreed(am.get(partner), bm.get(partner)) && life.isAlive()) {
					if (random.nextDouble() <= breed.getProbability()) {
						int births = random.nextInt(breed.getMaxLittle()) + 1;
						List<Location> free = field.getFreeAdjacentLocations(location);
						for (int b = 0; b < births && free.size() > 0; b++) {
							Location loc = free.remove(0);

							Entity child = Models.createAnimal(nm.get(entity).getName(), false, loc);

							engine.addEntity(child);

						}
					}
				}
			}
		}
	}
}

