package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	protected int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		// TODO Auto-generated method stub
		
		int indNextGreen = 0;
		
		if (roads.isEmpty()) {
			indNextGreen = -1;
		} else if(currGreen == -1) {
			
			int tamQueque = 0;
			int pos = 0;
			
			for (int i = 0; i < qs.size(); i++) {
				if (tamQueque < qs.get(i).size()) {
					tamQueque = qs.get(i).size();
					pos = i;
				}	
			}
			
			indNextGreen = pos;
			
		} else if ((currTime - lastSwitchingTime) < timeSlot) {
			indNextGreen = currGreen;
		} else {
			
			int tamQueque = 0;
			int pos = 0;
			int search = (currGreen + 1) % qs.size();
			
			for (int i = 0; i < qs.size(); i++) {
				if (tamQueque < qs.get(search).size()) {
					tamQueque = qs.get(search).size();
					pos = search;
				}
				
				search++;
				
				if (search == qs.size()) {
					search = 0;
				}
			}
			
			indNextGreen = pos;
					
		}
		
		return indNextGreen;
	}
	
	
	

}
