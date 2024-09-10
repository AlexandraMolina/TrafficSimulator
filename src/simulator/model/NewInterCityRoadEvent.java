package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {

	

	public NewInterCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2limit, int maxSpeed, Weather weather) {
		super(time, id, srcJunc, destJunc, length, co2limit, maxSpeed, weather);
		// TODO Auto-generated constructor stub
		
	}

	
	@Override
	public Road createRoadObject() {
		// TODO Auto-generated method stub
		return new InterCityRoad(this.id, this.src, this.dest, this.maxSpeed, this.co2limit, this.length, this.weather);
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "New InterCityRoad '"+ this.id + "'";
	}
	
	

}
