package com.karmorak.game.GameStates;

import java.util.ArrayList;
import javax.security.auth.login.LoginException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karmorak.api.bounding.Rectangle;
import com.karmorak.api.button.Button;
import com.karmorak.api.button.Scrolable;
import com.karmorak.api.files.FileManager;
import com.karmorak.api.funktions.GraphicFunktions;
import com.karmorak.api.gamestate.GameState;
import com.karmorak.api.listeners.GestureWrapper;
import com.karmorak.game.ButtonRegisters;
import com.karmorak.game.SoundButton;
import com.karmorak.game.Soundboard;

public class Main_Sounds extends ButtonRegisters implements GameState  {

	
	/* - Button alpha support
	 * - 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	private static final int UI_ABS = 13, BUTTON_ABS_X = 15, BUTTON_ABS_Y = 45, LEFT_ABS = 25;
	private static final float DEF_VOL = 0.4f, WARN_TIME = 3f;
	private static float ALL_Y;
	private static final float UI_SCALE = 0.75f;
	
	public static Scrolable s;
	
	private static float scroled;
	
	private static float time_discord_warning;
	
	private static Rectangle settings_rec, shift_rec, discord_rec, spotify_rec;
	private static Texture 
	settings, 
	shift, 
	discord, discord_top, discord_org,
	icon_background, spotify_top;
	private static Button general, youtube, hidden, discord_text;
	
	private static Scrolable COLOR_BAR;
	
	public static short button_tab = 0;
	private static final short STATE_HOTKEY = 0; 
	private static final short STATE_COLOR = 1; 
	public static short state_UI = -1;
//	public static boolean state_hotkey = false;
//	public static boolean state_color = false;
	
	private static Button UI_TOPIC, UI_KEY, UI_KEYCLEAR, UI_X, UI_RESULT;
	private static final float UI_RESULT_TIME = 2f;
	private static float UI_last_result;
	private static final Sprite UI_BACKGROUND_TEXTURE = new Sprite(new Texture(Gdx.files.internal("assets//ui_background.png")));
	
	
	/**
	 * 0= static; 1= floating; 2=showonhover
	 */	
	static int text_pos_mode = 0;
	static boolean changed_discord_state = false;
	public static final int PHASE_OFFLINE = 0, PHASE_DISCONNECTED = 1, PHASE_CONNECTED = 2, PHASE_NO_VOICE = 3, PHASE_NO_TOKEN = 4;
	public static boolean[] discord_phases = new boolean[] {true, false, false, false, false}; 
	/** 
	 * "Offline", "Disconnected", "Connected", "not in Voice Channel", "no token set"
	 */
	static final String[] DISCORD_TEXTS = new String[] {"Offline", "Disconnected", "Connected", "not in Voice Channel", "no token set"};
	
	private static final ArrayList<SoundButton> to_play = new ArrayList<>();
	
	@Override
	public void init() {
		
		settings = new Texture(Gdx.files.internal("assets//settings.png"));
		float x = Gdx.graphics.getWidth() - settings.getWidth()*UI_SCALE - UI_ABS;
		float y = Gdx.graphics.getHeight() - settings.getHeight()*UI_SCALE - UI_ABS;
		settings_rec = new Rectangle(x, y, settings.getWidth()*UI_SCALE, settings.getHeight()*UI_SCALE);
		
		
		shift = new Texture(Gdx.files.internal("assets//ui1.png"));
		x = x - shift.getWidth()*UI_SCALE - UI_ABS;
		shift_rec = new Rectangle(x, y, shift.getWidth()*UI_SCALE, shift.getHeight()*UI_SCALE);
		
		discord_org = new Texture(Gdx.files.internal("assets//discord_down.png"));
		discord = new Texture(Gdx.files.internal("assets//discord_down.png"));
		discord_top = new Texture(Gdx.files.internal("assets//discord_top.png"));
		x = x - discord.getWidth()*UI_SCALE - UI_ABS;
		discord_rec = new Rectangle(x, y, discord.getWidth()*UI_SCALE, discord.getHeight()*UI_SCALE);
		
		icon_background = new Texture(Gdx.files.internal("assets//iconbackground.png"));
		spotify_top = new Texture(Gdx.files.internal("assets//Spotify_top.png"));
		x = x - icon_background.getWidth()*UI_SCALE - UI_ABS;
		
		spotify_rec = new Rectangle(x, y, icon_background.getWidth()*UI_SCALE, icon_background.getHeight()*UI_SCALE);
		
		
		s = new Scrolable("0.0", true,Gdx.graphics.getHeight() - discord_rec.getHeight() - (Gdx.graphics.getHeight() - y), settings_rec.getWidth());
		s.setPosition(settings_rec.getX(), UI_ABS*2);
		s.showBorder(true);
		s.dochangeName(false);
		s.setName(" ");		

		general = new Button("General");		
		youtube = new Button("Youtuber");
		hidden = new Button("Hidden");
		
		
		if(buttons.isEmpty()) {			
			
			float volume = DEF_VOL;
			try {
				volume = Float.parseFloat(FileManager.getString(Soundboard.configpath, Settings_Screen.VOLUME));
			} catch (NullPointerException e) {
				FileManager.setString(Soundboard.configpath, Settings_Screen.VOLUME, "" + DEF_VOL);
			}
			
			ButtonRegisters.init(volume);
			
			posButtons();		
			
		}
		
		general.setPosition(LEFT_ABS, settings_rec.getY());
		general.setColor(Color.CYAN);
		general.setScale(1.3f);
		youtube.setPosition(general.getPosition().getX() + general.getwidth() + UI_ABS, general.getPosition().getY());
		youtube.setScale(1.3f);
		youtube.setColor(Color.WHITE);
		hidden.setPosition(youtube.getPosition().getX() + youtube.getwidth() + UI_ABS, youtube.getPosition().getY());
		hidden.setColor(Color.WHITE);
		hidden.setScale(1.3f);
		
		s.setSlider(1f);
			
		System.out.println("loaded gamestate 1");
	}
	
