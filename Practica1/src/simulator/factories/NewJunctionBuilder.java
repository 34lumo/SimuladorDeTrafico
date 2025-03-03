package simulator.factories;

import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewJunction;
import simulator.model.LightSwitchingStrategy;
import simulator.model.DequeuingStrategy;

public class NewJunctionBuilder extends Builder<Event> {
    private Factory<LightSwitchingStrategy> lssFactory;
    private Factory<DequeuingStrategy> dqsFactory;

    public NewJunctionBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
        super("new_junction", "Crea un nuevo evento de cruce en la simulación.");
        this.lssFactory = lssFactory;
        this.dqsFactory = dqsFactory;
    }

    @Override
    protected void fill_in_data(JSONObject o) {
        o.put("time", "Tiempo en el que se creará el cruce");
        o.put("id", "ID del cruce");
        o.put("ls_strategy", "Estrategia de cambio de semáforo");
        o.put("dq_strategy", "Estrategia de desencolado");
        o.put("x", "Coordenada X");
        o.put("y", "Coordenada Y");
    }

    @Override
    protected Event create_instance(JSONObject data) {
        int time = data.getInt("time");
        String id = data.getString("id");
        int x = data.getInt("x");
        int y = data.getInt("y");

        LightSwitchingStrategy lsStrategy = lssFactory.create_instance(data.getJSONObject("ls_strategy"));
        DequeuingStrategy dqStrategy = dqsFactory.create_instance(data.getJSONObject("dq_strategy"));

        return new NewJunction(time, id, lsStrategy, dqStrategy, x, y);
    }
}
