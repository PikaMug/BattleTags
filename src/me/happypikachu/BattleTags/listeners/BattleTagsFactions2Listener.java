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
    public BattleTagsFactions2Listener(BattleTags plugin) {
            this.plugin = plugin;
    }
	
    @EventHandler
	public void onFPlayerJoin (FactionsEventMembershipChange event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				plugin.update(p);
			}
		}
	}
	
    @EventHandler
	public void onFactionDisband (FactionsEventDisband event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				plugin.update(p);
			}
		}
	}
	
    @EventHandler
	public void onFactionRelation (FactionsEventRelationChange event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				plugin.update(p);
			}
		}
	}
}