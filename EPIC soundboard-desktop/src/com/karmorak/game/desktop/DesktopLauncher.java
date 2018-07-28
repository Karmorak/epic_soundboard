package com.karmorak.game.desktop;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.karmorak.api.files.FileManager;
import com.karmorak.api.Main;
import com.karmorak.api.Resolutions;
import com.karmorak.game.Soundboard;



public class DesktopLauncher {
	
	private static final int DEF_RATIO = 3;
	private static final int DEF_RES = 4;
	
	public static void main (String[] arg) {		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		Main.init(ApplicationType.Desktop);
		Resolutions.initRes();		
		
		int ratio = -1;
		int width = -1;
		int height = -1;
		
		
		try {
			String r = FileManager.getString(Soundboard.configpath,"ratio");
			System.out.println(r);
			ratio = Integer.parseInt(r);
		} catch (NullPointerException | NumberFormatException e) {
			FileManager.setString(Soundboard.configpath, "ratio",""+ DEF_RATIO);
			e.printStackTrace();
			ratio = DEF_RATIO;
		}	

		try {
			System.out.println(FileManager.getString(Soundboard.configpath, "res"));
			int res = Integer.parseInt(FileManager.getString(Soundboard.configpath, "res"));			
			width = Integer.parseInt(Resolutions.resolutions.get(ratio).get(res).split(":")[0]);			
			height = Integer.parseInt(Resolutions.resolutions.get(ratio).get(res).split(":")[1]);			
		} catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
			FileManager.setString(Soundboard.configpath, "res",""+ DEF_RES);
			e.printStackTrace();
		}
		
		if(width == -1 || height == -1) {
			width = Integer.parseInt(Resolutions.resolutions.get(0).get(DEF_RES).split(":")[0]);
			height = Integer.parseInt(Resolutions.resolutions.get(DEF_RATIO).get(DEF_RES).split(":")[1]);
		}
		
		boolean screen = false;
		
		try {
			String s  = FileManager.getString(Soundboard.configpath, "screen");		
			if(s.contains("1")) screen = true;
		} catch (NullPointerException e) {
			FileManager.setString(Soundboard.configpath, "screen", "0");
			e.printStackTrace();
		}
		
		
		config.width = width;
		config.height = height;
		config.fullscreen = screen;
		config.samples = 16;
		config.title = "EPIC Soundboard";
		config.addIcon("assets//button_red.png", FileType.Internal);
		config.forceExit = true;
		config.resizable = false;
		config.backgroundFPS = 30;
		config.foregroundFPS = 60;
		
		new LwjglApplication(new Soundboard(), config);
	}
}
