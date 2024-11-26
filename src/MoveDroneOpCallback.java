
public class MoveDroneOpCallback implements Amadrone.OperationCallback {
	private Amadrone drone;
	private MapPanel mapPanel;
	
	public MoveDroneOpCallback(Amadrone drone, MapPanel mapPanel) {
		this.drone = drone;
		this.mapPanel = mapPanel;
		
	}

	@Override
	public void offsetDrone(double xDest, double yDest, double speed) {
		double dx = xDest - drone.getX();
        double dy = yDest - drone.getY();
        double distance = Math.hypot(dx, dy);
        
        // Stop drone if close to destination
        if (distance < 1) {
            drone.setX(xDest);
            drone.setY(yDest);
            return;
        }
        //To-do: Speed too fast
        speed = speed/5;
        double moveX = speed * (dx / distance);
        double moveY = speed * (dy / distance);
        System.out.println(xDest);
        System.out.println(yDest);
        
        
        // Update the drone's position
        drone.setX(drone.getX() + moveX);
        drone.setY(drone.getY() + moveY);
        mapPanel.repaint();
	}
}
