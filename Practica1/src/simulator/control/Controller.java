package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.model.TrafficSimulator;
import simulator.model.Event;
import simulator.factories.Factory;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class Controller {
    private TrafficSimulator simulator;
    private Factory<Event> eventsFactory;

    public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
        if (sim == null || eventsFactory == null) {
            throw new IllegalArgumentException("El simulador y la factoría de eventos no pueden ser null.");
        }
        this.simulator = sim;
        this.eventsFactory = eventsFactory;
    }

    // cargará el evento desde input stream
    public void loadEvents(InputStream in) {
        if (in == null) {
            throw new IllegalArgumentException("El InputStream no puede ser null.");
        }

        JSONObject jo = new JSONObject(new JSONTokener(in));

        if (!jo.has("events")) {
            throw new IllegalArgumentException("El JSON de entrada debe contener una clave 'events'.");
        }

        JSONArray eventsArray = jo.getJSONArray("events");
        for (int i = 0; i < eventsArray.length(); i++) {
            JSONObject eventJSON = eventsArray.getJSONObject(i);
            Event event = eventsFactory.create_instance(eventJSON);
            simulator.addEvent(event);
        }
    }

    // Método para ejecutar la simulación n pasos y escribir los estados en OutputStream
    public void run(int n, OutputStream out) {
        if (n < 1) {
            throw new IllegalArgumentException("El número de pasos debe ser mayor que 0.");
        }
        if (out == null) {
            throw new IllegalArgumentException("El OutputStream no puede ser null.");
        }

        JSONArray statesArray = new JSONArray();
        PrintWriter writer = new PrintWriter(out);

        for (int i = 0; i < n; i++) {
            simulator.advance();
            statesArray.put(simulator.report());
        }

        JSONObject outputJSON = new JSONObject();
        outputJSON.put("states", statesArray);
        writer.println(outputJSON.toString(2)); 
        writer.flush();
    }

    // resetea el controllador
    public void reset() {
        simulator.reset();
    }
}
