package net.winneonsword.Collision.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.winneonsword.Collision.MoveableEntity;

import org.bukkit.Location;

public class Gameplay {
	
	private Location lobby;
	private List<Arena> arenas;
	private Map<String, MoveableEntity> entityMap = new HashMap<String, MoveableEntity>();
	
	public Gameplay(){
		
		lobby = null;
		arenas = new ArrayList<Arena>();
		
	}
	
	/**
	 * Gets the arena object with the specified name.
	 * @return The arena object if the arena list contains the specified name, otherwise null.
	 */
	public Arena getArena(String name){
		
		for (Arena arena : arenas){
			
			if (arena.getName().equalsIgnoreCase(name)){
				
				return arena;
				
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * Gets a list of arenas that have been previously created.
	 * @return A list of Arena objects.
	 */
	public List<Arena> getArenas(){
		
		return arenas;
		
	}
	
	/**
	 * Gets the location of the Collision lobby.
	 * @return The main Location object of the Collision lobby.
	 */
	public Location getLobby(){
		
		return lobby;
		
	}
	
	public User getPlayer(String name){
		
		for (Arena arena : arenas){
			
			for (User p : arena.getPlayers()){
				
				if (p.getName().equals(name)){
					
					return p;
					
				}
				
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * Checks to see if the arena list is empty.
	 * @return True if the arena list is NOT empty, otherwise false.
	 */
	public boolean isArenas(){
		
		return arenas.isEmpty() ? false : true;
		
	}
	
	/**
	 * Checks to see if the Collision lobby location is null.
	 * @return True if the lobby location is NOT null, otherwise false.
	 */
	public boolean isLobby(){
		
		return lobby == null ? false : true;
		
	}
	
	/**
	 * Checks to see if the arena list contains an arena<br />
	 * with the provided name.
	 * @return True if the arena list contains the arena, otherwise false.
	 */
	public boolean containsArena(String name){
		
		for (Arena arena : arenas){
			
			if (arena.getName().equalsIgnoreCase(name)){
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	/**
	 * Sets the arena list to a new list of Arena objects.
	 * @param list A List of Arena objects that represent the new arena list.
	 */
	public void setArenas(List<Arena> list){
		
		arenas = list;
		
	}
	
	/**
	 * Sets the Collision lobby to a new location.
	 * @param loc A Location object that will represent the new lobby.
	 */
	public void setLobby(Location loc){
		
		lobby = loc;
		
	}
	
	/**
	 * Adds a arena to the arena list.
	 * 
	 * @param arena The Arena object you wish to add to the arena list.
	 * @return True if the arena list doesn't contain the arena object, otherwise false. 
	 */
	public boolean addArena(Arena arena){
		
		for (Arena a : arenas){
			
			if (a.getName().equalsIgnoreCase(arena.getName())){
				
				return false;
				
			}
			
		}
		
		arenas.add(arena);
		
		return true;
		
	}
	
	/**
	 * Removed an arena from the arena list.
	 * 
	 *  @param arena The arena object you wish to remove from the arena list.
	 *  @return True if the arena list contains the arena object, otherwise false.
	 */
	public boolean removeArena(Arena arena){
		
		for (Arena a : arenas){
			
			if (a.getName().equalsIgnoreCase(arena.getName())){
				
				arenas.remove(arena);
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public MoveableEntity getMoveableEntity(String name){
		
		try {
			return entityMap.get(name);
		} catch (Exception e){
			return null;
		}
	}
	
	public void setMoveableEntity(String name, MoveableEntity e){
		entityMap.put(name, e);
	}
}