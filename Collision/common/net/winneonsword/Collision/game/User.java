package net.winneonsword.Collision.game;

import org.bukkit.entity.Player;

public class User {
	
	private Player p;
	private Arena arena;
	
	public User(Player p, Arena arena){
		
		this.p = p;
		this.arena = arena;
		
	}
	
	public Player getPlayer(){
		
		return p;
		
	}
	
	public String getName(){
		
		return p.getName();
		
	}
	
	public Arena getArena(){
		
		return arena;
		
	}
	
}
