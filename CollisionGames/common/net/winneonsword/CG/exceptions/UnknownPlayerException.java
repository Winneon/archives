package net.winneonsword.CG.exceptions;

public class UnknownPlayerException extends Exception {
	
	private static final long serialVersionUID = -3959984337553508499L;

	public UnknownPlayerException(String name){
		
		super(name);
		
	}
	
}
