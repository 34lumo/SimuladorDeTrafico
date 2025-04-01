package simulator.view;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import simulator.control.Controller;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private Controller _ctrl;
	private boolean _stopped;

	public ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		// Aquí añadiremos los botones
	}

	// Métodos de la interfaz TrafficSimObserver se implementarán más adelante
	@Override
	public void onAdvance(simulator.model.RoadMap map, Collection<simulator.model.Event> events, int time) {}

	@Override
	public void onEventAdded(simulator.model.RoadMap map, Collection<simulator.model.Event> events, simulator.model.Event e, int time) {}

	@Override
	public void onReset(simulator.model.RoadMap map, Collection<simulator.model.Event> events, int time) {}

	@Override
	public void onRegister(simulator.model.RoadMap map, Collection<simulator.model.Event> events, int time) {}
}
}
}
