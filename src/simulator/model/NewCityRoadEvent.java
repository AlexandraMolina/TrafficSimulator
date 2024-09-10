package simulator.model;



public class NewCityRoadEvent extends NewRoadEvent {
	
	

	public NewCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2limit, int maxSpeed, Weather weather) {
		super(time, id, srcJunc, destJunc, length, co2limit, maxSpeed, weather);
	}

	@Override
	public Road createRoadObject() {
		// TODO Auto-generated method stub
		return new CityRoad(this.id, this.src, this.dest, this.maxSpeed, this.co2limit, this.length, this.weather);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "New CityRoad '"+ this.id + "'";
	}

}
