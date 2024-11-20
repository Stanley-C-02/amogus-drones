import java.awt.*;
import javax.swing.*;

public class DronesPanel extends JPanel {

    public DronesPanel() {
        setBorder(BorderFactory.createTitledBorder("Drones Status"));
        setLayout(new GridLayout(3, 1, 0, 0)); 

        add(createDronePanel("Drone 1", "Active", 1000, 800, "GM2804", 100.0f, 15.0f, 500, 10));
        add(createDronePanel("Drone 2", "Idle", 1200, 600, "A2212", 120.0f, 12.0f, 300, 8));
        add(createDronePanel("Drone 3", "Charging", 1500, 1500, "GM3506", 150.0f, 10.0f, 700, 12));
    }

    private JPanel createDronePanel(String droneName, String status, int maxCapacity, int currentCapacity,
                                    String motor, float power, float speed, int payload, int windResistance) {
        JPanel dronePanel = new JPanel();
        dronePanel.setLayout(new GridLayout(6, 2, 0, 0));

        // Drone name and status
        dronePanel.add(new JLabel(droneName));
        dronePanel.add(new JLabel(""));
        dronePanel.add(new JLabel("Status:"));
        dronePanel.add(new JLabel(status));

        // Battery details
        dronePanel.add(new JLabel("Battery (Max/Current/Charge):"));
        int chargePercentage = (int) ((currentCapacity / (float) maxCapacity) * 100);
        dronePanel.add(new JLabel(maxCapacity + "Wh / " + currentCapacity + "Wh / " + chargePercentage + "%"));

        // Motor section
        dronePanel.add(new JLabel("Motor:"));
        JPanel motorPanel = new JPanel(new GridLayout(3, 1));
        motorPanel.add(new JLabel("Type: " + motor));
        motorPanel.add(new JLabel(String.format("Power: %.2f W, Speed: %.2f m/s", power, speed)));
        motorPanel.add(new JLabel("Payload: " + payload + " grams"));
        dronePanel.add(motorPanel);

        // Wind Resistance
        dronePanel.add(new JLabel("Wind Resistance (0-12):"));
        dronePanel.add(new JLabel(String.valueOf(windResistance)));

        // Flight Range
        int flightRange = (int) ((currentCapacity / power) * speed * 3600);
        dronePanel.add(new JLabel("Flight Range (metres):"));
        dronePanel.add(new JLabel(String.valueOf(flightRange)));

        return dronePanel;
    }
}
