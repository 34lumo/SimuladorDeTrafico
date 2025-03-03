package simulator.factories;

import org.json.JSONObject;
import simulator.model.CityRoad;
import simulator.model.Event;
import simulator.model.Road;

public class NewCityRoadBuilder extends NewRoadBuilder {
    public NewCityRoadBuilder() {
        super("new_city_road", "Crea una carretera urbana.");
    }

    @Override
    protected Event create_instance(JSONObject data) {
        return create_road_event(data, true);
    }
}
