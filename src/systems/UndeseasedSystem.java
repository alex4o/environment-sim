package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import components.*;
import main.Field;


public class UndeseasedSystem extends EntitySystem {


	private ImmutableArray<Entity> entities;
	private ComponentMapper<Location> lm = ComponentMapper.getFor(Location.class);
	private ComponentMapper<Disease> dm = ComponentMapper.getFor(Disease.class);


	@Override
	public void update(float deltaTime) {
		Field field = Field.getInstance();
		for (Entity entity : entities) {
			Location location = lm.get(entity);
			if (field.getTile(location).getData() instanceof Disease) {

				entity.add(new Disease(Disease.DiseaseType.Toxocariasis));
			}

			if (field.adjacentLocations(location, 1).stream().map(l -> (Entity) field.getObjectAt(l)).filter(e -> e != null).filter(e -> dm.has(e)).map(e -> dm.get(e)).filter(disease -> disease.getType() == Disease.DiseaseType.Generic).count() > 0) {

				entity.add(new Disease(Disease.DiseaseType.Generic));
			}
		}

	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Location.class).exclude(Disease.class).get());

	}
}


