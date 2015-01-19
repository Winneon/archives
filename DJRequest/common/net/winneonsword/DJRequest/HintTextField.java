package net.winneonsword.DJRequest;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class HintTextField extends JTextField implements FocusListener {
	
	private static final long serialVersionUID = -2303974084975946640L;
	
	private String hint;
	private boolean showing;
	
	private Color colour;
	
	public HintTextField(String hint){
		
		super(hint);
		colour = new Color(155, 155, 155);
		
		this.hint = hint;
		this.showing = true;
		
		super.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(216, 216, 216)), BorderFactory.createEmptyBorder(2, 5, 2, 2)));
		super.addFocusListener(this);
		
		super.setBackground(Color.white);
		super.setForeground(colour);
		
	}
	
	@Override
	public void focusGained(FocusEvent e){
		
		if (getText().isEmpty()){
			
			super.setText("");
			super.setForeground(new Color(45, 45, 45));
			
			showing = false;
			
		}
		
	}
	
	@Override
	public void focusLost(FocusEvent e){
		
		if (getText().isEmpty()){
			
			super.setText(hint);
			super.setForeground(colour);
			
			showing = true;
			
		}
		
	}
	
	@Override
	public String getText(){
		
		return showing ? "" : super.getText();
		
	}
	
}
