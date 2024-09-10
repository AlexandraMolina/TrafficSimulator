package simulator.model;

import java.util.*;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	
	//Atributos
	private RoadMap mapRoad;
	private List<Event> listEvent;
	private int time;
	private List<TrafficSimObserver> ob;
	
	public TrafficSimulator() {
		this.listEvent = new SortedArrayList<Event>();
		this.mapRoad = new RoadMap();
		this.time = 0;
		ob= new ArrayList<TrafficSimObserver>();
	}
	
	public void addEvent(Event e) {
		
		//revisar
		if (e.getTime()<=time)
			throw new IllegalArgumentException("error");
			listEvent.add(e);
			
		for (TrafficSimObserver o : ob) {
			o.onEventAdded(mapRoad, listEvent, e, time);
		}	
	}
	
	public void advance() {
		
		this.time++;
		
		for (TrafficSimObserver o : ob) {
			o.onAdvanceStart(mapRoad, listEvent, time);
		}
		
		try {
			
			while ((this.listEvent.size() > 0) && (this.listEvent.get(0).getTime() == this.time)) {
				this.listEvent.remove(0).execute(this.mapRoad);
			}

			for (Junction j : this.mapRoad.getJunctions()) {
				j.advance(this.time);
			}

			for (Road r : this.mapRoad.getRoads()) {
				r.advance(this.time);
			}
		
		} catch (Exception e2) {
			// TODO: handle exception
			for (TrafficSimObserver o : ob) {
				o.onError(e2.getMessage());
			}
			
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}
		
		for (TrafficSimObserver o : ob) {
			o.onAdvanceEnd(mapRoad, listEvent, time);
		}
		
		
			
	}
	
	public void reset() {
		this.mapRoad.reset();
		this.listEvent.clear();
		this.time = 0;
		
		for (TrafficSimObserver o : ob) {
			o.onReset(mapRoad, listEvent, time);
		}
	}
	
	public JSONObject report() {
		
		JSONObject json = new JSONObject();
		json.put("time", this.time);
		json.put("state", this.mapRoad.report());
		
		return json;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
	 if (!ob.contains(o)) {
		ob.add(o);
	 }
	 
	 o.onRegister(mapRoad, listEvent, time);
			
	}
		
	

	@Override
	public void removeObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		if (ob.contains(o)) {
			ob.remove(o);
		 }
		
	}

}
