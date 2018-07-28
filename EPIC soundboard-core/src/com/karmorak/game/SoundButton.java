package com.karmorak.game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karmorak.api.Vector2;
import com.karmorak.api.button.Button;
import com.karmorak.api.button.Expandable;
import com.karmorak.api.files.FileManager;
import com.karmorak.api.funktions.GraphicFunktions;
import com.karmorak.api.listeners.GestureWrapper;
import com.karmorak.game.GameStates.GameStateManager;
import com.karmorak.game.GameStates.Main_Sounds;
import com.karmorak.game.GameStates.Settings_Screen;
import com.karmorak.game.discord.Soundboard_AudioLoadResultHandler;

public class SoundButton {
	
	
	public static boolean RANDOM_COLORS = true;
	public static boolean ONE_SOUND = false, MUTE_BOT = false;
	public static boolean bg = true;
	private static boolean INIT = false;
	
	
	public static final ArrayList<SoundButton> playing = new ArrayList<>();
	private static final HashMap<String, SoundButton> all_buttons = new HashMap<>();	
	
	private Vector2 origin_pos;
	public static final Vector2 origin_size = new Vector2(150, 150);
	
	private static final float BUTTON_ABS_Y = -2f;
	private static Sprite FAVORITE;
	private final Sprite texture;	
	
	private final String[] paths;
	private final String[] temp_paths;
	private final float[] volumes;
	
	private Music s = null;
	
	private Vector2 pos;
	private final Vector2 size;
	
	public final Button b;
	private final Expandable e;
	private boolean shift;
	
	private boolean is_hided = false;
	private boolean isfavorite = false;
	
	
	private int hotkey;
	private Color custom_color;
	
