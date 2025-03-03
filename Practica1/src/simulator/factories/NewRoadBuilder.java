package simulator.factories;

import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewCityRoad;
import simulator.model.NewInterCityRoad;
import simulator.model.Weather;

public abstract class NewRoadBuilder extends Builder<Event> {

    public NewRoadBuilder(String type, String desc) {
        super(type, desc);
    }

    protected Event create_road_event(JSONObject data, boolean isCityRoad) {
        int time = data.getInt("time");
        String id = data.getString("id");
        String src = data.getString("src");
        String dest = data.getString("dest");
        int length = data.getInt("length");
        int co2Limit = data.getInt("co2limit");
        int maxSpeed = data.getInt("maxspeed");
        Weather weather = Weather.valueOf(data.getString("weather").toUpperCase());

        return isCityRoad
            ? new NewCityRoad(time, id, src, dest, length, co2Limit, maxSpeed, weather)
            : new NewInterCityRoad(time, id, src, dest, length, co2Limit, maxSpeed, weather);
    }
}
