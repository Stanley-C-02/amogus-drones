import java.awt.Color;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HousesPanel extends JPanel {
	private HousePanel[] panels;
	
	public HousesPanel(House[] houses) {
        setBorder(BorderFactory.createTitledBorder("House Order Status"));
        setLayout(new GridLayout(1, houses.length, 0, 0));
        
        this.panels = new HousePanel[houses.length];

        for (int i = 0; i < houses.length; i++) {
        	HousePanel panel = new HousePanel(houses[i]);
        	panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        	add(panel);
        	this.panels[i] = panel;
        }
	}
	
	public void readStatechartData() {
		for (HousePanel p : this.panels) {
			p.readStatechartData();
		}
	}
}
