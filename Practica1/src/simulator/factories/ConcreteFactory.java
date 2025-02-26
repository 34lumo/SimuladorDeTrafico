package simulator.factories;

import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;

//Permite crear objetos de distintos tipos dinámicamente a partir de un JSON, utilizando una lista de builders.
public class ConcreteFactory<T> implements Factory<T> {
    private List<Builder<T>> builders; //Lista de Builders que le entraran

    public ConcreteFactory(List<Builder<T>> builders) { // Inicializa la lista builders con todos los builders que hay (junctionBuilder, roadBuilder, vehicleBuilder...)
        this.builders = builders;
    }

    @Override
    public T create_instance(JSONObject info) { //este create_Instance es el que sabe a partir del json (mirando el type), a que subclase de builder llamar para crear la instancia.
        for (Builder<T> b : builders) { //Recorre la lista de builders. (Junction, Road, Vehicle)
            if (b.get_type_tag().equals(info.getString("type"))) { //Busca el Builder<T> que coincida con el type dentro del JSON.
                return b.create_instance(info.getJSONObject("data")); //Si lo encuentra, llama a create_instance(data) del builder para crear la instancia.
            }
        }
        throw new IllegalArgumentException("Tipo desconocido: " + info.getString("type"));
    }//ejemplo->  b= vehicleBuilder -> vehicleBuilder.create_instance(data) -> se mete en la clase NewVehicleBuilder y llama a su create_instance, este guarda los datos de Data y crea un nuevo objeto Vehicle con esos datos.
    
    @Override
    public List<JSONObject> get_info() {         
    	List<JSONObject> infoList = new ArrayList<>();
        for (Builder<T> b : builders) {
            infoList.add(b.get_info());
        }
        return infoList;
    }
}
