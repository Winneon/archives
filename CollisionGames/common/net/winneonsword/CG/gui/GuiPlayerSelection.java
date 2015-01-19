package net.winneonsword.CG.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import net.winneonsword.CG.CG;
import net.winneonsword.CG.utils.InvenUtils;

public class GuiPlayerSelection extends Gui {
	
	public static Player player;
	private Gui parent;
	
	private Player[] players;
	private boolean close;
	
	private Runnable run;
	private int total;
	
	private boolean cgp;
	
	public GuiPlayerSelection(CG main, Runnable run, boolean close, boolean players, Gui parent){
		
		super(main, 54, "&4&lPLAYER SELECTION");
		this.run = run;
		
		this.close = close;
		this.parent = parent;
		
		this.cgp = players;
		
	}
	
	@Override
	public void create(){
		
		if (cgp){
			
			players = new Player[main.game.getPlayers().size()];
			
			for (int i = 0; i < players.length; i++){
				
				String p = main.game.getPlayers().get(i);
				Player pp = Bukkit.getPlayer(p);
				players[i] = pp;
				
			}
			
		} else {
			
			players = Bukkit.getOnlinePlayers();
			
		}
		
		total = players.length;
		
		for (int i = 0; i < total; i++){
			
			Player p = players[i];
			addButton(i, InvenUtils.createItem(p.getDisplayName(), new String[] { "&f" + p.getName() }, Material.SKULL_ITEM, 1, 3));
			
		}
		
		String close = "&bRETURN";
		
		if (parent == null){
			
			close = "&bCLOSE";
			
		}
		
		addButton(53, InvenUtils.createItem(close, new String[] { "&b< < <" }, Enchantment.DURABILITY, 10, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		if (current.equals("click")){
			
			if (slot == 53){
				
				if (parent == null){
					
					p.closeInventory();
					
				} else {
					
					main.sys.displayGui(p, parent);
					
				}
				
			} else if (slot < total){
				
				try {
					
					player = players[slot];
					run.run();
					
					if (close){	
						
						p.closeInventory();	
						
					}
					
				} catch (Exception e){
					
					Bukkit.getLogger().severe("A player made an oopsie! :o'");
					
				}
				
			}
			
		}
		
	}
	
}