package net.winneonsword.CG.listeners;

import net.winneonsword.CG.CG;
import net.winneonsword.CG.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener extends Utils implements Listener {
	
	public JoinListener(CG main){
		
		super(main);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		
		if (!(main.sys.getUsers().contains(p.getName()))){
			
			main.sys.setupPlayer(p.getName(), true);
			
		}
		
	}
	
}
