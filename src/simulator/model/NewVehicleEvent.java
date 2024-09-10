package simulator.model;

import java.util.*;

public class NewVehicleEvent extends Event {
	
	
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;

	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		// TODO Auto-generated constructor stub
		if (maxSpeed <= 0) {
			 throw new IllegalArgumentException("Valor negativo");
		} 
		
		if (contClass < 0 || contClass > 10) {
			 throw new IllegalArgumentException("Valor no valido, no esta entre 0 y 10");
			
		} 
						
		if (itinerary.size() < 2) {
			
			throw new IllegalArgumentException("Lista tiene un tamaño menor a 2");
		} 
		
		
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
				
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		List<Junction> listJunc = new ArrayList<>();
		
		for (String s : itinerary) {
			listJunc.add(map.getJuntion(s));
		}
		
		Vehicle v = new Vehicle(id,maxSpeed,contClass, listJunc);
		map.addVehicle(v);
		v.moveToNextRoad();
				
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "New Vehicle '"+ this.id + "'";
	}

}
