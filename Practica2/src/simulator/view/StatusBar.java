package simulator.view;

import java.awt.FlowLayout;
import java.util.Collection;

import javax.swing.*;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private JLabel etiquetaTiempo;
	private JLabel campoTiempo;
	private JLabel mensajeEvento;

	private Controller controller;

	public StatusBar(Controller c) {
		controller = c;
		initGUI();
		controller.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1)); // relieve de barra

		etiquetaTiempo = new JLabel("Tiempo: ");
		campoTiempo = new JLabel("0");

		mensajeEvento = new JLabel("Listo.");
		mensajeEvento.setHorizontalAlignment(SwingConstants.LEFT);

		this.add(etiquetaTiempo);
		this.add(campoTiempo);

		this.add(new JSeparator(SwingConstants.VERTICAL));
		this.add(Box.createHorizontalStrut(10));
		this.add(mensajeEvento);
	}

	@Override
	public void onAdvance(RoadMap mapa, Collection<Event> eventos, int tiempo) {
		campoTiempo.setText(Integer.toString(tiempo));
		mensajeEvento.setText("Simulación avanzada.");
	}

	@Override
	public void onEventAdded(RoadMap mapa, Collection<Event> eventos, Event evento, int tiempo) {
		campoTiempo.setText(Integer.toString(tiempo));
		mensajeEvento.setText("Añadido: " + evento.toString());
	}

	@Override
	public void onReset(RoadMap mapa, Collection<Event> eventos, int tiempo) {
		campoTiempo.setText(Integer.toString(tiempo));
		mensajeEvento.setText("Simulación reiniciada.");
	}

	@Override
	public void onRegister(RoadMap mapa, Collection<Event> eventos, int tiempo) {
		campoTiempo.setText(Integer.toString(tiempo));
		mensajeEvento.setText("Observador registrado.");
	}
}
