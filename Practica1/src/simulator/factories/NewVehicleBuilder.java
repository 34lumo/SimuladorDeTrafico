package simulator.factories;

import org.json.JSONObject;
import simulator.model.Vehicle;
import simulator.model.Junction;
import java.util.ArrayList;
import java.util.List;

public class NewVehicleBuilder extends Builder<Vehicle> {
    public NewVehicleBuilder() {
        super("new_vehicle", "Crea un nuevo vehículo en la simulación.");
    }

    @Override
    protected void fill_in_data(JSONObject o) {
        o.put("id", "ID del vehículo");
        o.put("maxSpeed", "Velocidad máxima");
        o.put("class", "Clase de contaminación (0-10)");
        o.put("itinerary", "Lista de cruces en el itinerario");
    }

    @Override
    protected Vehicle create_instance(JSONObject data) {
        String id = data.getString("id");
        int maxSpeed = data.getInt("maxSpeed");
        int contaminationClass = data.getInt("class");

        List<Junction> itinerary = new ArrayList<>();
        for (Object jId : data.getJSONArray("itinerary")) {
            itinerary.add(RoadMapFactory.getJunction(jId.toString()));
        }

        return new Vehicle(id, maxSpeed, contaminationClass, itinerary);
    }
}
