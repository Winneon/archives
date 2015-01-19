package net.winneonsword.CG;

public class CGPlayer {
	
	private String name;
	private boolean inter;
	
	public CGPlayer(String name){
		
		this.name = name;
		
	}
	
	public String getName(){
		
		return name;
		
	}
	
	public boolean getInterface(){
		
		return inter;
		
	}
	
	public void setInterface(boolean inter){
		
		this.inter = inter;
		
	}
	
}
