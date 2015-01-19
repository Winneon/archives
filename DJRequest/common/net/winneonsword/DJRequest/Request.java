package net.winneonsword.DJRequest;

public class Request {
	
	private String user;
	
	private String name;
	private String link;
	private int duration;
	
	public Request(String user, String name, String link, int duration){
		
		this.user = user;
		
		this.name = name;
		this.link = link;
		this.duration = duration;
		
	}
	
	public String getUser(){
		
		return user;
		
	}
	
	public String getSongName(){
		
		return name;
		
	}
	
	public String getSongLink(){
		
		return link;
		
	}
	
	public int getSongDuration(){
		
		return duration;
		
	}
	
	public static Request toRequest(String user, String request){
		
		try {
			
			String[] ss = request.split("%1%");
			Request req = new Request(user, ss[0], ss[1], Integer.parseInt(ss[2]));
			
			return req;
			
		} catch (Exception e){
			
			e.printStackTrace();
			return null;
			
		}
		
	}
	
	/**
	 * Returns a string representation of the request.<br />
	 * First the song name, then the song link, following with the song duration.
	 * 
	 * @return name, link, duration
	 */
	@Override
	public String toString(){
		
		return getSongName() + "%1%" + getSongLink() + "%1%" + getSongDuration();
		
	}
	
}
