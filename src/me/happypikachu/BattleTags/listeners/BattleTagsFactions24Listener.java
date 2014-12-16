package me.happypikachu.BattleTags.listeners;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.factions.event.FactionsEventDisband;
import com.massivecraft.factions.event.FactionsEventMembershipChange;
import com.massivecraft.factions.event.FactionsEventRelationChange;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

public class BattleTagsFactions24Listener extends BattleTagsListener {
	
    public BattleTagsFactions24Listener(BattleTags plugin) {
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
    	update(e.getOtherFaction().getOnlinePlayers().get(0));
	}

	/**
	  * @see me.happypikachu.BattleTags.listeners.BattleTagsListener#getRelation(java.lang.String, java.lang.String)
	  */
	@Override
	public ChatColor getRelation(String viewer, String seen) {
		Faction upFaction = UPlayer.get(getPlayer(viewer)).getFaction();
		Faction up2Faction = UPlayer.get(getPlayer(seen)).getFaction();
		return upFaction.getColorTo(up2Faction);
	}
}