package simulator.model;

import java.awt.RenderingHints;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject{
	
	//Atributos
	private List<Road> listRoadsEnter;
	private Map<Junction, Road> mapRoadsExit;
	private List<List<Vehicle>> listQuequeIns;
	private Map<Road, List<Vehicle>> RoadQueque;
	private int indexGreen;
	private int lastChangeSem;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;

	Junction(String id, LightSwitchingStrategy lsStrategy,DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		
		if (lsStrategy == null || dqStrategy == null) {
			throw new IllegalArgumentException("No puede null las estrategias");
		}
		
		if ((xCoor < 0) || (yCoor < 0) ) {
			throw new IllegalArgumentException("Las coordenadas tienen que ser positivas");
		}
		
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.indexGreen = -1;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.mapRoadsExit = new HashMap<Junction, Road>();
		this.listRoadsEnter = new ArrayList<Road>();
		this.listQuequeIns = new ArrayList<List<Vehicle>>();
		this.RoadQueque = new HashMap<Road,List<Vehicle>>();
	}
	
	public int getX() {
		return this.xCoor;
	}
	
	public int getY() {
		return this.yCoor;
	}
	
	void addIncomingRoad(Road r) {
		
		if (!(this.equals(r.getDest()))) {
			throw new IllegalArgumentException("El cruce no hay");
		} else {
			this.listRoadsEnter.add(r); //Añadir a la cola de carreteras entrantes
			List<Vehicle> v = new LinkedList<Vehicle>();  //Creamos la cola de vehiculos con LinkedList, es una cola doblemente enlazada.
			this.listQuequeIns.add(v); //Añadiendo esa cola a la lista de colas entrantes.
			this.RoadQueque.put(r, v); //Añadimos al mapa, de clave carretera y valor es la lista de colas entrantes de los vehiculos.
		}
		
	}
	
	 void addOutgoingRoad(Road r) {
		
		if (!(this.equals(r.getSrc()))) {
			throw new IllegalArgumentException("El cruce no hay");
		}
		
		if (this.mapRoadsExit.get(r.getDest()) != null) {
			throw new IllegalArgumentException("Hay mas de un cruce");
		}
		
		this.mapRoadsExit.put(r.getDest(), r);
	}
	//a�ade V a la cora de la carretera r, donde r es la carretera actual de V
	void enter(Vehicle v) {
		
		Road r = v.getRoad();
		List<Vehicle> colaV = RoadQueque.get(r);
		colaV.add(v);
		
	}
	
	//devuelve la carretera que va desde el cruce actual al cruce j, busca las carreterasa salientes
	Road roadTo(Junction j) {
		
		return this.mapRoadsExit.get(j);
	}
	
	void advance(int time) {
		
		if ((this.indexGreen != -1) && !(listQuequeIns.isEmpty())) {
			
			List<Vehicle> colaV = this.listQuequeIns.get(this.indexGreen);
			List<Vehicle> colaV2 =  this.dqStrategy.dequeue(colaV);
			
			for (Vehicle v : colaV2) {
				v.moveToNextRoad();
				colaV.remove(v);
			}
			
		}
		
		
		int changeIndexGreen = lsStrategy.chooseNextGreen(listRoadsEnter, listQuequeIns, indexGreen, lastChangeSem, time);
		
		if (changeIndexGreen != this.indexGreen) {
			this.lastChangeSem = time;
			this.indexGreen = changeIndexGreen;
		}
	}
	
	public JSONObject report() {
		
		 JSONObject jsonJunction = new JSONObject();
		 
		 jsonJunction.put("id", this._id);
		 
		 if (this.indexGreen == -1) {
			 jsonJunction.put("green", "none");
		} else {
			jsonJunction.put("green", this.listRoadsEnter.get(indexGreen).getId());
		}
		 
		 JSONArray jArrayQuequeRoads = new JSONArray();
		 
		 jsonJunction.put("queues", jArrayQuequeRoads);
		 
		 for (Road r : listRoadsEnter) {
			JSONObject jsonRoads = new JSONObject();
			jArrayQuequeRoads.put(jsonRoads);
			jsonRoads.put("road", r.getId());
			
			JSONArray jArrayVehicles = new JSONArray();
			jsonRoads.put("vehicles", jArrayVehicles);
			
			for (Vehicle v : RoadQueque.get(r)) {
				
				jArrayVehicles.put(v.getId());
			}
		}
		return jsonJunction;
	}

	public int getGreenLightIndex() {
		// TODO Auto-generated method stub
		return indexGreen;
	}

	public List<Road> getInRoads() {
		// TODO Auto-generated method stub
		return listRoadsEnter;
	}
    //hay que mirar
	public List<List<Vehicle>> getQueue(Road r) {
		// TODO Auto-generated method stub
		return listQuequeIns;
	}

	
}
