package simulator.model;

import java.util.*;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	
	//Atritubos
	private List<Junction> itinerary; //lista de cruces que representan el itinerario del vehiculo
	private int maxSpeed; // velocidad maxima
	private int actSpeed; //velocidad actual
	private VehicleStatus status; //Estado del vehiculo
	private Road road; //Carretera sobre la que está el coche
	private int location; // posición del vehiculo sobre la carretera
	private int totalContamination; // total de CO2 emitido por el vehiculo en toda su trayectoria recorrida.
	private int contClass; //Clase de contaminacion medioambiental de CO2 [Etiqueta].
	private int distanciaTotal; // Distancia total recorrida por el vehiculo
	private int indice; // Donde se guarda el ultimo cruce del itinerario visitado por el vehiculo
	private Junction a = null; 
	private Junction s = null;
	
	
	//Constructora 
	Vehicle(String id, int maxSpeed, int contClass , List<Junction> itinerary) {
		super(id);
		// TODO Auto-generated constructor stub
		if (maxSpeed <= 0) {
			 throw new IllegalArgumentException("Valor negativo");
		} 
		
		if (contClass < 0 || contClass > 10) {
			 throw new IllegalArgumentException("Valor no valido, no esta entre 0 y 10");
			//this.contClass = contClass;
		} 
		
		
		if (itinerary.size() < 2) {

			throw new IllegalArgumentException("Lista tiene un tamaÃ±o menor a 2");
		} 
	
		
		this.actSpeed = 0;
		this.maxSpeed = maxSpeed;
		this.location = 0;
		this.road = null;
		this.indice = 0;
		this.status = VehicleStatus.PENDING;
		this.contClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		
	}
	
	

	

//GETTERS Y SETTERS
   public int getLocation() {
	   return this.location;
   }
   
   public int getSpeed() {
	   
	   return this.actSpeed;   
   }
   
   public int getMaxSpeed() {
		return this.maxSpeed;
	}
   
   public int getContClass() {
	   return this.contClass;
   }
   
   public VehicleStatus getStatus() {
	   return this.status;
   }
   
   public List<Junction> getItinerary() {
	   return itinerary;
   }
   
  public Road getRoad() {
	   return road;
	}
   
   public int getTotalCO2() {
	   return this.totalContamination;
   }
   public int getDistToRec() {
	   return distanciaTotal;
   }

/*
 * Pone la velocidad actual al valor minimo entre s y la velocidad máxima 
 */
   void setSpeed(int s) {
	   
	   
	  if (this.status != VehicleStatus.TRAVELING) {
		  
	  }else { 
		  
	   if (s < 0) {
		   throw new IllegalArgumentException("La velocidad actual no puede ser negativo");
	   }
	   
	   
	   // aquí sacamos el minimo entre maxSpeed y s para asignarlo a la velocidad actual.
	   this.actSpeed = Math.min(maxSpeed, s);
	   
	  }
   }
    /*
     * pone el valor de la contaminación del vehiculo a c.
     */
   void setContClass(int c) {
	   
	   
	   if (c < 0 || c > 10) {
			 throw new IllegalArgumentException("Valor no valido, no esta entre 0 y 10");
		
		} 
	   
	   this.contClass = c;
	   
   }
   
 
   void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
   
   
   //Metodos de la clase
   /*
    * Con este metodo movemos el vehiculo a la siguiente carretera 
    */
   void moveToNextRoad() {
	   
	     
	   if (this.status != VehicleStatus.PENDING && this.status != VehicleStatus.WAITING) {
		   throw new IllegalArgumentException("Estado no permitido");		 
	  } 
	   
	  if (road != null) {
		  this.road.exit(this);// El vehiculo sale de carreta actual
	  } 
	  
	  if (indice == this.itinerary.size() - 1) {
		  this.road = null;
		  this.status = VehicleStatus.ARRIVED;
     	  this.location = 0;
	  } else {
		     a = this.itinerary.get(indice);
	         s = this.itinerary.get(indice + 1);
	         this.location = 0;
	         Road cruce = a.roadTo(s); 
	         cruce.enter(this);
	         this.road = cruce;
	         this.status = VehicleStatus.TRAVELING; 
	  }
	   

	  
   }
   
   /*
    * Comprueba que el estado del vehiculo sea viajando , busca el valor
    * minimo entre la localización actual + velocidad actual con la longitud de la carretera
    * además calculo la contaminación producida, mediante una formula, además de que si la la nueva localización 
    * es igual a la longitud de la carretera, el vehiculo entrará a la cola de cruces  
    */
   
  void advance(int time) {
	   
	   int prevLocation = this.location;
	   
	   if (this.status == VehicleStatus.TRAVELING) {
		   //saca el minimo 
		   this.location = Math.min((prevLocation + this.actSpeed), this.road.getLength()); //Revisar localizacion actual (puesto ahora "location")
		   
		   //Calcular
		   int contProducida = 0;
		   
		   //saca la contaminación porducida mediante la formula c=(l*f) siendo f el grado de contaminación y l es la distancia recorrida en ese paso de simulación
		   contProducida = this.contClass * (this.location - prevLocation);
		   distanciaTotal += (this.location - prevLocation);
		   
		   this.totalContamination += contProducida;   
		   this.road.addContamination(contProducida);
		  
		   if (this.location >= this.road.getLength()) {
			   
			   this.road.getDest().enter(this);
			   this.status = VehicleStatus.WAITING; //Waiting : Esperando a un cruce.
			   this.actSpeed = 0;
			   this.indice++;
			      
		   }
		 
	   } else {
		   this.actSpeed = 0;
	   }
   }
   //Devuelve el estado del vehiculo en formato JSON
   public JSONObject report() {
	   
	   JSONObject jsonVehicule = new JSONObject();
	   
	   jsonVehicule.put("id", this._id); 
	   jsonVehicule.put("speed", this.actSpeed); 
	   jsonVehicule.put("distance", this.distanciaTotal); 
	   jsonVehicule.put("co2", this.totalContamination); 
	   jsonVehicule.put("class", this.contClass); 
	   jsonVehicule.put("status", this.status.toString()); 
	   
	   
	   if (this.status != VehicleStatus.PENDING && this.status != VehicleStatus.ARRIVED) {
		   jsonVehicule.put("road", this.road.getId());
		   jsonVehicule.put("location", this.location); 
	   }
	   
	   return jsonVehicule;
   }
   
}
