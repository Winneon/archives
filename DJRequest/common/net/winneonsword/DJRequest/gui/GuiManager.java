package net.winneonsword.DJRequest.gui;

import javax.swing.JFrame;

public class GuiManager {

	public static JFrame displayGui(Gui gui){
		
		gui.create();
		JFrame frame = gui.getFrame();
		
		frame.pack();
		frame.setVisible(true);
		
		return frame;
		
	}
	
}
