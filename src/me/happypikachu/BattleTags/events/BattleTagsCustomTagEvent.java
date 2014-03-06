package me.happypikachu.BattleTags.events;

import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BattleTagsCustomTagEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	@Override public HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }
	
	private String player, seenPlayer;
	private ChatColor tag;
	
	public BattleTagsCustomTagEvent(String player, String seenPlayer, ChatColor tag) {
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
	public ChatColor getTag() {
		return tag;
	}
	public void setTag(ChatColor tag) {
		this.tag = tag;
	}
}