	@Override
	public void update() {
		posButtons();		
		
		if(UI_RESULT != null) {
			if(UI_last_result >= UI_RESULT_TIME) {
				UI_RESULT.show(false);
			} else {
				UI_last_result += Gdx.graphics.getDeltaTime();
			}
		}
		
		if(discord_text != null && time_discord_warning < WARN_TIME) {
			if(!discord_text.getshow()) discord_text.show(true);
			
			if(text_pos_mode == 0) {			
				discord_text.setPosition(Gdx.graphics.getWidth() - discord_text.getwidth() - UI_ABS, discord_rec.getY() - discord_text.getheight());	
				time_discord_warning += Gdx.graphics.getDeltaTime();
				if(time_discord_warning >= WARN_TIME) discord_text.show(false);
			} else if (text_pos_mode == 1){
				float mouse_x = GestureWrapper.mouse_pos.getX();
				float mouse_y = GestureWrapper.mouse_pos.getY();

				discord_text.setPosition(mouse_x - discord_text.getwidth(), mouse_y - discord_text.getheight());

				if (mouse_x > discord_rec.getX() && mouse_x < discord_rec.getX() + discord_rec.getWidth()) {
					if (mouse_y > discord_rec.getY() && mouse_y < discord_rec.getY() + discord_rec.getHeight()) {
						time_discord_warning = 0;
						discord_text.show(true);
					} else {
						time_discord_warning = WARN_TIME;
						discord_text.show(false);
					}
				} else {
					time_discord_warning = WARN_TIME;
					discord_text.show(false);
				}
			} else if (text_pos_mode == 2) {
				discord_text.setPosition(Gdx.graphics.getWidth() - discord_text.getwidth() - UI_ABS, discord_rec.getY() - discord_text.getheight());	
				time_discord_warning += Gdx.graphics.getDeltaTime();
				if(time_discord_warning >= WARN_TIME) discord_text.show(false);
			}
		}
		
		if(text_pos_mode == 2 && discord_text != null && time_discord_warning >= WARN_TIME) {
			float mouse_x = GestureWrapper.mouse_pos.getX();
			float mouse_y = GestureWrapper.mouse_pos.getY();
			
			if (mouse_x > discord_rec.getX() && mouse_x < discord_rec.getX() + discord_rec.getWidth()) {
				if (mouse_y > discord_rec.getY() && mouse_y < discord_rec.getY() + discord_rec.getHeight()) {
					if(!discord_text.getshow()) discord_text.show(true);
					discord_text.setPosition(mouse_x - discord_text.getwidth(), mouse_y - discord_text.getheight());
				} else {
					if(discord_text.getshow()) discord_text.show(false);
				}
			} else {
				if(discord_text.getshow()) discord_text.show(false);
			}
		}
		
		
		
		for(SoundButton b : to_play) {
			if(!b.isPlaying()) b.play();
			else b.stopSound();
		}
		to_play.clear();
		
		
		for(SoundButton b : lists.get(button_tab)) {
			b.update();
		}
		
			if(discord_phases[PHASE_OFFLINE]) {
			} else if(discord_phases[PHASE_CONNECTED]) {
				if(Soundboard.audiomanager == null) {
					discord_phases[PHASE_CONNECTED] = false;
					discord_phases[PHASE_OFFLINE] = true;
					changed_discord_state = true;
				} else {
					if(!Soundboard.audiomanager.isConnected()) {
						discord_phases[PHASE_CONNECTED] = false;
						discord_phases[PHASE_DISCONNECTED] = true;
						changed_discord_state = true;
					}
				}
			} else if(discord_phases[PHASE_DISCONNECTED]) {
				if(Soundboard.audiomanager == null) {
					discord_phases[PHASE_DISCONNECTED] = false;
					discord_phases[PHASE_OFFLINE] = true;
					changed_discord_state = true;
				} else {
					if(Soundboard.audiomanager.isConnected()) {
						discord_phases[PHASE_DISCONNECTED] = false;
						discord_phases[PHASE_CONNECTED] = true;
						changed_discord_state = true;
					}
				}
			} else if(discord_phases[PHASE_NO_VOICE]) {
				if (Soundboard.audiomanager.isConnected()) {
					discord_phases[PHASE_NO_VOICE] = false;
					discord_phases[PHASE_CONNECTED] = true;
					changed_discord_state = true;
				} 
			}else if(discord_phases[PHASE_NO_TOKEN]) {
				if(Soundboard.audiomanager == null) {
					if(time_discord_warning >= WARN_TIME) {
						Change_Discord_Phase(PHASE_OFFLINE);
					}
				} else {
					if (Soundboard.audiomanager.isConnected()) {
						Change_Discord_Phase(PHASE_CONNECTED);
					} else {
						if(time_discord_warning >= WARN_TIME) {
							Change_Discord_Phase(PHASE_DISCONNECTED);
						}
					}		
				}
			}	
		
		if(changed_discord_state) {
			if(discord_phases[PHASE_OFFLINE]) {
				discord = GraphicFunktions.colorize2(discord_org, Color.WHITE, 1f);	
				
				text_pos_mode = 2;
				time_discord_warning = 0;
				
				if(discord_text != null) {
					if (discord_text.getName() != DISCORD_TEXTS[PHASE_OFFLINE]) {
						discord_text.setName(DISCORD_TEXTS[PHASE_OFFLINE]);
					}
				} else {
					discord_text = new Button(DISCORD_TEXTS[PHASE_OFFLINE]);
					discord_text.setThickFont(true);
					discord_text.setColor(Color.RED);	
				}
			} else if(discord_phases[PHASE_DISCONNECTED]) {
				discord = GraphicFunktions.colorize2(discord_org, Color.RED, 1f);
				
				text_pos_mode = 2;
				time_discord_warning = 0;
				
				if(discord_text != null) {
					if (discord_text.getName() != DISCORD_TEXTS[PHASE_DISCONNECTED]) {
						discord_text.setName(DISCORD_TEXTS[PHASE_DISCONNECTED]);
					}
				} else {
					discord_text = new Button(DISCORD_TEXTS[PHASE_DISCONNECTED]);
					discord_text.setThickFont(true);
					discord_text.setColor(Color.RED);	
				}
			} else if(discord_phases[PHASE_NO_VOICE]) {
				discord = GraphicFunktions.colorize2(discord_org, Color.YELLOW, 1f);
				
				text_pos_mode = 2;
				time_discord_warning = 0;
				
				if(discord_text != null) {
					if (discord_text.getName() != DISCORD_TEXTS[PHASE_NO_VOICE]) {
						discord_text.setName(DISCORD_TEXTS[PHASE_NO_VOICE]);
					}
				} else {
					discord_text = new Button(DISCORD_TEXTS[PHASE_NO_VOICE]);
					discord_text.setThickFont(true);
					discord_text.setColor(Color.RED);	
				}
			} else if(discord_phases[PHASE_CONNECTED]) {
				discord = GraphicFunktions.colorize2(discord_org, Color.GREEN, 1f);
				
				text_pos_mode = 2;
				time_discord_warning = 0;
				
				if(discord_text != null) {
					if (discord_text.getName() != DISCORD_TEXTS[PHASE_CONNECTED]) {
						discord_text.setName(DISCORD_TEXTS[PHASE_CONNECTED]);
					}
				} else {
					discord_text = new Button(DISCORD_TEXTS[PHASE_CONNECTED]);
					discord_text.setThickFont(true);
					discord_text.setColor(Color.RED);	
				}
			} else if(discord_phases[PHASE_NO_TOKEN]) {
				discord = GraphicFunktions.colorize2(discord_org, Color.WHITE, 1f);
				
				text_pos_mode = 2;
				time_discord_warning = 0;
				
				if(discord_text != null) {
					if (discord_text.getName() != DISCORD_TEXTS[PHASE_NO_TOKEN]) {
						discord_text.setName(DISCORD_TEXTS[PHASE_NO_TOKEN]);
					}
				} else {
					discord_text = new Button(DISCORD_TEXTS[PHASE_NO_TOKEN]);
					discord_text.setThickFont(true);
					discord_text.setColor(Color.RED);	
				}				
			}
			changed_discord_state = false;
		}
			
		
	}
	
