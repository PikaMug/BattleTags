package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;
import net.sacredlabyrinth.phaed.simpleclans.api.events.SimpleClansClanCreateEvent;
import net.sacredlabyrinth.phaed.simpleclans.api.events.SimpleClansJoinEvent;
import net.sacredlabyrinth.phaed.simpleclans.api.events.SimpleClansLeaveEvent;

import org.bukkit.event.EventHandler;

public class BattleTagsSimpleClansListener extends BattleTagListener {
	
	public BattleTagsSimpleClansListener(BattleTags plugin) {
		super(plugin, "SimpleClans");
    }
	
	@EventHandler
	public void onSimpleClansClanCreateEvent (SimpleClansClanCreateEvent e) {
		update(e.getClanPlayer().getName());
	}
	
	@EventHandler
	public void onSimpleClansJoin (SimpleClansJoinEvent e) {
		update(e.getClanPlayer().getName());
	}
	
	@EventHandler
	public void onSimpleClansLeave (SimpleClansLeaveEvent e) {
		update(e.getClanPlayer().getName());
	}
}