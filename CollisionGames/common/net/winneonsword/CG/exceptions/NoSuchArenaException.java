package net.winneonsword.CG.exceptions;

import org.bukkit.Bukkit;

public class NoSuchArenaException extends Exception {

	private static final long serialVersionUID = -6803060626714688826L;
	
	public NoSuchArenaException(String name){
		
		Bukkit.getLogger().severe("No such arena for the name '" + name + "'!");
		
	}

}
