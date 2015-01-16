package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.TagAPI;

import com.p000ison.dev.simpleclans2.api.events.ClanCreateEvent;
import com.p000ison.dev.simpleclans2.api.events.ClanRelationBreakEvent;
import com.p000ison.dev.simpleclans2.api.events.ClanRelationCreateEvent;

public class BattleTagsSimpleClans2Listener implements Listener {
    private BattleTags plugin;
	public BattleTagsSimpleClans2Listener(BattleTags plugin) {
            this.plugin = plugin;
    }
	
	@EventHandler
	public void onClanCreate (ClanCreateEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("SimpleClans2." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
	
	@EventHandler
	public void onClanRelationBreak (ClanRelationBreakEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("SimpleClans2." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
	
	@EventHandler
	public void onClanRelationCreate (ClanRelationCreateEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("SimpleClans2." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
}
