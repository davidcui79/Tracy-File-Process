package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindowAdapter extends WindowAdapter {

	MainWindow adaptee;
	
	public MainWindowAdapter(MainWindow adaptee){
		this.adaptee = adaptee;
	}
	
	
	public void windowClosing(WindowEvent e){
		adaptee.windowClosing();
	}
}
