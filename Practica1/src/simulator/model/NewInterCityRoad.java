package simulator.model;

public class NewInterCityRoad extends NewRoadEvent {
    public NewInterCityRoad(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
        super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
    }

    @Override
    void execute(RoadMap map) {
        map.addRoad(new InterCityRoad(id, map.getJunction(srcJunc), map.getJunction(destJunc), maxSpeed, co2Limit, length, weather));
    }
}