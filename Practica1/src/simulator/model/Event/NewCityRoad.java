package simulator.model.Event;

import simulator.model.RoadMap;
import simulator.model.CityRoad;
import simulator.model.Road;
import simulator.model.Weather;

public class NewCityRoad extends NewRoadEvent {
    public NewCityRoad(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
        super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
    }

    @Override
    void execute(RoadMap map) {
        Road road = new CityRoad(id, map.getJunction(srcJun), map.getJunction(destJunc), length, co2Limit, maxSpeed, weather);
        map.addRoad(road);
    }
}