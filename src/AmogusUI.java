import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class AmogusUI {
	// how often UI checks statechart values and updates itself, in Hz
	private static final double REFRESH_RATE = 4;
	
	private static DronesPanel dronesPanel;
	private static HousesPanel housesPanel;
	private static MapPanel mapPanel;
	
	public static void main (String[] args) {

		JFrame window = new JFrame("AMOGUS UI - Test");
		window.setSize(1800, 1000);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Statecharts are null by default; initialize all of them here
		Hub hub = new Hub();
		Amadrone[] drones = AmogusUI.initDroneSCs(hub);
		House[] houses = AmogusUI.initHouseSCs(hub);
		ChargingStation[] chargers = AmogusUI.initChargerSCs(hub);
		PackageSM[] packages = AmogusUI.initPackageSCs(hub);

		hub.setTimerService(new ScaledTimeTimerService(1));
		hub.enter();
		
		// Map Panel
		mapPanel = new MapPanel(hub, drones, houses, chargers);
		mapPanel.setBorder(BorderFactory.createTitledBorder("Map"));
		//To-do: A state will perform this action
		//mapPanel.sendDrone(50, 450);

        // Drones Panel
		dronesPanel = new DronesPanel(drones, mapPanel);

        // Packages Panel
        OrderingPanel orderingPanel = new OrderingPanel(hub, packages, houses, mapPanel);
		
        // Houses Panel
        housesPanel = new HousesPanel(houses);
        
        startRefresh();

        // Creating Adjustable Split Panes
        JSplitPane dronesOrdersSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dronesPanel, orderingPanel);
        dronesOrdersSplit.setResizeWeight(0.9); 

        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, dronesOrdersSplit);
        rightSplitPane.setResizeWeight(0.8);
        
        JSplitPane houseSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, rightSplitPane, housesPanel);
        houseSplitPane.setResizeWeight(1);

        window.add(houseSplitPane);
		window.setVisible(true);
	}
	
	private static Amadrone[] initDroneSCs(Hub hub) {
		hub.drone.setD0(new Amadrone());
		hub.drone.setD1(new Amadrone());
		hub.drone.setD2(new Amadrone());
		
		final Amadrone[] drones = { hub.drone.getD0(), hub.drone.getD1(), hub.drone.getD2() };
		
		for (final Amadrone drone : drones) {
			drone.setBattery(new Battery());
			drone.setOperationCallback(new MoveDroneOpCallback(drone));
			drone.getBattery().setTimerService(new ScaledTimeTimerService(1));
			
			drone.setGps(new Gps());
			drone.setMotor(new Motor());
			drone.setTimerService(new ScaledTimeTimerService(1));
		}
		
		return drones;
	}
	
	private static House[] initHouseSCs(Hub hub) {
		hub.houses.setH0(new House());
		hub.houses.setH1(new House());
		hub.houses.setH2(new House());
		hub.houses.setH3(new House());
		hub.houses.setH4(new House());
		
		final House[] houses = { hub.houses.getH0(), hub.houses.getH1(), hub.houses.getH2(), hub.houses.getH3(), hub.houses.getH4() };
		
		return houses; 
	}
	
	private static ChargingStation[] initChargerSCs(Hub hub) {
		hub.charger.setS0(new ChargingStation());
		hub.charger.setS1(new ChargingStation());
		
		final ChargingStation[] chargers = { hub.charger.getS0(), hub.charger.getS1() };
		
		for (final ChargingStation charger : chargers) {
			charger.setTimerService(new ScaledTimeTimerService(1));
		}
		
		return chargers; 
	}
	
	private static PackageSM[] initPackageSCs(Hub hub) {
		hub.package_ID.setP0(new PackageSM());
		hub.package_ID.setP1(new PackageSM());
		hub.package_ID.setP2(new PackageSM());
		hub.package_ID.setP3(new PackageSM());
		
		final PackageSM[] packages = { hub.package_ID.getP0(), hub.package_ID.getP1(), hub.package_ID.getP2(), hub.package_ID.getP3() };
		
		return packages;
	}

    private static void startRefresh() {
  	   class refresh extends TimerTask {
  	       @Override
  	       public void run() {
               readStatechartData();
  	       }
  	   }
  	 
  	   Timer timer = new Timer(true);
  	   TimerTask timerTask = new refresh(); //reference created for TimerTask class
  		
  	   timer.schedule(timerTask, 0, (int) (1000 / REFRESH_RATE));
    }
    
    private static void readStatechartData() {
    	dronesPanel.readStatechartData();
    	housesPanel.readStatechartData();
    }
}
