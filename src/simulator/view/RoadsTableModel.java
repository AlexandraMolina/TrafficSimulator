package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.*;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private List<Road> _roads;
	private String _cols[] = {"id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	private Controller ctrl;
	
	public RoadsTableModel(Controller _ctrl) {
		// TODO Auto-generated constructor stub
		this.ctrl=_ctrl;
		_roads = new ArrayList<Road>();
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
		return _roads == null ? 0 : _roads.size() ;
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
		Road r= _roads.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			s=""+r.getId();
			break;
		case 1:
			s=""+r.getLength();
			break;
		case 2:
			s=""+r.getWeather();
			break;
		case 3:
			s=""+r.getMaxSpeed();
			break;
		case 4:
			s=""+r.getSpeedLimit();
			break;
		case 5:
			s=""+r.getTotalCO2();
			break;
		case 6: 
			s= ""+r.getContLimit();
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
		_roads = map.getRoads();
		update();	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		_roads = map.getRoads();
		update();	
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		_roads = map.getRoads();
		update();	
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		_roads = map.getRoads();
		update();	
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	public void update() {
		fireTableDataChanged();
	}
	
	/*public void setRoadList(List<Road> roads) {
		_roads = roads;
		update();
	}*/

}
