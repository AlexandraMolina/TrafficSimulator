package simulator.control;

import java.io.*;
import java.util.Iterator;

import org.json.*;

import simulator.factories.*;
import simulator.model.*;

public class Controller implements Observable<TrafficSimObserver> {
	
	private TrafficSimulator sim;
	private Factory<Event> eventsFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		
		this.sim = sim;
		this.eventsFactory = eventsFactory;
		
		if (sim == null) {
			throw new IllegalArgumentException("Sim no puede ser vacía");
		}

		if (eventsFactory == null) {
			throw new IllegalArgumentException("EventsFactory no puede ser vacía");
		}
	}
	
	public void loadEvents(InputStream in) {
		
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray jArray = jo.getJSONArray("events");
		
		if (!jo.has("events")) {
			throw new IllegalArgumentException("events son diferentes");
		}
		
		for (int i = 0; i < jArray.length(); i++) {
			sim.addEvent(eventsFactory.createInstance(jArray.getJSONObject(i)));
		}
	}
	
	public void run(int n, OutputStream out) {
		
		PrintStream ps = new PrintStream(out); 
		ps.println("{");
		ps.println("\"states\": [");
			
		for (int i = 0; i < n; i++) {
			this.sim.advance();
			ps.print(this.sim.report());
			if(i!=n-1) {
			ps.println(",");	
			}
		}
		ps.println("");
		ps.println("]");
		ps.println("}"); 
		
	}
	
	public void reset() {
		this.sim.reset();	
	}

	
	public void addEvent(Event e) {
		
		this.sim.addEvent(e);
		
	}
	/*simulador GUI*/
	
	public void run(int n) {
		for (int i=1; i<=n;i++) {
			sim.advance();
			
		}
	}
	
	
	@Override
	public void addObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		
		this.sim.addObserver(o);
		
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		
		this.sim.removeObserver(o);
		
	}
}
 