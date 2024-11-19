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
		JPanel mapPanel = new MapPanel();
		mapPanel.setBorder(BorderFactory.createTitledBorder("Map"));

        // Drones Panel
        JPanel dronesPanel = new JPanel();
        dronesPanel.setBorder(BorderFactory.createTitledBorder("Drones"));
        dronesPanel.setLayout(new BoxLayout(dronesPanel, BoxLayout.Y_AXIS));
        
        JLabel droneStatusLabel = new JLabel("Status:");
        dronesPanel.add(droneStatusLabel);
        JTextArea droneStatusArea = new JTextArea("Drone status information here...");
        droneStatusArea.setEditable(false);
        dronesPanel.add(droneStatusArea);

        JLabel alertLabel = new JLabel("Alert:");
        dronesPanel.add(alertLabel);
        JTextArea alertArea = new JTextArea("Alert messages here...");
        alertArea.setEditable(false);
        dronesPanel.add(alertArea);

        dronesPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        JTextArea info1 = new JTextArea("Additional info 1");
        info1.setEditable(false);
        JTextArea info2 = new JTextArea("Additional info 2");
        info2.setEditable(false);
        infoPanel.add(info1);
        infoPanel.add(info2);
        dronesPanel.add(infoPanel);

        // Packages Panel
        JPanel packagesPanel = new JPanel();
        packagesPanel.setBorder(BorderFactory.createTitledBorder("Packages"));
        packagesPanel.setLayout(new BorderLayout());

        JPanel addPackagePanel = new JPanel();
        addPackagePanel.setLayout(new GridLayout(2, 2));
        addPackagePanel.add(new JLabel("Weight:"));
        JTextField weightField = new JTextField();
        addPackagePanel.add(weightField);
        addPackagePanel.add(new JLabel("Destination:"));
        JTextField destinationField = new JTextField();
        addPackagePanel.add(destinationField);
        
        packagesPanel.add(addPackagePanel, BorderLayout.NORTH);

        JPanel packageListPanel = new JPanel();
        packageListPanel.setLayout(new BorderLayout());
        JLabel packageListLabel = new JLabel("Packages:");
        JTextArea packagesArea = new JTextArea("List of packages here...");
        packagesArea.setEditable(false);
        packageListPanel.add(packageListLabel, BorderLayout.NORTH);
        packageListPanel.add(new JScrollPane(packagesArea), BorderLayout.CENTER);

        packagesPanel.add(packageListPanel, BorderLayout.CENTER);

        // Creating Adjustable Split Panes
        JSplitPane dronesPackagesSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dronesPanel, packagesPanel);
        dronesPackagesSplit.setResizeWeight(0.5); 

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, dronesPackagesSplit);
        mainSplitPane.setResizeWeight(0.7); 

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