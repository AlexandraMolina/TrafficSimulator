package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {

	//Constructora
	// timeSlot: que representa el nÃºmero de â€œticksâ€� consecutivos durante los cuales 
	//la carretera puede tener el semÃ¡foro en verde
	private int timeSlot; 
	
	public RoundRobinStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		// TODO Auto-generated method stub
		int indNextGreen;
		
		if (roads.isEmpty()) {
			indNextGreen = -1;
		} else if (currGreen == -1) {
			indNextGreen = 0;
		} else if ((currTime - lastSwitchingTime) < timeSlot) {
			indNextGreen = currGreen;
		} else {
			/*
			 * devuelve currGreen+1mÃ³dulo la longitud de la lista roads
			 * (es decir, el Ã­ndice de la siguiente carretera entrante, 
			 * recorriendo la lista de forma circular).
			 * */
			indNextGreen = (currGreen + 1) % roads.size();
		}
		
		return indNextGreen;
	}
	
	

}