	public static void posButtons() {
		float x = LEFT_ABS;
		float y = s.getBGPos().getY() + s.getBGheight() - SoundButton.origin_size.getHeight() +scroled;
		ALL_Y = (SoundButton.origin_size.getHeight()*2) + BUTTON_ABS_Y;
		
		
		for(SoundButton b : lists.get(button_tab)) {
			if(!b.isHided()) {
				b.setPosition(x, y);
				
				x+= SoundButton.origin_size.getWidth() + BUTTON_ABS_X;
				
				if(x + SoundButton.origin_size.getWidth() >= Gdx.graphics.getWidth() - s.getBGwidth() - BUTTON_ABS_X) {
					x = LEFT_ABS;
					y -= SoundButton.origin_size.getHeight() + BUTTON_ABS_Y;
					ALL_Y += SoundButton.origin_size.getHeight() + BUTTON_ABS_Y;
				}
			}
		}
				
		float c = Gdx.graphics.getHeight() / ALL_Y;
		
		if(c < 1) s.setSliderSize(s.getSliderWidth(), s.getBGheight() * c);
		else s.setSliderSize(s.getSliderWidth(), s.getBGheight());
		
		
		scroled = (ALL_Y - Gdx.graphics.getHeight()) * (1f-s.getValue());
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		ArrayList<SoundButton> blist = new ArrayList<>();
		for(SoundButton b : lists.get(button_tab)) {
			if(!b.isHided()) {
				if(b.getTexture().getY() > -SoundButton.origin_size.getHeight()) {
					if(b.getTexture().getY() +b.getTexture().getHeight() <= s.getBGPos().getY() + s.getBGheight()) {
						b.getTexture().setAlpha(1f);
						if(b.isSelected() || b.isPlaying()) {
							blist.add(b);
						} else b.draw(batch);
					} else {
						float abs = (s.getBGPos().getY() + s.getBGheight()) - (b.getTexture().getY() + b.getTexture().getHeight());
						if(b.getTexture().getY() >= s.getBGPos().getY() + s.getBGheight()) {
							b.getTexture().setAlpha(1f);
							if(b.isSelected() || b.isPlaying()) {
								blist.add(b);
							} else b.draw(batch);
						} else {
							b.getTexture().setAlpha( abs / b.getTexture().getHeight());
							if(b.isSelected() || b.isPlaying()) {
								blist.add(b);
							} else b.draw(batch);
						}				
					}
				}
			}
		}
		for(int i = 0; i < blist.size(); i++) {
			SoundButton b = blist.get(blist.size()-1-i);
			b.draw(batch);		
		}		
		
		s.draw(batch);
		general.draw(batch);
		youtube.draw(batch);
		if(!hided_buttons.isEmpty()) hidden.draw(batch);; 
		
		batch.draw(settings, settings_rec.getX(), settings_rec.getY(), settings_rec.getWidth(), settings_rec.getHeight());
		
		batch.draw(shift, shift_rec.getX(), shift_rec.getY(), shift_rec.getWidth(), shift_rec.getHeight());
		
		batch.draw(discord, discord_rec.getX(), discord_rec.getY(), discord_rec.getWidth(), discord_rec.getHeight());
		batch.draw(discord_top, discord_rec.getX(), discord_rec.getY(), discord_rec.getWidth(), discord_rec.getHeight());
		
		//batch.draw(icon_background, spotify_rec.getX(), spotify_rec.getY(), spotify_rec.getWidth(), spotify_rec.getHeight());
		batch.draw(spotify_top, spotify_rec.getX(), spotify_rec.getY(), spotify_rec.getWidth(), spotify_rec.getHeight());
		
		if(discord_text != null) discord_text.draw(batch);
		
		if(state_UI == STATE_HOTKEY) {			
			UI_BACKGROUND_TEXTURE.draw(batch);	
			UI_TOPIC.draw(batch);
			UI_KEY.draw(batch);
			UI_KEYCLEAR.draw(batch);
			UI_X.draw(batch);
		} else if (state_UI == STATE_COLOR) {
			UI_BACKGROUND_TEXTURE.draw(batch);	
			UI_TOPIC.draw(batch);
			UI_X.draw(batch);
			COLOR_BAR.draw(batch);
			
		}
		if(UI_RESULT != null) UI_RESULT.draw(batch);
		
	}
	
