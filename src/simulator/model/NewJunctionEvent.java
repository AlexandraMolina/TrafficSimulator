package simulator.model;

public class NewJunctionEvent extends Event {
	
	//Atributos
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;
	private String id;

	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(time);
		
		if (lsStrategy == null || dqStrategy == null) {
			throw new IllegalArgumentException("No puede null las estrategias");
		}
		
		if ((xCoor < 0) || (yCoor < 0) ) {
			throw new IllegalArgumentException("Las coordenadas tienen que ser positivas");
		}
		
		this.id = id;
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		Junction junc = new Junction(this.id, this.lsStrategy, this.dqStrategy, this.xCoor, this.yCoor);
		map.addJunction(junc);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "New Junction '"+ this.id + "'";
	}

}
