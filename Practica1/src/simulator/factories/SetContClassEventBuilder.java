package simulator.factories;

import org.json.JSONObject;
import simulator.model.SetContaminationClass;
import simulator.misc.Pair;
import java.util.ArrayList;
import java.util.List;

public class SetContClassEventBuilder extends Builder<SetContaminationClass> {
    public SetContClassEventBuilder() {
        super("set_cont_class", "Cambia la clase de contaminación de vehículos.");
    }

    @Override
    protected void fill_in_data(JSONObject o) {
        o.put("cs", "Lista de pares [vehículo, nueva clase de contaminación]");
    }

    @Override
    protected SetContaminationClass create_instance(JSONObject data) {
        List<Pair<String, Integer>> cs = new ArrayList<>();
        for (Object obj : data.getJSONArray("cs")) {
            JSONObject pair = (JSONObject) obj;
            cs.add(new Pair<>(pair.getString("vehicle"), pair.getInt("class")));
        }
        return new SetContaminationClass(data.getInt("time"), cs);
    }
}
