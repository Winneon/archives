package net.winneonsword.CG.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.winneonsword.CG.CG;
import net.winneonsword.CG.utils.InvenUtils;

public class GuiStaffMenu extends Gui {
	
	private Gui parent;
	
	public GuiStaffMenu(CG main, Gui parent){
		
		super(main, 9, "&c&lC&4&lG &7&l-> &2&lStaff Menu");
		this.parent = parent;
		
	}
	
	@Override
	public void create(){
		
		addButton(0, InvenUtils.createItem("&e&lStart", new String[] { "&eStart a game." },  Material.INK_SACK, 1, 11));
		addButton(1, InvenUtils.createItem("&6&lStop", new String[] { "&6Stop a game." },  Material.INK_SACK, 1, 14));
		addButton(2, InvenUtils.createItem("&c&lKick", new String[] {
				
				"&cKick a player", "&cfrom the game."
				
		}, Material.INK_SACK, 1, 1));
		addButton(7, InvenUtils.createItem("&b&lSettings", new String[] {
				
				"&bView the", "&bsettings menu."
				
		}, Material.INK_SACK, 1, 12));
		addButton(8, InvenUtils.createItem("&7&lCancel", new String[] {
				
				"&7Return to", "&7previous gui."
				
		}, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		switch (slot){
		
		case 0:
			
			
			break;
			
		case 1:
			
			
			break;
			
		case 2:
			
			
			break;
			
		case 7:
			
			main.sys.displayGui(p, new GuiSettings(main, this));
			break;
			
		case 8:
			
			main.sys.displayGui(p, parent);
			break;
			
		}
		
	}
	
}
