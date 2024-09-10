package simulator.factories;

import java.util.*;

import org.json.*;

import simulator.misc.Pair;
import simulator.model.*;

public class SetWeatherEventBuilder extends Builder<Event> {

	private int time;
	private List<Pair<String, Weather>> ws;
	
	public SetWeatherEventBuilder() {
		super("set_weather");

	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		this.time = data.getInt("time");
		JSONArray jArray = data.getJSONArray("info"); 
		
		ws = new ArrayList<Pair<String, Weather>>();
		
		for (int i = 0; i < jArray.length(); i++) {
			
			ws.add(new Pair<String, Weather>(jArray.getJSONObject(i).getString("road"), Weather.valueOf(jArray.getJSONObject(i).getString("weather"))));
			
		}
		
		
		return new SetWeatherEvent(time, ws);
	}

}
