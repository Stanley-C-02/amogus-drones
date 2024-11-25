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
         	DronePanel panel = new DronePanel(drones[i]);
         	add(panel);
         	panels[i] = panel;
        }
        //add(createDronePanel("Drone 1", "Active", 1000, 800, "GM2804", 100.0f, 15.0f, 500, 10));
        //add(createDronePanel("Drone 2", "Idle", 1200, 600, "A2212", 120.0f, 12.0f, 300, 8));
        //add(createDronePanel("Drone 3", "Charging", 1500, 1500, "GM3506", 150.0f, 10.0f, 700, 12));
    }
    
    public void readStatechartData() {
    	for (DronePanel p : panels) {
    		p.readStatechartData();
    	}
    }

    class DronePanel extends JPanel {
    	Amadrone drone;
    	JLabel name, location, status, battery, motorType, motorDetails, motorPayload, range;
    	JButton startDrone, stopDrone;
    	JPanel statusPanel, motorPanel;
    	
    	public DronePanel(Amadrone drone) {
    		this.drone = drone;
    		
    		setBackground(Color.WHITE);
    		
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

            statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            statusPanel.setBackground(Color.WHITE);
            motorPanel = new JPanel(new GridLayout(3, 1));
            motorPanel.setBackground(Color.WHITE);
    		
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
                    drone.raiseOn();
                    mapPanel.setSelectedDroneId(drone.getId());
                    readStatechartData();
                }
            });

            stopDrone.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drone.raiseOff();
                    mapPanel.setSelectedDroneId(drone.getId());
                    readStatechartData();
                }
            });
            
            
            addMouseListener(new DronePanelMouseListener());
    	}
    	
    	public void readStatechartData() {
    		name.setText("Drone #" + drone.getId() + ": " + drone.getName());
    		
    		location.setText(String.format("Location: x%.2f y%.2f", drone.getX(), drone.getY()));
    		
    		status.setText(drone.getStatus());
    		
    		startDrone.setBackground(drone.isStateActive(Amadrone.State.MAIN_REGION_ON) ? Color.GREEN : Color.LIGHT_GRAY);
    		stopDrone.setBackground(drone.isStateActive(Amadrone.State.MAIN_REGION_OFF) ? Color.PINK : Color.LIGHT_GRAY);

            battery.setText(drone.getBattery().getAvailable() + "Wh / " + drone.getBattery().getMaxCapacity() + "Wh / " + drone.getBattery().getCharge() + "%");
            
            motorType.setText("Type #" + drone.getMotor().getId() + ": " + drone.getMotor().getName());
            motorDetails.setText(String.format("Power: %.2f W, Speed: %.2f m/s", drone.getMotor().getPower(), drone.getMotor().getSpeed()));
            motorPayload.setText("Payload: " + drone.getMotor().getMax_payload() + " grams");
            
//            TODO Integrate this into drone statechart???
            final int flightRange = (int) ((drone.getBattery().getAvailable() / drone.getMotor().getPower()) * drone.getMotor().getSpeed() * 3600);
            range.setText(String.valueOf(flightRange));
    	}
        
        private class DronePanelMouseListener implements MouseListener {

    		@Override
    		public void mouseClicked(MouseEvent arg0) {
    			// TODO Auto-generated method stub
    			
    		}

    		@Override
    		public void mouseEntered(MouseEvent e) {
    			DronePanel p = (DronePanel) e.getSource();
    			p.setBackground(Color.LIGHT_GRAY);
    			p.statusPanel.setBackground(Color.LIGHT_GRAY);
    			p.motorPanel.setBackground(Color.LIGHT_GRAY);
                mapPanel.setSelectedDroneId(p.drone.getId());
    		}
    		
    		@Override
    		public void mouseExited(MouseEvent e) {
    			DronePanel p = (DronePanel) e.getSource();
    			p.setBackground(Color.WHITE);
    			p.statusPanel.setBackground(Color.WHITE);
    			p.motorPanel.setBackground(Color.WHITE);
                mapPanel.clearSelectedDroneId();
    		}

    		@Override
    		public void mousePressed(MouseEvent arg0) {
    			// TODO Auto-generated method stub
    			
    		}

    		@Override
    		public void mouseReleased(MouseEvent arg0) {
    			// TODO Auto-generated method stub
    			
    		}
        }
    }
}
