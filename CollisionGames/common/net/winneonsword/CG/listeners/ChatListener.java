package net.winneonsword.CG.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	
	public HashMap<String, Runnable> chatInput;
	private HashMap<String, String> input;
	
	public ChatListener(){
		
		this.chatInput = new HashMap<String, Runnable>();
		this.input = new HashMap<String, String>();
		
	}
	
	@EventHandler
	public void onTheChat(AsyncPlayerChatEvent e){
		
		Player p = e.getPlayer();
		
		if (chatInput.containsKey(p.getName())){
			
			if (input.containsKey(p.getName())){
				
				input.remove(p.getName());
				
			}
			
			input.put(p.getName(), e.getMessage());	
			Runnable run = chatInput.get(p.getName());
			
			run.run();
			chatInput.remove(p.getName());
			
			e.setCancelled(true);
			
		}
		
	}
	
	public String getInput(Player p){
		
		if (input.containsKey(p.getName())){
			
			return input.get(p.getName());
			
		} else {
			
			Bukkit.getLogger().severe("The player '" + p.getName() + "' has no input!");
			
			return null;
			
		}
		
	}
	
}
