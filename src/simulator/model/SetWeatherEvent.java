package simulator.model;

import java.util.*;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	private List<Pair<String, Weather>> ws;
	
	
	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		// TODO Auto-generated constructor stub
		if (ws == null) {
			throw new IllegalArgumentException("Clima no puede ser null");
		} else {
			this.ws = ws;
		}
		
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		for (Pair<String, Weather> w : ws) {
			
			if (map.getRoad(w.getFirst()) == null) {
				throw new IllegalArgumentException("Carretera no exite en el mapa de carreteras");
			} else {
				map.getRoad(w.getFirst()).setWeather(w.getSecond());
			}
		}
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Weather (setWeaher) " + ws.get(0).getFirst().toString() + "," + ws.get(0).getSecond().toString();
	}

}
