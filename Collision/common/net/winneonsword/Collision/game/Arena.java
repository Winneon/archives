package net.winneonsword.Collision.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Arena {
	
	private String name;
	
	private Location loc;
	private List<User> users;
	
	/**
	 * Creates a new instance of the Arena class<br />
	 * with the name and location objects.
	 * 
	 * @param name The arena name.
	 * @param loc The arena's center location.
	 */
	public Arena(String name, Location loc){
		
		this.name = name;
		
		this.loc = loc;
		users = new ArrayList<User>();
		
	}
	
	/**
	 * Gets the name of the arena.
	 * @return The String object that represents the arena name.
	 */
	public String getName(){
		
		return name;
		
	}
	
	/**
	 * Gets the center location of the arena.
	 * @return The Location object that represents the center of the arena.
	 */
	public Location getLocation(){
		
		return loc;
		
	}
	
	public List<User> getPlayers(){
		
		return users;
		
	}
	
	/**
	 * Gets the x coordinate of the arena center location.
	 * @return The double variable that represents the x coordinate of the arena center location.
	 */
	public double getX(){
		
		return loc.getX();
		
	}
	
	/**
	 * Gets the y coordinate of the arena center location.
	 * @return The double variable that represents the y coordinate of the arena center location.
	 */
	public double getY(){
		
		return loc.getY();
		
	}
	
	/**
	 * Gets the z coordinate of the arena center location.
	 * @return The double variable that represents the z coordinate of the arena center location.
	 */
	public double getZ(){
		
		return loc.getZ();
		
	}
	
	/**
	 * Gets the World of the arena center location.
	 * @return The World object that represnts the world of the arena center location.
	 */
	public World getWorld(){
		
		return loc.getWorld();
		
	}
	
	public boolean isPlayers(){
		
		return users.isEmpty() ? false : true;
		
	}
	
	public boolean containsPlayer(String name){
		
		for (User player : users){
			
			if (player.getName().equals(name)){
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public boolean addPlayer(Player p){
		
		if (containsPlayer(p.getName())){
			
			return false;
			
		} else {
			
			User player = new User(p, this);
			users.add(player);
			
			return true;
			
		}
		
	}
	
	public boolean removePlayer(User user){
		
		if (users.contains(user)){
			
			users.remove(user);
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
}
