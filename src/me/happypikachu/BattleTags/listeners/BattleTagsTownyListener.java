package me.happypikachu.BattleTags.listeners;

import com.palmergames.bukkit.towny.event.TownAddResidentEvent;
import com.palmergames.bukkit.towny.event.TownRemoveResidentEvent;
import com.palmergames.bukkit.towny.utils.CombatUtil;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

public class BattleTagsTownyListener extends BattleTagsListener {
    
	public BattleTagsTownyListener(BattleTags plugin) {
		super(plugin, "Towny");
    }
	
	@EventHandler
	public void onTownAddResident (TownAddResidentEvent e) {
		update(e.getResident().getName());
	}
	
	@EventHandler
	public void onTownRemoveResident (TownRemoveResidentEvent e) {
		update(e.getResident().getName());
	}

	/**
	  * @see me.happypikachu.BattleTags.listeners.BattleTagsListener#getRelation(java.lang.String, java.lang.String)
	  */
	@Override
	public ChatColor getRelation(String viewer, String seen) {
		if (CombatUtil.isAlly(viewer, seen)) {
			return ChatColor.getByChar(plugin.getConfig().getString("Towny.ally"));
		} else if (CombatUtil.isEnemy(viewer, seen)) {
			return ChatColor.getByChar(plugin.getConfig().getString("Towny.enemy"));
		} else {
			return null;
		}
	}
}
