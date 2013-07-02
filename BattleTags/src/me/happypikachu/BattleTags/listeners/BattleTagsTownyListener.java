package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.TagAPI;

//import com.palmergames.bukkit.towny.event.PlayerChangePlotEvent;
//import com.palmergames.bukkit.towny.event.TownAddResidentEvent;
//import com.palmergames.bukkit.towny.event.TownRemoveResidentEvent;

public class BattleTagsTownyListener implements Listener {
    private BattleTags plugin;
	public BattleTagsTownyListener(BattleTags plugin) {
            this.plugin = plugin;
    }
	
//	@EventHandler
//	public void onPlayerChangePlotEvent (PlayerChangePlotEvent event) {
//		for (Player p : Bukkit.getOnlinePlayers()) {
//			if (plugin.getConfig().getBoolean("Towny." + p.getWorld().getName())) {
//				TagAPI.refreshPlayer(p);
//			}
//		}
//	}
//	
//	@EventHandler
//	public void onTownAddResident (TownAddResidentEvent event) {
//		for (Player p : Bukkit.getOnlinePlayers()) {
//			if (plugin.getConfig().getBoolean("Towny." + p.getWorld().getName())) {
//				TagAPI.refreshPlayer(p);
//			}
//		}
//	}
//	
//	@EventHandler
//	public void onTownRemoveResident (TownRemoveResidentEvent event) {
//		for (Player p : Bukkit.getOnlinePlayers()) {
//			if (plugin.getConfig().getBoolean("Towny." + p.getWorld().getName())) {
//				TagAPI.refreshPlayer(p);
//			}
//		}
//	}
}
