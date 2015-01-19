package net.winneonsword.CG.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.winneonsword.CG.CG;
import net.winneonsword.CG.utils.InvenUtils;

public class GuiSettings extends Gui {
	
	private Gui parent;
	
	public GuiSettings(CG main, Gui parent){
		
		super(main, 9, "&2&lSM &7&l-> &9&lSettings");
		this.parent = parent;
		
	}
	
	@Override
	public void create(){
		
		addButton(0, InvenUtils.createItem("&a&lArenas", new String[] {
				
				"&aChange the", "&aarena settings."
				
		}, Material.INK_SACK, 1, 10));
		addButton(8, InvenUtils.createItem("&7&lCancel", new String[] {
				
				"&7Return to", "&7previous gui."
				
		}, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(final Player p){
		
		switch (slot){
		
		case 0:
			
			main.sys.displayGui(p, new GuiArenaSelection(main, new Runnable(){
				
				public void run(){
					
					main.sys.displayGui(p, new GuiArenaSettings(main, GuiArenaSelection.arena));
					
				}
				
			}, false, this));
			
			break;
			
		case 8:
			
			main.sys.displayGui(p, parent);
			break;
			
		}
		
	}
	
}
