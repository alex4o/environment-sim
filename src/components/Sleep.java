package components;

import com.badlogic.ashley.core.Component;
import main.DayTime;

public class Sleep implements Component {
    DayTime timeOfTheDay;

    public Sleep(DayTime timeOfTheDay) {
        this.timeOfTheDay = timeOfTheDay;
    }

    public DayTime getTimeOfTheDay() {
        return timeOfTheDay;
    }

    public void setTimeOfTheDay(DayTime timeOfTheDay) {
        this.timeOfTheDay = timeOfTheDay;
    }

    @Override
    public String toString() {
        return "Sleep{" +
                "timeOfTheDay=" + timeOfTheDay +
                '}';
    }
}
