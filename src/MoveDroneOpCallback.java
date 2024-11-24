
public class MoveDroneOpCallback implements Amadrone.OperationCallback {
	private Amadrone drone;
	
	public MoveDroneOpCallback(Amadrone drone) {
		this.drone = drone;
	}

	@Override
	public void offsetDrone(double xDest, double yDest, double speed) {
		System.out.println("Operation myOp has been called by the state machine");
		drone.setX(25);
		drone.setY(25);
	}
	
//    public long offsetDrone(long xValue, long yValue, long speed) {
//        // Logic to calculate and update drone's offset position in the GUI
//        for (Amadrone drone : drones) {
//            if (drone.getId() == selectedDroneId) {
//                double currentX = drone.getX();
//                double currentY = drone.getY();
//
//                
//                double newX = currentX + xValue * speed;
//                double newY = currentY + yValue * speed;
//
//                
//                drone.setX(newX);
//                drone.setY(newY);
//
//                repaint();
//                return (long)0;
//            }
//        }
//		return -1 ;
//    }
}
