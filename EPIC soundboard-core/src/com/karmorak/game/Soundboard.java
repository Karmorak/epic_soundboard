package com.karmorak.game;

import java.io.File;

import javax.security.auth.login.LoginException;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karmorak.api.Main;
import com.karmorak.api.Vector2;
import com.karmorak.api.button.Button;
import com.karmorak.api.listeners.Global_GestureWrapper;
import com.karmorak.game.GameStates.GameStateManager;
import com.karmorak.game.discord.JDAListener;
import com.karmorak.game.discord.Soundboard_AudioSendHandler;
import com.karmorak.game.discord.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

public class Soundboard extends ApplicationAdapter {
	
	private SpriteBatch batch;
	//private SpriteCache cache;
	
	private Button b;
	
	/*Changelog r1 (24.11.17)
	 * 	- first version
	 * r2 (25.11.17)
	 *	- 5 new sounds
	 * 	- much optimizations
	 * 	- random Colors
	 * 	- settings screen
	 * r3 (26.11.17)
	 *  - volume now configurable
	 *  - config system is up
	 *  - optimizations
	 *  - 7 new sounds
	 * r4 (27.11.17)
	 *  - config Screen got some settings
	 *  - some debugging stuff
	 * r5(28.11.17)
	 * 	- console will now be logged
	 * r6(03.12.17)
	 * - settings
	 * - scrolable vertical
	 * - 21 new sounds
	 * r7(04.12.17)
	 * - bug fixes
	 * - fullscreen setting now supported
	 * - now can escape with from the settings menu
	 * - some optimizations
	 * r8(08.12.17)
	 * - 14 new sounds
	 * - one button can now have different sounds
	 * r9(April-18)
	 * - (BOTH) decreased fadetime of the logos
	 * - (BOTH) performance improvement
	 * - (ANDROID) should not crash anymore
	 * - (ANDROID) fixed a heavy button bug
	 * - (ANDROID) now completly on JAVA 6
	 * - (ANDROID) changed appname and icon
	 * - some android related compatibility fixes
	 * - more performance on buttons
	 * - reduced fading time of the appstart to 0.7 seconds
	 * r10(May-18)
	 * (DesktopLauncher, SoundButton,ButtonRegisters,Main_Sounds)
	 * - fixed some saving issues on PC
	 * - added 13 new sounds
	 * - scrolbar should work now correct
	 * - buttons now in the middle when pressed
	 * - reduced fading time of the appstart to 0.6 seconds
	 * r11(July-18)
	 * (Main, AppStart, SettingsScreen)
	 * - new font
	 * - upgraded K-API from 1.8.0 to 1.9.8
	 * - added information and option setting to button
	 * - Buttons now have a White Background and the text fit more to their Button
	 * - you can disable it in the settings (no restart required)
	 * - fixed some UI bugs in the settings Screen
	 * - some other small UI changes
	 * - discord integration
	 * r12
	 * - upgraded K-API from 1.9.8 to 1.9.10
	 * - UI changes
	 * - bugfix
	 * 
	 * 
	 *
	 * scale setting for the buttons
	 * scrolling fixen
	 * dateispeichern fixen
	 * bestätigung für settings
	 * eigensounds mit eigenen kategorien(soundpacks)
	 * frei erstellbare kategorisierung
	 * 
	 * API: Button tooltips
	 * option: discordbot für dich stumm sodas du local hörst 
	 * anzeigen das das ein button mit mehreren sounds ist
	 * favoriten
	 * 
	 */

	
	public static JDA jda;
	public static VoiceChannel voicechannel;
	public static AudioManager audiomanager;
	public static AudioPlayerManager audioPlayerManager;
	public static TrackScheduler trackScheduler;
	private static AudioPlayer player;	
	
	
	private static final String 				USER_PATH = "C://Users//" + System.getProperty("user.name");
	private static final String logpath = 		USER_PATH + "//Documents//epicsoundboard//log.txt";
	public static final String temp_path = 		USER_PATH + "//AppData//Local//Temp//epicsoundboard//";
	public static final String configpath = 	USER_PATH + "//Documents//epicsoundboard//config.yml";	
	public static final String button_config = 	USER_PATH + "//Documents//epicsoundboard//buttonconfig.yml";
	
//	private static final String SPOTIFY_CLIENT_ID = "75b84efdd12942aea58177fb442cb77a";
//	private static final String SPOTIFY_CLIENT_SECRET = "e943bbdea71a4f3699d5088dcbebc89c";
//	private static final String SPOTIFY_REDIRECT_URI = "http://localhost:8888/callback/";
	
	
	//public final static String vol_name = "volume";
	
    public static void main(String[] args) {}
	
//    public static void initpaths(ApplicationType type, String root) {
//    	if(type == ApplicationType.Android) {
//    		configpath = root + "//epicsoundboard//config.yml";
//    	} else {
//    		configpath = "C://Users//" + System.getProperty( "user.name" ) + "//Documents//epicsoundboard//config.yml";
//    	}    	
//    }
    
