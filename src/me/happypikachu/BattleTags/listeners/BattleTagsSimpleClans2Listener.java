package me.happypikachu.BattleTags.listeners;

import com.p000ison.dev.simpleclans2.api.SCCore;
import com.p000ison.dev.simpleclans2.api.clanplayer.ClanPlayer;
import com.p000ison.dev.simpleclans2.api.events.ClanCreateEvent;
import com.p000ison.dev.simpleclans2.api.events.ClanRelationBreakEvent;
import com.p000ison.dev.simpleclans2.api.events.ClanRelationCreateEvent;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

public class BattleTagsSimpleClans2Listener extends BattleTagsListener {
	
	public BattleTagsSimpleClans2Listener(BattleTags plugin) {
		super(plugin, "SimpleClans2");
    }
	
	@EventHandler
	public void onClanCreate (ClanCreateEvent e) {
		update(e.getClan().getAllMembers().toArray(new ClanPlayer[1])[0].getName());
	}
	
	@EventHandler
	public void onClanRelationBreak (ClanRelationBreakEvent e) {
		update(e.getClan().getAllMembers().toArray(new ClanPlayer[1])[0].getName());
	}
	
	@EventHandler
	public void onClanRelationCreate (ClanRelationCreateEvent e) {
		update(e.getClan().getAllMembers().toArray(new ClanPlayer[1])[0].getName());
	}

	/**
	  * @see me.happypikachu.BattleTags.listeners.BattleTagsListener#getRelation(java.lang.String, java.lang.String)
	  */
	@Override
	public ChatColor getRelation(String viewer, String seen) {
		SCCore sc= (SCCore) plugin.getServer().getPluginManager().getPlugin("SimpleClans2");
		if (sc.getClanPlayerManager().getClanPlayer(viewer).getClan().isAlly(sc.getClanPlayerManager().getClanPlayer(seen).getClan())) {
			return ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.ally"));
		} else if (sc.getClanPlayerManager().getClanPlayer(viewer).getClan().isMember(sc.getClanPlayerManager().getClanPlayer(seen))) {
			return ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.member"));
		} else if (sc.getClanPlayerManager().getClanPlayer(viewer).getClan().isRival(sc.getClanPlayerManager().getClanPlayer(seen).getClan())) {
			return ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.rival"));
		} else if (sc.getClanPlayerManager().getClanPlayer(viewer).getClan().isWarring(sc.getClanPlayerManager().getClanPlayer(seen).getClan())) {
			return ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.warring"));
		}
		return null;
	}
}
