import java.awt.*;
import javax.swing.*;

public class AmogusUI {
//	public static JTextPane door_state, door_lock, door_mode, door_timer;

//   @SuppressWarnings("deprecation")
	public static void main (String[] args) {

		JFrame window = new JFrame("AMOGUS UI - Test");
		window.setSize(1800, 1000);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Hub hub = new Hub();
		
		Amadrone[] drones = { hub.drone.getD0(), hub.drone.getD1(), hub.drone.getD2() };
		House[] houses = { hub.houses.getH0() };
		ChargingStation[] chargers = { hub.charger.getStation0(), hub.charger.getStation1() };
		PackageSM[] packages = { hub.package_ID.getP0(), hub.package_ID.getP1(), hub.package_ID.getP2(), hub.package_ID.getP3() };
		
		// Map Panel
		MapPanel mapPanel = new MapPanel();
		mapPanel.setBorder(BorderFactory.createTitledBorder("Map"));
		//To-do: A state will perform this action
		//mapPanel.sendDrone(50, 450);

        // Drones Panel
		DronesPanel dronesPanel = new DronesPanel();

        // Packages Panel
        OrderingPanel orderingPanel = new OrderingPanel();

        // Creating Adjustable Split Panes
        JSplitPane dronesOrdersSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dronesPanel, orderingPanel);
        dronesOrdersSplit.setResizeWeight(0.9); 

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, dronesOrdersSplit);
        mainSplitPane.setResizeWeight(0.8); 

        window.add(mainSplitPane);
		window.setVisible(true);
		
		
//		JFrame doorFrame = new JFrame ("SmartDoorCtrl.ysc - USER INTERFACE");
//		doorFrame.setSize(2000,2000);
//		doorFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
//			
//		SmartDoorPanel door = new SmartDoorPanel();
//		SmartDoorControl controls = new SmartDoorControl(door);
//			
//		JPanel panel = new JPanel();
//		door_state = new JTextPane();
//		door_lock = new JTextPane();
//		door_mode = new JTextPane();
//		door_timer = new JTextPane();
//			  
//		door_state.setBackground(Color.white);
//		door_lock.setBackground(Color.white);
//		door_mode.setBackground(Color.white);
//		door_timer.setBackground(Color.white);
//			  
//		panel.setBackground (Color.black);
//		panel.setLayout (new BoxLayout(panel, BoxLayout.Y_AXIS));
//		panel.add (Box.createRigidArea (new Dimension (0, 50)));
//		panel.add (door);
//		panel.add(door_state);
//		panel.add(door_lock);
//		panel.add(door_mode);
//		panel.add(door_timer);
//		panel.add (Box.createRigidArea (new Dimension (0, 100)));
//		panel.add (controls);
//		panel.add (Box.createRigidArea (new Dimension (0, 100)));
//			  
//		doorFrame.getContentPane().add(panel);
//		doorFrame.pack();
//		doorFrame.show();
	}
}