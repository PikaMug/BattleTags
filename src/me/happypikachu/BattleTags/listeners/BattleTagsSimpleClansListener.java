package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.api.events.SimpleClansClanCreateEvent;
import net.sacredlabyrinth.phaed.simpleclans.api.events.SimpleClansJoinEvent;
import net.sacredlabyrinth.phaed.simpleclans.api.events.SimpleClansLeaveEvent;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

public class BattleTagsSimpleClansListener extends BattleTagsListener {
	
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

	/**
	  * @see me.happypikachu.BattleTags.listeners.BattleTagsListener#getRelation(java.lang.String, java.lang.String)
	  */
	@Override
	public ChatColor getRelation(String viewer, String seen) {
		SimpleClans sc = (SimpleClans) plugin.getServer().getPluginManager().getPlugin("SimpleClans");
		Clan pClan = sc.getClanManager().getClanByPlayerName(viewer);
		Clan npClan = sc.getClanManager().getClanByPlayerName(seen);
		if (pClan.isAlly(npClan.getTag())) {
			return ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.ally"));
		} else if (pClan.isMember(seen)) {
			return ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.member"));
		} else if (pClan.isRival(npClan.getTag())) {
			return ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.rival"));
		} else if (pClan.isWarring(npClan.getTag())) {
			return ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.warring"));
		}
		return null;
	}
}