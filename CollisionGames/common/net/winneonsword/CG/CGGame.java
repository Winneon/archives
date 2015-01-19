package net.winneonsword.CG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import net.winneonsword.CG.exceptions.NoSuchArenaException;

public class CGGame {
	
	private CG main;
	
	private List<String> players;
	private HashMap<String, CGArena> arenas;
	
	public CGGame(CG main){
		
		this.main = main;
		
		this.players = new ArrayList<String>();
		this.arenas = new HashMap<String, CGArena>();
		
	}
	
	public List<String> getPlayers(){
		
		return players;
		
	}
	
	public List<CGArena> getArenas(){
		
		List<CGArena> list = new ArrayList<CGArena>();
		
		for (String name : arenas.keySet()){
			
			CGArena arena = arenas.get(name);
			list.add(arena);
			
		}
		
		return list;
		
	}
	
	public CGArena getArena(String name){
		
		if (arenas.containsKey(name)){
			
			return arenas.get(name);
			
		} else {
			
			try {
				
				throw new NoSuchArenaException(name);
				
			} catch (Exception e){
				
				// Nothing.
				
			}
			
			return null;
			
		}
		
	}
	
	public void setArenas(List<String> list){
		
		for (String l : list){
			
			World world = Bukkit.getWorld(main.datacore.getString("arenas." + l + ".world"));
			int x = main.datacore.getInt("arenas." + l + ".x");
			int y = main.datacore.getInt("arenas." + l + ".y");
			int z = main.datacore.getInt("arenas." + l + ".z");
			int r = main.datacore.getInt("arenas." + l + ".r");
			
			Location loc = new Location(world, x, y, z);
			addArena(l, loc, r);
			
		}
		
	}
	
	public boolean addPlayer(String player){
		
		if (players.contains(player)){
			
			return false;
			
		} else {
			
			players.add(player);
			
			return true;
			
		}
		
	}
	
	public boolean addArena(String name, Location loc, int radius){
		
		if (arenas.containsKey(name)){
			
			return false;
			
		} else {
			
			CGArena arena = new CGArena(name, loc, radius);
			arenas.put(name, arena);
			
			return true;
			
		}
		
	}
	
	public boolean removePlayer(String player){
		
		if (players.contains(player)){
			
			players.remove(player);
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public boolean removeArena(String name){
		
		if (arenas.containsKey(name)){
			
			arenas.remove(name);
			
			return true;
			
		} else {
			
			try {
				
				throw new NoSuchArenaException(name);
				
			} catch (Exception e){
				
				// Nothing.
				
			}
			
			return false;
			
		}
		
	}
	
}
