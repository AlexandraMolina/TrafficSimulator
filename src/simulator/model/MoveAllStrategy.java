package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	public MoveAllStrategy() {}
	

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// TODO Auto-generated method stub
		List<Vehicle> vehicle = new ArrayList<Vehicle>();
		//devuelve la lista de todos los vehiculos que están en q
		for (Vehicle v : q) {
			vehicle.add(v);
		}
		
		return vehicle;
	}
	
}
