package simulator.model.Event;

import simulator.model.DequeuingStrategy;
import simulator.model.Junction;
import simulator.model.LightSwitchingStrategy;
import simulator.model.RoadMap;

class NewJunction extends Event {
    private String id;
    private LightSwitchingStrategy lsStrategy;
    private DequeuingStrategy dqStrategy;
    private int xCoor, yCoor;

    public NewJunction(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
        super(time);
        this.id = id;
        this.lsStrategy = lsStrategy;
        this.dqStrategy = dqStrategy;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }

    @Override
    void execute(RoadMap map) {
        Junction j = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
        map.addJunction(j);
    }
}