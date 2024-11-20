import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderingPanel extends JPanel {

    public OrderingPanel() {
        setBorder(BorderFactory.createTitledBorder("Order Package"));
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Select Package:"));
        //To-Do: Set this to be for the Package Instances Array
        JComboBox<String> packageDropdown = new JComboBox<>(new String[]{"Package A", "Package B", "Package C"});
        add(packageDropdown);

        //To-Do: Set this to be for the House Instances Array
        add(new JLabel("Select Delivery House:"));
        JComboBox<String> houseDropdown = new JComboBox<>(new String[]{"House 1", "House 2", "House 3", "House 4", "House 5"});
        add(houseDropdown);

        JButton submitButton = new JButton("Submit Order");
        add(new JLabel());
        add(submitButton);

        submitButton.addActionListener(e -> {
            String selectedPackage = (String) packageDropdown.getSelectedItem();
            String selectedHouse = (String) houseDropdown.getSelectedItem();
            JOptionPane.showMessageDialog(
                this,
                "Order Submitted!\n" +
                "Package: " + selectedPackage + "\n" +
                "Delivery House: " + selectedHouse,
                "Order Confirmation",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
}
