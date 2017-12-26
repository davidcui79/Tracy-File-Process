package logic;

import gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileExitActionListener implements ActionListener {

	MainWindow adaptee;
	
	public FileExitActionListener(MainWindow adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent arg0) {
		adaptee.windowClosing();

	}

}
