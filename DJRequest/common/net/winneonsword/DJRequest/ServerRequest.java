package net.winneonsword.DJRequest;

public enum ServerRequest {
	
	CUSTOM("custom"),
	UPDATE("update"),
	SEND_SONG("sendSong"),
	LOGIN("login"),
	REFRESH("refresh"),
	STAFF_VETO_SONG("staffVetoSong"),
	STAFF_VETO_QUEUE("staffVetoQueue"),
	DISPOSE("dispose"),
	NOW_PLAYING("nowPlaying"),
	DROP_SONG("dropSong");
	
	public String request;
	
	ServerRequest(String request){
		
		this.request = request;
		
	}
	
	public String go(){
		
		return request;
		
	}
	
}
