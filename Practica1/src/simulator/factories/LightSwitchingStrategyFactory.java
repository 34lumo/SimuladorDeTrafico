package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;
import simulator.model.MostCrowdedStrategy;

public class LightSwitchingStrategyFactory {
    public static LightSwitchingStrategy create(JSONObject data) {
        String type = data.getString("type");
        int timeSlot = data.optInt("timeslot", 1); 

        switch (type) {
            case "round_robin":
                return new RoundRobinStrategy(timeSlot);
            case "most_crowded":
                return new MostCrowdedStrategy(timeSlot);
            default:
                throw new IllegalArgumentException("Tipo de estrategia de cambio de semáforo desconocido: " + type);
        }
    }
}
