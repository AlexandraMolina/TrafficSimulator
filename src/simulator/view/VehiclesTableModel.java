package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	private List<Vehicle> _vehicle;
	private String _cols[] = {"id", "Location", "Itinerary", "CO2 Class", "Max. Speed", "Speed", "Total CO2", "Distance"};
	private Controller ctrl;
	
	public VehiclesTableModel(Controller _ctrl) {
		
		this.ctrl=_ctrl;
		_vehicle = new ArrayList<Vehicle>();
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
		return _vehicle == null ? 0 : _vehicle.size() ;
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
		Vehicle v= _vehicle.get(rowIndex);
		switch ( columnIndex) {
		case 0:
		s= "" + v.getId();
		break;
		case 1:
			switch (v.getStatus()) {
			case PENDING:
				s= "Pending";
				break;
				case TRAVELING:
				s= v.getRoad().getId() + ":" +v.getLocation();
				break;
				case WAITING:
				s= "Waiting:"+v.getRoad().getDest().getId();
				break;
				case ARRIVED:
				s= "Arrived";
				break;
			}
	
		break;
		case 2:
			s="" + v.getItinerary();
			break;
		case 3:
			s=""+v.getContClass();
			break;
		case 4:
			s=""+v.getMaxSpeed();
			break;
		case 5:
			s=""+v.getSpeed();
			break;
		case 6:
			s=""+v.getTotalCO2();
			break;
		case 7:
			s=""+v.getDistToRec();
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
		_vehicle = map.getVehicles();
		update();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		_vehicle = map.getVehicles();
		update();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		_vehicle = map.getVehicles();
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		_vehicle = map.getVehicles();
		update();
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	public void update() {
		fireTableDataChanged();
	}
	
	/*public void setVehicleList(List<Vehicle> vehicle) {
		_vehicle = vehicle;
		update();
	}*/
	
}
