package simulator.model.Event;

import java.util.List;

import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Weather;

public class SetWeather extends Event {
    private List<Pair<String, Weather>> ws;

    public SetWeather(int time, List<Pair<String, Weather>> ws) {
        super(time);
        if (ws == null) throw new IllegalArgumentException("La lista del weather no puede ser nula");
        this.ws = ws;
    }

    @Override
    void execute(RoadMap map) {
        for (Pair<String, Weather> w : ws) {
            Road r = map.getRoad(w.getFirst());
            if (r == null) throw new IllegalArgumentException("Carretera no encontrada: " + w.getFirst());
            r.setWeather(w.getSecond());
        }
    }
}
