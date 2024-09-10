package simulator.model;

public class CityRoad extends Road{
	
	CityRoad(String id, Junction srcJunc, Junction descJunc,int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, descJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}
	
	void reduceTotalContamination() {
		
		int x = 0;
		
		switch (this.getWeather()) {
		case WINDY:
		case STORM:
			x = 10;
			break;
		default:
			x = 2;
			break;
		}
		
		//
		
		if (getTotalCO2() - x >=0) {
			setTotalCO2(getTotalCO2()-x);
		} else {
			setTotalCO2(0);
		}	
		
	}
	
	void updateSpeedLimit( ) {
		
		int limit = getSpeedLimit();
			limit = getMaxSpeed();

    }
	
	int calculateVehicleSpeed(Vehicle v) {
		
		int velocidad = 0;
		
		if (v.getStatus()==VehicleStatus.TRAVELING) {
			
		/*(int)(((11.0-f )/11.0)*s), donde s es la velocidad límite de la carretera y f es el grado de contaminación del
			vehículo.*/
			velocidad = (int)(((11.0 - v.getContClass()) /11.0) * getSpeedLimit());
		}
		
		return velocidad;
	}

}
