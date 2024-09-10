package simulator.view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import simulator.model.Event;
import simulator.control.Controller;
import simulator.model.*;

public class MapByRoadComponent extends JPanel implements TrafficSimObserver{

	private int x1;
	private int x2;
	private int y;
	private int i;
	private int xCoche;
	
	private RoadMap _map;
	private Image[] cont;
	private Image _car;
	private Image _weather;
	private Image _cont;
	
	private static final int _JRADIUS = 10;
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;

	
	public MapByRoadComponent(Controller ctrl) {
		initGUI();
		setPreferredSize(new Dimension(300,300));
		ctrl.addObserver(this);
	}
	private void initGUI() {
		cont = new Image[6];
		// TODO Auto-generated method stub
		_car =loadImage("car.png");
		for (int i=0; i<6;i++) {
			cont[i]=loadImage("cont_"+i+".png");
		}		
	}
	
	public void paintComponent (Graphics graphics) {
		super.paintComponent(graphics);
		
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		if (_map == null || _map.getJunctions().size() ==0) {
			g.setColor(Color.red);
			g.drawString("No map by road yet!", getWidth()/2-50, getHeight()/2);
		}else {
			drawMap(g);
		}
	}
	private void drawMap(Graphics2D g) {
		// TODO Auto-generated method stub
		i=0;
		x1=50;
		x2=getWidth()-100;
		y=0;
		for (Road r : _map.getRoads()) {
			
			y=(i+1)*50;
			
			//Draw id Road, the line
			g.setColor(Color.black);
			g.drawString(r.getId(), x1-30, y+_JRADIUS/2);
			g.drawLine(x1, y, x2, y);
			
			//Draw the circle in start
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1-_JRADIUS/2, y-_JRADIUS/2, _JRADIUS, _JRADIUS);
		
			//draw the circles end in the color or other
			
			int idx = r.getDest().getGreenLightIndex();
			if(idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				g.setColor(_GREEN_LIGHT_COLOR);
			}else {
				g.setColor(_RED_LIGHT_COLOR);
			}
			g.fillOval(x2-_JRADIUS/2, y-_JRADIUS/2, _JRADIUS, _JRADIUS);
			
			//draw the names in the junctions
			
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getSrc().toString(), x1, y-_JRADIUS);
			g.drawString(r.getDest().toString(), x2, y-_JRADIUS);
			
			
			//Draw the image of weather
			
			switch(r.getWeather()) {
			
			case SUNNY:
				_weather = loadImage("sun.png");
				break;
			case CLOUDY:
				_weather = loadImage("cloud.png");
				break;
			case RAINY:
				_weather = loadImage("rain.png");
				break;
			case WINDY:
				_weather = loadImage("wind.png");
				break;
			case STORM:
				_weather=loadImage("storm.png");
				break;
			
			}
			g.drawImage(_weather, x2+15, y-_JRADIUS*2, 32, 32, this);
			
			//draw image in the contamination
			
			int c = (int) Math.floor(Math.min((double)r.getTotalCO2()/(1.0+(double)r.getContLimit()),1.0)/0.19);
			switch (c) {
			case 0:
				g.drawImage(cont[0], x2+55, y-_JRADIUS*2, 32, 32, this);

				break;
			case 1:
				g.drawImage(cont[1], x2+55, y-_JRADIUS*2, 32, 32, this);
				break;
			case 2:
				g.drawImage(cont[2], x2+55, y-_JRADIUS*2, 32, 32, this);
				break;
			case 3:
				g.drawImage(cont[3], x2+55, y-_JRADIUS*2, 32, 32, this);
				break;
			case 4:
				g.drawImage(cont[4], x2+55, y-_JRADIUS*2, 32, 32, this);
				break;
			case 5:
				g.drawImage(cont[5], x2+55, y-_JRADIUS*2, 32, 32, this);
				break;
			}
			
		
			
			//Draw the car and calculate the move
			
			if(!r.getVehicles().isEmpty()) {
				
				for (Vehicle v: r.getVehicles()) {
					
				xCoche = x1+(int)((x2-x1)*((double)v.getLocation()/(double)r.getLength()));
				g.setColor(_GREEN_LIGHT_COLOR);
				g.drawString(v.getId(), xCoche, y- _JRADIUS-5);
				g.drawImage(_car, xCoche, y- _JRADIUS-3, 16, 16,this);
				}
			}
			i++;
		}
		
		
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	private Image loadImage(String img) {
		Image i=null;
		try {
			return ImageIO.read(new File("resources/icons/"+img));
		}catch (IOException e) {
			// TODO: handle exception
		}
		return i;
	}
	
	public void update(RoadMap map) {
		_map=map;
		repaint();
	}
	


}
