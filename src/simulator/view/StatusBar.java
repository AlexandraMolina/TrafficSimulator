package simulator.view;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{

	private JLabel jTime;
	private JLabel currenTime= new JLabel();
	private JLabel currentEvent = new JLabel();
	private Controller ctrl;
	
	public StatusBar(Controller _ctrl) {
		// TODO Auto-generated constructor stub
		this.ctrl=_ctrl;
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		jTime = new JLabel("Time: " , JLabel.LEFT);
		currenTime = new JLabel("");
		this.add(jTime);
		this.add(currenTime);
		
		this.add(new JSeparator(SwingConstants.VERTICAL));
		currentEvent = new JLabel("");
		this.add(currentEvent);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		currenTime.setText(" " + time);
		currentEvent.setText("");
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		currenTime.setText(" " + time);
		currentEvent.setText("");
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		currenTime.setText(" "+time);
		currentEvent.setText(e.toString());
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		currenTime.setText(" "+ time);
		currentEvent.setText("");
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		currenTime.setText(" "+time);
		currentEvent.setText("");
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}

}
