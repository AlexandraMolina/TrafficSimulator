package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.*;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	
	//Atributos
	private List<Event> _events;
	private String _cols[] = {"Time", "Desc."};
	private Controller ctrl;
	
	
	//Constructor
	public EventsTableModel(Controller _ctrl){
		this.ctrl =_ctrl;
		_events = new ArrayList<Event>();
		ctrl.addObserver(this);
		
	}
	

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return _cols[column];
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return _events == null ? 0 : _events.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _cols.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		String s= "";
		Event e= _events.get(rowIndex);
		switch ( columnIndex) {
		case 0:
		s= "" + e.getTime();
		break;
		case 1:
		s= e.toString();
		break;
		default:
		assert(false);
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		_events = events;
		update();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		_events = events;
		update();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		_events = events;
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		_events = events;
		update();
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	public void update() {
		fireTableStructureChanged();
	}
	
	/*public void setEventList(List<Event> events) {
		_events = events;
		update();
	}*/

	
}
