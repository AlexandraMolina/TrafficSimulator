package simulator.factories;

import org.json.JSONObject;

import simulator.model.*;

public class NewJunctionEventBuilder extends Builder<Event> {
	
	//Atributos
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dsFactory;
	private int time;
	private String id;
	private int x;
	private int y;
	private LightSwitchingStrategy lss;
	private DequeuingStrategy ds;
	

	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dsFactory) {
		super("new_junction");
		this.lssFactory = lssFactory;
		this.dsFactory = dsFactory;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		this.time = data.getInt("time");
		this.id = data.getString("id");
		this.x = data.getJSONArray("coor").getInt(0);
		this.y = data.getJSONArray("coor").getInt(1);
		
		JSONObject ls = data.getJSONObject("ls_strategy");
		JSONObject dq = data.getJSONObject("dq_strategy");
		
		this.lss = lssFactory.createInstance(ls);
		this.ds =  dsFactory.createInstance(dq);
		
		return new NewJunctionEvent(this.time, this.id, this.lss, this.ds, this.x, this.y);
	}

}
