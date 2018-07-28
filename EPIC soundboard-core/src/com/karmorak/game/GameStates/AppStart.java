package com.karmorak.game.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karmorak.api.funktions.GameFunktions;
import com.karmorak.api.gamestate.GameState;

class AppStart implements GameState {

	private Sprite java, s2, gdx;
	
	
	private static final float STAY_TIME= 0.4f, FADE_STRENGTH = 0.18f;
	private float ts_lastfade;
	
	private float state, alpha;
	
	@Override
	public void dispose() {
		
	}
	@Override
	public void init() {
		System.out.println("init Gamestate 0");
		
		java = new Sprite(new Texture(Gdx.files.internal("assets//javalogo.png")));
		java.setScale(0.35f*GameFunktions.getScaleMulti((short) 1));
		java.setPosition((Gdx.graphics.getWidth() - java.getWidth())*0.5f, (Gdx.graphics.getHeight() - java.getHeight())*0.5f);
		java.setAlpha(0f);
		
		gdx = new Sprite(new Texture(Gdx.files.internal("assets//gdxlogo_transparent.png")));
		gdx.setScale(0.55f*GameFunktions.getScaleMulti((short) 1));
		gdx.setPosition((Gdx.graphics.getWidth() - gdx.getWidth())*0.5f, (Gdx.graphics.getHeight() - gdx.getHeight())*0.5f);
		gdx.setAlpha(0f);
		
		Pixmap pix = new Pixmap(1, 1, Format.RGBA8888);
		pix.setColor(Color.WHITE);
		pix.fill();
		
		s2 = new Sprite(new Texture(pix));		
		s2.setPosition(0, 0);
		s2.setSize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		pix.dispose();
		
	}
	
	@Override
	public void update() {
		if(state == 0) {
			if(ts_lastfade >= 0.1) {
				alpha += FADE_STRENGTH;
				if(alpha>= 1) {
					java.setAlpha(1f);
					state = 1;
					alpha = 1f;
				} else {
					java.setAlpha(alpha);						
				}
				ts_lastfade = 0;
			}
			ts_lastfade += Gdx.graphics.getDeltaTime();
		} else if (state == 1) {
			if(ts_lastfade >= STAY_TIME) {
				state = 2;
				ts_lastfade = 0;
			} else ts_lastfade += Gdx.graphics.getDeltaTime();
		} else if(state == 2) {
			if(ts_lastfade >= 0.1) {
				alpha -= FADE_STRENGTH;
				if(alpha<= 0) {
					java.setAlpha(0f);
					state = 3;
					alpha = 0f;
				} else {
					java.setAlpha(alpha);						
				}
				ts_lastfade = 0;
			}
			ts_lastfade += Gdx.graphics.getDeltaTime();
		} else if(state == 3) {
			if(ts_lastfade >= 0.1) {
				alpha += FADE_STRENGTH;
				if(alpha>= 1) {
					gdx.setAlpha(1f);
					state = 4;
					alpha = 1f;
					GameStateManager.init(1);
				} else {
					gdx.setAlpha(alpha);						
				}
				ts_lastfade = 0;
			}
			ts_lastfade += Gdx.graphics.getDeltaTime();
		} else if (state == 4) {
			if(ts_lastfade >= STAY_TIME) {
				state = 5;
				ts_lastfade = 0;
			} else ts_lastfade += Gdx.graphics.getDeltaTime();
		} else if(state == 5) {
			if(ts_lastfade >= 0.1) {
				alpha -= FADE_STRENGTH;
				if(alpha<= 0) {
					gdx.setAlpha(0f);
					s2.setAlpha(0f);
					state = 6;
					alpha = 0f;
				} else {
					gdx.setAlpha(alpha);
					s2.setAlpha(alpha);
					
				}
				ts_lastfade = 0;
			}
			ts_lastfade += Gdx.graphics.getDeltaTime();
		} else if(state == 6) {
			s2 = null;
			java = null;
			gdx = null;
			state = 0;
			alpha = 0;
			ts_lastfade = 0;
			GameStateManager.changestate((short)1);
		}
		
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		s2.draw(batch);
		if(state < 3) java.draw(batch);
		if(state >= 3) gdx.draw(batch);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyDown(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void scrolled(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void tap(float arg0, float arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}



}
