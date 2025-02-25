package simulator.factories;

import org.json.JSONObject;
import simulator.model.SetWeather;
import simulator.misc.Pair;
import simulator.model.Weather;
import java.util.ArrayList;
import java.util.List;

public class SetWeatherEventBuilder extends Builder<SetWeather> {
    public SetWeatherEventBuilder() {
        super("set_weather", "Cambia el clima en carreteras.");
    }

    @Override
    protected void fill_in_data(JSONObject o) {
        o.put("ws", "Lista de pares [carretera, clima]");
    }

    @Override
    protected SetWeather create_instance(JSONObject data) { //explica
        List<Pair<String, Weather>> ws = new ArrayList<>();
        for (Object obj : data.getJSONArray("ws")) {
            JSONObject pair = (JSONObject) obj;
            ws.add(new Pair<>(pair.getString("road"), Weather.valueOf(pair.getString("weather").toUpperCase())));
        }
        return new SetWeather(data.getInt("time"), ws);
    }
}
