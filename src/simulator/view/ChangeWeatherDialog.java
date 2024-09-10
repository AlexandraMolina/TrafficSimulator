package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import simulator.model.*;

public class ChangeWeatherDialog extends JDialog{

	private JPanel dialog = new JPanel();
	private JLabel info;
	private JLabel infoRoad;
	private JLabel infoWeather;
	private JLabel infoTicks;
	private JComboBox<Road> jRoad;
	private JComboBox<Weather> jWeather;
	private JSpinner sTicks = new JSpinner();
	
	private JPanel buttons = new JPanel();
	private JPanel options = new JPanel();
	private JButton ok;
	private JButton cancel;
	
	private int state=0;
	private DefaultComboBoxModel<Road> roadM;
	private DefaultComboBoxModel<Weather> weatherM;
	
	public ChangeWeatherDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		setTitle("Change Road Weather");
		dialog.setLayout(new BoxLayout(dialog,BoxLayout.Y_AXIS));
		setContentPane(dialog);
		
		info = new JLabel("<html>Schedule an event to change the weather class of a road after a given number of<br>simulation ticks form now.</html>");
		info.setAlignmentX(CENTER_ALIGNMENT);
		dialog.add(info);
		dialog.add(Box.createRigidArea(new Dimension(0,20)));
		
		buttons.setAlignmentX(CENTER_ALIGNMENT);
		dialog.add(buttons);
		
		infoRoad = new JLabel("Road: ", JLabel.CENTER);
		roadM = new DefaultComboBoxModel<Road>();
		jRoad = new JComboBox<Road>(roadM);
		jRoad.setVisible(true);
		buttons.add(infoRoad);
		buttons.add(jRoad);
		
		
		infoWeather = new JLabel("Weather : ", JLabel.CENTER);
		weatherM = new DefaultComboBoxModel<Weather>();
		jWeather = new JComboBox<Weather>(weatherM);
		jWeather.setVisible(true);
		buttons.add(infoWeather);
		buttons.add(jWeather);
		
		infoTicks = new JLabel("Ticks: ", JLabel.CENTER);
		sTicks = new JSpinner(new SpinnerNumberModel(10,1,99999,1));
		sTicks.setMinimumSize(new Dimension(80,30));
		sTicks.setMaximumSize(new Dimension(200,30));
		sTicks.setPreferredSize(new Dimension(80,30));
		buttons.add(infoTicks);
		buttons.add(sTicks);
		
		options.setAlignmentX(CENTER_ALIGNMENT);
		dialog.add(options);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			state=0;
			ChangeWeatherDialog.this.setVisible(false);
			}
		});
		options.add(cancel);
		

		ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if((roadM.getSelectedItem()!=null) && (weatherM.getSelectedItem()!=null)) {
					state=1;
					ChangeWeatherDialog.this.setVisible(false);
				}
			}
		});
		options.add(ok);
		setPreferredSize(new Dimension(500,200));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	public int open (RoadMap map) {
		for (Road r: map.getRoads()) {
			roadM.addElement(r);
		}
		for (Weather w: Weather.values()) {
			weatherM.addElement(w);
		}
		
		setLocation(getParent().getLocation().x+10,getParent().getLocation().y+10);
		setVisible(true);
		return state;
	}
	
	public Integer getTicks() {
		return (Integer) sTicks.getValue();
	}
	public Road getRoad() {
		return (Road) roadM.getSelectedItem();
	}
	public Weather getWeather() {
		return (Weather) weatherM.getSelectedItem();
	}
	

}
