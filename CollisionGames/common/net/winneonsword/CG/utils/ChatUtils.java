package net.winneonsword.CG.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.winneonsword.CG.CG;

public class ChatUtils {
	
	public static String CG;
	
	public ChatUtils(CG main){
		
		this.CG = "&dCG &5// &d";
		
	}
	
	public static void s(Player p, String message){
		
		p.sendMessage(AS(CG + message));
		
	}
	
	public static void s(Player p, String[] message){
		
		p.sendMessage(AS(message));
		
	}
	
	public static void blankS(Player p, String message){
		
		p.sendMessage(message);
		
	}
	
	public static void b(String message){
		
		Bukkit.broadcastMessage(AS(CG + message));
		
	}
	
	public static void blankB(String message){
		
		Bukkit.broadcastMessage(message);
		
	}
	
	public static String AS(String message){
		
		if (message == null){
			
			return null;
			
		} else {
			
			return message = ChatColor.translateAlternateColorCodes('&', message);
			
		}
		
	}
	
	public static String[] AS(String[] message){
		
		for (int i = 0; i < message.length; i++)
			
			message[i] = AS(message[i]);
		
		return message;
		
	}	
	
}
