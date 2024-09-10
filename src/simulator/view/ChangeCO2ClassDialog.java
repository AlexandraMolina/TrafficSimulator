package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import simulator.model.*;

public class ChangeCO2ClassDialog extends JDialog {
	
	private JPanel dialog = new JPanel();
	private JLabel info;
	private JLabel infoVehicle;
	private JLabel infoCO2;
	private JLabel infoTicks;
	private JComboBox<Vehicle> jvehicles;
	private JComboBox<Integer> jCO2;
	private JSpinner sTicks = new JSpinner();
	
	private JPanel buttons = new JPanel();
	private JPanel options = new JPanel();
	private JButton ok;
	private JButton cancel;
	
	private int state=0;
	private DefaultComboBoxModel<Vehicle> vehicleM;
	private DefaultComboBoxModel<Integer> CO2M;
	
	public ChangeCO2ClassDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		setTitle("Change CO2 Class");
		dialog.setLayout(new BoxLayout(dialog,BoxLayout.Y_AXIS));
		setContentPane(dialog);
		
		info = new JLabel("<html>Schedule an event to change the CO2 class of a vehicle after a given number of<br>simulation ticks form now.</html>");
		info.setAlignmentX(CENTER_ALIGNMENT);
		dialog.add(info);
		dialog.add(Box.createRigidArea(new Dimension(0,20)));
		
		buttons.setAlignmentX(CENTER_ALIGNMENT);
		dialog.add(buttons);
		
		infoVehicle = new JLabel("Vehicle: ", JLabel.CENTER);
		vehicleM = new DefaultComboBoxModel<Vehicle>();
		jvehicles = new JComboBox<Vehicle>(vehicleM);
		jvehicles.setVisible(true);
		buttons.add(infoVehicle);
		buttons.add(jvehicles);
		
		
		infoCO2 = new JLabel("CO2 Class: ", JLabel.CENTER);
		CO2M = new DefaultComboBoxModel<Integer>();
		jCO2 = new JComboBox<Integer>(CO2M);
		jCO2.setVisible(true);
		buttons.add(infoCO2);
		buttons.add(jCO2);
		
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
			ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		options.add(cancel);
		

		ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if((vehicleM.getSelectedItem()!=null) && (CO2M.getSelectedItem()!=null)) {
					state=1;
					ChangeCO2ClassDialog.this.setVisible(false);
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
		
		for (Vehicle v: map.getVehicles()) {
			vehicleM.addElement(v);
		}
		for (int i=0; i<11; i++) {
			CO2M.addElement(i);
		}
		setLocation(getParent().getLocation().x+10,getParent().getLocation().y+10);
		setVisible(true);
		return state;
	}
	
	public Integer getTicks() {
		return (Integer) sTicks.getValue();
	}
	public Integer getCO2Class() {
		return (Integer) CO2M.getSelectedItem();
	}
	
	public Vehicle getVehicle() {
		return (Vehicle) vehicleM.getSelectedItem();
	}
}

