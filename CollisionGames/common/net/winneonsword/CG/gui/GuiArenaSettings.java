package net.winneonsword.CG.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.winneonsword.CG.CG;
import net.winneonsword.CG.CGArena;
import net.winneonsword.CG.utils.InvenUtils;

public class GuiArenaSettings extends Gui {
	
	private CGArena arena;
	private Gui parent;
	
	public GuiArenaSettings(CG main, CGArena arena){
		
		super(main, 9, "&4&l" + arena.getName());
		this.arena = arena;
		this.parent = this;
		
	}
	
	@Override
	public void create(){
		
		addButton(0, InvenUtils.createItem("&6&lChange Location", new String[] {
				
				"&6Change the", "&6arena location."
				
		}, Material.INK_SACK, 1, 14));
		addButton(8, InvenUtils.createItem("&7&lClose", new String[] {
				
				"&7Close this", "&7prompt."
				
		}, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(final Player p){
		
		switch (slot){
		
		case 0:
			
			main.sys.displayGui(p, new GuiConfirm(main, "&c&lChange to your location?", new Runnable(){
				
				public void run(){
					
					p.closeInventory();
					arena.setLocation(p.getLocation());
					s(p, "Changed the location of the arena &6" + arena.getName() + " &dto &6" + arena.getX() + "&5, &6" + arena.getY() + "&5, &6" + arena.getZ() + "&d.");
					
				}
				
			}, new Runnable(){
				
				public void run(){
					
					main.sys.displayGui(p, parent);
					
				}
				
			}));
			break;
		
		case 8:
			
			p.closeInventory();
			break;
			
		}
		
	}
	
}
