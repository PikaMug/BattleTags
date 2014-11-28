package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.event.EventHandler;

import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.event.EventFactionsRelationChange;

public class BattleTagsFactions2Listener extends BattleTagListener {
	
    public BattleTagsFactions2Listener(BattleTags plugin) {
    	super(plugin, "Factions");
    }
	
    @EventHandler
	public void onFPlayerJoin (EventFactionsMembershipChange e) {
    	System.out.println(e.getMPlayer().getName() + " " + e.getMSender().getName());
    	update(e.getMPlayer().getPlayer());
	}
	
    @EventHandler
	public void onFactionDisband (EventFactionsDisband e) {
    	update(e.getMSender().getPlayer());
	}
	
    @EventHandler
	public void onFactionRelation (EventFactionsRelationChange e) {
    	update(e.getMSender().getPlayer());
    	update(e.getOtherFaction().getOnlinePlayers().get(0));
	}
}