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
	
    public OrderingPanel(Hub hub, PackageSM[] packages, House[] houses) {
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

        submitButton.addActionListener(e -> {
            String selectedPackage = (String) packageDropdown.getSelectedItem();
            String selectedHouse = (String) houseDropdown.getSelectedItem();
            
            int packageIndex = packageDropdown.getSelectedIndex();
            int houseIndex = houseDropdown.getSelectedIndex();
            
            if (this.houses[houseIndex].getPackage_id() == -1) {
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
//    	this.houses[houseIndex].raiseStart_order();
//    	switch (packageIndex) {
//    		case 0:
//    	    	switch (houseIndex) {
//        		case 0:
//        			hub.
//        			break;
//        		case 1:
//        			this.houses[houseIndex].raiseOrder_1();
//        			break;
//        		case 2:
//        			this.houses[houseIndex].raiseOrder_2();
//        			break;
//        		case 3:
//        			this.houses[houseIndex].raiseOrder_3();
//        			break;
//    			default:
//    				System.out.println("Error: unknown package order detected: index #" + packageIndex);
//    	    	}
//    			break;
//    		case 1:
//    			this.houses[houseIndex].raiseOrder_1();
//    			break;
//    		case 2:
//    			this.houses[houseIndex].raiseOrder_2();
//    			break;
//    		case 3:
//    			this.houses[houseIndex].raiseOrder_3();
//    			break;
//			default:
//				System.out.println("Error: unknown package order detected: index #" + packageIndex);
//    	}
    }
}
