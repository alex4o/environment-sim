package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.rits.cloning.Cloner;
import components.*;
import main.Field;
import main.Models;
import main.Randomizer;
import main.Tile;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static main.Models.createAnimal;

public class PlantBreedSystem extends EntitySystem {


	private ImmutableArray<Entity> entities;


	private ComponentMapper<Age> am = ComponentMapper.getFor(Age.class);
	private ComponentMapper<Life> lm = ComponentMapper.getFor(Life.class);
	private ComponentMapper<Breed> bm = ComponentMapper.getFor(Breed.class);
	private ComponentMapper<Location> locm = ComponentMapper.getFor(Location.class);
	private ComponentMapper<FoodValue> fvm = ComponentMapper.getFor(FoodValue.class);
	private ComponentMapper<Name> nm = ComponentMapper.getFor(Name.class);
	private Cloner cloner = new Cloner();

	Field field;
	Engine engine;

	public PlantBreedSystem(Engine engine) {
		this.field = Field.getInstance();
		this.engine = engine;
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Age.class, FoodValue.class, Location.class, Breed.class).exclude(Gender.class, Food.class).get());
	}

	private boolean canBreed(Age age, Breed breed) {
		return age.getAge() >= breed.getBreedAge();
	}

	public void update(float deltaTime) {

		Random random = Randomizer.getRandom();

		for (Entity entity : entities) {
			Age age = am.get(entity);
			Life life = lm.get(entity);
			Breed breed = bm.get(entity);
			Location location = locm.get(entity);
			FoodValue foodValue = fvm.get(entity);
			Name name = nm.get(entity);
			if(name.getName().equals("Moss")){
				continue;
			}

			if(field.getTile(location).weather.isRainging()){
				foodValue.setValue(foodValue.getValue() + 2);
			}

			if (canBreed(age, breed)) {

				if (breed.getProbability() < random.nextDouble()) {
					for (Location freeLocation : field.getFreeAdjacentLocations(location).stream().limit(breed.getMaxLittle()).collect(Collectors.toList())) {
						if (field.adjacentLocations(freeLocation, 3).stream().map(l -> field.getTile(l)).filter(t -> t.getType() == (name.getName().equals("Plant") ? Tile.TileType.Water : Tile.TileType.Rock)).count() > 0) {
							Entity e = Models.createAnimal(name.getName(), false, freeLocation);
							engine.addEntity(e);
						}
					}
//					field.plusLocations(new Location(row, col), 1).stream().filter(location -> field.getTile(location).getType() == Tile.TileType.Water).count()

				} else {
					if (!(foodValue.getValue() == foodValue.getMax())) {
						foodValue.setValue(foodValue.getValue() + 1);
					}
				}
			}

		}

	}
}

