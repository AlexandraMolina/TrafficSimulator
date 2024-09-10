package simulator.model;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	//Atributos
	private List<Junction> listJunc; //Lista de cruces
	private List<Road> listRoad; //Lista de carreteras
	private List<Vehicle> listVehicles; //Lista de vehiculos
	private Map<String, Junction> mapJunc; //Mapa de cruces
	private Map<String, Road> mapRoad;     //Mapa de carreteras
	private Map<String, Vehicle> mapVehicles; //Mapa de vehicles
	
	//Constructora
	protected RoadMap() {
		
		this.listJunc = new ArrayList<Junction>();
		this.listRoad = new ArrayList<Road>();
		this.listVehicles = new ArrayList<Vehicle>();
		this.mapJunc = new HashMap<String, Junction>();
		this.mapRoad = new HashMap<String, Road>();
		this.mapVehicles = new HashMap<String, Vehicle>();
	}
	//añade el cruce j al final de cruces y modifica el mapa
	void addJunction(Junction j) {
		
		if (!this.mapJunc.containsKey(j.getId())) {
			this.mapJunc.put(j.getId(), j);
			this.listJunc.add(j);
		} else throw new IllegalArgumentException("Ya existe un cruce con esta id");
	}
	//Añade la carretera r al final de la lista de carreteras y modifica el mapa
	void addRoad(Road r) {
		
		//Revisar el constains de listRoad
		if (this.listRoad.contains(r.getId()) && this.mapJunc.containsValue(r.getSrc()) && this.mapJunc.containsValue(r.getDest())) {
			throw new IllegalArgumentException("La carretera ya existe");
		}
		
		this.listRoad.add(r);
		this.mapRoad.put(r.getId(), r);
	}
	//añade el vehiculo v al final de la lista de vehiculos y modifica el mapa
	void addVehicle(Vehicle v) {
		
		List<Junction> itinerario = v.getItinerary();
		
		if (this.listVehicles.contains(v)) {
			throw new IllegalArgumentException("Vehiculo ya existe");
		}
		
		for (int i = 0; i < itinerario.size() - 1; i++) {
			if (itinerario.get(i).roadTo(itinerario.get(i + 1)) == null) {
				throw new IllegalArgumentException("El itinerario no puede estar vacio");
			}
			
		}
		
		this.listVehicles.add(v);
		this.mapVehicles.put(v.getId(), v);
		
	}
	
	
	//Getter y Setter
	public Junction getJuntion(String id) {
		
		if (this.listJunc.contains(this.mapJunc.get(id))) {
			return this.mapJunc.get(id);
		}
		
		return null;
		
	}
	
	public Road getRoad(String id) {
		
		if (this.listRoad.contains(this.mapRoad.get(id))) {
			return this.mapRoad.get(id);
		}
		
		return null;
	}
	
	public Vehicle getVehicle(String id) {
		
		if (this.listVehicles.contains(this.mapVehicles.get(id))) {
			return this.mapVehicles.get(id);
		}
		
		return null;
	}
	
	public List<Junction> getJunctions() {
		
		return Collections.unmodifiableList(this.listJunc);
	}
	
	public List<Road> getRoads() {
		
		return Collections.unmodifiableList(this.listRoad);
	}
	
	public List<Vehicle> getVehicles() {
		
		return Collections.unmodifiableList(this.listVehicles);
	}
	
	
	
	void reset() {
		
		this.listJunc.clear();
		this.listRoad.clear();
		this.listVehicles.clear();
		this.mapJunc.clear();
		this.mapRoad.clear();
		this.mapVehicles.clear();
		
		
	}
	
	public JSONObject report() {
		
		JSONObject json = new JSONObject();
		JSONArray jJunc = new JSONArray();
		JSONArray jRoad = new JSONArray();
		JSONArray jVehicle = new JSONArray();
		
		
		json.put("roads", jRoad);
		json.put("vehicles", jVehicle);
		json.put("junctions", jJunc);
		
		
		for (Road r : this.listRoad) {
			jRoad.put(r.report());
		}
		
		for (Vehicle v : this.listVehicles) {
			jVehicle.put(v.report());
		}
		
		for (Junction j : this.listJunc) {
			jJunc.put(j.report());
		}

		
		return json;
			
	}
	
}
