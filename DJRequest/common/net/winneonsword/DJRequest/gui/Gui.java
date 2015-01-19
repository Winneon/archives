package net.winneonsword.DJRequest.gui;

import net.winneonsword.DJRequest.HintTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Gui implements ActionListener {
	
	protected String title;
	protected JFrame frame;
	
	public Gui(String title){
		
		this.title = title;
		frame = new JFrame(title);
		
	}
	
	public abstract void create();
	public abstract void actionPerformed(String command);
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		actionPerformed(e.getActionCommand());
		
	}
	
	public JButton createButton(String name, String command){
		
		JButton button = new JButton(name);
		
		button.setActionCommand(command);
		button.addActionListener(this);
		
		return button;
		
	}
	
	public HintTextField createTextField(String hint, String command){

		HintTextField field = new HintTextField(hint);
		
		field.setActionCommand(command);
		field.addActionListener(this);
		
		return field;
		
	}
	
	public String getTitle(){
		
		return title;
		
	}
	
	public JFrame getFrame(){
		
		return frame;
		
	}
	
}
