import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class AmogusUI {
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
		
		hub.enter();
		
		// Map Panel
		MapPanel mapPanel = new MapPanel();
		mapPanel.setBorder(BorderFactory.createTitledBorder("Map"));
		//To-do: A state will perform this action
		//mapPanel.sendDrone(50, 450);

        // Drones Panel
		DronesPanel dronesPanel = new DronesPanel(drones);

        // Packages Panel
        OrderingPanel orderingPanel = new OrderingPanel(packages, houses);

        // Creating Adjustable Split Panes
        JSplitPane dronesOrdersSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dronesPanel, orderingPanel);
        dronesOrdersSplit.setResizeWeight(0.9); 

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, dronesOrdersSplit);
        mainSplitPane.setResizeWeight(0.8); 

        window.add(mainSplitPane);
		window.setVisible(true);
	}
	
	private static Amadrone[] initDroneSCs(Hub hub) {
		hub.drone.setD0(new Amadrone());
		hub.drone.setD1(new Amadrone());
		hub.drone.setD2(new Amadrone());
		
		final Amadrone[] drones = { hub.drone.getD0(), hub.drone.getD1(), hub.drone.getD2() };
		
		class refresh extends TimerTask {
			@Override
			public void run() {
		        try {
		            Thread.sleep(999);
		            System.out.println("TODO: DRONE STUFF");
		            //readStatechartData(hub);
		        } catch (InterruptedException error) {
		            System.out.println(error);
		        }
			}
		}
		
		for (final Amadrone drone : drones) {
			drone.setTimerService(new ScaledTimeTimerService(1));
			
			Timer timer = new Timer(true);
			TimerTask timerTask = new refresh();
			
			timer.schedule(timerTask, 0, 999);
			
//			drone.enter();
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
		
//		for (final House house : houses) {
//			house.enter();
//		}
		
		return houses; 
	}
	
	private static ChargingStation[] initChargerSCs(Hub hub) {
		hub.charger.setStation0(new ChargingStation());
		hub.charger.setStation1(new ChargingStation());
		
		final ChargingStation[] chargers = { hub.charger.getStation0(), hub.charger.getStation1() };
		
		class refresh extends TimerTask {
			@Override
			public void run() {
		        try {
		            Thread.sleep(999);
		            System.out.println("TODO: CHARGING STATION STUFF");
		            //readStatechartData(hub);
		        } catch (InterruptedException error) {
		            System.out.println(error);
		        }
			}
		}
		
		for (final ChargingStation charger : chargers) {
			charger.setTimerService(new ScaledTimeTimerService(1));
			
			Timer timer = new Timer(true);
			TimerTask timerTask = new refresh();
			
			timer.schedule(timerTask, 0, 999);
			
//			charger.enter();
		}
		
		return chargers; 
	}
	
	private static PackageSM[] initPackageSCs(Hub hub) {
		hub.package_ID.setP0(new PackageSM());
		hub.package_ID.setP1(new PackageSM());
		hub.package_ID.setP2(new PackageSM());
		hub.package_ID.setP3(new PackageSM());
		
		final PackageSM[] packages = { hub.package_ID.getP0(), hub.package_ID.getP1(), hub.package_ID.getP2(), hub.package_ID.getP3() };
		
//		for (final PackageSM packageSC : packages) {
//			packageSC.enter();
//		}
		
		return packages;
	}
}
