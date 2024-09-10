package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import simulator.model.*;

public class NewVehicleEventBuilder extends Builder<Event> {
	
	//Atributos
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;
	private int time;
	
	public NewVehicleEventBuilder() {
		super("new_vehicle");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		this.time = data.getInt("time");
		this.id = data.getString("id");
		this.maxSpeed = data.getInt("maxspeed");
		this.contClass = data.getInt("class");
		this.itinerary = new ArrayList<String>();
		
		JSONArray jArray = data.getJSONArray("itinerary");
		
		for (int i = 0; i < jArray.length(); i++) {
			itinerary.add(jArray.getString(i));
		}
		
		return new NewVehicleEvent(this.time, this.id, this.maxSpeed, this.contClass, this.itinerary);
	}
	
	
	

}