	public static void show(boolean bool) {
		if(bool) {
			for(SoundButton sb : buttons) {
				sb.showButtons(true);
			}
			general.show(true);
			youtube.show(true);
		} else {
			general.show(false);
			youtube.show(false);
			for(SoundButton b : buttons) {
				b.showButtons(false);
			}
		}
	}
	
	@Override
	public void tap(float x, float y, int count, int button) {
		if(state_UI == STATE_HOTKEY) {			
			if(UI_X.isSelected()) {
				HotkeyState(ID);
			} else if (UI_KEYCLEAR.isSelected()) {
				UI_KEY.setName("Key:");
			}
		} else if (state_UI == STATE_COLOR) {
			if(UI_X.isSelected()) {
				ColorState(ID);
			}
		}
		
		if(x > settings_rec.getX() && x < settings_rec.getX() + settings_rec.getWidth()) {		
			if(y > settings_rec.getY() && y < settings_rec.getY() + settings_rec.getHeight()) {
				GameStateManager.changestate((short) 2);
				return;
			}
		} 
		if(x > shift_rec.getX() && x < shift_rec.getX() + shift_rec.getWidth()) {
			if(y > shift_rec.getY() && y < shift_rec.getY() + shift_rec.getHeight()) {
				for(SoundButton b : buttons) {
					b.shift();
				}	
				return;
			}
		} 
		
		if(x > discord_rec.getX() && x < discord_rec.getX() + discord_rec.getWidth()) {		
			if(y > discord_rec.getY() && y < discord_rec.getY() + discord_rec.getHeight()) {
				String token = Settings_Screen.getDiscordToken();
				if(token == null) {
					time_discord_warning = 0;
					Change_Discord_Phase(PHASE_NO_TOKEN);
				} else {
						if(Soundboard.jda == null)	{
							
							try {											
								String id = Settings_Screen.getDiscordVoiceChannelID();
								
								Soundboard.initDiscord(token);		
								Soundboard.connectDiscordVoice(id);
								
								Change_Discord_Phase(PHASE_NO_VOICE);
							} catch (LoginException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}							
						} else {
							if(Soundboard.audiomanager.isConnected()) {
								Soundboard.audiomanager.closeAudioConnection();	
								Change_Discord_Phase(PHASE_DISCONNECTED);
							} else {
								Soundboard.audiomanager.openAudioConnection(Soundboard.voicechannel);
								Change_Discord_Phase(PHASE_NO_VOICE);
							}
						}
				}				
				return;
			}
		} 

		
		
		if(youtube.isSelected()) {
			button_tab = YTBER_BUTTONS;
			general.setColor(Color.WHITE);
			youtube.setColor(Color.CYAN);
		} else if(general.isSelected()) {
			button_tab = BUTTONS;
			general.setColor(Color.CYAN);
			youtube.setColor(Color.WHITE);
		} 
		
		for(SoundButton b : lists.get(button_tab)) {
			if(b.tap(x, y, count, button) && SoundButton.ONE_SOUND) {
				for(SoundButton b2 : SoundButton.playing)
					if(b != b2) b2.play();
			}
		}
	}