	private static final short FAVORITE_OPTION = 0;
	private static final short HOTKEY_OPTION = 1;
	private static final short HIDE_OPTION = 2;
	private static final short COLOR_OPTION = 3;
	
	
	public SoundButton(float volume, String path, String name, final ArrayList<SoundButton> list) {
		
		if(!INIT)  {
			String s = Settings_Screen.getRandomColors();
			if(s.equals("false")) {
				RANDOM_COLORS = false;
			}
		}
			
		this.pos = new Vector2(0, 0);		
		size = new Vector2(origin_size.getWidth(), origin_size.getHeight());
		
		b = new Button(name);	
		e = new Expandable(new Vector2(pos.getX(), pos.getY()), new Vector2(size.getWidth(), size.getWidth()));
		
		this.paths = new String[1];
		this.temp_paths = new String[1];
		volumes = new float[] {volume};
		this.paths[0] = path;
		
		
		InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(paths[0]);
		try {
			File tempFile = File.createTempFile(getButtonName(), ".ogg");
			tempFile.deleteOnExit();
			FileOutputStream out = new FileOutputStream(tempFile);
			IOUtils.copy(in, out);
			out.close();
			in.close();
			temp_paths[0] = tempFile.getAbsolutePath();
		} catch (Exception e) {}	
	
		//______________________
		if(!INIT) {
			INIT = true;
			GameStateManager.init(GameStateManager.CONFIG_STATE);
			String s = Settings_Screen.getBGPlates();
			if(s.equals("false")) {
				bg = false;
			}
			
			s = Settings_Screen.getOneSound();
			if(s.equals("true")) {
				ONE_SOUND = true;
			}
			
			s = Settings_Screen.getMuteBot();
			if(s.equals("true")) {
				MUTE_BOT = true;
			}
			
			if(bg) {
				b.addFontCacheColor(Color.BLACK, false);
				b.addFontCacheColor(Color.BLACK, true);
			} else {
				b.addFontCacheColor(Color.WHITE, false);
				b.addFontCacheColor(Color.WHITE, true);
			}
			
		}		
		
		String c = null;
		
		try {		
			c = FileManager.getString(Soundboard.button_config, getButtonName());
		} catch (NullPointerException e) {}
		
		
		if(c != null)  {
			if(c.contains("fav1")) isfavorite = true;			
			if(c.contains("hide1")) is_hided = true;
			
			if(!c.contains("0-0-0-0")) {
				try {
					String[] colorstring = c.split(",")[COLOR_OPTION].replace("color", "").split("-");
					float r = Float.parseFloat(colorstring[0]);
					float g = Float.parseFloat(colorstring[1]);					
					float b = Float.parseFloat(colorstring[2]);
					float a = Float.parseFloat(colorstring[3]);
		
					custom_color = new Color(r, g, b, a);
					
				} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {}
			}	
			
		}
		
		if(isfavorite && FAVORITE == null) {
			FAVORITE = new Sprite(new Texture(Gdx.files.internal("assets//favorite_yellow.png")));
		}
		
		if(custom_color == null) {
			if(RANDOM_COLORS)		
				texture = GraphicFunktions.colorize(Gdx.files.internal("assets//button_grey_2.png"), GraphicFunktions.getRandomColor(), 2.35f);	
			else
				texture = GraphicFunktions.colorize(Gdx.files.internal("assets//button_grey_2.png"), Color.RED, 2.35f);	
		} else {
			texture = GraphicFunktions.colorize(Gdx.files.internal("assets//button_grey_2.png"), custom_color, 2.35f);	
		}
		
		texture.setPosition(pos.getX(), pos.getY());
		origin_pos= new Vector2(pos.getX(), pos.getY());
		texture.setSize(size.getX(), size.getY());
		
		e.setPosition(pos.getX()+size.getWidth()-e.getwidth(), pos.getY()+size.getHeight()-e.getheight());
		e.addOption("Favorite");
		e.addOption("Hotkey");
		e.addOption("Hide");
		e.addOption("Color");
		
		if(!RANDOM_COLORS)
			e.addOption("Custom Color");
		
		name = name.toUpperCase();
		if(bg)
			b.setColor(Color.BLACK);
		b.setPositioningMode(1);
		b.setThickFont(true);
		b.setMaxTextWidth(texture.getWidth());
		b.setInteractable(false);
		float scale = 0.8f;
		while(b.getwidth() > size.getX()) {
			scale = scale - 0.05f;
			b.setScale(scale);
			if(scale < 0.65f) {	
				if(name.contains(" ")) {
					String[] names = name.split(" ");
					String name1 = "";
					String name2 = "";
					float halff;
					int half;
					if(names.length > 2) {
						halff = (names.length)*0.5f;
						half = (int)halff;
					} else {
						half = 1;
					}
					
					for(int i = 0; i < half; i++) {
						if(name1.equals("")) name1 = names[i];
						else name1 = name1 + " " + names[i];
					}
					for(int i = half; i < names.length; i++) {
						if(name2.equals("")) name2 = names[i];
						else name2 = name2 + " " + names[i];
					}
					
					b.setName(new String[] {name1,name2});
					b.setScale(0.75f);
					if(b.getwidth(1) > size.getWidth()) {
						name1 = "";
						name2 = "";
						if(names.length > 2) {
							halff = (names.length)*0.5f+1;
							half = (int)halff;
						} else {
							half = 1;
						}
						
						for(int i = 0; i < half; i++) {
							if(name1.equals("")) name1 = names[i];
							else name1 = name1 + " " + names[i];
						}
						for(int i = half; i < names.length; i++) {
							if(name2.equals("")) name2 = names[i];
							else name2 = name2 + " " + names[i];
						}
						b.setName(new String[] {name1,name2});
						b.setScale(0.75f);
						if(b.getwidth() > size.getWidth()) {
							name1 = "";
							name2 = "";
							if(names.length > 2) {
								halff = (names.length)*0.5f;
								half = (int)halff;
							} else {
								half = 1;
							}
							for(int i = 0; i < half; i++) {
								if(name1.equals("")) name1 = names[i];
								else name1 = name1 + " " + names[i];
							}
							for(int i = half; i < names.length; i++) {
								if(name2.equals("")) name2 = names[i];
								else name2 = name2 + " " + names[i];
							}
							if(b.getwidth(1) > size.getWidth()) {
								b.setName(new String[] {name1,name2});
								b.setScale(0.70f);
							} else {
								b.setName(new String[] {name1,name2});
								b.setScale(0.75f);
							}
						}
					}
				}	
				break;
			}
			b.setScale(scale);			
		}		
		
		b.setPosition(pos.getX() - texture.getWidth()*0.05f, pos.getY() - b.getheight() + BUTTON_ABS_Y);	
		if(bg) {
			b.initBackground();
			b.getBackground().show(true);
			b.getBackground().setXOffset(5);
		}
		
		all_buttons.put(getButtonName(), this);
		if(list!=null) list.add(this);				
		if(is_hided) ButtonRegisters.hided_buttons.add(this);
	}
	
