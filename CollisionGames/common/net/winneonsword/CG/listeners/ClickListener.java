package net.winneonsword.CG.listeners;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import net.winneonsword.CG.CG;
import net.winneonsword.CG.Utils;
import net.winneonsword.CG.gui.Gui;

public class ClickListener extends Utils implements Listener {
	
	public HashMap<String, Gui> guis;
	public HashMap<String, Gui> current;
	
	public ClickListener(CG main){
		
		super(main);
		this.guis = new HashMap();
		this.current = new HashMap<String, Gui>();
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onClick(InventoryClickEvent e){
		
		if (e.getWhoClicked() instanceof Player){
			
			Player p = (Player) e.getWhoClicked();
			
			if (current.containsKey(p.getName())){
				
				Gui gui = current.get(p.getName());
				
				if (e.getInventory().getName().equals(AS(gui.title))){
					
					e.setCancelled(true);
					main.sys.mouseClicked(p, e.getSlot(), gui, e.getCurrentItem(), e);
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onDrag(InventoryDragEvent e){
		
		if (e.getWhoClicked() instanceof Player){
			
			Player p = (Player) e.getWhoClicked();
			
			if (current.containsKey(p.getName())){
				
				Gui gui = current.get(p.getName());
				
				if (e.getInventory().getName().equals(AS(gui.title))){
					
					e.setCancelled(true);
					main.sys.mouseDragged(p, gui, e.getCursor(), e);
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onMove(InventoryMoveItemEvent e){
		
		if (e.getSource().getHolder() instanceof Player){
			
			Player p = (Player) e.getSource().getHolder();
			
			if (current.containsKey(p.getName())){
				
				Gui gui = current.get(p.getName());
				
				if (e.getSource().getHolder().getInventory().getName().equals(AS(gui.title))){
					
					e.setCancelled(true);
					main.sys.mouseMoved(p, gui, e.getItem(), e);
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInteract(InventoryInteractEvent e){
		
		if (e.getWhoClicked() instanceof Player){
			
			Player p = (Player) e.getWhoClicked();
			
			if (current.containsKey(p.getName())){
				
				Gui gui = current.get(p.getName());
				
				if (e.getInventory().getName().equals(AS(gui.title))){
					
					e.setCancelled(true);
					main.sys.mouseInteracted(p, gui, e);
					
				}
			}
		}
	}
	
}
