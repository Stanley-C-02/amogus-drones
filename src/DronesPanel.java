import java.awt.*;
import javax.swing.*;

public class DronesPanel extends JPanel {
	DronePanel[] panels;
	
    public DronesPanel(Amadrone[] drones) {
        setBorder(BorderFactory.createTitledBorder("Drones Status"));
        setLayout(new GridLayout(3, 1, 0, 0)); 
        
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
    	
    	public DronePanel(Amadrone drone) {
    		this.drone = drone;
    		
            setLayout(new GridLayout(5, 2, 0, 0));
            
            name = new JLabel();
            location = new JLabel();
            status = new JLabel();
            battery = new JLabel();
            motorType = new JLabel();
            motorDetails = new JLabel();
            motorPayload = new JLabel();
            range = new JLabel();
    		
            readStatechartData();

            // Drone name and status
            add(name);
            add(location);
            add(new JLabel("Status: "));
            add(status);

            // Battery details
            add(new JLabel("Battery (Current/Max/%): "));
            add(battery);

            // Motor section
            add(new JLabel("Motor:"));
            JPanel motorPanel = new JPanel(new GridLayout(3, 1));
            motorPanel.add(motorType);
            motorPanel.add(motorDetails);
            motorPanel.add(motorPayload);
            add(motorPanel);

            // Flight Range
            add(new JLabel("Flight Range (metres): "));
            add(range);
    	}
    	
    	public void readStatechartData() {
    		name.setText("Drone #" + drone.getId() + ": " + drone.getName());
    		
    		location.setText(String.format("Location: x%.2f y%.2f", drone.getX(), drone.getY()));
    		
    		status.setText(drone.getStatus());

            battery.setText(drone.getBattery().getAvailable() + "Wh / " + drone.getBattery().getMaxCapacity() + "Wh / " + drone.getBattery().getCharge() + "%");
            
            motorType.setText("Type #" + drone.getMotor().getId() + ": " + drone.getMotor().getName());
            motorDetails.setText(String.format("Power: %.2f W, Speed: %.2f m/s", drone.getMotor().getPower(), drone.getMotor().getSpeed()));
            motorPayload.setText("Payload: " + drone.getMotor().getMax_payload() + " grams");
            
//            TODO Integrate this into drone statechart???
            final int flightRange = (int) ((drone.getBattery().getAvailable() / drone.getMotor().getPower()) * drone.getMotor().getSpeed() * 3600);
            range.setText(String.valueOf(flightRange));
    	}
    }


}
