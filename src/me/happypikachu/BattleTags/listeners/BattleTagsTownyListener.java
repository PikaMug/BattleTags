package me.happypikachu.BattleTags.listeners;

import com.palmergames.bukkit.towny.event.TownAddResidentEvent;
import com.palmergames.bukkit.towny.event.TownRemoveResidentEvent;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BattleTagsTownyListener implements Listener {
    private BattleTags plugin;
    
    private Runnable updateTask = new Runnable() {
		@Override
		public void run() {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (plugin.getConfig().getBoolean("Towny." + p.getWorld().getName(), false)) {
					plugin.update(p);
				}
			}
		}
	};
    
	public BattleTagsTownyListener(BattleTags plugin) {
            this.plugin = plugin;
    }
	
	
	@EventHandler
	public void onTownAddResident (TownAddResidentEvent event) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, updateTask);
	}
	
	@EventHandler
	public void onTownRemoveResident (TownRemoveResidentEvent event) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, updateTask);
	}
}
