package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveCRActionListener implements ActionListener {

	MainFrameAdaptee adaptee;
	
	public RemoveCRActionListener(MainFrameAdaptee a){
		adaptee = a;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		adaptee.removeCRActionPerformed();

	}

}
