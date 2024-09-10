package simulator.factories;

import org.json.JSONObject;

import simulator.model.*;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy>  {

	public MoveAllStrategyBuilder() {
		super("move_all_dqs");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		return new MoveAllStrategy();
	}

}
