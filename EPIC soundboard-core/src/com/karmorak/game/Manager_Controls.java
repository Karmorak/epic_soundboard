package com.karmorak.game;

import com.badlogic.gdx.math.Vector2;
import com.karmorak.api.listeners.GestureAdapter;
import com.karmorak.game.GameStates.GameStateManager;
import com.karmorak.game.GameStates.Main_Sounds;
import com.karmorak.game.GameStates.Settings_Screen;

public class Manager_Controls implements GestureAdapter {

	

	
	@Override
	public boolean fling(float arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void keyDown(int arg0) {
		GameStateManager.keyDown(arg0);
	}
	//0,625 > 1920:1080 < 0,666
	
	@Override
	public boolean keyTyped(char c) {
		if(c == 'l' && GameStateManager.getState() instanceof Settings_Screen) {
			Settings_Screen.exit();
		}
		return false;
	}
	
	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float arg0, float arg1, float arg2, float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float arg0, float arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 arg0, Vector2 arg1, Vector2 arg2, Vector2 arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pinchStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean scrolled(int s) {
		if(s > 0)  {
			if(Main_Sounds.s.getValue()-0.1f >= 0)
				Main_Sounds.s.setSlider((Main_Sounds.s.getValue() - 0.1f));
			else 
				Main_Sounds.s.setSlider(0);
		} else {
			if(Main_Sounds.s.getValue()+0.1f <= 1)
				Main_Sounds.s.setSlider((Main_Sounds.s.getValue() + 0.1f));
			else 
				Main_Sounds.s.setSlider(1f);
		}
		
		
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
//		System.out.println("test");
//		GameStateManager.tap(x, y, count, button);
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int count, int button) {
		//System.out.println("test");
		GameStateManager.tap(x, y, count, button);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		GameStateManager.touchDragged(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void keyNativeDown(int keycode) {
		Main_Sounds.NativeKey(keycode);
	}

//	@Override
//	public void nativeKeyPressed(NativeKeyEvent e) {
//    	if(e.getKeyCode() == NativeKeyEvent.VC_K) {
//    		Main_Sounds.buttons.get(0).play();
//    	}	}
//
//	@Override
//	public void nativeKeyReleased(NativeKeyEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void nativeKeyTyped(NativeKeyEvent arg0) {
//		// TODO Auto-generated method stub		
//	}

	
	
	
}