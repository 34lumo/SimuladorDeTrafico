package simulator.model.Event;

import simulator.model.RoadMap;
import simulator.model.Weather;
import simulator.model.InterCityRoad;
import simulator.model.Road;


public class NewInterCityRoad extends NewRoadEvent {
    public NewInterCityRoad(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
        super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
    }

    @Override
    void execute(RoadMap map) {
        Road road = new InterCityRoad(id, map.getJunction(srcJun), map.getJunction(destJunc), length, co2Limit, maxSpeed, weather);
        map.addRoad(road);
    }


}