package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.TagAPI;

import com.massivecraft.factions.event.FactionsEventMembershipChange;
import com.massivecraft.factions.event.FactionsEventDisband;
import com.massivecraft.factions.event.FactionsEventRelationChange;

public class BattleTagsFactionsListener implements Listener {
	private BattleTags plugin;
    public BattleTagsFactionsListener(BattleTags plugin) {
            this.plugin = plugin;
    }
	
    @EventHandler
	public void onFPlayerJoin (FactionsEventMembershipChange event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
	
    @EventHandler
	public void onFactionDisband (FactionsEventDisband event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
	
    @EventHandler
	public void onFactionRelation (FactionsEventRelationChange event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
}