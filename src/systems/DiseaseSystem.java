package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import components.Disease;
import components.Life;
import components.Location;
import main.Field;
import main.Simulator;

import java.awt.*;
import java.util.stream.Collectors;

public class DiseaseSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private ComponentMapper<Disease> dm = ComponentMapper.getFor(Disease.class);
	private ComponentMapper<Life> lm = ComponentMapper.getFor(Life.class);
	private ComponentMapper<Location> locm = ComponentMapper.getFor(Location.class);


	public DiseaseSystem() {
	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Location.class, Disease.class, Life.class).get());
	}

	public void update(float deltaTime) {
		Field field = Field.getInstance();
		for(Entity entity: entities){
			Disease disease = dm.get(entity);
			Life life = lm.get(entity);
			Location location = locm.get(entity);

			switch (disease.getType()) {
				case Toxocariasis:
					field.getTile(location).setStatusColor(new Color(102, 51, 0));
					field.getTile(location).setData(disease);


					break;
				case Generic:
					break;
			}

			if(disease.getCurrentDeterioration() > disease.getDeterioration()){
				life.setDead();
			}else{
				disease.setCurrentDeterioration(disease.getDeterioration() + 1);
			}
		}
	}

}
