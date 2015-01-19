package net.winneonsword.CG.gui;

import org.bukkit.entity.Player;

import net.winneonsword.CG.CG;

public class GuiMessage extends Gui {
	
	private Player p;
	private Gui parent;
	private int ticks;
	
	public GuiMessage(CG main, Player p, String message, Gui parent, int ticks){
		
		super(main, 0, message);
		this.p = p;
		this.parent = parent;
		this.ticks = ticks;
		
	}
	
	@Override
	public void create(){
		
		delay(new Runnable(){
			
			public void run(){
				
				main.sys.displayGui(p, parent);
				
			}
			
		}, this.ticks);
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		// No actions for this Gui.
		
	}
	
}
