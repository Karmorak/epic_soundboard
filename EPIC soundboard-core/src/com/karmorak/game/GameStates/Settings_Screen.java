package com.karmorak.game.GameStates;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karmorak.api.files.FileManager;
import com.karmorak.api.Resolutions;
import com.karmorak.api.Vector2;
import com.karmorak.api.button.Button;
import com.karmorak.api.button.Scrolable;
import com.karmorak.api.gamestate.GameState;
import com.karmorak.game.ButtonRegisters;
import com.karmorak.game.SoundButton;

import static com.karmorak.game.Soundboard.configpath;

public class Settings_Screen implements GameState {
	
	/* laustaerke
	 * random collor
	 * aufloesung
	 * antitilising
	 * 
	 * 
	 * 
	 */
	
	public static final String 
		VOLUME = "vol", 
		BG_PLATES = "bg_plates", 
		DISCORD = "discord_token", 
		DISCORD_VOICE = "discord_voice_id",
		ONE_SOUND = "one_sound",
		RANDOM_COLORS = "random_colors",
		MUTE_BOT = "mute_bot";
	
	public static final float UI_X = 0.3f, UI_X2 = 0.6f;
	
	private static Button X, volume, resolution, aspect_ratio, 
	random_colors, rand_colors_2,
	fullscreen, fullscreen_2,  	
	bg_plates, bg_plates_2,
	discord_token, discord_token2,
	discord_voice_channel, discord_voice_channel2,
	play_one, play_one2,
	mute_bot, mute_bot2;
	
	private static Scrolable volume_2;
	private static Scrolable resolution2, aspect_ratio2;
	private static int reso, ratio;
	
