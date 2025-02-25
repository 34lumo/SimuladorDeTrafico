package simulator.factories;

import simulator.model.RoadMap;
import simulator.model.Junction;
import simulator.model.Road;

public class RoadMapFactory {
    private static RoadMap roadMap;

    public static void setRoadMap(RoadMap rm) {
        roadMap = rm;
    }

    public static Junction getJunction(String id) {
        Junction j = roadMap.getJunction(id);
        if (j == null) throw new IllegalArgumentException("Cruce no encontrado: " + id);
        return j;
    }

    public static Road getRoad(String id) {
        Road r = roadMap.getRoad(id);
        if (r == null) throw new IllegalArgumentException("Carretera no encontrada: " + id);
        return r;
    }
}
