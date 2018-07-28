package com.karmorak.game.GameStates;

import com.karmorak.api.gamestate.GSM;

public class GameStateManager extends GSM {
	
	public static final short MAIN_SOUND_STATE = 1; 
	public static final short CONFIG_STATE = 2; 
	
	public GameStateManager() {
		super();
		AppStart as = new AppStart();
		Main_Sounds ms = new Main_Sounds();
		Settings_Screen ss = new Settings_Screen();
		
		
		GSM.addState(as, "App Start",(short) 0);
		GSM.addState(ms, "Main Sound", MAIN_SOUND_STATE);
		GSM.addState(ss, "Config Menu", CONFIG_STATE);
		
	}
	
	public static short init() {	
		
//		if(getStateInt() == 0 && getState() == null)  {
//			GSM.states.add(0, new AppStart());
//		} else if(getStateInt() == 1 && getState() == null) {
//			GSM.states.add(1, new Main_Sounds());
//		} else if(getStateInt() == 2 && getState() == null) {
//			GSM.states.add(2, new Settings_Screen());
//		}
		
		return currentstate;
	}
	
	
	

}
