package simulator.model;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {
	
	//Atributos
	private Junction srcJunc; //cruces a los cuales la carretera está conectada (origen)
	private Junction destJunc; //cruces a los cuales la carretera está conectada (destino)
	private int lengthRoad; // longitud de la carretera
	private int maxSpeed;  // Maxima velocidad de la carretera.
	private int limitSpeed; //Limite que se puede circular por la carretera.
	private int contLimit; //Limite Alarma de contaminacion
	private Weather weather; // El tiempo que hay en la carreta.
	private int contTotal; //contaminacion total de la carretera
	private List<Vehicle> vehicles; //Ordenarla de manera descendente por la localizacion. 
	private Comparator<Vehicle> cv;
	//private String idAux;
	

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		
		super(id);
		
		if (maxSpeed <= 0) {
			throw new IllegalArgumentException("Argumentos no validos");
		}
		if (contLimit < 0) {
			throw new IllegalArgumentException("Argumentos no validos");
		}
		if (length <= 0) {
			throw new IllegalArgumentException("Argumentos no validos");
		}
		if (srcJunc == null) {
			throw new IllegalArgumentException("Argumentos no validos");
		}
		if (destJunc == null) {
			throw new IllegalArgumentException("Argumentos no validos");
		}
		if (weather == null) {
			throw new IllegalArgumentException("Argumentos no validos");
		}
		
		setSrc(srcJunc);
		setDest(destJunc);
		this.limitSpeed = maxSpeed; 
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.lengthRoad = length;
		this.weather = weather;
		this.srcJunc.addOutgoingRoad(this);
     	this.destJunc.addIncomingRoad(this);
		
		this.vehicles = new ArrayList<Vehicle>();
		cv = new Comparator<Vehicle>() {
			//comparador de vehiculos
			@Override
			public int compare(Vehicle v1, Vehicle v2) {
				// TODO Auto-generated method stub
				if (v1.getLocation() < v2.getLocation()) {
					return 1;
				} else if (v1.getLocation() > v2.getLocation()) {
					return -1;
				} else {
					return 0;
				}
			}};
		
				
	}
	
	//Getters y Setters
	void setDest(Junction destJunc) {
		// TODO Auto-generated method stub
		this.destJunc = destJunc;
	}


	void setSrc(Junction srcJunc) {
		// TODO Auto-generated method stub
		this.srcJunc = srcJunc;
	}
	
	void setTotalCO2(int contTotal) {
		this.contTotal=contTotal;
	}

	void setSpeedLimit(int limitSpeed) {
		this.limitSpeed=limitSpeed;
		
	}

	public int getLength() {
		return this.lengthRoad;
	}
	
	public Junction getDest() {
		 return this.destJunc;
	}
	
	public Junction getSrc() {
		 return this.srcJunc;
	}
	
	public Weather getWeather() {
		return this.weather;
	}
	
	public int getContLimit() {
		
		return this.contLimit;
	}
	
	public int getMaxSpeed() {
		return this.maxSpeed;
	}
	
	public int getTotalCO2() {
		return this.contTotal;
	}
	
	
	public int getSpeedLimit() {
		return this.limitSpeed;
	}
	
	public List<Vehicle> getVehicles() {	
		return Collections.unmodifiableList(new ArrayList<>(vehicles));
	}

	//pone las condiciones atmosfericas de la carretera
	void setWeather(Weather w) {
		
		if (w != null) {
			this.weather = w;
		} else throw new IllegalArgumentException("Weather no puede ser vacÃ­o");
	}
	
	//añade c unidades de co2 al total de contaminación de la carretera.
	void addContamination(int c) {
		
		if (c < 0) {
			 throw new IllegalArgumentException("c no puede ser negativo");
		} 
		this.contTotal += c;
	}
	
	//para entrar a una carretera dada
	
	void enter(Vehicle v) {
		
		
		if (v.getLocation() == 0 && v.getSpeed() == 0) {
			this.vehicles.add(v);
		} else throw new IllegalArgumentException("Location y/o Speed no es 0");
		
	}
    //abandonar una carretera
	void exit(Vehicle v) {
		this.vehicles.remove(v);
	}
	
	//avanzar el estado de la carretera
	
	void advance(int time) {
		
		reduceTotalContamination();
		updateSpeedLimit();
		
		for (Vehicle v : this.vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		
		vehicles.sort(cv); //sort ordenar 
		
	}
	//Devuelve estado de la carretera en formato JSON
	public JSONObject report() {
		
		 JSONObject jsonRoad = new JSONObject();
		 
		 jsonRoad.put("id", this._id);
		 jsonRoad.put("speedlimit", this.limitSpeed);
		 jsonRoad.put("weather", this.weather.toString());
		 jsonRoad.put("co2", this.contTotal);
		 
		 JSONArray jArrayVehicles = new JSONArray();
		 
		 
		 //[v1,v2,v3]
		 for (Vehicle v : vehicles) {
			jArrayVehicles.put(v.getId());
		}
		 
		 jsonRoad.put("vehicles", jArrayVehicles);
		 
		 return jsonRoad;
		
	}
	
	abstract void reduceTotalContamination(); //reducir el total contaminación de la carretera
	
	abstract void updateSpeedLimit(); //actualizar la velocidad limite de la carretera
	
	abstract int calculateVehicleSpeed(Vehicle v); //Calcular la velocidad de un vehículo
}
