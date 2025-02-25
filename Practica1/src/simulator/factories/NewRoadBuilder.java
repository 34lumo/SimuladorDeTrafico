package simulator.factories;
import org.json.JSONObject;
import simulator.model.Road;
import simulator.model.CityRoad;
import simulator.model.InterCityRoad;
import simulator.model.Junction;
import simulator.model.Weather;

public abstract class NewRoadBuilder extends Builder<Road> {
    public NewRoadBuilder(String type, String desc) {
        super(type, desc);
    }

    protected Road create_road(JSONObject data, boolean isCityRoad) { //boleano?
    	//comprobar el time = 1;
        String id = data.getString("id");
        Junction src = RoadMapFactory.getJunction(data.getString("src"));
        Junction dest = RoadMapFactory.getJunction(data.getString("dest"));
        int length = data.getInt("length");
        int co2Limit = data.getInt("co2Limit");
        int maxSpeed = data.getInt("maxSpeed");
        Weather weather = Weather.valueOf(data.getString("weather").toUpperCase());

        return isCityRoad ? new CityRoad(id, src, dest, maxSpeed, co2Limit, length, weather)
                          : new InterCityRoad(id, src, dest, maxSpeed, co2Limit, length, weather);
    }
}
