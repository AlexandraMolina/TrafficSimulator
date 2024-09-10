package simulator.factories;

import simulator.model.*;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	
	public NewCityRoadEventBuilder() {
		super("new_city_road");
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public Event createTheRoad() {
		// TODO Auto-generated method stub
		return new NewCityRoadEvent(this.time, this.id, this.srcJunc, 
				this.destJunc, this.length, this.co2limit, this.maxSpeed, this.weather);
	}

}
