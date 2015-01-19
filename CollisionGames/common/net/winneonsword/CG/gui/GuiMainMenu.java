package net.winneonsword.CG.gui;

import net.winneonsword.CG.CG;
import net.winneonsword.CG.utils.InvenUtils;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GuiMainMenu extends Gui {
	
	public GuiMainMenu(CG main){
		
		super(main, 9, "&c&lCollision&4&lGames");
		
	}
	
	@Override
	public void create(){
		
		addButton(0, InvenUtils.createItem("&e&lJoin", new String[] { "&eJoin a game." }, Material.GLOWSTONE_DUST));
		addButton(1, InvenUtils.createItem("&6&lLeave", new String[] { "&6Leave a game." }, Material.INK_SACK, 1, 14));
		addButton(2, InvenUtils.createItem("&d&lPlayer List", new String[] {
				
				"&dShow the CG", "&dplayer list."
				
		}, Material.INK_SACK, 1, 13));
		addButton(6, InvenUtils.createItem("&3&lToggle Interface", new String[] {
				
				"&3Toggle between gui", "&3and chat."
				
		}, Material.INK_SACK, 1, 4));
		addButton(7, InvenUtils.createItem("&a&lStaff", new String[] { "&aView the staff menu." }, Material.INK_SACK, 1, 10));
		addButton(8, InvenUtils.createItem("&c&lClose", new String[] { "&cClose this prompt." }, Material.TNT));
		
	}
	
	@Override
	public void actionPerformed(final Player p){
		
		switch (slot){
		
		case 0:
			
			if (main.game.addPlayer(p.getName())){
				
				main.sys.displayGui(p, new GuiMessage(main, p, "&d&lJoined the game!", this, 30));
				delay(new Runnable(){
					
					public void run(){
						
						main.sys.displayGui(p, new GuiMessage(main, p, "&d&lTeleporting to the lobby...", new GuiMainMenu(main), 30));
						
					}
					
				}, 30);
				
			} else {
				
				main.sys.displayGui(p, new GuiMessage(main, p, "&c&lYou have already joined!", this, 30));
				
			}
			
			break;
			
		case 1:
			
			if (main.game.removePlayer(p.getName())){
				
				main.sys.displayGui(p, new GuiMessage(main, p, "&d&lLeft the game!", this, 30));
				delay(new Runnable(){
					
					public void run(){
						
						main.sys.displayGui(p, new GuiMessage(main, p, "&d&lTeleporting to spawn...", new GuiMainMenu(main), 30));
						
					}
					
				}, 30);
				
			} else {
				
				main.sys.displayGui(p, new GuiMessage(main, p, "&c&lYou have not joined yet!", this, 30));
				
			}
			
			break;
			
		case 2:
			
			if (main.game.getPlayers().isEmpty()){
				
				main.sys.displayGui(p, new GuiMessage(main, p, "&c&lThere are no players!", this, 30));
				
			} else {
				
				main.sys.displayGui(p, new GuiPlayerSelection(main, new Runnable(){ public void run(){ } }, false, true, this));
				
			}
			
			break;
			
		case 6:
			
			boolean toggle = main.sys.toggleInterface(p);
			
			if (toggle == true){
				
				main.sys.displayGui(p, new GuiMessage(main, p, "&d&lToggled to &6&lchat&d&l.", this, 30));
				
			} else {
				
				main.sys.displayGui(p, new GuiMessage(main, p, "&d&lToggled to &6&lgui&d&l.", this, 30));
				
			}
			
			break;
			
		case 7:
			
			if (p.hasPermission("wa.staff")){
				
				main.sys.displayGui(p, new GuiStaffMenu(main, this));
				
			} else {
				
				main.sys.displayGui(p, new GuiMessage(main, p, "&c&lYou don't have permission!", this, 30));
				
			}
			
			break;
			
		case 8:
			
			p.closeInventory();
			break;
			
		}
		
	}
	
}
