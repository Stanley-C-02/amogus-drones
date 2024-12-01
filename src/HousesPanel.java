import java.awt.Color;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HousesPanel extends JPanel {
	private HousePanel[] panels;
	private MapPanel mapPanel;
	
	public HousesPanel(House[] houses, MapPanel mapPanel) {
		this.mapPanel = mapPanel;
        setBorder(BorderFactory.createTitledBorder("House Order Status"));
        setLayout(new GridLayout(1, houses.length, 0, 0));
        
        panels = new HousePanel[houses.length];

        for (int i = 0; i < houses.length; i++) {
        	HousePanel panel = new HousePanel(mapPanel, houses[i]);
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
}

class HousePanel extends JPanel {
	private static Color colorBgDefault = Color.WHITE;
	private static Color colorBgFocus = Color.LIGHT_GRAY;
	private static Color colorBgMoving = new Color(255, 238, 137);
	
	private MapPanel mapPanel;
	House house;
	JLabel name, status, location;
	
	public HousePanel(MapPanel mapPanel, House house) {
		this.house = house;
		this.mapPanel = mapPanel;
		
		setBackground(Color.WHITE);
		
        setLayout(new GridLayout(3, 1, 0, 0));
        
        name = new JLabel();
        status = new JLabel();
        location = new JLabel();
        
        readStatechartData();
        
        add(name);
        add(status);
        add(location);
        
        addMouseListener(new HousePanelMouseListener());
	}
	
	public void readStatechartData() {
		name.setText("House Name: " + house.getName());
		
		String orderStatus;
		if (house.getOrderedPackage() == null) {
			orderStatus = "None";
		} else {
			orderStatus = "#" + house.getOrderedPackage().getId() + ": " + house.getOrderedPackage().getName() + " (" + house.getOrderedPackage().getWeight() + "g)";
		}
		status.setText("Order Status: " + orderStatus);
		
		location.setText("Location: x" + house.getX() + " y" + house.getY());
		
		setBgDefault();
	}
	
	void setBgFocus() {
		setBackground(colorBgFocus);
	}
	
	void setBgDefault() {
		setBackground(house.getOrderedPackage() == null ? colorBgDefault : colorBgMoving);
	}

    private class HousePanelMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent e) {
			setBgFocus();
            mapPanel.setSelectedHouseId(house.getId());
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			setBgDefault();
            mapPanel.clearSelectedHouseId();
		}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
    }
}