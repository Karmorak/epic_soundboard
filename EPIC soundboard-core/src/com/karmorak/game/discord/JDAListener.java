package com.karmorak.game.discord;

import com.karmorak.game.Soundboard;

import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JDAListener extends ListenerAdapter {

	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
		super.onGuildVoiceJoin(event);
		
		Soundboard.voicechannel = event.getChannelJoined();
		
		
	}

}
