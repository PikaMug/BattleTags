package me.happypikachu.BattleTags.listeners;

import com.massivecraft.factions.event.FactionsEventDisband;
import com.massivecraft.factions.event.FactionsEventMembershipChange;
import com.massivecraft.factions.event.FactionsEventRelationChange;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.event.EventHandler;

public class BattleTagsFactions2Listener extends BattleTagListener {
	
    public BattleTagsFactions2Listener(BattleTags plugin) {
    	super(plugin, "Factions");
    }
	
    @EventHandler
	public void onFPlayerJoin (FactionsEventMembershipChange e) {
    	update(e.getUPlayer().getPlayer());
	}
	
    @EventHandler
	public void onFactionDisband (FactionsEventDisband e) {
    	update(e.getUSender().getPlayer());
	}
	
    @EventHandler
	public void onFactionRelation (FactionsEventRelationChange e) {
    	update(e.getUSender().getPlayer());
	}
}