package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
    private int timeSlot;

    public MostCrowdedStrategy(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
        if (roads.isEmpty()) 
        	return -1;

        int colaMasLarga = currGreen;
        if (currGreen == -1 || currTime - lastSwitchingTime >= timeSlot) {
            int maxQueue = -1;
            for (int i = 0; i < qs.size(); i++) {
                if (qs.get(i).size() > maxQueue) {
                    maxQueue = qs.get(i).size();
                    colaMasLarga = i;
                }
            }
        }

        return colaMasLarga; // Devuelve el índice de la carretera con la cola más larga.
    }
}
