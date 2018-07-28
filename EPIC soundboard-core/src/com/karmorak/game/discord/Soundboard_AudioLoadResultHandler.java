package com.karmorak.game.discord;

import com.karmorak.game.Soundboard;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class Soundboard_AudioLoadResultHandler implements AudioLoadResultHandler {

	@Override
	public void trackLoaded(AudioTrack track) {
		Soundboard.trackScheduler.queue(track);
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		for (AudioTrack track : playlist.getTracks()) {
			Soundboard.trackScheduler.queue(track);
		}
		
	}

	@Override
	public void noMatches() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadFailed(FriendlyException exception) {
		// TODO Auto-generated method stub
		
	}	

}
