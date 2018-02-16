package systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import components.Location;
import components.Move;
import components.Sleep;
import main.Simulator;


import static main.DayTime.Morning;
import static main.DayTime.Night;

public class SleepSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<Move> mm = ComponentMapper.getFor(Move.class);
    private ComponentMapper<Sleep> sm = ComponentMapper.getFor(Sleep.class);
    private ComponentMapper<Location> lm = ComponentMapper.getFor(Location.class);


    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(Move.class, Sleep.class, Location.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity: entities){

            Move move = mm.get(entity);
            Sleep sleep = sm.get(entity);
            Location location = lm.get(entity);

            if(Simulator.getTime() == sleep.getTimeOfTheDay()){
                move.setNext(location);
            }
        }
    }
}
