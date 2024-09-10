package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.*;


public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver  {

	private List<Junction> _junctions;
	private String _cols[] = {"Id", "Green", "Queues"};
	private Controller ctrl;
	
	public JunctionsTableModel(Controller _ctrl) {
		// TODO Auto-generated constructor stub
		this.ctrl =_ctrl;
		_junctions = new ArrayList<Junction>();
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
		return _junctions == null ? 0 : _junctions.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _cols.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String s= "";
		Junction j= _junctions.get(rowIndex);
		switch ( columnIndex) {
		case 0:
		s= "" + j.getId();
		break;
		
		case 1:
			s= j.getGreenLightIndex() == -1 ? //
			"NONE" : //
			j.getInRoads().get(j.getGreenLightIndex()).getId();
			break;
			case 2:
			for (Road r: j.getInRoads()) {
			s= s+ " " + r.getId() + ":" + j.getQueue(r); //Preguntar si el getQueue es así o no?
			}
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
		_junctions = map.getJunctions();
		update();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		_junctions = map.getJunctions();
		update();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		_junctions = map.getJunctions();
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		_junctions = map.getJunctions();
		update();
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	public void update() {
		fireTableStructureChanged();
	}
	
	/*public void setJunctionsList(List<Junction> junctions) {
		_junctions = junctions;
		update();
	}*/

}
