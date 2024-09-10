package simulator.factories;

import java.util.*;

import org.json.*;

import simulator.misc.Pair;
import simulator.model.*;

public class NewSetContClassEventBuilder extends Builder<Event> {

	private List<Pair<String, Integer>> cs;
	private int time;
	
	
	public NewSetContClassEventBuilder() {
		super("set_cont_class");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		this.time = data.getInt("time");
		
		JSONArray jArray = data.getJSONArray("info"); 
		
		cs = new ArrayList<Pair<String, Integer>>();
		
		for (int i = 0; i < jArray.length(); i++) {
			
			cs.add(new Pair<String, Integer>(jArray.getJSONObject(i).getString("vehicle"), jArray.getJSONObject(i).getInt("class")));
			
		}
		
		return new NewSetContClassEvent(this.time, cs);
	}

}
