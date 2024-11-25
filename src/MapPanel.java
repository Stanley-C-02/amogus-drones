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
	private long selectedDroneId = -1;
	private long selectedHouseId = -1;

	private int targetX;
	private int targetY;
	private Timer movementTimer;

	private int hub_x;
	private int hub_y;
	private Amadrone[] drones;
	private House[] houses;
	private ChargingStation[] chargers;

	private final static int HOUSE_ICON_SIZE = 50;
	private final static int WAREHOUSE_ICON_SIZE = 50;
	private final static int DRONE_ICON_SIZE = 30;
	private final static int CHARGER_ICON_SIZE = 30;
	private final static double SCALE = 9;

	public MapPanel(Hub hub, Amadrone[] drones, House[] houses, ChargingStation[] chargers) {
		this.hub_x = (int) hub.getHub_pos_x();
		this.hub_y = (int) hub.getHub_pos_y();
		this.drones = drones;
		this.houses = houses;
		this.chargers = chargers;

		try {
			houseIcon = ImageIO.read(new File("ui-components/house.png"));
			warehouseIcon = ImageIO.read(new File("ui-components/warehouse.png"));
			droneIcon = ImageIO.read(new File("ui-components/drone.png"));
			chargingStationIcon = ImageIO.read(new File("ui-components/charging-station.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Draw the terrain background
		g2d.setColor(new Color(144, 238, 144));
		g2d.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 0; i < houses.length; i++) {
			House house = houses[i];
			g2d.drawImage(houseIcon, (int) (house.getX() * SCALE - HOUSE_ICON_SIZE / 2),
					(int) (house.getY() * SCALE - HOUSE_ICON_SIZE / 2), HOUSE_ICON_SIZE, HOUSE_ICON_SIZE, this);
			if (house.getId() == selectedHouseId) {
				g2d.setColor(Color.RED);
				g2d.drawRect((int) (house.getX() * SCALE - HOUSE_ICON_SIZE / 2) - 2,
						(int) (house.getY() * SCALE - HOUSE_ICON_SIZE / 2) - 2, HOUSE_ICON_SIZE + 4,
						HOUSE_ICON_SIZE + 4);
			}
		}

		g2d.drawImage(warehouseIcon, (int) (hub_x * SCALE - WAREHOUSE_ICON_SIZE / 2),
				(int) (hub_y * SCALE - WAREHOUSE_ICON_SIZE / 2), WAREHOUSE_ICON_SIZE, WAREHOUSE_ICON_SIZE, this);

		for (Amadrone drone : drones) {
			g2d.drawImage(droneIcon, (int) (drone.getX() * SCALE - DRONE_ICON_SIZE / 2),
					(int) (drone.getY() * SCALE - DRONE_ICON_SIZE / 2), DRONE_ICON_SIZE, DRONE_ICON_SIZE, this);

			if (drone.getId() == selectedDroneId) {
				g2d.setColor(Color.RED);
				g2d.drawRect((int) (drone.getX() * SCALE - DRONE_ICON_SIZE / 2) - 2,
						(int) (drone.getY() * SCALE - DRONE_ICON_SIZE / 2) - 2, DRONE_ICON_SIZE + 4,
						DRONE_ICON_SIZE + 4);
			}

		}

		for (ChargingStation charger : chargers) {
			g2d.drawImage(chargingStationIcon, (int) (charger.getX() * SCALE - CHARGER_ICON_SIZE / 2),
					(int) (charger.getY() * SCALE - CHARGER_ICON_SIZE / 2), CHARGER_ICON_SIZE, CHARGER_ICON_SIZE, this);
		}

	}

//    public void sendDrone(int locationX, int locationY) {
//    	//To-do: Adjust this to be a house in the future
//    	this.targetX = (int) (locationX * SCALE - DRONE_ICON_SIZE / 2); 
//    	this.targetY = (int) (locationY * SCALE - DRONE_ICON_SIZE / 2);
//    	
//    	if(movementTimer != null && movementTimer.isRunning()) {
//    		movementTimer.stop();
//    	}
//    	
//    	//Update the drone position only one step
//    	//updateDronePosition(drones[0])
//    	movementTimer = new Timer(100, e -> updateDronePosition(drones[0]));
//    	movementTimer.start();
//    	
//    }
//    
//    private void updateDronePosition(Amadrone drone) {
//        // Calculate the distance to the target
//        double dx = targetX - drone.getX();
//        double dy = targetY - drone.getY();
//        double distance = Math.hypot(dx, dy);
//        
//        // Stop drone if it gets close to house
//        if (distance < 1) {
//            drone.setX(targetX);
//            drone.setY(targetY);
//            movementTimer.stop();
//            repaint();
//            return;
//        }
//        
//        //Calculate movement
//        double step = 2;
//        double moveX = step * (dx / distance);
//        double moveY = step * (dy / distance);
//
//        // Update the drone's position
//        drone.setX(drone.getX() + moveX);
//        drone.setY(drone.getY() + moveY);
//
//        repaint();
//    }
//    
	public void setSelectedDroneId(long droneId) {
		this.selectedDroneId = droneId;
		repaint();
	}

	public void clearSelectedDroneId() {
		setSelectedDroneId(-1);
	}

	public void setSelectedHouseId(long houseId) {
		this.selectedHouseId = houseId;
		repaint();
	}

	public void clearSelectedHouseId() {
		setSelectedHouseId(-1);
	}
}
