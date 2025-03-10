package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContaminationClass extends Event {
    private List<Pair<String, Integer>> cs;
    

    public SetContaminationClass (int time, List<Pair<String, Integer>> cs) {
        super(time);
        if (cs == null) throw new IllegalArgumentException("La contaminacion no puede ser nula");
        this.cs = cs;
    }

    @Override
    void execute(RoadMap map) {
        for (Pair<String, Integer> c : cs) {
            Vehicle v = map.getVehicle(c.getFirst());
            
            if (v == null) 
            	throw new IllegalArgumentException("Vehiculo no encontrado; " + c.getFirst());
            
            v.setContaminationClass(c.getSecond());
        }
    }
}
