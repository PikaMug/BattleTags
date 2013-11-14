package me.happypikachu.BattleTags.listeners;

import com.massivecraft.factions.event.FactionsEventDisband;
import com.massivecraft.factions.event.FactionsEventMembershipChange;
import com.massivecraft.factions.event.FactionsEventRelationChange;
import me.happypikachu.BattleTags.BattleTags;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BattleTagsFactions2Listener implements Listener {
	private BattleTags plugin;
	private Runnable updateTask = new Runnable() {
		@Override
		public void run() {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
					plugin.update(p);
				}
			}
		}
	};
	
    public BattleTagsFactions2Listener(BattleTags plugin) {
            this.plugin = plugin;
    }
	
    @EventHandler
	public void onFPlayerJoin (FactionsEventMembershipChange event) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, updateTask);
	}
	
    @EventHandler
	public void onFactionDisband (FactionsEventDisband event) {
    	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, updateTask);
	}
	
    @EventHandler
	public void onFactionRelation (FactionsEventRelationChange event) {
    	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, updateTask);
	}
}