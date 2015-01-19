package net.winneonsword.puush;

import com.melloware.jintellitype.HotkeyListener;

public class HotKey implements HotkeyListener {
	
	public void onHotKey(int id){
		
		switch (id){
		
		case 1:
			
			Capture.desktop();
			break;
			
		case 2:
			
			Capture.window();
			break;
			
		case 3:
			
			Capture.selection();
			break;
			
		}
		
	}
	
}
