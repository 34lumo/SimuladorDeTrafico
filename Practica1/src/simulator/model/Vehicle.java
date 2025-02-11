package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

    private int maxSpeed;
    private int currentSpeed;
    private int contaminationClass;
    private List<Junction> itinerary;
    private VehicleStatus status;
    private Road road;
    private int location;
    private int totalCO2;
    private int totalDistance;
    

    public Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
        super(id);
        if (maxSpeed <= 0) throw new IllegalArgumentException("Maximum speed must be positive.");
        if (contClass < 0 || contClass > 10) throw new IllegalArgumentException("Contamination class must be between 0 and 10.");
        if (itinerary == null || itinerary.size() < 2) throw new IllegalArgumentException("Itinerary must have at least two junctions.");
        this.maxSpeed = maxSpeed;
        this.contaminationClass = contClass;
        this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
        this.status = VehicleStatus.PENDING;
        this.currentSpeed = 0;
        this.totalCO2 = 0;
        this.totalDistance = 0;
    }

    public void setSpeed(int s) {
        if (s < 0) throw new IllegalArgumentException("Speed cannot be negative.");
        this.currentSpeed = Math.min(s, this.maxSpeed);
    }

    public void setContaminationClass(int c) {
        if (c < 0 || c > 10) throw new IllegalArgumentException("Contamination class must be between 0 and 10.");
        this.contaminationClass = c;
    }

    public void advance(int currTime) { 
        if (this.status == VehicleStatus.TRAVELING) {
            int prevLocation = this.location;
            this.location = Math.min(this.location + this.currentSpeed, road.getLength());  //CHEKEAR
            int distanceTraveled = this.location - prevLocation;
            int contamination = distanceTraveled * this.contaminationClass; 
            this.totalCO2 += contamination;
            road.addContamination(contamination); //TERMINAR
            this.totalDistance += distanceTraveled;

            if (this.location >= road.getLength()) {
                this.status = VehicleStatus.WAITING;
                road.getDest().enter(this); //comprobar que sea un metodo junction
            }
        }
    }

    public void moveToNextRoad() {
        if (this.status != VehicleStatus.PENDING && this.status != VehicleStatus.WAITING) {
            throw new IllegalStateException("Vehicle must be in PENDING or WAITING state.");
        }

        if (road != null) {
            road.exit(this);
        }

        // Move to the next road
        Junction nextJunction = itinerary.get(0); // Get the first junction in the itinerary
        Road nextRoad = nextJunction.roadTo(itinerary.get(1)); // Find the next road
        this.road = nextRoad;
        nextRoad.enter(this);
        this.status = VehicleStatus.TRAVELING;
        this.location = 0;
    }

    public JSONObject report() {
        JSONObject jo = new JSONObject();
        jo.put("id", getId());
        jo.put("speed", currentSpeed);
        jo.put("distance", totalDistance);
        jo.put("co2", totalCO2);
        jo.put("class", contaminationClass);
        jo.put("status", status.toString());
        if (status != VehicleStatus.PENDING && status != VehicleStatus.ARRIVED) {
            jo.put("road", road.getId());
            jo.put("location", location);
        }
        return jo;
    }

    // Getters
    public int getLocation() {
        return location;
    }

    public int getSpeed() {
        return currentSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getContClass() {
        return contaminationClass;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public int getTotalCO2() {
        return totalCO2;
    }

    public List<Junction> getItinerary() {
        return itinerary;
    }

    public Road getRoad() {
        return road;
    }
}
