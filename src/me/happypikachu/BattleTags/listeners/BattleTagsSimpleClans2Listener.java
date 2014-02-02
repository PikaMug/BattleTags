package me.happypikachu.BattleTags.listeners;

import com.p000ison.dev.simpleclans2.api.clanplayer.ClanPlayer;
import com.p000ison.dev.simpleclans2.api.events.ClanCreateEvent;
import com.p000ison.dev.simpleclans2.api.events.ClanRelationBreakEvent;
import com.p000ison.dev.simpleclans2.api.events.ClanRelationCreateEvent;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.event.EventHandler;

public class BattleTagsSimpleClans2Listener extends BattleTagListener {
	
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
}
