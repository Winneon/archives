package net.winneonsword.DJRequest;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;

public class HintPasswordField extends JPasswordField implements FocusListener {
	
	private static final long serialVersionUID = -5514915189051982880L;
	
	private String hint;
	private boolean showing;
	
	private Color colour;
	
	public HintPasswordField(String hint){
		
		super(hint);
		colour = new Color(155, 155, 155);
		
		this.hint = hint;
		this.showing = true;
		
		super.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(216, 216, 216)), BorderFactory.createEmptyBorder(2, 5, 2, 2)));
		super.addFocusListener(this);
		
		super.setForeground(colour);
		super.setBackground(Color.white);
		
		super.setEchoChar((char) 0);
		
	}
	
	@Override
	public void focusGained(FocusEvent e){
		
		if (getText().isEmpty()){
			
			super.setText("");
			super.setForeground(new Color(45, 45, 45));
			super.setEchoChar('•');
			
			showing = false;
			
		}
		
	}
	
	@Override
	public void focusLost(FocusEvent e){
		
		if (getText().isEmpty()){
			
			super.setText(hint);
			super.setForeground(colour);
			super.setEchoChar((char) 0);
			
			showing = true;
			
		}
		
	}
	
	@Override
	public String getText(){
		
		return showing ? "" : new String(getPassword());
		
	}
	
}