	public SoundButton(float volume, String[] path, String name, final ArrayList<SoundButton> list) {
		
		if(!INIT)  {
			String s = Settings_Screen.getRandomColors();
			if(s.equals("false")) {
				RANDOM_COLORS = false;
			}
		}
		

		this.pos = new Vector2(0, 0);		
		size = new Vector2(origin_size.getWidth(), origin_size.getHeight());
		
		b = new Button(name);	
		e = new Expandable(new Vector2(pos.getX(), pos.getY()), new Vector2(size.getWidth(), size.getWidth()));
		
		this.paths = new String[path.length];
		this.temp_paths = new String[path.length];
		volumes = new float[path.length];
		for(int i = 0; i < path.length; i++) {
			this.paths[i] = path[i];
			volumes[i] = volume;
		}		
		
		for(int i = 0; i < paths.length; i++) {
			InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("assets//" +paths[i]);
			
			
			try {
				File tempFile = File.createTempFile(getButtonName(), ".ogg");
				tempFile.deleteOnExit();
				FileOutputStream out = new FileOutputStream(tempFile);
				IOUtils.copy(in, out);
				out.close();
				in.close();
				temp_paths[i] = tempFile.getAbsolutePath();
			} catch (Exception e) {}	
		}
		//______________________
		if(!INIT) {
			INIT = true;
			GameStateManager.init(GameStateManager.CONFIG_STATE);
			String s = Settings_Screen.getBGPlates();
			if(s.equals("false")) {
				bg = false;
			}
			
			s = Settings_Screen.getOneSound();
			if(s.equals("true")) {
				ONE_SOUND = true;
			}
			
			s = Settings_Screen.getMuteBot();
			if(s.equals("true")) {
				MUTE_BOT = true;
			}
			
			if(bg) {
				b.addFontCacheColor(Color.BLACK, false);
				b.addFontCacheColor(Color.BLACK, true);
			} else {
				b.addFontCacheColor(Color.WHITE, false);
				b.addFontCacheColor(Color.WHITE, true);
			}
			
		}		
		
		String c = null;
		
		try {		
			c = FileManager.getString(Soundboard.button_config, getButtonName());
		} catch (NullPointerException e) {}
		
		
		if(c != null)  {
			if(c.contains("fav1")) isfavorite = true;			
			if(c.contains("hide1")) is_hided = true;
			
			if(!c.contains("0-0-0-0")) {
				try {
					String[] colorstring = c.split(",")[COLOR_OPTION].replace("color", "").split("-");
					
					float r = Float.parseFloat(colorstring[0]);
					float g = Float.parseFloat(colorstring[1]);					
					float b = Float.parseFloat(colorstring[2]);
					float a = Float.parseFloat(colorstring[3]);
		
					custom_color = new Color(r, g, b, a);
					
				} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {}
			}	
			
		}
		
		if(isfavorite && FAVORITE == null) {
			FAVORITE = new Sprite(new Texture(Gdx.files.internal("assets//favorite_yellow.png")));
		}
		
		if(custom_color == null) {
			if(RANDOM_COLORS)		
				texture = GraphicFunktions.colorize(Gdx.files.internal("assets//button_grey_2.png"), GraphicFunktions.getRandomColor(), 2.35f);	
			else
				texture = GraphicFunktions.colorize(Gdx.files.internal("assets//button_grey_2.png"), Color.RED, 2.35f);	
		} else {
			texture = GraphicFunktions.colorize(Gdx.files.internal("assets//button_grey_2.png"), custom_color, 2.35f);	
		}
		
		texture.setPosition(pos.getX(), pos.getY());
		origin_pos= new Vector2(pos.getX(), pos.getY());
		texture.setSize(size.getX(), size.getY());
		
		e.setPosition(pos.getX()+size.getWidth()-e.getwidth(), pos.getY()+size.getHeight()-e.getheight());
		e.addOption("Favorite");
		e.addOption("Hotkey");
		e.addOption("Hide");
		e.addOption("Color");
		
		if(!RANDOM_COLORS)
			e.addOption("Custom Color");
		
		name = name.toUpperCase();
		if(bg)
			b.setColor(Color.BLACK);
		b.setPositioningMode(1);
		b.setThickFont(true);
		b.setMaxTextWidth(texture.getWidth());
		b.setInteractable(false);
		float scale = 0.8f;
		while(b.getwidth() > size.getX()) {
			scale = scale - 0.05f;
			b.setScale(scale);
			if(scale < 0.65f) {	
				if(name.contains(" ")) {
					String[] names = name.split(" ");
					String name1 = "";
					String name2 = "";
					float halff;
					int half;
					if(names.length > 2) {
						halff = (names.length)*0.5f;
						half = (int)halff;
					} else {
						half = 1;
					}
					
					for(int i = 0; i < half; i++) {
						if(name1.equals("")) name1 = names[i];
						else name1 = name1 + " " + names[i];
					}
					for(int i = half; i < names.length; i++) {
						if(name2.equals("")) name2 = names[i];
						else name2 = name2 + " " + names[i];
					}
					
					b.setName(new String[] {name1,name2});
					b.setScale(0.75f);
					if(b.getwidth(1) > size.getWidth()) {
						name1 = "";
						name2 = "";
						if(names.length > 2) {
							halff = (names.length)*0.5f+1;
							half = (int)halff;
						} else {
							half = 1;
						}
						
						for(int i = 0; i < half; i++) {
							if(name1.equals("")) name1 = names[i];
							else name1 = name1 + " " + names[i];
						}
						for(int i = half; i < names.length; i++) {
							if(name2.equals("")) name2 = names[i];
							else name2 = name2 + " " + names[i];
						}
						b.setName(new String[] {name1,name2});
						b.setScale(0.75f);
						if(b.getwidth() > size.getWidth()) {
							name1 = "";
							name2 = "";
							if(names.length > 2) {
								halff = (names.length)*0.5f;
								half = (int)halff;
							} else {
								half = 1;
							}
							for(int i = 0; i < half; i++) {
								if(name1.equals("")) name1 = names[i];
								else name1 = name1 + " " + names[i];
							}
							for(int i = half; i < names.length; i++) {
								if(name2.equals("")) name2 = names[i];
								else name2 = name2 + " " + names[i];
							}
							if(b.getwidth(1) > size.getWidth()) {
								b.setName(new String[] {name1,name2});
								b.setScale(0.70f);
							} else {
								b.setName(new String[] {name1,name2});
								b.setScale(0.75f);
							}
						}
					}
				}	
				break;
			}
			b.setScale(scale);			
		}		
		
		b.setPosition(pos.getX() - texture.getWidth()*0.05f, pos.getY() - b.getheight() + BUTTON_ABS_Y);	
		if(bg) {
			b.initBackground();
			b.getBackground().show(true);
			b.getBackground().setXOffset(5);
		}
		
		all_buttons.put(getButtonName(), this);
		if(list!=null) list.add(this);				
		if(is_hided) ButtonRegisters.hided_buttons.add(this);
	}
	
	
	public boolean isSelected() {
		Vector2 mouse = GestureWrapper.mouse_pos;		
		float pos_y = Gdx.graphics.getHeight() - mouse.getY();
		if(mouse.getX() > b.getPosition().getX() && mouse.getX() < b.getPosition().getX() + texture.getWidth()) {
			return pos_y > b.getPosition().getY() && pos_y < b.getPosition().getY() + texture.getHeight();
		}
		return  false;
	}
		
