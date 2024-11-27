import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MapPanel extends JPanel {
	private Image houseIcon;
	private Image orderedIcon;
	
	private Image warehouseIcon;
	
	private Image droneIcon;
	private Image blockedIcon;
	private Image packageIcon;
	private Image yieldIcon;
	private Image continueIcon;
	private Image chargingIcon;
	
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

	private final static int HOUSE_ICON_SIZE = 60;
	private final static int ORDERED_ICON_SIZE = 40;
	
	private final static int WAREHOUSE_ICON_SIZE = 100;
	
	private final static int DRONE_ICON_SIZE = 40;
	private final static int CHARGER_ICON_SIZE = 30;
	private final static int BLOCKED_ICON_SIZE = 30;	
	private final static int PACKAGE_ICON_SIZE = 30;
	private final static int YIELD_ICON_SIZE = 40;
	private final static int CONTINUE_ICON_SIZE = 40;
	
	private final static int CHARGING_ICON_SIZE = 30;
	
	private final static double SCALE = 9;

	public MapPanel(Hub hub, Amadrone[] drones, House[] houses, ChargingStation[] chargers) {
		this.hub_x = (int) hub.getHub_pos_x();
		this.hub_y = (int) hub.getHub_pos_y();
		this.drones = drones;
		this.houses = houses;
		this.chargers = chargers;

		try {
			houseIcon = ImageIO.read(new File("assets/house.png"));
			orderedIcon = ImageIO.read(new File("assets/ordered-icon.png"));
			
			warehouseIcon = ImageIO.read(new File("assets/warehouse.png"));
			
			droneIcon = ImageIO.read(new File("assets/drone.png"));
			packageIcon = ImageIO.read(new File("assets/package-icon.png"));
			blockedIcon = ImageIO.read(new File("assets/blocked-icon.png"));
			yieldIcon = ImageIO.read(new File("assets/yield-icon.png"));
			continueIcon = ImageIO.read(new File("assets/continue-icon.png"));
			chargingIcon = ImageIO.read(new File("assets/charging-icon.png"));
			
			chargingStationIcon = ImageIO.read(new File("assets/charging-station.png"));
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

		for (ChargingStation charger : chargers) {
			g2d.drawImage(chargingStationIcon, (int) (charger.getX() * SCALE - CHARGER_ICON_SIZE / 2),
					(int) (charger.getY() * SCALE - CHARGER_ICON_SIZE / 2), CHARGER_ICON_SIZE, CHARGER_ICON_SIZE, this);
		}

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

			if (house.isStateActive(House.State.MAIN_REGION_WAITING_FOR_PACKAGE)) {
				g2d.drawImage(orderedIcon, (int) (house.getX() * SCALE - ORDERED_ICON_SIZE / 2),
						(int) (house.getY() * SCALE + HOUSE_ICON_SIZE / 2), ORDERED_ICON_SIZE, ORDERED_ICON_SIZE, this);
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
			
			if (drone.isStateActive(Amadrone.State.MAIN_REGION_ON_R_LOGISTICS__REGION0_IN_TRANSIT_Y_PACKAGE_ATTACHED)) {
				g2d.drawImage(packageIcon, (int) (drone.getX() * SCALE - PACKAGE_ICON_SIZE / 2),
						(int) (drone.getY() * SCALE + DRONE_ICON_SIZE / 2), PACKAGE_ICON_SIZE, PACKAGE_ICON_SIZE, this);
			}
			
			if (drone.isStateActive(Amadrone.State.MAIN_REGION_OFF)) {
				g2d.drawImage(blockedIcon, (int) (drone.getX() * SCALE - BLOCKED_ICON_SIZE / 2),
						(int) (drone.getY() * SCALE - DRONE_ICON_SIZE / 2 - BLOCKED_ICON_SIZE), BLOCKED_ICON_SIZE, BLOCKED_ICON_SIZE, this);
			}
			
			if (drone.isStateActive(Amadrone.State.MAIN_REGION_ON_R_LOGISTICS__REGION0_IN_TRANSIT_COLLISION_YIELD)) {
				g2d.drawImage(yieldIcon, (int) (drone.getX() * SCALE - YIELD_ICON_SIZE / 2),
						(int) (drone.getY() * SCALE - DRONE_ICON_SIZE / 2 - YIELD_ICON_SIZE), YIELD_ICON_SIZE, YIELD_ICON_SIZE, this);
			}
			
			if (drone.isStateActive(Amadrone.State.MAIN_REGION_ON_R_LOGISTICS__REGION0_IN_TRANSIT_COLLISION_PROCEED)) {
				g2d.drawImage(continueIcon, (int) (drone.getX() * SCALE - CONTINUE_ICON_SIZE / 2),
						(int) (drone.getY() * SCALE - DRONE_ICON_SIZE / 2 - CONTINUE_ICON_SIZE), CONTINUE_ICON_SIZE, CONTINUE_ICON_SIZE, this);
			}
			
			if (drone.isStateActive(Amadrone.State.MAIN_REGION_ON_R_LOGISTICS_R_LOW_BATTERY)) {
				g2d.drawImage(chargingIcon, (int) (drone.getX() * SCALE - CHARGING_ICON_SIZE / 2),
						(int) (drone.getY() * SCALE - DRONE_ICON_SIZE / 2 - CHARGING_ICON_SIZE), CHARGING_ICON_SIZE, CHARGING_ICON_SIZE, this);
			}
		}

	}

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
