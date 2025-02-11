package simulator.model.Event;

import java.util.List;

import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class NewVehicle extends Event {
    private String id;
    private int maxSpeed, contClass;
    private List<String> itinerary;

    public NewVehicle (int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
        super(time);
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.contClass = contClass;
        this.itinerary = itinerary;
    }

    @Override
    void execute(RoadMap map) {
        List<Junction> route = map.getJunction(itinerary);
        Vehicle v = new Vehicle(id, maxSpeed, contClass, route);
        map.addVehicle(v);
        v.moveToNextRoad();
    }

}