	public boolean isPlaying() {
		return s != null && s.isPlaying();
	}

	public void shift() {
		if(shift) {
			shift = false;
			e.show(false);
			for(SoundButton sb : TO_HIDE) {
				sb.is_hided = true;
				b.show(false);
				ButtonRegisters.hided_buttons.add(this);
				sb.saveButtonAttributes();
			}
			for(SoundButton sb : TO_UNHIDE) {
				sb.is_hided = false;
				b.show(true);
				ButtonRegisters.hided_buttons.remove(this);
				sb.saveButtonAttributes();
			}
			TO_HIDE.clear();
			TO_UNHIDE.clear();
			
			Main_Sounds.posButtons();
		} else {
			shift = true;
			e.show(true);
		}
	}
	
	public void showButtons(boolean bool) {
		b.show(bool);
		e.show(bool);
	}
	
	public void update() {
		
		e.update();
		
		if(shift && e.expand_state >= 0) 
			stopSound();
		
		if(s != null && s.isPlaying()) {
			b.setMaxTextWidth(0);
			if(bg) b.getBackground().setXOffset(12);
			if(b.getRealBounds().getWidth() > origin_size.getWidth()) {
				b.setPosition(origin_pos.getX(), pos.getY() - b.getheight() + BUTTON_ABS_Y);
			} else {
				b.setPosition(origin_pos.getX() + (origin_size.getWidth() - b.getTotalBounds().getWidth())*0.5f, origin_pos.getY() - b.getheight() + BUTTON_ABS_Y);
			}
		} else {
			stopSound();
			if(bg && b.getBackground() != null) b.getBackground().setXOffset(5);
			b.setMaxTextWidth(texture.getWidth());
			b.setPosition(origin_pos.getX(), origin_pos.getY() - b.getheight() + BUTTON_ABS_Y);	
			e.setPosition(pos.getX()+size.getWidth()-e.getwidth(), pos.getY()+size.getHeight()-e.getheight());
		}
		
		Vector2 mouse = GestureWrapper.mouse_pos;
			
		float pos_y = mouse.getY();
		
		if(mouse.getX() > b.getPosition().getX() && mouse.getX() < b.getPosition().getX() + texture.getWidth()) {
			if(pos_y > b.getPosition().getY() && pos_y < b.getPosition().getY() + texture.getHeight()) {
				b.setMaxTextWidth(0);
				if(bg)
				b.getBackground().setXOffset(12);
				if(b.getRealBounds().getWidth() > origin_size.getWidth()) {
					b.setPosition(origin_pos.getX(), pos.getY() - b.getheight() + BUTTON_ABS_Y);
				} else {
					b.setPosition(origin_pos.getX() + (origin_size.getWidth() - b.getTotalBounds().getWidth())*0.5f, origin_pos.getY() - b.getheight() + BUTTON_ABS_Y);
				}
			}			
		}
		
	}
	
