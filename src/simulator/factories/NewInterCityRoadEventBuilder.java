package simulator.factories;

import simulator.model.*;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	
	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
		// TODO Auto-generated constructor stub
	}

	@Override
	public Event createTheRoad() {
		// TODO Auto-generated method stub
		return new NewInterCityRoadEvent(this.time, this.id, this.srcJunc, 
				this.destJunc, this.length, this.co2limit, this.maxSpeed, this.weather);
	}
	
}
