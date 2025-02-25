package simulator.factories;

import org.json.JSONObject;
import simulator.model.CityRoad;
import simulator.model.Road;

public class NewCityRoadBuilder extends NewRoadBuilder {
    public NewCityRoadBuilder() {
        super("new_city_road", "Crea una carretera urbana.");
    }

    @Override
    protected Road create_instance(JSONObject data) {
        return create_road(data, true);
    }
}
