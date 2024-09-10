package simulator.factories;

import org.json.JSONObject;

import simulator.model.*;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{
	
	
	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		MostCrowdedStrategy ms;
		
		if (data.has("timeslot")) {
			ms = new MostCrowdedStrategy(data.getInt("timeslot"));
		} else {
			ms = new MostCrowdedStrategy(1);
		}
		
		return ms;
	}


}
