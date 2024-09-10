package simulator.factories;

import org.json.JSONObject;

import simulator.model.*;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	 //Atributos
	 protected String srcJunc;
	 protected String destJunc;
	 protected String id;
	 protected int length;
	 protected int maxSpeed;  // Maxima velocidad de la carretera.
	 protected int co2limit; //Limite Alarma de contaminacion
	 protected Weather weather; // El tiempo que hay en la carreta.
	 protected int time;
	
	
	NewRoadEventBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		this.time = data.getInt("time");
		this.id = data.getString("id");
		this.srcJunc = data.getString("src");
		this.destJunc = data.getString("dest");
		this.length = data.getInt("length");
		this.co2limit = data.getInt("co2limit");
		this.maxSpeed = data.getInt("maxspeed");
		this.weather = Weather.valueOf(data.getString("weather")); 
		
		return createTheRoad();
	}
	
	abstract public Event createTheRoad();

}
