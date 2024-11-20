import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HousePanel extends JPanel {
	House house;
	JLabel name, status, location;
	
	public HousePanel(House house) {
		this.house = house;
		
        setLayout(new GridLayout(3, 1, 0, 0));
        
        this.name = new JLabel();
        this.status = new JLabel();
        this.location = new JLabel();
        
        this.readStatechartData();
        
        add(this.name);
        add(this.status);
        add(this.location);
	}
	
	public void readStatechartData() {
		this.name.setText("House Name: " + house.getName());
		
		String orderStatus;
		if (house.getPackage_id() == -1) {			
			orderStatus = "None (-1)";
		} else {
			orderStatus = "Ordered #" + house.getPackage_id();
		}
		this.status.setText("Order Status: " + orderStatus);
		
		this.location.setText("Location: x" + house.getX() + " y" + house.getY());
	}
}
