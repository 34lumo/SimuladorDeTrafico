package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
    private List<Road> incomingRoads;
    private Map<Junction, Road> outgoingRoads;
    private List<List<Vehicle>> queues;
    private Map<Road, List<Vehicle>> roadToQueueMap;
    private int greenLightIndex;
    private int lastSwitchingTime;
    private LightSwitchingStrategy lsStrategy;
    private DequeuingStrategy dqStrategy;
    private int xCoor;
    private int yCoor;

    public Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
        super(id);
        if (lsStrategy == null || dqStrategy == null)
            throw new NullPointerException("Strategies cannot be null.");
        if (xCoor < 0 || yCoor < 0)
            throw new IllegalArgumentException("Coordinates cannot be negative.");

        this.lsStrategy = lsStrategy;
        this.dqStrategy = dqStrategy;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.incomingRoads = new ArrayList<>();
        this.outgoingRoads = new HashMap<>();
        this.queues = new ArrayList<>();
        this.roadToQueueMap = new HashMap<>();
        this.greenLightIndex = -1; // Initially, all signals are red.
        this.lastSwitchingTime = 0;
    }

    public void addIncomingRoad(Road r) {
        if (r.getDest() != this)
            throw new IllegalArgumentException("Incoming road's destination must be this junction.");
        incomingRoads.add(r);
        List<Vehicle> queue = new LinkedList<>();
        queues.add(queue);
        roadToQueueMap.put(r, queue);
    }

    public void addOutgoingRoad(Road r) {
        if (outgoingRoads.containsKey(r.getDest()))
            throw new IllegalArgumentException("There is already an outgoing road to the destination junction.");
        if (r.getSrc() != this)
            throw new IllegalArgumentException("Outgoing road's source must be this junction.");
        outgoingRoads.put(r.getDest(), r);
    }

    public void enter(Vehicle v) {
        Road r = v.getRoad();
        List<Vehicle> queue = roadToQueueMap.get(r);
        if (queue == null)
            throw new IllegalArgumentException("Vehicle's road is not an incoming road to this junction.");
        queue.add(v);
    }

    public Road roadTo(Junction j) {
        return outgoingRoads.get(j);
    }

    @Override
    public void advance(int time) {
        // Dequeue vehicles according to the current green light
        if (greenLightIndex >= 0) {
            List<Vehicle> queue = queues.get(greenLightIndex);
            List<Vehicle> vehiclesToMove = dqStrategy.dequeue(queue);
            for (Vehicle v : vehiclesToMove) {
                v.moveToNextRoad();
                queue.remove(v);
            }
        }

        // Determine the next green light
        int nextGreen = lsStrategy.chooseNextGreen(incomingRoads, queues, greenLightIndex, lastSwitchingTime, time);
        if (nextGreen != greenLightIndex) {
            greenLightIndex = nextGreen;
            lastSwitchingTime = time;
        }
    }

    @Override
    public JSONObject report() {
        JSONObject jo = new JSONObject();
        jo.put("id", getId());
        jo.put("green", greenLightIndex == -1 ? "none" : incomingRoads.get(greenLightIndex).getId());
        JSONArray jsonQueues = new JSONArray();
        for (List<Vehicle> queue : queues) {
            JSONObject jsonQueue = new JSONObject();
            jsonQueue.put("road", incomingRoads.get(queues.indexOf(queue)).getId());
            JSONArray jsonVehicles = new JSONArray();
            for (Vehicle v : queue) {
                jsonVehicles.put(v.getId());
            }
            jsonQueue.put("vehicles", jsonVehicles);
            jsonQueues.put(jsonQueue);
        }
        jo.put("queues", jsonQueues);
        return jo;
    }
}