	private static SoundButton ID = null;
	
	public static void HotkeyState(SoundButton id) {
		if(state_UI == STATE_HOTKEY) {
			state_UI = -1;
			UI_TOPIC.show(false);
			UI_KEY.show(false);
			UI_KEYCLEAR.show(false);
			UI_X.show(false);
			
			String key = "";
			
			try {
				key =  UI_KEY.getName().split(":")[1];
			} catch (ArrayIndexOutOfBoundsException e) {}
			
			
			String value = "";
			
			if(key != null && key != " " && key != "") {				
				try {
					value = FileManager.getString(Soundboard.button_config,""+ Keys.valueOf(key));
					if(!value.contains(id.getButtonName())) value = value + ","+ id.getButtonName(); 					
				} catch (Exception e) {
					value = id.getButtonName();
				}
				
				
				String key_attribute = id.getButtonAttributes()[0];
				if(!key_attribute.equals("key-1")) {
					int keyatrint = Integer.parseInt(key_attribute.replace("key", ""));
					
										
					String var1 = "";		
					try {
						var1 = FileManager.getString(Soundboard.button_config, "" + keyatrint); 
					} catch (NullPointerException e) {}
					
					
					if(var1 != "") {
						var1 = var1.replace(ID.getButtonName(), "");
						var1 = var1.replace(",,", ",");						
					}
					FileManager.setString(Soundboard.button_config,""+ keyatrint, var1);
					
				}
				
				
				
				
				FileManager.setString(Soundboard.button_config,""+ Keys.valueOf(key), value);
				
				ID.setHotkey(Keys.valueOf(key));
				ID.saveButtonAttributes();
			}
			
			UI_RESULT.setName("saved Hotkey");
			UI_last_result = 0;
			UI_RESULT.show(true);
			UI_RESULT.setPosition(Gdx.graphics.getWidth() - UI_RESULT.getwidth() - UI_ABS, UI_ABS*2);
			
			UI_KEY.setName("Key:");
			
			
		} else if (state_UI == -1){
			ID = id;
			if(UI_RESULT != null) {
				UI_RESULT.show(false);
				UI_RESULT.setName("result");
			} else {
				UI_RESULT = new Button("result");
				UI_RESULT.setThickFont(true);
				UI_RESULT.setColor(Color.BLACK);
				UI_RESULT.setScale(1.5f);
				UI_RESULT.initBackground();
				UI_RESULT.setInteractable(false);				
				UI_last_result = UI_RESULT_TIME;
				UI_RESULT.show(false);
				UI_RESULT.getBackground().show(true);
			}
			if(UI_KEY != null) {
				UI_KEY.show(true);
				UI_KEYCLEAR.show(true);
				int key = -1;					
				try {
					key = Integer.parseInt(id.getButtonAttributes()[0].replace("key", ""));
				} catch (Exception e) {}
				
				if(key > -1) {
					UI_KEY.setName("Key:" + Keys.toString(key));
				}		
			} else {
				UI_KEY = new Button("Key:");
				int key = -1;					
				try {
					key = Integer.parseInt(id.getButtonAttributes()[0].replace("key", ""));
				} catch (Exception e) {}
				
				if(key > -1) {
					UI_KEY.setName("Key:" + Keys.toString(key));
				}					
				UI_KEYCLEAR = new Button("(CLEAR)");
				UI_KEYCLEAR.setSelectable(false);				
			}
			
			
			if(UI_TOPIC == null) {
				UI_BACKGROUND_TEXTURE.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getHeight()*0.2f);
				UI_TOPIC = new Button("Enter a Hotkey");
				UI_TOPIC.setInteractable(false);
				UI_TOPIC.setThickFont(true);
				
				UI_X = new Button("X");
				UI_X.setThickFont(true);
				UI_X.setSelectable(false);
				
				UI_BACKGROUND_TEXTURE.setX((Gdx.graphics.getWidth() - UI_BACKGROUND_TEXTURE.getWidth()) *0.5f);
				UI_BACKGROUND_TEXTURE.setY((Gdx.graphics.getHeight() - UI_BACKGROUND_TEXTURE.getHeight()) *0.5f);			
				UI_TOPIC.setPosition(UI_BACKGROUND_TEXTURE.getX() + (UI_BACKGROUND_TEXTURE.getWidth() - UI_TOPIC.getwidth())*0.5f, UI_BACKGROUND_TEXTURE.getY() + UI_BACKGROUND_TEXTURE.getHeight() - UI_TOPIC.getheight() - UI_ABS);
							
				UI_X.setPosition(UI_BACKGROUND_TEXTURE.getX() + UI_ABS, UI_BACKGROUND_TEXTURE.getY() + UI_BACKGROUND_TEXTURE.getHeight() - UI_ABS - UI_X.getheight());
				
				Button.selected_Button = UI_KEY.getID();
			} else {
				ID = id;
				UI_TOPIC.show(true);
				UI_TOPIC.setName("Enter a Hotkey");
				UI_X.show(true);		
				state_UI = STATE_HOTKEY;
			}
			UI_KEY.setPosition(UI_BACKGROUND_TEXTURE.getX() + (UI_BACKGROUND_TEXTURE.getWidth()*0.3f - UI_KEY.getwidth()*0.5f), UI_BACKGROUND_TEXTURE.getY() + (UI_BACKGROUND_TEXTURE.getHeight() - UI_KEY.getheight())*0.5f);
			UI_KEYCLEAR.setPosition(UI_BACKGROUND_TEXTURE.getX() + (UI_BACKGROUND_TEXTURE.getWidth()*0.6f - UI_KEYCLEAR.getwidth()*0.5f), UI_BACKGROUND_TEXTURE.getY() + (UI_BACKGROUND_TEXTURE.getHeight() - UI_KEYCLEAR.getheight())*0.5f);
			
		}	
	}
	
	public static void ColorState(SoundButton id) { 
		if(state_UI==STATE_COLOR) {
			state_UI = -1;
			UI_TOPIC.show(false);
			UI_X.show(false);
			COLOR_BAR.show(false);
			
			if(COLOR_BAR.getValue() > 0f) {
				id.setCustomColor(
						GraphicFunktions.FloattoColor(
								COLOR_BAR.getValue()));
			} else {
				id.setCustomColor(null);
			}
			
			id.saveButtonAttributes();
			
			UI_RESULT.setName("saved Color");
			UI_last_result = 0;
			UI_RESULT.show(true);
			UI_RESULT.setPosition(Gdx.graphics.getWidth() - UI_RESULT.getwidth() - UI_ABS, UI_ABS*2);
			
		} else if (state_UI == -1){
			ID = id;
			if(COLOR_BAR != null) {
				COLOR_BAR.show(true);
				COLOR_BAR.setValue(0);
			} else {
				COLOR_BAR = new Scrolable("0", false, 300);
				COLOR_BAR.setBackground(GraphicFunktions.createColorBar());
				COLOR_BAR.setPosition(200, 200);
				COLOR_BAR.showBorder(true);
				COLOR_BAR.dochangeName(false);
				COLOR_BAR.setName(" ");		
				COLOR_BAR.show(true);
			}
			if(UI_RESULT != null) {
				UI_RESULT.show(false);
				UI_RESULT.setName("result");
			} else {
				UI_RESULT = new Button("result");
				UI_RESULT.setThickFont(true);
				UI_RESULT.setColor(Color.BLACK);
				UI_RESULT.setScale(1.5f);
				UI_RESULT.initBackground();
				UI_RESULT.setInteractable(false);				
				UI_last_result = UI_RESULT_TIME;
				UI_RESULT.show(false);
				UI_RESULT.getBackground().show(true);
			}
		
			if(UI_TOPIC != null) {
				UI_TOPIC.show(true);
				UI_TOPIC.setName("Select a Color");	
				UI_X.show(true);
			} else {
				UI_BACKGROUND_TEXTURE.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getHeight()*0.2f);
				UI_TOPIC = new Button("Select a Color");
				UI_TOPIC.setInteractable(false);
				UI_TOPIC.setThickFont(true);
				
				UI_X = new Button("X");
				UI_X.setThickFont(true);
				UI_X.setSelectable(false);
			}
			
			UI_BACKGROUND_TEXTURE.setX((Gdx.graphics.getWidth() - UI_BACKGROUND_TEXTURE.getWidth()) *0.5f);
			UI_BACKGROUND_TEXTURE.setY((Gdx.graphics.getHeight() - UI_BACKGROUND_TEXTURE.getHeight()) *0.5f);			
			UI_TOPIC.setPosition(UI_BACKGROUND_TEXTURE.getX() + (UI_BACKGROUND_TEXTURE.getWidth() - UI_TOPIC.getwidth())*0.5f, UI_BACKGROUND_TEXTURE.getY() + UI_BACKGROUND_TEXTURE.getHeight() - UI_TOPIC.getheight() - UI_ABS);			
			UI_X.setPosition(UI_BACKGROUND_TEXTURE.getX() + UI_ABS, UI_BACKGROUND_TEXTURE.getY() + UI_BACKGROUND_TEXTURE.getHeight() - UI_ABS - UI_X.getheight());			
			state_UI = STATE_COLOR;
			
			COLOR_BAR.setPosition(UI_BACKGROUND_TEXTURE.getX() + (UI_BACKGROUND_TEXTURE.getWidth() - COLOR_BAR.getBGwidth()) *0.5f, UI_BACKGROUND_TEXTURE.getY() + (UI_BACKGROUND_TEXTURE.getHeight()*0.5f) - COLOR_BAR.getBGheight());
			
		}
		
		
	}
	
	
	public static void Change_Discord_Phase(int PHASE) {
		if(PHASE == PHASE_NO_VOICE) {
			discord_phases[PHASE_DISCONNECTED] = false;
			discord_phases[PHASE_OFFLINE] = false;
			discord_phases[PHASE_CONNECTED] = false;
			discord_phases[PHASE_NO_VOICE] = true;
			discord_phases[PHASE_NO_TOKEN] = false;
		} else if(PHASE == PHASE_CONNECTED) {
			discord_phases[PHASE_DISCONNECTED] = false;
			discord_phases[PHASE_OFFLINE] = false;
			discord_phases[PHASE_NO_VOICE] = false;
			discord_phases[PHASE_CONNECTED] = true;
			discord_phases[PHASE_NO_TOKEN] = false;
		} else if(PHASE == PHASE_DISCONNECTED) {
			discord_phases[PHASE_DISCONNECTED] = true;
			discord_phases[PHASE_OFFLINE] = false;
			discord_phases[PHASE_NO_VOICE] = false;
			discord_phases[PHASE_CONNECTED] = false;
			discord_phases[PHASE_NO_TOKEN] = false;
		} else if(PHASE == PHASE_OFFLINE) {
			discord_phases[PHASE_DISCONNECTED] = false;
			discord_phases[PHASE_OFFLINE] = true;
			discord_phases[PHASE_NO_VOICE] = false;
			discord_phases[PHASE_CONNECTED] = false;
			discord_phases[PHASE_NO_TOKEN] = false;
		} else if(PHASE == PHASE_NO_TOKEN) {
			discord_phases[PHASE_DISCONNECTED] = false;
			discord_phases[PHASE_OFFLINE] = false;
			discord_phases[PHASE_NO_VOICE] = false;
			discord_phases[PHASE_CONNECTED] = false;
			discord_phases[PHASE_NO_TOKEN] = true;
			
		}
		changed_discord_state = true;
	}
		
	@Override
	public void keyDown(int c) {
		if(c == Keys.ESCAPE && state_UI==STATE_HOTKEY) {
			HotkeyState(ID);
		} else if (state_UI==STATE_HOTKEY) {
			UI_KEY.setName("Key:" + Keys.toString(c));
			
			float x = UI_BACKGROUND_TEXTURE.getX() + (UI_BACKGROUND_TEXTURE.getWidth()*0.3f - UI_KEY.getwidth()*0.5f);
			float x2 = UI_BACKGROUND_TEXTURE.getX() + (UI_BACKGROUND_TEXTURE.getWidth()*0.6f - UI_KEYCLEAR.getwidth()*0.5f);
			
			UI_KEY.setPosition(x, UI_BACKGROUND_TEXTURE.getY() + (UI_BACKGROUND_TEXTURE.getHeight() - UI_KEY.getheight())*0.5f);			
			
			if(x + UI_KEY.getwidth() > x2) {
				UI_KEYCLEAR.setPosition(UI_KEY.getPosition().getX() + UI_KEY.getwidth(), UI_BACKGROUND_TEXTURE.getY() + (UI_BACKGROUND_TEXTURE.getHeight() - UI_KEYCLEAR.getheight())*0.5f);
			} else {
				UI_KEYCLEAR.setPosition(x2, UI_BACKGROUND_TEXTURE.getY() + (UI_BACKGROUND_TEXTURE.getHeight() - UI_KEYCLEAR.getheight())*0.5f);
			}
		} else {
			
		}
	}
	
	public static void NativeKey(int keycode) {
		if(state_UI!=STATE_HOTKEY && keycode != Keys.ESCAPE) {
			String s = "";
			try {
				s = FileManager.getString(Soundboard.button_config, ""+keycode);
				String[] list = s.split(",");			
				
				
				SoundButton.playing.clear();
				for(String buttonname : list) {
					SoundButton button = SoundButton.getButton(buttonname);
					if(button != null) {
						to_play.add(button);
						if(to_play.size() >= 3) break;
					}
				}
				
			} catch(Exception e) {}
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

	@Override
	public void touchDragged(int arg0, int arg1, int arg2) {
		if(state_UI == STATE_COLOR) {
			if(COLOR_BAR.isDragged()) {
				COLOR_BAR.setName(GraphicFunktions.getColorName(COLOR_BAR.getValue()));
			}
		}	
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
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


	




}
