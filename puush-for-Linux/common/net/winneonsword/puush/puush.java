package net.winneonsword.puush;

import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.melloware.jintellitype.JIntellitype;

public class puush extends JPanel {
	
	private static final long serialVersionUID = 1259240849565301601L;
	
	public puush(){
		
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		left.setLayout(new GridLayout(3, 1));
		left.setBorder(BorderFactory.createTitledBorder("Actions"));
		
		JButton desktop = new JButton("Capture Desktop");
		desktop.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				Capture.desktop();
				
			}
			
		});
		
		left.add(desktop);
		
		JButton window = new JButton("Capture Window");
		window.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				Capture.window();
				
			}
			
		});
		
		left.add(window);
		
		JButton selection = new JButton("Capture Selection");
		selection.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				Capture.selection();
				
			}
			
		});
		
		left.add(selection);
		
		right.setLayout(new GridLayout(3, 1));
		right.setBorder(BorderFactory.createTitledBorder("Settings"));
		
		right.add(new JButton("Desktop Hotkey"));
		right.add(new JButton("Window Hotkey"));
		right.add(new JButton("Selection Hotkey"));
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		
		JPanel title = new JPanel();
		title.setBorder(new EmptyBorder(0, 5, 0, 5));
		
		JLabel label = new JLabel("<html><strong>puush for Linux, 1.0</strong></html>");
		label.setHorizontalAlignment(JLabel.CENTER);
		
		title.add(label);
		add(title, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		add(left, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		add(right, c);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
	}
	
	private static void createGui(){
		
		JFrame frame = new JFrame("puush for Linux");
		
		frame.add(new puush());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args){
		
		JIntellitype type = JIntellitype.getInstance();
		
		type.registerSwingHotKey(1, Event.CTRL_MASK, (int) 'A');
		type.registerSwingHotKey(2, Event.CTRL_MASK, (int) 'B');
		type.registerSwingHotKey(3, Event.CTRL_MASK, (int) 'D');
		
		type.addHotKeyListener(new HotKey());
		new Capture();
		
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			createGui();
			
		} catch (Exception e){
			
			// Nothing!
			
		}
		
	}
	
}
