package simulator.factories;

import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewJunction;
import simulator.model.LightSwitchingStrategy;
import simulator.model.DequeuingStrategy;

public class NewJunctionEventBuilder extends Builder<Event> {
    private Factory<LightSwitchingStrategy> lssFactory;
    private Factory<DequeuingStrategy> dqsFactory;

    public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
        super("new_junction", "Crea un nuevo evento de cruce en la simulación.");
        this.lssFactory = lssFactory;
        this.dqsFactory = dqsFactory;
    }


    @Override
    protected Event create_instance(JSONObject data) {
        int time = data.getInt("time");
        String id = data.getString("id");

        // Extraer las coordenadas correctamente desde el array "coor"
        int x = data.getJSONArray("coor").getInt(0);
        int y = data.getJSONArray("coor").getInt(1);

        LightSwitchingStrategy lsStrategy = lssFactory.create_instance(data.getJSONObject("ls_strategy"));
        DequeuingStrategy dqStrategy = dqsFactory.create_instance(data.getJSONObject("dq_strategy"));

        return new NewJunction(time, id, lsStrategy, dqStrategy, x, y);
    }

}
