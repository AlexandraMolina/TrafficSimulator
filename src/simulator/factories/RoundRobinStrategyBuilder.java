package simulator.factories;

import org.json.JSONObject;

import simulator.model.*;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		RoundRobinStrategy rs;
		
		if (data.has("timeslot")) {
			rs = new RoundRobinStrategy(data.getInt("timeslot"));
		} else {
			rs = new RoundRobinStrategy(1);
		}
		
		return rs;
	}

}
