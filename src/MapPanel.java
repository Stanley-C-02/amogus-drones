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
        g2d.drawImage(droneIcon, 1050, 50, 30, 30, this);
        g2d.drawImage(droneIcon, 950, 150, 30, 30, this);

        
        g2d.drawImage(chargingStationIcon, 250, 850, 30, 30, this);
        g2d.drawImage(chargingStationIcon, 350, 850, 30, 30, this);
        
    }
}