	private static SoundButton dummy;
	private static float cur_dummy_sound_time = 1f;
	private static final float DUMMY_SOUND_TIME = 1f;
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void init() {
		
		float vol;
		try {
			vol = Float.parseFloat(FileManager.getString(configpath, VOLUME));
		} catch (NullPointerException e) {
			FileManager.setString(configpath, VOLUME,""+ 0.1);
			vol = 0.1f;
			e.printStackTrace();
		} catch (NumberFormatException e) {
			FileManager.setString(configpath, VOLUME,""+ 0.1);
			vol = 0.1f;
			e.printStackTrace();
		}
				
		String scr = "false";		
		try {
			String s  = FileManager.getString(configpath,"screen");		
			if(s.contains("1")) scr = "true";
		} catch (NullPointerException e) {
			e.printStackTrace();
		}	
		
		String show_bg_plates = "true";		
		try {
			String s  = FileManager.getString(configpath, BG_PLATES);		
			if(s.contains("0")) show_bg_plates = "false";
		} catch (NullPointerException e) {
			FileManager.setString(configpath, BG_PLATES,"1");
			e.printStackTrace();
		}
		
		String one_sound = "false";		
		try {
			String s  = FileManager.getString(configpath, ONE_SOUND);		
			if(s.contains("1")) show_bg_plates = "true";
		} catch (NullPointerException e) {
			FileManager.setString(configpath, ONE_SOUND,"0");
			e.printStackTrace();
		}
		
		String mute_bot_str = "false";		
		try {
			String s  = FileManager.getString(configpath, MUTE_BOT);		
			if(s.contains("1")) show_bg_plates = "true";
		} catch (NullPointerException e) {
			FileManager.setString(configpath, MUTE_BOT,"0");
			e.printStackTrace();
		}
		
		String random_colors_str = "true";		
		try {
			String s  = FileManager.getString(configpath, RANDOM_COLORS);		
			if(s.contains("0")) random_colors_str = "false";
		} catch (NullPointerException e) {
			FileManager.setString(configpath, RANDOM_COLORS,"0");
			e.printStackTrace();
		}
		
		String c = FileManager.getString(configpath, "ratio");
		ratio =Integer.parseInt(c);		
		String asp = Resolutions.aspectratios.get(ratio);
		
		
		c = FileManager.getString(configpath, "res");
		reso = Integer.parseInt(c);
		String res = Resolutions.resolutions.get(ratio).get(reso);	
		
		float f = (float)((float)(reso+1)/(float)Resolutions.resolutions.get(ratio).size());	
		f += (1f/(float)Resolutions.resolutions.get(ratio).size())*0.5f;		
		
		float f2 = ((float) (ratio+1) / (float)(Resolutions.aspectratios.size()));
		f2 += (1f/(float)Resolutions.aspectratios.size())*0.5f;		
		
		dummy = new SoundButton(vol, "assets//sounds//spsw_fantasie.ogg", "Wonderfull Dummy Button", null);
		dummy.showBackground(Boolean.parseBoolean(show_bg_plates));
		dummy.setPosition(Gdx.graphics.getWidth() * (UI_X*0.5f) - dummy.getTexture().getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f);
		
		X = new Button("X");
		X.setPosition(0, Gdx.graphics.getHeight()- X.getheight());
//		X.setSelectable(false);
		
		
		float UI_Y_ABS = 10; 
		Vector2 UI_bounds;
		Vector2 UI_pos2;	
		
		volume_2 = new Scrolable(new Button(""+vol),false, 300);
		volume_2.setPosition(Gdx.graphics.getWidth() * UI_X2 - volume_2.getBGwidth() * 0.5f, Gdx.graphics.getHeight() - volume_2.getBGheight() - UI_Y_ABS);
		volume_2.setSlider(vol);
		volume_2.setName(""+vol);
		
		volume = new Button("Lautstaerke:");
		volume.setPosition(Gdx.graphics.getWidth() * UI_X - volume.getwidth() * 0.5f, Gdx.graphics.getHeight() - (volume_2.getBGheight() - volume.getheight()) *0.5f -volume.getheight() - UI_Y_ABS);
		volume.setInteractable(false);	
		
		UI_bounds = new Vector2(volume_2.getBGwidth(), volume_2.getBGheight());
		//UI_pos = new Vector2(volume.getPosition().getX(), 0);
		UI_pos2 = new Vector2(volume_2.getBGPos().getX(), 0);
		
		random_colors = new Button("Random Colors:");
		random_colors.setPosition(Gdx.graphics.getWidth() * UI_X - random_colors.getwidth() * 0.5f, volume_2.getPosition().getY() - UI_bounds.getHeight());
		random_colors.setInteractable(false);
		
		rand_colors_2 = new Button(random_colors_str);
		rand_colors_2.setPosition(volume_2.getBGPos().getX() + (UI_bounds.getWidth() - rand_colors_2.getwidth()) * 0.5f, random_colors.getPosition().getY());
		rand_colors_2.setInteractable(false);
		rand_colors_2.addHang("<");		
		rand_colors_2.setHangLeft(0);
		rand_colors_2.getHang(0).setSelectColor(Color.WHITE);
		rand_colors_2.addHang(">");
		rand_colors_2.setHangRight(1);
		rand_colors_2.getHang(1).setSelectColor(Color.WHITE);
		
		resolution = new Button("Resolution:");
		resolution.setPosition(Gdx.graphics.getWidth() * UI_X - resolution.getwidth() * 0.5f, rand_colors_2.getPosition().getY() - UI_bounds.getHeight() - UI_Y_ABS);
		resolution.setInteractable(false);		
		
		resolution2 = new Scrolable(new Button("0"),false, 300);
		resolution2.dochangeName(false);
		resolution2.setName(res);
		resolution2.setPosition(Gdx.graphics.getWidth() * UI_X2 - resolution2.getBGwidth() * 0.5f, resolution.getPosition().getY());
		resolution2.setSlider(f);
		
		aspect_ratio = new Button("Seitenverhaeltnis:");
		aspect_ratio.setPosition(Gdx.graphics.getWidth() * UI_X - aspect_ratio.getwidth() * 0.5f, resolution.getPosition().getY() - UI_bounds.getHeight() - UI_Y_ABS);
		aspect_ratio.setInteractable(false);
		
		aspect_ratio2 = new Scrolable(new Button("0"),false, 300);
		aspect_ratio2.setPosition(Gdx.graphics.getWidth() * UI_X2 - aspect_ratio2.getBGwidth() * 0.5f, aspect_ratio.getPosition().getY());
		aspect_ratio2.dochangeName(false);
		aspect_ratio2.setName(asp);
		aspect_ratio2.setSlider(f2);
		
		fullscreen = new Button("Fullscreen:");
		fullscreen.setPosition(Gdx.graphics.getWidth() * UI_X - fullscreen.getwidth() * 0.5f, aspect_ratio.getPosition().getY() - UI_bounds.getHeight());
		fullscreen.setInteractable(false);		
		
		fullscreen_2 = new Button(scr); 
		fullscreen_2.setPosition(UI_pos2.getX() + (volume_2.getBGwidth() - fullscreen_2.getwidth()) * 0.5f, fullscreen.getPosition().getY());
		fullscreen_2.addHang("<");
		fullscreen_2.addHang(">");
		fullscreen_2.setHangLeft(0);
		fullscreen_2.setHangRight(1);
		fullscreen_2.setInteractable(false);	
		
		bg_plates = new Button("Show Background Plates:");
		bg_plates.setPosition(Gdx.graphics.getWidth() * UI_X - bg_plates.getwidth() * 0.5f, fullscreen.getPosition().getY() - UI_bounds.getHeight());
		bg_plates.setInteractable(false);
		
		bg_plates_2 = new Button(show_bg_plates); 
		bg_plates_2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - bg_plates_2.getwidth()) * 0.5f, bg_plates.getPosition().getY());
		bg_plates_2.addHang("<");
		bg_plates_2.addHang(">");
		bg_plates_2.setHangLeft(0);
		bg_plates_2.setHangRight(1);
		bg_plates_2.setInteractable(false);		
		
		discord_token = new Button("Discord-Bot Token:");
		discord_token.setPosition(Gdx.graphics.getWidth() * UI_X - discord_token.getwidth() * 0.5f, bg_plates.getPosition().getY() - UI_bounds.getHeight());
		discord_token.setInteractable(false);
		
		discord_token2 = new Button("> ");
		discord_token2.setPosition(UI_pos2.getX(), discord_token.getPosition().getY());
		discord_token2.addHang("B");
		discord_token2.setHangRight(0);
		discord_token2.getHang(0).setMaxTextWidth(UI_bounds.getWidth()*2);
		discord_token2.setWritable(0, 59, 0);
		
		discord_voice_channel = new Button("Auto Join Voice Channel");
		discord_voice_channel.setPosition(Gdx.graphics.getWidth() * UI_X - discord_voice_channel.getwidth() * 0.5f, discord_token.getPosition().getY() - UI_bounds.getHeight());
		discord_voice_channel.setInteractable(false);
		
		discord_voice_channel2 = new Button("> ");
		discord_voice_channel2.setPosition(UI_pos2.getX(), discord_voice_channel.getPosition().getY());
		discord_voice_channel2.addHang("B");
		discord_voice_channel2.setHangRight(0);
		discord_voice_channel2.getHang(0).setMaxTextWidth(UI_bounds.getWidth()*2);
		discord_voice_channel2.setWritable(0, 18, 0);
		
		play_one = new Button("Play only 1 Button:");
		play_one.setPosition(Gdx.graphics.getWidth() * UI_X - play_one.getwidth() * 0.5f, discord_voice_channel.getPosition().getY() - UI_bounds.getHeight());
		play_one.setInteractable(false);
		
		play_one2 = new Button(one_sound); 
		play_one2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - play_one2.getwidth()) * 0.5f, play_one.getPosition().getY());
		play_one2.addHang("<");
		play_one2.addHang(">");
		play_one2.setHangLeft(0);
		play_one2.setHangRight(1);
		play_one2.setInteractable(false);	
		
		mute_bot = new Button("Mute Bot for you:");
		mute_bot.setPosition(Gdx.graphics.getWidth() * UI_X - mute_bot.getwidth() * 0.5f, play_one.getPosition().getY() - UI_bounds.getHeight());
		mute_bot.setInteractable(false);
		
		mute_bot2 = new Button(mute_bot_str); 
		mute_bot2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - mute_bot2.getwidth()) * 0.5f, mute_bot.getPosition().getY());
		mute_bot2.addHang("<");
		mute_bot2.addHang(">");
		mute_bot2.setHangLeft(0);
		mute_bot2.setHangRight(1);
		mute_bot2.setInteractable(false);	
		
		
	}
	
	@Override
	public void update() {
		cur_dummy_sound_time += Gdx.graphics.getDeltaTime();
		
		
		for(SoundButton b : Main_Sounds.buttons) {
			b.setVolume(getVolume());
		}
				
		ratio = (int)((Resolutions.aspectratios.size()-1) * aspect_ratio2.getValue());
		if(ratio > Resolutions.aspectratios.size()) ratio = Resolutions.aspectratios.size()-1;
		else if (ratio < 0) ratio = 0;		
		aspect_ratio2.setName(Resolutions.aspectratios.get(ratio));
		
		reso = (int)((Resolutions.resolutions.get(ratio).size()-1) * resolution2.getValue());
		if(reso > Resolutions.resolutions.get(ratio).size()) reso = Resolutions.resolutions.get(ratio).size()-1;
		else if (reso < 0) reso = 0;		
		resolution2.setName(Resolutions.resolutions.get(ratio).get(reso));
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		volume.draw(batch);
		volume_2.draw(batch);		

		random_colors.draw(batch);
		rand_colors_2.draw(batch);
		
		resolution.draw(batch);
		resolution2.draw(batch);
		
		aspect_ratio.draw(batch);
		aspect_ratio2.draw(batch);
		
		fullscreen.draw(batch);
		fullscreen_2.draw(batch);
		
		
		bg_plates.draw(batch);
		bg_plates_2.draw(batch);
		
		discord_token.draw(batch);
		discord_token2.draw(batch);
		
		discord_voice_channel.draw(batch);
		discord_voice_channel2.draw(batch);
		
		play_one.draw(batch);
		play_one2.draw(batch);
		
		mute_bot.draw(batch);
		mute_bot2.draw(batch);
		
		X.draw(batch);
		
		dummy.draw(batch);
		
		strg = false;
		v = false;
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
	public void tap(float x, float y, int count, int button) {
		if(rand_colors_2.getHang(0).isSelected()) {
			if(rand_colors_2.getName()=="true") {
				rand_colors_2.setName("false");
				SoundButton.RANDOM_COLORS = false;
			} else if(rand_colors_2.getName()=="false") {
				rand_colors_2.setName("true");
				SoundButton.RANDOM_COLORS = true;
			}
			rand_colors_2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - rand_colors_2.getwidth()) * 0.5f, random_colors.getPosition().getY());
			rand_colors_2.setHangRight(1);
		} else if(rand_colors_2.getHang(1).isSelected()) {
			if(rand_colors_2.getName()=="true") {
				rand_colors_2.setName("false");
				SoundButton.RANDOM_COLORS = false;
			} else if(rand_colors_2.getName()=="false") {
				rand_colors_2.setName("true");
				SoundButton.RANDOM_COLORS = true;
			}
			rand_colors_2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - rand_colors_2.getwidth()) * 0.5f, random_colors.getPosition().getY());
			rand_colors_2.setHangRight(1);
		} else if(fullscreen_2.getHang(0).isSelected()) {
			if(fullscreen_2.getName()=="true") {
				fullscreen_2.setName("false");
			} else if(fullscreen_2.getName()=="false") {
				fullscreen_2.setName("true");
			}
			fullscreen_2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - fullscreen_2.getwidth()) * 0.5f, fullscreen.getPosition().getY());
			fullscreen_2.setHangRight(1);
		} else if(fullscreen_2.getHang(1).isSelected()) {
			if(fullscreen_2.getName()=="true") {
				fullscreen_2.setName("false");
			} else if(fullscreen_2.getName()=="false") {
				fullscreen_2.setName("true");
			}
			fullscreen_2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - fullscreen_2.getwidth()) * 0.5f, fullscreen.getPosition().getY());
			fullscreen_2.setHangRight(1);
			
		} else if(bg_plates_2.getHang(0).isSelected()) {
			if(bg_plates_2.getName()=="true") {
				bg_plates_2.setName("false");
				dummy.showBackground(false);
			} else if(bg_plates_2.getName()=="false") {
				bg_plates_2.setName("true");
				dummy.showBackground(true);
			}
			bg_plates_2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - bg_plates_2.getwidth()) * 0.5f, bg_plates.getPosition().getY());
			bg_plates_2.setHangRight(1);
		} else if(bg_plates_2.getHang(1).isSelected()) {
			if(bg_plates_2.getName()=="true") {
				bg_plates_2.setName("false");
				dummy.showBackground(false);
			} else if(bg_plates_2.getName()=="false") {
				bg_plates_2.setName("true");
				dummy.showBackground(true);
			}
			bg_plates_2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - bg_plates_2.getwidth()) * 0.5f, bg_plates.getPosition().getY());
			bg_plates_2.setHangRight(1);
		} else if(play_one2.getHang(0).isSelected()) {
			if(play_one2.getName()=="true") {
				play_one2.setName("false");
				SoundButton.ONE_SOUND = false;
			} else if(play_one2.getName()=="false") {
				play_one2.setName("true");
				SoundButton.ONE_SOUND = true;
			}
			play_one2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - play_one2.getwidth()) * 0.5f, play_one.getPosition().getY());
			play_one2.setHangRight(1);
		} else if(play_one2.getHang(1).isSelected()) {
			if(play_one2.getName()=="true") {
				play_one2.setName("false");
				SoundButton.ONE_SOUND = false;
			} else if(play_one2.getName()=="false") {
				play_one2.setName("true");
				SoundButton.ONE_SOUND = true;
			}
			play_one2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - play_one2.getwidth()) * 0.5f, play_one.getPosition().getY());
			play_one2.setHangRight(1);
		}else if(mute_bot2.getHang(0).isSelected()) {
			if(mute_bot2.getName()=="true") {
				mute_bot2.setName("false");
				SoundButton.MUTE_BOT = false;
			} else if(mute_bot2.getName()=="false") {
				mute_bot2.setName("true");
				SoundButton.MUTE_BOT = true;
			}
			mute_bot2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - mute_bot2.getwidth()) * 0.5f, mute_bot.getPosition().getY());
			mute_bot2.setHangRight(1);
		} else if(mute_bot2.getHang(1).isSelected()) {
			if(mute_bot2.getName()=="true") {
				mute_bot2.setName("false");
				SoundButton.MUTE_BOT = false;
			} else if(mute_bot2.getName()=="false") {
				mute_bot2.setName("true");
				SoundButton.MUTE_BOT = true;
			}
			mute_bot2.setPosition(volume_2.getBGPos().getX() + (volume_2.getBGwidth() - mute_bot2.getwidth()) * 0.5f, mute_bot.getPosition().getY());
			mute_bot2.setHangRight(1);
		}else if (X.isSelected()) {	
			if(bg_plates_2.getName() != ""+SoundButton.bg) {
				boolean bool = Boolean.parseBoolean(bg_plates_2.getName());
				for(SoundButton b : Main_Sounds.buttons) {
					b.showBackground(bool);
				}
			}		

			exit();
		}
		dummy.tap(x, y, count, button);
	}

	@Override
	public void touchDragged(int screenX, int screenY, int pointer) {
		for(SoundButton b : ButtonRegisters.buttons) {
			b.setVolume(getVolume());
		}
		if(cur_dummy_sound_time >= DUMMY_SOUND_TIME) {
			ButtonRegisters.buttons.get(0).play();
			cur_dummy_sound_time = 0;
		}
		
	}

	public static void exit() {
		for(SoundButton b : ButtonRegisters.buttons) {
			b.setVolume(Settings_Screen.getVolume());
		}
		FileManager.setString(configpath, VOLUME,""+ Settings_Screen.getVolume());
		
		int ratio = (int)((Resolutions.aspectratios.size()) * Settings_Screen.getRatio());
		ratio--;
		int res = (int)((Resolutions.resolutions.get((int) ratio).size()) * Settings_Screen.getReso());
		res--;
		
		FileManager.setString(configpath, "ratio",""+ (int)ratio);
		FileManager.setString(configpath,"res",""+ (int)res );
		
		String str = Settings_Screen.getScreen();
		if(str == "true") {
			FileManager.setString(configpath, "screen", "1");
		} else {
			FileManager.setString(configpath, "screen", "0");
		}		
		
		str = getBGPlates();
		if(str == "true") {
			FileManager.setString(configpath, BG_PLATES, "1");
		} else {
			FileManager.setString(configpath, BG_PLATES, "0");
		}
		
		str = getOneSound();
		if(str == "true") {
			FileManager.setString(configpath, ONE_SOUND, "1");
		} else {
			FileManager.setString(configpath, ONE_SOUND, "0");
		}
		
		str = getMuteBot();
		if(str == "true") {
			FileManager.setString(configpath, MUTE_BOT, "1");
		} else {
			FileManager.setString(configpath, MUTE_BOT, "0");
		}

		str = getRandomColors();
		if(str == "true") {
			FileManager.setString(configpath, RANDOM_COLORS, "1");
		} else {
			FileManager.setString(configpath, RANDOM_COLORS, "0");
		}
		
		if(!discord_token2.getHang(0).getName().contains("_")) {
			String[] names = discord_token2.getHang(0).getNames();
			
			String name = "";
			for(String s : names) {
				name = name + s;
			}
			name = name.replace(">", "").replace(" ", "").replace("-", "");
			
			FileManager.setString(configpath, DISCORD, name);
		}
		
		if(!discord_voice_channel2.getHang(0).getName().contains("_")) {
			String[] names = discord_voice_channel2.getHang(0).getNames();
			
			String name = "";
			for(String s : names) {
				name = name + s;
			}
			name = name.replace(">", "").replace(" ", "").replace("-", "");
			
			FileManager.setString(configpath, DISCORD_VOICE, name);
		}
		
		Main_Sounds.show(true);
		GameStateManager.changestate((short)1);
	}
	
	
	public static float getVolume() {
		return volume_2.getValue();		
	}
	
	public static float getReso() {
		return resolution2.getValue();		
	}
	
	public static float getRatio() {
		return aspect_ratio2.getValue();		
	}
	
	public static String getScreen() {
		return fullscreen_2.getName();		
	}
	
	
	public static String getRandomColors() {
		
		if(rand_colors_2 == null) {		
			String random_colors = "true";		
			try {
				String s  = FileManager.getString(configpath, RANDOM_COLORS);		
				if(s.contains("0")) random_colors = "false";
			} catch (NullPointerException e) {
				FileManager.setString(configpath, RANDOM_COLORS,"1");
			}
			return random_colors;
		}
		
		return rand_colors_2.getName();		
	}
	
	public static String getBGPlates() {
		
		if(bg_plates_2 == null) {		
			String show_bg_plates = "true";		
			try {
				String s  = FileManager.getString(configpath, BG_PLATES);		
				if(s.contains("0")) show_bg_plates = "false";
			} catch (NullPointerException e) {
				FileManager.setString(configpath, BG_PLATES,"1");
			}
			return show_bg_plates;
		}
		
		return bg_plates_2.getName();		
	}
	
	public static String getOneSound() {
		
		if(play_one2 == null) {		
			String one_sound_str = "false";		
			try {
				String s  = FileManager.getString(configpath, ONE_SOUND);		
				if(s.contains("1")) one_sound_str = "true";
			} catch (NullPointerException e) {
				FileManager.setString(configpath, ONE_SOUND,"0");
			}
			return one_sound_str;
		}
		
		return play_one2.getName();		
	}
	
	public static String getMuteBot() {
		
		if(mute_bot2 == null) {		
			String one_sound_str = "false";		
			try {
				String s  = FileManager.getString(configpath, MUTE_BOT);		
				if(s.contains("1")) one_sound_str = "true";
			} catch (NullPointerException e) {
				FileManager.setString(configpath, MUTE_BOT,"0");
			}
			return one_sound_str;
		}
		
		return mute_bot2.getName();		
	}
	
	public static String getDiscordToken() {
		try {
			String s  = FileManager.getString(configpath, DISCORD);
			return s;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public static String getDiscordVoiceChannelID() {
		try {
			String s  = FileManager.getString(configpath, DISCORD_VOICE);
			return s;
		} catch (NullPointerException e) {
			return null;
		}
	}

	
	private boolean strg;
	private boolean v;
	
	@Override
	public void keyDown(int arg0) {
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) strg = true;
		if(Gdx.input.isKeyPressed(Keys.V)) v = true;
		
		if(discord_token2.isSelected()) {
			if(strg && v) {
				String text = "";
				
				Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable transferData = systemClipboard.getContents(null);
				for (DataFlavor dataFlavor : transferData.getTransferDataFlavors()) {
					Object content = null;
					try {
						content = transferData.getTransferData(dataFlavor);
					} catch (UnsupportedFlavorException | IOException e) {
						e.printStackTrace();
					}
					if (content != null && content instanceof String) {
						text = (String) content;
						break;
					}
				}
				
				discord_token2.write(0, text);
			} else {
				if(Keys.toString(arg0) != Keys.toString(Keys.CONTROL_LEFT)) {
					discord_token2.write(0, Keys.toString(arg0).charAt(0));
				}
			}
		} else if(discord_voice_channel2.isSelected()) {
			if(strg && v) {
				String text = "";
				
				Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable transferData = systemClipboard.getContents(null);
				for (DataFlavor dataFlavor : transferData.getTransferDataFlavors()) {
					Object content = null;
					try {
						content = transferData.getTransferData(dataFlavor);
					} catch (UnsupportedFlavorException | IOException e) {
						e.printStackTrace();
					}
					if (content != null && content instanceof String) {
						text = (String) content;
						break;
					}
				}
				
				discord_voice_channel2.write(0, text);
			} else {
				if(Keys.toString(arg0) != Keys.toString(Keys.CONTROL_LEFT)) {
					discord_voice_channel2.write(0, Keys.toString(arg0).charAt(0));
				}
			}
		}
	}

	@Override
	public void mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrolled(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
