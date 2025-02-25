package simulator.factories;

import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;
import simulator.model.MoveAllStrategy;

public class DequeuingStrategyFactory {
    public static DequeuingStrategy create(JSONObject data) {
        String type = data.getString("type");

        switch (type) {
            case "move_first":
                return new MoveFirstStrategy();
            case "move_all":
                return new MoveAllStrategy();
            default:
                throw new IllegalArgumentException("Tipo de estrategia de desencolado desconocido: " + type);
        }
    }
}