	@Override
	public void create () {
		System.out.println("file.encoding=" + System.getProperty("file.encoding"));
		Main.init(Gdx.app.getType());
		
		batch = new SpriteBatch();
		
//		cache = new SpriteCache();
//		cache.setTransformMatrix(batch.getTransformMatrix());
//		cache.setProjectionMatrix(batch.getProjectionMatrix());
		
		//LogInfo
		System.out.println("Java-Version: " + System.getProperty("java.version"));
		System.out.println("Location: " + System.getProperty("user.dir").toString());
		System.out.println("Data-Path: " + System.getProperty("user.home").toString());
		System.out.println("Java_Location: " + System.getProperty("java.home").toString());
		System.out.println(System.getProperty("os.name").toString() + " v."+ System.getProperty("os.version").toString() + " " + System.getProperty("os.arch").toString());
		File f = new File(temp_path);
		if(!f.exists()) f.mkdirs();
		System.setProperty("java.io.tmpdir", temp_path);		
		
		
		
		
//		System.out.println(System.getProperty("java.class.path").toString());		
//		System.out.println(System.getProperty("java.vendor").toString());
//		System.out.println(System.getProperty("java.vendor.url").toString());
//		System.out.println(System.getProperty("line.separator"));
//		System.out.println(System.getProperty("path.separator").toString());
//		System.out.println(System.getProperty("user.name").toString());
		
		
		new GameStateManager();		
		GameStateManager.init();
		GameStateManager.changestate((short) 0);
		
		b = new Button("v.r12", new Vector2(0,0));
		b.setFontScale(0.40f);
		b.setScale(0.6f);
		b.setPosition(Gdx.graphics.getWidth()-b.getwidth()-10, 5);		
		b.setInteractable(false);
		//b.show = true;
		
		
		System.out.println("Initialising K-API " + com.karmorak.api.Main.getVersionString() + " ...");
		try {
			@SuppressWarnings("unused")
			Global_GestureWrapper wrapper = new Global_GestureWrapper();
			Global_GestureWrapper.registerAdapater(new Manager_Controls());
			System.out.println("Initialisation Completed");
		} catch (Exception e) { e.printStackTrace(); }
		
		System.out.println("Loading LavaPlayer...");
		try {	
			
			audioPlayerManager = new DefaultAudioPlayerManager();
			AudioSourceManagers.registerRemoteSources(audioPlayerManager);
			AudioSourceManagers.registerLocalSource(audioPlayerManager);
			player = audioPlayerManager.createPlayer();
			
			trackScheduler = new TrackScheduler(player);
			player.addListener(trackScheduler);
 						
			System.out.println("...Ready");
		} catch (Exception e) { 			
			System.out.println("...something went wrong with the audio player");
			e.printStackTrace(); 
		}
		
//		SpotifyApi api = new SpotifyApi.Builder().setClientId(SPOTIFY_CLIENT_ID).setClientSecret(SPOTIFY_CLIENT_SECRET).build();
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		
		

		
		batch.begin();
		GameStateManager.update();
		GameStateManager.draw(batch);
		b.draw(batch);
		batch.end();		
		
//		cache.begin();
//		for(int i : Main.caches)
//			cache.draw(i);
//		cache.end();
		//Main.caches.clear();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		Main.dispose(logpath);
		GameStateManager.dispose();
		if(audiomanager != null) audiomanager.closeAudioConnection();
		System.exit(0);
	}
	
	public static void initDiscord(String token) throws LoginException, InterruptedException {
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		
		builder.setToken(token);
		builder.setAutoReconnect(true);
		builder.setStatus(OnlineStatus.ONLINE);
								
		jda = builder.buildBlocking();
		jda.addEventListener(new JDAListener());
			
		Guild guild = jda.getGuilds().get(0);								
		audiomanager = guild.getAudioManager();
		audiomanager.setSendingHandler(new Soundboard_AudioSendHandler(player));
	}
	
	public static void connectDiscordVoice(String voice_id) throws LoginException, InterruptedException {		
		if(voice_id != null && voice_id != "") {
			try {
				voicechannel = jda.getVoiceChannelById(Long.parseLong(voice_id));
				audiomanager.openAudioConnection(voicechannel);
			} catch (Exception e) {
				for(VoiceChannel c : jda.getVoiceChannels()) {											
					Soundboard.voicechannel = c;
					try {
						audiomanager.openAudioConnection(voicechannel);
						break;
					} catch (Exception e2) {
						e2.printStackTrace();
					}		
				}
			}	
		}
	}
	
//	public static Soundboard_AudioLoadResultHandler getResulthandler() {
//		return resulthandler;
//	}
	
	
}
