
public class MoveDroneOpCallback implements Amadrone.OperationCallback {
	private Amadrone drone;
	private MapPanel mapPanel;
	
	public MoveDroneOpCallback(Amadrone drone, MapPanel mapPanel) {
		this.drone = drone;
		this.mapPanel = mapPanel;
		
	}

	@Override
	public void offsetDrone() {
		double xDist = drone.getDestX() - drone.getX();
        double yDist = drone.getDestY() - drone.getY();
        double distance = Math.hypot(xDist, yDist);
        
        if (distance == 0) {
        	return;
        }
        
        double moveX = drone.getMotor().getSpeed() * (xDist / distance);
        double moveY = drone.getMotor().getSpeed() * (yDist / distance);

        // Update the drone's position
        if (Math.hypot(moveX, moveY) > distance) {
        	// Move onto destination when near
            drone.setX(drone.getDestX());
            drone.setY(drone.getDestY());        	
        } else {
        	// Move towards destination
            drone.setX(drone.getX() + moveX);
            drone.setY(drone.getY() + moveY);
        }
        mapPanel.repaint();
	}
}

// speed / distance 
// moveX / dx