package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileOpenActionListener implements ActionListener {
	MainFrameAdaptee adaptee;
	
	public FileOpenActionListener(MainFrameAdaptee adaptee){
		this.adaptee = adaptee;
	}
	
	public void actionPerformed(ActionEvent event) {
		adaptee.fileOpenActionPerformed(event);

	}

}
