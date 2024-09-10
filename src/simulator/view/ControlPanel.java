package simulator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.*;
import simulator.model.Event;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	//Atributos
	private Controller ctrl;
	private RoadMap rMap;
	private int time;
	private boolean _stopped;
	private int state = 0;
	
	//Componentes visuales
	private JToolBar toolBar;
	private JButton bFile;
	private JButton bCO2;
	private JButton bWeather;
	private JButton bRun;
	private JButton bStop;
	private JButton bExit;
	private JLabel lTicks;
	private JSpinner sTicks;
	private JFileChooser selectFile;
	private ChangeCO2ClassDialog changecO2;
	private ChangeWeatherDialog changeW;

	
	

	//Falta crear clases para el JDialog
	
	//Constructura
	ControlPanel(Controller _ctrl){
		
		this.ctrl = _ctrl;
		_stopped=true;
		initGUI();
		ctrl.addObserver(this);
	}
	

	private void initGUI() {
		// TODO Auto-generated method stub
		toolBar = new JToolBar();
		bFile = new JButton();
		bCO2 = new JButton();
		bWeather = new JButton();
		bRun = new JButton();
		bStop = new JButton();
		bExit = new JButton();
		
		toolBar.setFloatable(false);
		this.setLayout(new BorderLayout());
		this.add(toolBar,BorderLayout.PAGE_START);
		selectFile = new JFileChooser();
		
		toolBar.addSeparator();
		cargarFichero();
		toolBar.addSeparator();
		buttonCo2();
		buttonWeather();
		toolBar.addSeparator();
		buttonRun();
		buttonStop();
		spinnerTicks();
		toolBar.add(Box.createHorizontalGlue());
		toolBar.addSeparator();
		buttonExit();
		
	
	}

/*Cargar fichero*/
	private void cargarFichero() {
		bFile.setToolTipText("Load an event file");
		bFile.setIcon(new ImageIcon(uploadImage("resources/icons/open.png")));
		bFile.setPreferredSize(new Dimension(36,36));
		bFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//
				uploadFiles();
				
			}
		});
		toolBar.add(bFile);
	}
	private void uploadFiles() {
		int deVal = this.selectFile.showOpenDialog(null);
		if(deVal == JFileChooser.APPROVE_OPTION) {
			this.selectFile.setCurrentDirectory(new File("."));
			File file = this.selectFile.getSelectedFile();
			try {
				InputStream in = new FileInputStream(file);
				ctrl.reset();
				ctrl.loadEvents(in);
			}catch (FileNotFoundException e) {
				// TODO: handle exception
				dialogError("Error reading the file: "+e.getMessage());;
			}
			
				
			}else {
				dialogError("Load Cancelled by user");
		}
	}
	
	private void buttonCo2() {
		bCO2.setIcon(new ImageIcon(uploadImage("resources/icons/co2class.png")));
		bCO2.setToolTipText("Modify Contamination");
		bCO2.setPreferredSize(new Dimension(36,36));
		bCO2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changeCO2Class();
			}
		});
		toolBar.add(bCO2);
	}
	
	private void changeCO2Class() {
		state=0;
		changecO2 = new  ChangeCO2ClassDialog((Frame)SwingUtilities.getWindowAncestor(this));
		
		state = changecO2.open(rMap);
		if (state!=0) {
			List<Pair<String,Integer>> cs = new ArrayList<>();
			cs.add(new Pair<String,Integer>(changecO2.getVehicle().getId(),changecO2.getCO2Class()));
			try {
				ctrl.addEvent(new NewSetContClassEvent(time+changecO2.getTicks(), cs));
				
			}catch (Exception e) {
				dialogError("No se ha podido cambiar el CO2");
			}
		}
		
	}
	
	private void buttonWeather() {
		bWeather.setToolTipText("Modify Weather");
		bWeather.setIcon(new ImageIcon(uploadImage("resources/icons/weather.png")));
		bWeather.setPreferredSize(new Dimension(36,36));
		bWeather.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changeWeather();
			}
		});
		toolBar.add(bWeather);
	}
	
	private void changeWeather() {
		state=0;
		changeW = new ChangeWeatherDialog((Frame)SwingUtilities.getWindowAncestor(this));
		state= changeW.open(rMap);
		
		if (state!=0) {
			List<Pair<String, Weather>>cs = new ArrayList<>();
			cs.add(new Pair<String,Weather>(changeW.getRoad().getId(), changeW.getWeather()));
			try {
				ctrl.addEvent(new SetWeatherEvent(time+changeW.getTicks(), cs));
				
			}catch (Exception e) {
				dialogError("No se ha podido cambiar el Weather");
			}
		}
	}
	
	private void buttonRun() {
		bRun.setToolTipText("All buttons are disabled except stop");
		bRun.setIcon(new ImageIcon(uploadImage("resources/icons/run.png")));
		bRun.setPreferredSize(new Dimension(36,36));
		bRun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				enableToolBar(false);
				_stopped=false;
				run_sim((Integer)sTicks.getValue());
			
			}
		});
		toolBar.add(bRun);
	}
	
	private void run_sim(int n) {
		
		if (n > 0 && !_stopped) {
			try {
				ctrl.run(1);
				
			}catch (Exception e) {
				// TODO: handle exception
				dialogError("¡Error!");
				enableToolBar(true);
				_stopped=true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					run_sim(n-1);
				}
			});
		}else {
			enableToolBar(true);
			_stopped=true;
		}
	}
	
	private void buttonStop() {
		bStop.setToolTipText("Stop Simulation");
		bStop.setIcon(new ImageIcon(uploadImage("resources/icons/stop.png")));
		bStop.setPreferredSize(new Dimension(36,36));
		bStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stop();
			}
		});
		toolBar.add(bStop);
	}
	
	private void spinnerTicks() {
		lTicks = new JLabel("Ticks: ", JLabel.CENTER);
		sTicks = new JSpinner(new SpinnerNumberModel(10,1,99999,1));
		sTicks.setMinimumSize(new Dimension(80,40));
		sTicks.setMaximumSize(new Dimension(80,40));
		sTicks.setPreferredSize(new Dimension(80,40));
		toolBar.add(lTicks);
		toolBar.add(sTicks);
	}
	
	private void buttonExit() {
		bExit.setAlignmentX(RIGHT_ALIGNMENT);
		bExit.setToolTipText("Exit");
		bExit.setIcon(new ImageIcon(uploadImage("resources/icons/exit.png")));
		bExit.setPreferredSize(new Dimension(36,36));
		bExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				exit();
			}
		});
		toolBar.add(bExit);
	}
	
	public void exit() {
		int diag = JOptionPane.showConfirmDialog((Frame) SwingUtilities.getWindowAncestor(this), "¿Está seguro de Salir?", "Exit", JOptionPane.YES_NO_OPTION);
		if (diag==0) {
			System.exit(0);
		}
	}

	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.rMap = map;
		this.time=time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		this.rMap = map;
		this.time=time;
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.rMap = map;
		this.time=time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.rMap = map;
		this.time=time;
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	public Image uploadImage(String path) {
		return Toolkit.getDefaultToolkit().createImage(path);
	}
	public void dialogError(String string) {
		JOptionPane.showMessageDialog(null, string);
	}
	public void enableToolBar(boolean tool) {
		bFile.setEnabled(tool);
		bCO2.setEnabled(tool);
		bWeather.setEnabled(tool);
		bRun.setEnabled(tool);
		bExit.setEnabled(tool);
		sTicks.setEnabled(tool);
	}
	
	private void stop() {
		_stopped=true;
	}

}
