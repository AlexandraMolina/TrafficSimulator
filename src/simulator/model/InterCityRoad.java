package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction descJunc,int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, descJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}
	
	void updateSpeedLimit() {
		
		if (getTotalCO2() > getContLimit()) {
			setSpeedLimit((int)(getMaxSpeed() * 0.5));
		} else {
			setSpeedLimit(getMaxSpeed());
		}
}
	
	void reduceTotalContamination() {
		
		int x = 0;
		
		switch (this.getWeather()) {
		case SUNNY:
			x = 2;
			break;
		case CLOUDY:
			x = 3;
			break;
		case RAINY:
			x = 10;
			break;
		case WINDY:
			x = 15;
			break;
		case STORM:
			x = 20;
			break;
		}
	/*
		(int)((100.0- x)/100.0)*tc)” donde tc es la	contaminación total actual y x depende de las condiciones
		atmosféricas*/
		setTotalCO2((int)(((100.0 - x)/100.0)* getTotalCO2()));
	}
	

	
	int calculateVehicleSpeed(Vehicle v) {
		
		
		if (getWeather() == Weather.STORM) {
			return (int)(getSpeedLimit()*0.8); 
		} else {
		
			return getSpeedLimit();
		}
		
	}
	
}
