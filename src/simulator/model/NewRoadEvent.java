 package simulator.model;


public abstract class NewRoadEvent extends Event {
	
	protected String srcJunc;
	protected String destJunc;
	protected String id;
	protected int length;
	protected int maxSpeed;  // Maxima velocidad de la carretera.
	protected int co2limit; //Limite Alarma de contaminacion
	protected Weather weather; // El tiempo que hay en la carreta.
	protected Junction src;
	protected Junction dest;

	NewRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2limit, int maxSpeed, Weather weather) {
		super(time);
		// TODO Auto-generated constructor stub
		if (time < 0) {
			throw new IllegalArgumentException("El tiempo no puede ser negativo");
		}
		
		if (maxSpeed <= 0) {
			throw new IllegalArgumentException("Argumentos no validos");
		}
		if (co2limit < 0) {
			throw new IllegalArgumentException("Argumentos no validos");
		}
		if (length < 0) {
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
	
	    this.id = id;
	    this.srcJunc = srcJunc;
	    this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.co2limit = co2limit;
		this.length = length;
		this.weather = weather;
		this.src = null;
		this.dest = null;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		src = map.getJuntion(this.srcJunc);
		dest = map.getJuntion(this.destJunc);
		Road r = createRoadObject();
		map.addRoad(r);

		
	}
	
	abstract public Road createRoadObject();

}
