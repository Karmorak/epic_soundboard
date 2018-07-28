package com.karmorak.game.discord;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.sedmelluq.discord.lavaplayer.filter.PcmFilterFactory;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;

public class Soundboard_AudioPlayer implements AudioPlayer {

	@Override
	public AudioFrame provide() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AudioFrame provide(long timeout, TimeUnit unit) throws TimeoutException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean provide(MutableAudioFrame targetFrame) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean provide(MutableAudioFrame targetFrame, long timeout, TimeUnit unit)
			throws TimeoutException, InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AudioTrack getPlayingTrack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void playTrack(AudioTrack track) {
	}

	@Override
	public boolean startTrack(AudioTrack track, boolean noInterrupt) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stopTrack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVolume(int volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFilterFactory(PcmFilterFactory factory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFrameBufferDuration(Integer duration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPaused() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPaused(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void addListener(AudioEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(AudioEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkCleanup(long threshold) {
		// TODO Auto-generated method stub
		
	}

}
