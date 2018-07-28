package com.karmorak.game;

import java.util.ArrayList;

public class ButtonRegisters {
	
	
	public static final short BUTTONS = 0;
	public static final short YTBER_BUTTONS = 1;
	public static final short HIDED_BUTTONS = 2;
	
	public static final ArrayList<ArrayList<SoundButton>> lists = new ArrayList<>();
	
	public static final ArrayList<SoundButton> buttons = new ArrayList<>();
	protected static final ArrayList<SoundButton> ytber_buttons = new ArrayList<>();
	public static final ArrayList<SoundButton> hided_buttons = new ArrayList<>();
	
	protected static final void init(float volume) {
		
		lists.add(buttons);
		lists.add(ytber_buttons);
		lists.add(hided_buttons);
		
		new SoundButton(1f * volume, "assets//sounds//mario_coin.ogg", "Mario Coin", buttons);
		
		String sound = "assets//sounds//dragonball_kameha.ogg";		
		new SoundButton(volume, sound, "Kameehha", buttons);
		
		sound = "assets//sounds//mario.ogg";					
		new SoundButton(volume, sound, "mario", buttons);	
		
		sound = "assets//sounds//mario_lose.ogg";					
		new SoundButton(volume, sound, "Mario Lose", buttons);
		
		sound = "assets//sounds//mario_lvlup.ogg";					
		new SoundButton(volume, sound, "mario 1-UP", buttons);
		
		sound = "assets//sounds//mario_win.ogg";					
		new SoundButton(volume, sound, "mario Win", buttons);
		
		sound = "assets//sounds//mario_lose2.ogg";					
		new SoundButton(volume, sound, "mario lose2", buttons);		
		
		sound = "assets//sounds//mario_jump.ogg";
		new SoundButton(volume, sound, "Mario Jump", buttons);	
		
		sound = "assets//sounds//ohhhh.ogg";
		new SoundButton(0.95f * volume, sound, "whooho", buttons);
				
		sound = "assets//sounds//ohkeh.ogg";		
		new SoundButton(1f * volume, sound, "Oh Keh", buttons);
		
		sound = "assets//sounds//spsw_fantasie.ogg";		
		new SoundButton(1f * volume, sound, "Spongebob Fantasie", buttons);		
		
		sound = "assets//sounds//facepalm.ogg";		
		new SoundButton(1.2f * volume, sound, "Facepalm", buttons);
		
		sound = "assets//sounds//goofy_laugh.ogg";		
		new SoundButton(1f * volume, sound, "Goofy Lache", buttons);
		
		sound = "assets//sounds//goofy_vell.ogg";		
		new SoundButton(1f * volume, sound, "Goofy Fallend", buttons);		
		
		sound = "assets//sounds//spsw_klaus.ogg";		
		new SoundButton(1f * volume, sound, "Spongebob Klaus", buttons);
		
		sound = "assets//sounds//letsgetstarted.ogg";		
		new SoundButton(1f * volume, sound, "lets get started", buttons);		
		
		sound = "assets//sounds//donnerbuddys.ogg";		
		new SoundButton(1f * volume, sound, "Donnersong", buttons);	
		
		sound = "assets//sounds//escelated_quickly.ogg";
		new SoundButton(1f * volume, sound, "Escelated quickly", buttons);
		
		sound = "assets//sounds//psycho_long.ogg";		
		new SoundButton(1f * volume, sound, "Psycho (lang)", buttons);
		
		sound = "assets//sounds//psycho.ogg";
		new SoundButton(1f * volume, sound, "Psycho", buttons);
		
		sound = "assets//sounds//sw_lukeno.ogg";		
		new SoundButton(1f * volume, sound, "Luke noo", buttons);	
		
		sound = "assets//sounds//sw_vaderno.ogg";		
		new SoundButton(1f * volume, sound, "Vader noo", buttons);	
		
		sound = "assets//sounds//willhelm.ogg";		
		new SoundButton(1f * volume, sound, "Willhelm Schrei", buttons);	
		
		sound = "assets//sounds//fus_ro_dah.ogg";		
		new SoundButton(1f * volume, sound, "Fus Ro Dah", buttons);
		
		sound = "assets//sounds//watcha_watcha.ogg";		
		new SoundButton(1f * volume, sound, "RKO Watcha", buttons);
		
		sound = "assets//sounds//bennyhill.ogg";					
		new SoundButton(1f * volume, sound, "Benny Hill", buttons);	
		
		sound = "assets//sounds//das_tat_weh.ogg";					
		new SoundButton(1f * volume, sound, "Uhh das tat weh", buttons);
		
		sound = "assets//sounds//read_all_about_it.ogg";					
		new SoundButton(1f * volume, sound, "sad", buttons);		
		
		sound = "assets//sounds//here_comes_the_money.ogg";					
		new SoundButton(1f * volume, sound, "Here comes the money", buttons);
		
		sound = "assets//sounds//danke_iblali.ogg";					
		new SoundButton(1f * volume, sound, "Danke IBlali", buttons);
		
		sound = "assets//sounds//paf_okaay.ogg";					
		new SoundButton(1f * volume, sound, "Klap Ohkaaaj", buttons);
		
		sound = "assets//sounds//uhh_watchasay.ogg";					
		new SoundButton(1f * volume, sound, "Uhh Watcha say", buttons);
		
		sound = "assets//sounds//slow_clap.ogg";					
		new SoundButton(1f * volume, sound, "langsames Klatschen", buttons);
		
		sound = "assets//sounds//stroh.ogg";					
		new SoundButton(1f * volume, sound, "Stroh? Maske?", buttons);
		
		sound = "assets//sounds//kungfu_fighting.ogg";					
		new SoundButton(1f * volume, sound, "Kung Fu Fighting", buttons);
		
		sound = "assets//sounds//hamma.ogg";					
		new SoundButton(1f * volume, sound, "Hamma", buttons);
		
		sound = "assets//sounds//gonna_ok.ogg";					
		new SoundButton(volume, sound, "its gonna ok", buttons);
		
		sound = "assets//sounds//badumtss.ogg";					
		new SoundButton(volume, sound, "badumtss", buttons);
		
		sound = "assets//sounds//afro_circus.ogg";			
		new SoundButton(1f * volume, sound, "Afro Zirkus", buttons);
		
		sound = "assets//sounds//goodbye_mother.ogg";
		new SoundButton(1f * volume, sound, "Goodbye Motherfucker", buttons);	
		
		sound = "assets//sounds//shutdown_life.ogg";
		new SoundButton(1f * volume, sound, "Shutdown Life", buttons);	
		
		sound = "assets//sounds//win_shutdown.ogg";
		new SoundButton(1f * volume, sound, "WinXP Shutdown", buttons);	
		
		sound = "assets//sounds//feel_good.ogg";
		new SoundButton(1f * volume, sound, "Wow i feel good", buttons);	
		
		sound = "assets//sounds//raining_man.ogg";			
		new SoundButton(1f * volume, sound, "It`s Raining Man", buttons);	
		
		sound = "assets//sounds//helloitsme.ogg";			
		new SoundButton(1f * volume, sound, "Hello it`s me", buttons);	
		
		sound = "assets//sounds//jobforme.ogg";			
		new SoundButton(1f * volume, sound, "Job for me", buttons);	
		
		sound = "assets//sounds//hagay.ogg";			
		new SoundButton(1f * volume, sound, "HA GAAAY", buttons);	
		sound = "assets//sounds//shootingstars.ogg";			
		new SoundButton(0.95f * volume, sound, "Shooting Stars", buttons);	
		
		sound = "assets//sounds//dotheflop.ogg";			
		new SoundButton(1f * volume, sound, "everbody do the Flop", buttons);	
		
		sound = "assets//sounds//nogoodno.ogg";			
		new SoundButton(1.15f * volume, sound, "NO GOOD PLEASE NOO", buttons);	
		
		sound = "assets//sounds//undwarum.ogg";			
		new SoundButton(1.05f * volume, sound, "Und Warum", buttons);	
		
		sound = "assets//sounds//fuckthepolice.ogg";			
		new SoundButton(0.95f * volume, sound, "Fuck the Police", buttons);	
		
		sound = "assets//sounds//beepbeepimasheep.ogg";			
		new SoundButton(0.85f * volume, sound, "Beep Beep i`am a sheep", buttons);
		
		sound = "assets//sounds//thatswhatshesaid.ogg";			
		new SoundButton(1.25f * volume, sound, "Thats what she said", buttons);	
		
		sound = "assets//sounds//triggered.ogg";			
		new SoundButton(0.9f * volume, sound, "Triggered", buttons);	
		
		sound = "assets//sounds//fireinthehole.ogg";			
		new SoundButton(1.2f * volume, sound, "Fire in the hole", buttons);	
		
		sound = "assets//sounds//iamtheone.ogg";			
		new SoundButton(1f * volume, sound, "I`am the One", buttons);		
		
		sound = "assets//sounds//howyouare.ogg";			
		new SoundButton(volume, sound, "The ask you how you are", buttons);	

		String[] m = new String[2];
		m[0] = "assets//sounds//rezo_wannafu.ogg";
		m[1] = "assets//sounds//wannafu.ogg";
		new SoundButton(volume, m, "I Wanna Fu...", buttons);		
		
		
		m = new String[6];
		m[0] = "assets//sounds//maxim//gege.ogg";
		m[1] = "assets//sounds//maxim//gege_1.ogg";
		m[2] = "assets//sounds//maxim//gege_2.ogg";
		m[3] = "assets//sounds//maxim//gege_3.ogg";
		m[4] = "assets//sounds//maxim//gege_4.ogg";
		m[5] = "assets//sounds//maxim//gege_5.ogg";		
		new SoundButton(1f * volume, m, "Ja Ge Ge", ytber_buttons);		
		
		sound = "assets//sounds//maxim//trigger.ogg";			
		new SoundButton(1f * volume, sound, "Triggered", ytber_buttons);	
		
		sound = "assets//sounds//maxim//boosted.ogg";			
		new SoundButton(volume, sound, "Boosted", ytber_buttons);
		
		sound = "assets//sounds//maxim//grammar.ogg";			
		new SoundButton(volume, sound, "Grammatik", ytber_buttons);

		sound = "assets//sounds//maxim_istdasnormal.ogg";			
		new SoundButton(volume, sound, "Iiiist das Normal", ytber_buttons);	
		
		sound = "assets//sounds//irelia_main.ogg";					
		new SoundButton(volume, sound, "Irelia stil the main", ytber_buttons);
		
		m = new String[2];
		m[0] = "assets//sounds//gronkh//gronkh_5vswilli_2.ogg";
		m[1] = "assets//sounds//gronkh//gronkh_5vswilli.ogg";		
		new SoundButton(volume, m, "Gronkh 5 VS Willi", ytber_buttons);	
		
		sound = "assets//sounds//gronkh//gronkh_neidisch.ogg";			
		new SoundButton(volume, sound, "Gronkhs Neid", ytber_buttons);	
		
		sound = "assets//sounds//gronkh//gronkh_puma.ogg";			
		new SoundButton(volume, sound, "Ohh Ein Puma", ytber_buttons);	
		
		sound = "assets//sounds//gronkh//gronkh_currysloch.ogg";			
		new SoundButton(volume, sound, "Currywursts Loch", ytber_buttons);
		
		sound = "assets//sounds//gronkh//gronkh_umweltschutz.ogg";			
		new SoundButton(volume, sound, "Gronkh Umweltschutz", ytber_buttons);	
				
		
//		buttons.add(mario);
//		buttons.add(mario_win);
//		buttons.add(mario_lose);
//		buttons.add(mario_lose2);
//		buttons.add(mario_lvlup);
//		buttons.add(mario_jump);
//		buttons.add(dragonball_kameha);
//		
//		//Micky Maus
//		buttons.add(goofy_yell);
//		buttons.add(goofy_laugh);	
//		//Spongebob
//		buttons.add(spsw_fantasie);
//		buttons.add(spsw_klaus);
//		
//		
//		//YT Standard
//		buttons.add(ohhh);
//		buttons.add(ohkeh);
//		buttons.add(facepalm);
//		buttons.add(slow_clap);
//		buttons.add(badumtss);
//		buttons.add(escelated_quickly);
//		buttons.add(willhelm);
//		buttons.add(danke_iblali);	
//		buttons.add(howyouare);
//	
//		buttons.add(psycho);
//		buttons.add(psycho_long);
//		buttons.add(sw_lukeno);
//		buttons.add(sw_vaderno);
//	
//		buttons.add(watcha_watcha);		
//		buttons.add(paf_okaay);		
//		buttons.add(stroh);		
//		buttons.add(afro_circus);
//				
//		buttons.add(win_shutdown);
//		buttons.add(shutdown_life);	
//
//		buttons.add(nogoodno);
//		buttons.add(thatswhatshesaid);
//		buttons.add(hagay);		
//		buttons.add(triggered);
//		buttons.add(fireinthehole);
//		
//		buttons.add(dotheflop);
//		buttons.add(beepbeepimasheep);
//		buttons.add(iamtheone);
//		buttons.add(shootingstars);		
//		buttons.add(fuckthepolice);
//		
//		//Music
//		buttons.add(fus_ro_dah);		
//		buttons.add(bennyhill);
//		buttons.add(das_tat_weh);
//		buttons.add(read_all_about_it);
//		buttons.add(here_comes_the_money);
//		buttons.add(kungfu_fighting);
//		buttons.add(hamma);
//		buttons.add(gonna_ok);
//		buttons.add(uhh_watchasay);		
//		buttons.add(donnerbudys);
//		buttons.add(letsgetstarted);
//		buttons.add(raining_man);
//		buttons.add(feel_good);
//		buttons.add(goodbye_mother);
//		buttons.add(helloitsme);
//		buttons.add(jobforme);
//		buttons.add(undwarum);
//		buttons.add(wanna_fu);
		
		

	}

}
