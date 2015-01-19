package net.winneonsword.CG.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import net.winneonsword.CG.CG;
import net.winneonsword.CG.CGArena;
import net.winneonsword.CG.utils.InvenUtils;

public class GuiArenaSelection extends Gui {
	
	public static CGArena arena;
	
	private Runnable run;
	private CGArena[] arenas;
	private int total;
	
	private boolean close;
	private Gui parent;
	
	public GuiArenaSelection(CG main, Runnable run, boolean close, Gui parent){
		
		super(main, 54, "&4&lARENA SELECTION");
		this.run = run;
		
		this.close = close;
		this.parent = parent;
		
	}
	
	@Override
	public void create(){
		
		arenas = new CGArena[main.game.getArenas().size()];
		total = arenas.length;
		
		for (int i = 0; i < arenas.length; i++){
			
			CGArena arena = main.game.getArenas().get(i);
			arenas[i] = arena;
			
		}
		
		for (int i = 0; i < total; i++){
			
			CGArena arena = arenas[i];
			addButton(i, InvenUtils.createItem("&7" + arena.getName(), new String[] { "&6" + arena.getX() + "&5, &6" + arena.getY() + "&5, &6" + arena.getZ() }, Material.SKULL_ITEM, 1, 3));
			
		}
		
		String close = "&bRETURN";
		
		if (parent == null){
			
			close = "&bCLOSE";
			
		}
		
		addButton(52, InvenUtils.createItem("&b&lAdd Arena", new String[] {
				
				"&bAdd an", "&barena."
				
		}, Material.NETHER_STAR));
		addButton(53, InvenUtils.createItem(close, new String[] { "&b< < <" }, Enchantment.DURABILITY, 10, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(final Player p){
		
		switch (slot){
		
		default:
			
			try {
				
				arena = arenas[slot];
				run.run();
				
				if (close){	
					
					p.closeInventory();	
					
				}
				
			} catch (Exception e){
				
				Bukkit.getLogger().severe("A player made an oopsie! :o");
				
			}
			
			break;
			
		case 52:
			
			p.closeInventory();
			s(p, "Type in the name of the arena:");
			chatInput(p, new Runnable(){
				
				public void run(){
					
					final String name = main.chat.getInput(p);
					
					s(p, "Type in the radius of the arena. (Integer only)");
					chatInput(p, new Runnable(){
						
						public void run(){
							
							if (ifInt(main.chat.getInput(p))){
								
								main.sys.displayGui(p, new GuiConfirm(main, "&c&lCreate arena?", new Runnable(){
									
									public void run(){
										
										int radius = toInteger(main.chat.getInput(p));
										
										p.closeInventory();
										main.game.addArena(name, p.getLocation(), radius);
										
										CGArena arena = main.game.getArena(name);
										s(p, "Created the arena &6" + arena.getName() + " &dat the location &6" + arena.getX() + "&5, &6" + arena.getY() + "&5, &6" + arena.getZ() + " &dwith a radius of &6" + radius + "&d.");
										
									}
									
								}, new Runnable(){
									
									public void run(){
										
										p.closeInventory();
										
									}
									
								}));
								
							} else {
								
								s(p, "&cYou did not enter an integer!");
								
							}
							
						}
						
					});
					
				}
				
			});
			
			break;
			
		case 53:
			
			p.closeInventory();
			break;
			
		}
		
	}
	
}
