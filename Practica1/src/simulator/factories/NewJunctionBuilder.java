package simulator.factories;

import org.json.JSONObject;
import simulator.model.Junction;
import simulator.model.LightSwitchingStrategy;
import simulator.model.DequeuingStrategy;

public class NewJunctionBuilder extends Builder<Junction> {
    public NewJunctionBuilder() {
        super("new_junction", "Crea un nuevo cruce en la simulación.");
    }

    @Override
    protected void fill_in_data(JSONObject o) {
        o.put("id", "ID del cruce");
        o.put("lsStrategy", "Estrategia de cambio de semáforo");
        o.put("dqStrategy", "Estrategia de desencolado");
        o.put("xCoor", "Coordenada X");
        o.put("yCoor", "Coordenada Y");
    }

    @Override
    protected Junction create_instance(JSONObject data) {
        String id = data.getString("id");
        LightSwitchingStrategy lsStrategy = LightSwitchingStrategyFactory.create(data.getJSONObject("lsStrategy"));
        DequeuingStrategy dqStrategy = DequeuingStrategyFactory.create(data.getJSONObject("dqStrategy"));
        int x = data.getInt("xCoor");
        int y = data.getInt("yCoor");

        return new Junction(id, lsStrategy, dqStrategy, x, y);
    }
}
