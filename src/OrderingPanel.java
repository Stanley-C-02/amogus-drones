import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class OrderingPanel extends JPanel {
	private Hub hub;
	private PackageSM[] packages;
	private House[] houses;
	private String[] packageOptions;
	private String[] deliveryOptions;
	private MapPanel mapPanel;
	
    public OrderingPanel(Hub hub, PackageSM[] packages, House[] houses, MapPanel mapPanel) {
    	this.mapPanel = mapPanel;
    	this.hub = hub;
    	this.packages = packages;
    	this.houses = houses;
    	
        this.packageOptions = Arrays.stream(packages)
        		.map(p -> "#" + p.getId() + ": " + p.getName() + " (" + p.getWeight() + "g)")
        		.toArray(size -> new String[size]);
        
        this.deliveryOptions = Arrays.stream(houses)
        		.map(h -> h.getName() + " (" + h.getX() + ", " + h.getY() + ")")
        		.toArray(size -> new String[size]);
        
        setBorder(BorderFactory.createTitledBorder("Order Package"));
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Select Package:"));
        JComboBox<String> packageDropdown = new JComboBox<>(this.packageOptions);
        add(packageDropdown);

        add(new JLabel("Select Delivery House:"));
        JComboBox<String> houseDropdown = new JComboBox<>(this.deliveryOptions);
        add(houseDropdown);

        JButton submitButton = new JButton("Submit Order");
        add(new JLabel());
        add(submitButton);
        
        houseDropdown.addActionListener(e -> {
            int selectedHouseIndex = houseDropdown.getSelectedIndex();
            mapPanel.setSelectedHouseIndex(selectedHouseIndex);
        });

        submitButton.addActionListener(e -> {
            String selectedPackage = (String) packageDropdown.getSelectedItem();
            String selectedHouse = (String) houseDropdown.getSelectedItem();
            
            int packageIndex = packageDropdown.getSelectedIndex();
            int houseIndex = houseDropdown.getSelectedIndex();
            
            if (this.houses[houseIndex].getPackage() == null) {
            	this.orderPackage(packageIndex, houseIndex);
            	
                JOptionPane.showMessageDialog(
                        this,
                        "Order Submitted!\n" +
                        "Package: " + selectedPackage + "\n" +
                        "Delivery House: " + selectedHouse,
                        "Order Confirmation",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
            	JOptionPane.showMessageDialog(
            			this,
            			"Error: " + selectedHouse + "\n" +
            			"Already has an order in progress.",
            			"Order in Progress",
            			JOptionPane.ERROR_MESSAGE
    			);
            }
        });
    }
    
    private void orderPackage(int packageIndex, int houseIndex) {
    	switch (packageIndex) {
    		case 0:
    	    	switch (houseIndex) {
	        		case 0:
	        			hub.houses.raiseOrder_p0_to_h0();
	        			hub.drone.getD0().routing().raiseReceived();
	        			
	        			System.out.print("rasised");
	        			break;
	        		case 1:
	        			hub.houses.raiseOrder_p0_to_h1();
	        			break;
	        		case 2:
	        			hub.houses.raiseOrder_p0_to_h2();
	        			break;
	        		case 3:
	        			hub.houses.raiseOrder_p0_to_h3();
	        			break;
	        		case 4:
	        			hub.houses.raiseOrder_p0_to_h4();
	        			break;
	    			default:
	    				System.out.println("Error: unknown house order detected: houseIndex #" + houseIndex);
    	    	}
    			break;
    		case 1:
    	    	switch (houseIndex) {
	        		case 0:
	        			hub.houses.raiseOrder_p1_to_h0();
	        			break;
	        		case 1:
	        			hub.houses.raiseOrder_p1_to_h1();
	        			break;
	        		case 2:
	        			hub.houses.raiseOrder_p1_to_h2();
	        			break;
	        		case 3:
	        			hub.houses.raiseOrder_p1_to_h3();
	        			break;
	        		case 4:
	        			hub.houses.raiseOrder_p1_to_h4();
	        			break;
	    			default:
	    				System.out.println("Error: unknown house order detected: houseIndex #" + houseIndex);
		    	}
				break;
    		case 2:
    	    	switch (houseIndex) {
	        		case 0:
	        			hub.houses.raiseOrder_p2_to_h0();
	        			break;
	        		case 1:
	        			hub.houses.raiseOrder_p2_to_h1();
	        			break;
	        		case 2:
	        			hub.houses.raiseOrder_p2_to_h2();
	        			break;
	        		case 3:
	        			hub.houses.raiseOrder_p2_to_h3();
	        			break;
	        		case 4:
	        			hub.houses.raiseOrder_p2_to_h4();
	        			break;
	    			default:
	    				System.out.println("Error: unknown house order detected: houseIndex #" + houseIndex);
		    	}
			break;
    		case 3:
    	    	switch (houseIndex) {
	        		case 0:
	        			hub.houses.raiseOrder_p3_to_h0();
	        			break;
	        		case 1:
	        			hub.houses.raiseOrder_p3_to_h1();
	        			break;
	        		case 2:
	        			hub.houses.raiseOrder_p3_to_h2();
	        			break;
	        		case 3:
	        			hub.houses.raiseOrder_p3_to_h3();
	        			break;
	        		case 4:
	        			hub.houses.raiseOrder_p3_to_h4();
	        			break;
	    			default:
	    				System.out.println("Error: unknown house order detected: houseIndex #" + houseIndex);
		    	}
			break;
			default:
				System.out.println("Error: unknown package order detected: packageIndex #" + packageIndex);
    	}
    }
}
