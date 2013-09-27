package me.happypikachu.BattleTags.managers;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mcsg.double0negative.tabapi.TabAPI;

public class BattleTagsTabManager extends TagsManager implements Listener {
	
	private boolean grouping = false;
	
	public BattleTagsTabManager(BattleTags plugin) {
		super(plugin);
		grouping = plugin.getConfig().getBoolean("group", false);
	}
	
	@EventHandler
	public void login(PlayerLoginEvent e){
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			update(p);
		}
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e){
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			update(p);
		}
	}
	
	@Override
	public void update(Player player) {
		TabAPI.clearTab(player);
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			
		}
	}
	
}