	public void draw(SpriteBatch batch) {
		
		
		
		if(isPlaying()) {
			batch.draw(texture, texture.getX() + (origin_size.getWidth()-texture.getWidth()*0.8f)*0.5f, texture.getY(), texture.getWidth()*0.8f, texture.getHeight()*0.8f);
			if(isfavorite) {
				batch.draw(FAVORITE, texture.getX() + (origin_size.getWidth()-texture.getWidth()*0.8f)*0.5f, texture.getY() + texture.getHeight()*0.8f - FAVORITE.getHeight());
			}
		} else {
			texture.draw(batch);
			//batch.draw(texture, texture.getX(), texture.getY(), texture.getWidth(), texture.getHeight());
			if(isfavorite) {
				batch.draw(FAVORITE, texture.getX(), texture.getY() + texture.getHeight() - FAVORITE.getHeight());
			}
		}
		b.draw(batch);
		
		
		
		if(shift)
			e.draw(batch);
	}
	
	public void showBackground(boolean bool) {
		if(bool) {
			if(b.getBackground() == null) {
				b.setColor(Color.BLACK);
				b.initBackground();
				b.getBackground().show(true);
				b.getBackground().setXOffset(5);
			} else {
				b.setColor(Color.BLACK);
				b.getBackground().show(true);
			}			
		} else {
			if(b.getBackground() == null) {
				
			} else {
				b.setColor(Color.WHITE);
				b.setMaxTextWidth(origin_size.getWidth());
				b.getBackground().show(false);
			}
		}
		bg = bool;
	}
	
	private static final ArrayList<SoundButton> TO_HIDE = new ArrayList<>();
	private static final ArrayList<SoundButton> TO_UNHIDE = new ArrayList<>();
	
