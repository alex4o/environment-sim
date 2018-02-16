package systems;

import com.badlogic.ashley.core.*;



import static main.DayTime.Morning;
import static main.DayTime.Night;

public class SleepSystem extends EntitySystem {



    public static boolean sleep(){

        if(main.Simulator.getTime() == Night  )
        {
            return false;
        }
        return true;
    }

}
