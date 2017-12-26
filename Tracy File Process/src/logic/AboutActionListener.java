package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutActionListener implements ActionListener {

	MainFrameAdaptee adaptee;
	
	public AboutActionListener(MainFrameAdaptee adaptee){
		this.adaptee = adaptee;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		adaptee.aboutActionPerformed();

	}

}
