import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class MapPanel extends JPanel {
    private Image houseIcon;
    private Image warehouseIcon;
    private Image droneIcon;
    private Image chargingStationIcon;
    private int drone1X = 950;
    private int drone1Y = 150;
    
    private int targetX; 
    private int targetY; 
    private Timer movementTimer;
    
    public MapPanel() {
    	
    try {
    houseIcon = ImageIO.read(new File("ui-components/house.png"));
    warehouseIcon = ImageIO.read(new File("ui-components/warehouse.png"));
    droneIcon = ImageIO.read(new File("ui-components/drone.png"));
    chargingStationIcon = ImageIO.read(new File("ui-components/charging-station.png"));
    }
    catch(IOException e) {}

    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the terrain background
        g2d.setColor(new Color(144, 238, 144));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.drawImage(houseIcon, 50, 50, 50, 50, this);
        g2d.drawImage(houseIcon, 50, 150, 50, 50, this);
        g2d.drawImage(houseIcon, 50, 250, 50, 50, this);
        g2d.drawImage(houseIcon, 50, 350, 50, 50, this);
        g2d.drawImage(houseIcon, 50, 450, 50, 50, this);
        

        
        g2d.drawImage(warehouseIcon, 950, 50, 50, 50, this);
        
        g2d.drawImage(droneIcon, 900, 50, 30, 30, this); 
        g2d.drawImage(droneIcon, 1000, 50, 30, 30, this);
        if (droneIcon != null) {
            g2d.drawImage(droneIcon, drone1X, drone1Y, 30, 30, this);
        }

        
        g2d.drawImage(chargingStationIcon, 250, 850, 30, 30, this);
        g2d.drawImage(chargingStationIcon, 350, 850, 30, 30, this);
        
    }
    
    public void sendDrone(int locationX, int locationY) {
    	//To-do: Adjust this to be a house in the future
    	this.targetX = locationX; 
    	this.targetY = locationY;
    	
    	if(movementTimer != null && movementTimer.isRunning()) {
    		movementTimer.stop();
    	}
    	
    	movementTimer = new Timer(10, e -> updateDronePosition());
    	movementTimer.start();
    	
    }
    
    private void updateDronePosition() {
        // Calculate the distance to the target
        double dx = targetX - drone1X;
        double dy = targetY - drone1Y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        // Stop drone if it gets close to house
        if (distance < 1) {
            drone1X = targetX;
            drone1Y = targetY;
            movementTimer.stop();
            repaint();
            return;
        }
        
        //Calculate movement
        double step = 2;
        double moveX = step * (dx / distance);
        double moveY = step * (dy / distance);

        // Update the drone's position
        drone1X += moveX;
        drone1Y += moveY;

        repaint();
    }

    
    
}
