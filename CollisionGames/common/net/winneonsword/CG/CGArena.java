package net.winneonsword.CG;

import org.bukkit.Location;
import org.bukkit.World;

public class CGArena {
	
	private String name;
	
	private Location loc;
	private int radius;
	
	public CGArena(String name, Location loc, int radius){
		
		this.name = name;
		
		this.loc = loc;
		this.radius = radius;
		
	}
	
	public CGArena(String name, World world, int x, int y, int z, int radius){
		
		this.name = name;
		this.loc = new Location(world, x, y, z);
		
	}
	
	public String getName(){
		
		return name;
		
	}
	
	public Location getLocation(){
		
		return loc;
		
	}
	
	public int getX(){
		
		return loc.getBlockX();
		
	}
	
	public int getY(){
		
		return loc.getBlockY();
		
	}
	
	public int getZ(){
		
		return loc.getBlockZ();
		
	}
	
	public World getWorld(){
		
		return loc.getWorld();
		
	}
	
	public int getRadius(){
		
		return radius;
		
	}
	
	public void setLocation(Location loc){
		
		this.loc = loc;
		
	}
	
	public void setLocation(World world, int x, int y, int z){
		
		loc = new Location(world, x, y, z);
		
	}
	
	public void setX(int x){
		
		loc = new Location(getWorld(), x, getY(), getZ());
		
	}
	
	public void setY(int y){
		
		loc = new Location(getWorld(), getX(), y, getZ());
		
	}
	
	public void setZ(int z){
		
		loc = new Location(getWorld(), getX(), getY(), z);
		
	}
	
	public void setWorld(World world){
		
		loc = new Location(world, getX(), getY(), getZ());
		
	}
	
	public void setRadius(int radius){
		
		this.radius = radius;
		
	}
	
}
