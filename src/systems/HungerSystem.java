package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import components.Age;
import components.Hunger;
import components.Life;


public class HungerSystem extends IteratingSystem {

	private ComponentMapper<Hunger> hm = ComponentMapper.getFor(Hunger.class);
	private ComponentMapper<Life> lm = ComponentMapper.getFor(Life.class);


	public HungerSystem() {
		super(Family.all(Hunger.class, Life.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Hunger hunger = hm.get(entity);
		Life life = lm.get(entity);

		hunger.setLevel(hunger.getLevel() - 1);
		if(hunger.getLevel() <= 0){
			life.setDead();
		}
	}
}