	public boolean tap(float x, float y, int count, int button) {
		if((!shift || e.expand_state >= 0) && !is_hided) {
			if(x > pos.getX() && x < pos.getX() + size.getX()) {	
				if(y > pos.getY() && y < pos.getY() + size.getY()) {
					return play();
				}
			}
		} else {
			if(e.isOptionSelected(FAVORITE_OPTION)) {
				if(isfavorite) {
					isfavorite = false;
					saveButtonAttributes();
				} else {
					isfavorite = true;
					if(FAVORITE == null) FAVORITE = new Sprite(new Texture(Gdx.files.internal("assets//favorite_yellow.png")));
					saveButtonAttributes();
				}
			} else if (e.isOptionSelected(HOTKEY_OPTION)) {
				Main_Sounds.HotkeyState(this);
			} else if (e.isOptionSelected(COLOR_OPTION)) {
				Main_Sounds.ColorState(this);
			} else if (e.isOptionSelected(HIDE_OPTION)){
				if(!is_hided) TO_HIDE.add(this);
				else TO_UNHIDE.add(this);				
			}
		}
		return false;
	}
	
	public Sprite getTexture() {
		return texture;
	}
	
	public void setPosition(float x,float  y) {
		texture.setPosition(x, y);
		pos = new Vector2(x, y);
		origin_pos = new Vector2(x, y);
		
		b.setPosition(x, y - BUTTON_ABS_Y);
	}


	public void setVolume(float volume) {
		volumes[0] = volume;
	}
	
	public void setVolume(int index, float volume) throws ArrayIndexOutOfBoundsException {
		if(index < 0 || index >= volumes.length) throw new ArrayIndexOutOfBoundsException();
		volumes[index] = volume;
	}
	
	public void setVolumeAll(float volume) {
		for(int i = 0; i < volumes.length; i++) {
			volumes[i] = volume;
		}
	}
		
	private void playSound(int index) {
		stopSound();		
		if(!playing.contains(this))	playing.add(this);
		s = Gdx.audio.newMusic(Gdx.files.internal(paths[index]));
		
		
		if(Main_Sounds.discord_phases[Main_Sounds.PHASE_CONNECTED]) {
			Soundboard.audioPlayerManager.loadItem(temp_paths[index], new Soundboard_AudioLoadResultHandler());	
			s.setVolume(0);
		} else {
			s.setVolume(volumes[index]);
		}
		s.play();
	}

	public void stopSound() {
		if(s != null) {
			s.stop();
			s.dispose();
			s = null;
			playing.remove(this);			
			if(Main_Sounds.discord_phases[Main_Sounds.PHASE_CONNECTED]) {
				Soundboard.trackScheduler.nextTrack();
			}
		}
		
	}


	
	public static SoundButton getButton(String name) {		
		return all_buttons.get(name);	
	}
	
	
	public String getButtonName() {		
		String[] list = paths[0].split("//");		
		String name = list[list.length-1].replace(".ogg", "");
		
		return name;	
	}
	
	public String getButtonAttributeString() {				
		String out = FileManager.getString(Soundboard.button_config, getButtonName());	
		return out;	
	}
	
	
	public String[] getButtonAttributes() {		
		String[] out;
		try {
			out = FileManager.getString(Soundboard.button_config, getButtonName()).split(",");	
		} catch(Exception e) { 
			return new String[] {"key-1", "fav0", "hide0", "color0-0-0-0"};
		}
		
		return out;	
	}
	public void saveButtonAttributes() {	
		String out = "";	
		
		if(hotkey >= 0) out = "key" + hotkey + ",fav";
		else out = "key-1,fav";
		
		if(isfavorite) out = out + "1";
		else out = out + "0";
		
		if(is_hided) out = out + ",hide1,";
		else out = out + ",hide0,";
		
		if(custom_color == null || GraphicFunktions.isEqualColor(custom_color, new Color(0, 0, 0, 0))) out = out + "color0-0-0-0";
		else out = out + "color" + custom_color.r + "-" + custom_color.g + "-" + custom_color.b + "-" + custom_color.a;
		
		FileManager.setString(Soundboard.button_config, getButtonName(), out);
	}

	public boolean play() {
		if(isPlaying()) {
			stopSound();
			return false;
		} else {
			playSound((int)(Math.random() * paths.length));
			return true;
		}
	}
	
	public boolean isHided() {
		return is_hided;
	}

	public int getHotkey() {
		return hotkey;
	}

	public void setHotkey(int hotkey) {
		this.hotkey = hotkey;
	}
	
	public Color getCustomColor() {
		return custom_color;
	}

	public void setCustomColor(Color color) {
		this.custom_color = color;
	}

}
