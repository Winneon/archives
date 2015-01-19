package net.winneonsword.DJRequest.gui;

import net.winneonsword.DJRequest.HintTextField;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends Gui {
	
	private HintTextField request;
	
	public MainWindow(String title){
		
		super(title);
		
	}
	
	@Override
	public void create(){

		JPanel main = new JPanel();

		main.setBorder(BorderFactory.createTitledBorder("Add Song"));
		main.setLayout(new GridLayout(1, 1));
		
		request = createTextField("Add Request", "request");
		main.add(request);
		
		frame.add(main);
		
	}
	
	@Override
	public void actionPerformed(String command){
		
		switch (command){
			
			case "request":
				
				System.out.println(request.getText());
				request.setText("");
				break;
				
		}
		
	}
	
}
