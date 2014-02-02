package me.happypikachu.BattleTags.listeners;

import com.palmergames.bukkit.towny.event.TownAddResidentEvent;
import com.palmergames.bukkit.towny.event.TownRemoveResidentEvent;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.event.EventHandler;

public class BattleTagsTownyListener extends BattleTagListener {
    
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
}
