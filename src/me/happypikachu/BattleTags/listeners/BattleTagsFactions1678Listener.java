package me.happypikachu.BattleTags.listeners;

import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.event.FactionRelationEvent;
import me.happypikachu.BattleTags.BattleTags;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BattleTagsFactions1678Listener implements Listener {
	
	private BattleTags plugin;
	
    public BattleTagsFactions1678Listener(BattleTags plugin) {
            this.plugin = plugin;
    }
	
	@EventHandler
	public void onFPlayerJoin (FPlayerJoinEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				plugin.update(p);
			}
		}
	}

	@EventHandler
	public void onFPlayerLeave (FPlayerLeaveEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				plugin.update(p);
			}
		}
	}

    @EventHandler
	public void onFactionDisband (FactionDisbandEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				plugin.update(p);
			}
		}
	}

    @EventHandler
	public void onFactionRelation (FactionRelationEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				plugin.update(p);
			}
		}
	}
}
