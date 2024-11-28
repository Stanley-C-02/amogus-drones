import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DronesPanel extends JPanel {
	DronePanel[] panels;
	private MapPanel mapPanel;
	
    public DronesPanel(Amadrone[] drones, MapPanel mapPanel) {
    	this.mapPanel = mapPanel;
        setBorder(BorderFactory.createTitledBorder("Drones Status"));
        setLayout(new GridLayout(drones.length, 1, 0, 0)); 
        
        panels = new DronePanel[drones.length];

        for (int i = 0; i < drones.length; i++) {
         	DronePanel panel = new DronePanel(drones[i], mapPanel);
         	add(panel);
         	panels[i] = panel;
        }
    }
    
    public void readStatechartData() {
    	for (DronePanel p : panels) {
    		p.readStatechartData();
    	}
    }
}

class DronePanel extends JPanel {
	private static Color colorBgDefault = Color.WHITE;
	private static Color colorBgFocus = Color.LIGHT_GRAY;
	
	Amadrone drone;
	MapPanel mapPanel;
	JLabel name, location, status, battery, motorType, motorDetails, motorPayload, range;
	JButton startDrone, stopDrone;
	JPanel statusPanel, motorPanel;
	
	public DronePanel(Amadrone drone, MapPanel mapPanel) {
		this.drone = drone;
		this.mapPanel = mapPanel;
		
        setLayout(new GridLayout(5, 2, 0, 0));
        
        name = new JLabel();
        location = new JLabel();
        status = new JLabel();
        battery = new JLabel();
        motorType = new JLabel();
        motorDetails = new JLabel();
        motorPayload = new JLabel();
        range = new JLabel();
        
        startDrone = new JButton("On");
        stopDrone = new JButton("Off");

        startDrone.addMouseListener(new DronePanelMouseListener());
        stopDrone.addMouseListener(new DronePanelMouseListener());

        statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        motorPanel = new JPanel(new GridLayout(3, 1));
		
        readStatechartData();

        // Drone name 
        add(name);
        add(location);
        
        //Drone Status
        add(new JLabel("Status: "));
        statusPanel.add(startDrone);
        statusPanel.add(stopDrone);
        statusPanel.add(status);
        add(statusPanel);

        // Battery details
        add(new JLabel("Battery (Current/Max/%): "));
        add(battery);

        // Motor section
        add(new JLabel("Motor:"));
        motorPanel.add(motorType);
        motorPanel.add(motorDetails);
        motorPanel.add(motorPayload);
        add(motorPanel);

        // Flight Range
        add(new JLabel("Flight Range (metres): "));
        add(range);
        
        // Button Listeners
        startDrone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drone.raiseTurnOn();
                mapPanel.setSelectedDroneId(drone.getId());
                readStatechartData();
            }
        });

        stopDrone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drone.raiseTurnOff();
                mapPanel.setSelectedDroneId(drone.getId());
                readStatechartData();
            }
        });
        
		setBgColor(colorBgDefault);
        addMouseListener(new DronePanelMouseListener());
	}
	
	public void readStatechartData() {
		name.setText("Drone #" + drone.getId() + ": " + drone.getName());
		
		location.setText(String.format("At: x%.2f y%.2f, heading to: x%.2f y%.2f", drone.getX(), drone.getY(), drone.getDestX(), drone.getDestY()));
		
		status.setText(drone.getStatus());

		startDrone.setBackground(drone.isStateActive(Amadrone.State.ACTIVE_DRONE_STATUS_ON) ? Color.GREEN : Color.DARK_GRAY);
		stopDrone.setBackground(drone.isStateActive(Amadrone.State.ACTIVE_DRONE_STATUS_OFF) ? Color.PINK : Color.DARK_GRAY);
		startDrone.setForeground(drone.isStateActive(Amadrone.State.ACTIVE_DRONE_STATUS_ON) ? Color.BLACK : Color.WHITE);
		stopDrone.setForeground(drone.isStateActive(Amadrone.State.ACTIVE_DRONE_STATUS_OFF) ? Color.BLACK : Color.WHITE);

        battery.setText(String.format("%.2f%% (%d / %d mWh)", drone.getBattery().getCharge(), (long) drone.getBattery().getAvailable()*1000, (long) drone.getBattery().getMaxCapacity()*1000));
        
        motorType.setText("Type #" + drone.getMotor().getId() + ": " + drone.getMotor().getName());
        motorDetails.setText(String.format("Power: %.2f W, Speed: %.2f m/s", drone.getMotor().getPower(), drone.getMotor().getSpeed()));
        motorPayload.setText("Payload: " + drone.getMotor().getMax_payload() + " grams");
        
        range.setText(String.format("%.2f", drone.getFlight_range()));
	}
	
	void setBgColor(Color color) {
		this.setBackground(color);
		this.statusPanel.setBackground(color);
		this.motorPanel.setBackground(color);
	}
    
    private class DronePanelMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent e) {
			setBgColor(colorBgFocus);
            mapPanel.setSelectedDroneId(drone.getId());
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			setBgColor(colorBgDefault);
            mapPanel.clearSelectedDroneId();
		}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
    }
}
