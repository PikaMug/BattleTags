package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;
import net.sacredlabyrinth.phaed.simpleclans.api.events.SimpleClansClanCreateEvent;
import net.sacredlabyrinth.phaed.simpleclans.api.events.SimpleClansJoinEvent;
import net.sacredlabyrinth.phaed.simpleclans.api.events.SimpleClansLeaveEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.TagAPI;

public class BattleTagsSimpleClansListener implements Listener {
    private BattleTags plugin;
	public BattleTagsSimpleClansListener(BattleTags plugin) {
            this.plugin = plugin;
    }
	
	@EventHandler
	public void onSimpleClansClanCreateEvent (SimpleClansClanCreateEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("SimpleClans." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
	
	@EventHandler
	public void onSimpleClansJoin (SimpleClansJoinEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("SimpleClans." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
	
	@EventHandler
	public void onSimpleClansLeave (SimpleClansLeaveEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("SimpleClans." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
}