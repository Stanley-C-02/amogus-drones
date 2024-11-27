import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class AmogusUI {
	// how often UI checks statechart values and updates itself, in Hz
	private static final double REFRESH_RATE = 1;

	private static Hub hub;
	private static DronesPanel dronesPanel;
	private static HousesPanel housesPanel;
	private static MapPanel mapPanel;

	public static void main (String[] args) {

		JFrame window = new JFrame("AMOGUS UI - Test");
		window.setSize(1800, 1000);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Statecharts are null by default; initialize all of them here
		hub = new Hub();

		Amadrone[] drones = AmogusUI.initDroneSCs(hub);
		House[] houses = AmogusUI.initHouseSCs(hub);
		ChargingStation[] chargers = AmogusUI.initChargerSCs(hub);
		PackageSM[] packages = AmogusUI.initPackageSCs(hub);

		mapPanel = new MapPanel(hub, drones, houses, chargers);

		for (Amadrone drone : drones) {
			drone.setOperationCallback(new MoveDroneOpCallback(drone, mapPanel));
			drone.enter();
		}
		
		hub.setTimerService(new ScaledTimeTimerService(1));
		hub.enter();
		hub.charger.raiseStart_all();
		
		reInitAll(drones, houses, chargers, packages);

		// Map Panel
		mapPanel.setBorder(BorderFactory.createTitledBorder("Map"));
		//To-do: A state will perform this action
		//mapPanel.sendDrone(50, 450);

        // Drones Panel
		dronesPanel = new DronesPanel(drones, mapPanel);

        // Packages Panel
        OrderingPanel orderingPanel = new OrderingPanel(hub, packages, houses, mapPanel);

        // Houses Panel
        housesPanel = new HousesPanel(houses, mapPanel);

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
			drone.setTimerService(new ScaledTimeTimerService(1));
			drone.setBattery(new Battery());
			drone.setGps(new Gps());
			drone.setMotor(new Motor());
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

		for (final House house : houses) {
			house.enter();
		}

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

		for (final PackageSM packageSM : packages) {
			packageSM.enter();
		}

		return packages;
	}
	
	private static void reInitAll(Amadrone[] drones, House[] houses, ChargingStation chargers[], PackageSM[] packages) {
		drones[0].setId(0);
		drones[0].setName("Drone 01");
		drones[0].setX(50);
		drones[0].setY(55);
		drones[0].getBattery().setId(0);
		drones[0].getBattery().setName("Medium");
		drones[0].getBattery().setAvailable(1.5);
		drones[0].getBattery().setMaxCapacity(2);
		drones[0].getGps().setId(0);
		drones[0].getGps().setName("Standard GPS");
		drones[0].getGps().setPower(10);
		drones[0].getMotor().setId(0);
		drones[0].getMotor().setName("GM3506");
		drones[0].getMotor().setPower(100);
		drones[0].getMotor().setSpeed(3);
		drones[0].getMotor().setMax_payload(500);
		drones[1].setId(1);
		drones[1].setName("Drone 02");
		drones[1].setX(40);
		drones[1].setY(45);
		drones[1].getBattery().setId(1);
		drones[1].getBattery().setName("Small");
		drones[1].getBattery().setAvailable(0.5);
		drones[1].getBattery().setMaxCapacity(1);
		drones[1].getGps().setId(1);
		drones[1].getGps().setName("Standard GPS");
		drones[1].getGps().setPower(10);
		drones[1].getMotor().setId(1);
		drones[1].getMotor().setName("GM2804");
		drones[1].getMotor().setPower(50);
		drones[1].getMotor().setSpeed(5);
		drones[1].getMotor().setMax_payload(200);
		drones[2].setId(2);
		drones[2].setName("Drone 03");
		drones[2].setX(70);
		drones[2].setY(35);
		drones[2].getBattery().setId(2);
		drones[2].getBattery().setName("Large");
		drones[2].getBattery().setAvailable(8);
		drones[2].getBattery().setMaxCapacity(8);
		drones[2].getGps().setId(2);
		drones[2].getGps().setName("Advanced GPS");
		drones[2].getGps().setPower(50);
		drones[2].getMotor().setId(2);
		drones[2].getMotor().setName("A2212");
		drones[2].getMotor().setPower(350);
		drones[2].getMotor().setSpeed(2);
		drones[2].getMotor().setMax_payload(700);

		drones[0].getBattery().raiseUpdate_charge();
		drones[1].getBattery().raiseUpdate_charge();
		drones[2].getBattery().raiseUpdate_charge();
		
		houses[0].setId(0);
		houses[0].setName("TMU");
		houses[0].setX(30);
		houses[0].setY(80);
		houses[1].setId(1);
		houses[1].setName("Stanley");
		houses[1].setX(65);
		houses[1].setY(20);
		houses[2].setId(2);
		houses[2].setName("Alice");
		houses[2].setX(3);
		houses[2].setY(60);
		houses[3].setId(3);
		houses[3].setName("Bob");
		houses[3].setX(35);
		houses[3].setY(40);
		houses[4].setId(4);
		houses[4].setName("Arshdeep");
		houses[4].setX(60);
		houses[4].setY(70);

		chargers[0].setId(0);
		chargers[0].setName("Fast Hub Charger");
		chargers[0].setX(60);
		chargers[0].setY(50);
		chargers[0].setChargeRate(250);
		chargers[1].setId(1);
		chargers[1].setName("Slow Remote Charger");
		chargers[1].setX(40);
		chargers[1].setY(30);
		chargers[1].setChargeRate(100);
		
		packages[0].setId(0);
		packages[0].setName("BIC Mechanical Pencil Set (10 pcs)");
		packages[0].setWeight(62);
		packages[1].setId(1);
		packages[1].setName("AA Battery Pack (20 pcs)");
		packages[1].setWeight(400);
		packages[2].setId(2);
		packages[2].setName("Lysol Spray");
		packages[2].setWeight(680);
		packages[3].setId(3);
		packages[3].setName("Tim Hortons Original Blend");
		packages[3].setWeight(930);
	}

    private static void startRefresh() {
  	   class refresh extends TimerTask {
  	       @Override
  	       public void run() {
  	    	   mapPanel.repaint();
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
