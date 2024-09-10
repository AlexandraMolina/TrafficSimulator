package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {

	private List<Pair<String, Integer>> cs;
	
	
	public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		// TODO Auto-generated constructor stub
		if (cs == null) {
			throw new IllegalArgumentException("Contaminacion no puede ser null");
		} 
		
		this.cs = cs;
		
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		for (Pair<String, Integer> c : cs) {
			
			if (map.getVehicle(c.getFirst()) == null) {
				throw new IllegalArgumentException("Vehiculo no exite en el mapa de carreteras");
			} 
				
			map.getVehicle(c.getFirst()).setContClass(c.getSecond());
			
		}
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Co2 (setContClass) " + cs.get(0).getFirst().toString() + "," + cs.get(0).getSecond().toString();
	}

}
