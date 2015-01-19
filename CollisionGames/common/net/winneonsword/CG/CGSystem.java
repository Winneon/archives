package net.winneonsword.CG;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;

import net.winneonsword.CG.exceptions.UnknownPlayerException;
import net.winneonsword.CG.gui.Gui;

public class CGSystem extends Utils {
	
	private HashMap<String, CGPlayer> players;
	private List<String> users;
	
	public CGSystem(CG main){
		
		super(main);
		this.players = new HashMap();
		this.users = new ArrayList();
		
	}
	
	public void displayGui(Player p, Gui gui){
		
		main.click.current.put(p.getName(), gui);
		gui.create();
		p.openInventory(gui.getInv());
		
	}
	
	public void mouseClicked(Player p, int slot, Gui gui, ItemStack item, InventoryClickEvent click){
		
		gui.current = "click";
		gui.slot = slot;
		gui.item = item;
		gui.click = click;
		gui.actionPerformed(p);
		
	}
	
	public void mouseDragged(Player p, Gui gui, ItemStack item, InventoryDragEvent drag){
		
		gui.current = "drag";
		gui.item = item;
		gui.drag = drag;
		gui.actionPerformed(p);
		
	}
	
	public void mouseMoved(Player p, Gui gui, ItemStack item, InventoryMoveItemEvent move){
		
		gui.current = "move";
		gui.item = item;
		gui.move = move;
		gui.actionPerformed(p);
		
	}
	
	public void mouseInteracted(Player p, Gui gui, InventoryInteractEvent interact){
		
		gui.current = "interact";
		gui.interact = interact;
		gui.actionPerformed(p);
	}
	
	public List<String> getUsers(){
		
		return users;
		
	}
	
	public CGPlayer getPlayer(String name){
		
		if (players.containsKey(name)){
			
			return players.get(name);
			
		} else {
			
			// Using a try / catch clause instead of throws declaration to prevent messy code in future.
			
			try {
				
				throw new UnknownPlayerException(name);
				
			} catch (UnknownPlayerException e){
				
				Bukkit.getLogger().severe("No such player for the player name '" + name + "'!");
				e.printStackTrace();
				
			}
			
			return null;
			
		}
		
	}
	
	public void addPlayer(String name, CGPlayer p){
		
		players.put(name, p);
		
	}
	
	public void addUser(String name){
		
		users.add(name);
		
	}
	
	public void setUsers(List<String> users){
		
		this.users = users;
		
	}
	
	public void savePlayer(String name){
		
		CGPlayer p = getPlayer(name);
		File file = new File(main.userFolder, name + ".yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		
		yaml.set("interface", p.getInterface());
		
		try {
			
			yaml.save(file);
			
		} catch (IOException e){
			
			Bukkit.getLogger().severe("Unable to save the data for the player '" + name + "'!");
			e.printStackTrace();
			
		}
		
	}
	
	public void setupPlayer(String name, boolean n){
		
		CGPlayer p = new CGPlayer(name);
		File file = new File(main.userFolder, name + ".yml");
		
		if (n){
			
			Bukkit.getLogger().info("Creating new user file for player '" + name + "'!");
			
			try {
				
				file.createNewFile();
				p.setInterface(false);
				
				this.addUser(name);
				
			} catch (IOException e){
				
				Bukkit.getLogger().severe("Unable to setup the data for the new player '" + name + "'!");
				e.printStackTrace();
				
				return;
				
			}
			
		} else {
			
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
			p.setInterface(yaml.getBoolean("interface"));
			
		}
		
		addPlayer(name, p);
		
	}
	
	public boolean toggleInterface(Player p){
		
		CGPlayer cgp = main.sys.getPlayer(p.getName());
		boolean toggle = !(cgp.getInterface());
		
		cgp.setInterface(toggle);
		
		return toggle;
		
	}
	
}
