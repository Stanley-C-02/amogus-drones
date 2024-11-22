import java.awt.Color;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HousesPanel extends JPanel {
	private HousePanel[] panels;
	
	public HousesPanel(House[] houses) {
        setBorder(BorderFactory.createTitledBorder("House Order Status"));
        setLayout(new GridLayout(1, houses.length, 0, 0));
        
        panels = new HousePanel[houses.length];

        for (int i = 0; i < houses.length; i++) {
        	HousePanel panel = new HousePanel(houses[i]);
        	panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        	add(panel);
        	panels[i] = panel;
        }
	}
	
	public void readStatechartData() {
		for (HousePanel p : panels) {
			p.readStatechartData();
		}
	}
	
	class HousePanel extends JPanel {
		House house;
		JLabel name, status, location;
		
		public HousePanel(House house) {
			this.house = house;
			
	        setLayout(new GridLayout(3, 1, 0, 0));
	        
	        name = new JLabel();
	        status = new JLabel();
	        location = new JLabel();
	        
	        readStatechartData();
	        
	        add(name);
	        add(status);
	        add(location);
		}
		
		public void readStatechartData() {
			name.setText("House Name: " + house.getName());
			
			String orderStatus;
			if (house.getPackage_id() == -1) {			
				orderStatus = "None (-1)";
			} else {
				orderStatus = "Ordered #" + house.getPackage_id();
			}
			status.setText("Order Status: " + orderStatus);
			
			location.setText("Location: x" + house.getX() + " y" + house.getY());
		}
	}
}
