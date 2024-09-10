package simulator.model;

import java.util.*;


public class MoveFirstStrategy implements DequeuingStrategy {

	public MoveFirstStrategy() {}
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// TODO Auto-generated method stub
		List<Vehicle> vehicle = new ArrayList<Vehicle>();
		
		if (!q.isEmpty()) {
			vehicle.add(q.get(0)); //incluye el primer vehiculo de q
		}
		
		return vehicle;
	}

}
