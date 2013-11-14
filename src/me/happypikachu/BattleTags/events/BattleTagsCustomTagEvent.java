package me.happypikachu.BattleTags.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BattleTagsCustomTagEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	@Override public HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }
	
	private String player, seenPlayer, tag;
	
	public BattleTagsCustomTagEvent(String player, String seenPlayer, String tag) {
		this.player = player;
		this.seenPlayer = seenPlayer;
		this.tag = tag;
	}
	
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getSeenPlayer() {
		return seenPlayer;
	}
	public void setSeenPlayer(String seenPlayer) {
		this.seenPlayer = seenPlayer;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}
