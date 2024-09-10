package simulator.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import simulator.control.Controller;

public class MainWindow extends JFrame {
	
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("TrafficSimulator");
		this._ctrl= ctrl;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel= new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
	
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		
		JPanel viewsPanel= new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		
		JPanel tablesPanel= new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		JPanel mapsPanel= new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		// tables
		JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);
		
		// TODO add other tables
		JPanel vehiclesView = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesView);
		
		JPanel roadsView = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
		roadsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsView);
		
		
		JPanel junctionsView = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
		junctionsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsView);
		
		// maps
		JPanel mapView= createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		// TODO add a map for MapByRoadComponent
		
		JPanel mapByRoadView= createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
		mapByRoadView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapByRoadView);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("./resources/icons/weather.png"));
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(true); //Para poder redimensionar los componentes
		this.pack();
		this.setVisible(true);
	}
	
	//Falta createViewPanel (Preguntar de que modo sería)
	private JPanel createViewPanel(JComponent c, String title) {
		
		JPanel p= new JPanel( new BorderLayout());
		// TODO add a framed border to p with a title
		
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), title, TitledBorder.LEFT, TitledBorder.TOP));
		
		p.add(new JScrollPane(c));
		
		//Alternativa
		
		/*
		 * JPanel p= new JPanel( new BorderLayout());
		 * 
		 * Border b = BorderFactory.createLineBorder(Color.red, 1);
		 * 
		 * p.setBorder(BorderFactory.createTitledBorder(b, title));
		 * p.add(new JScrollPane(c));
		 * */
		
		
		return p;
		}
		

}
