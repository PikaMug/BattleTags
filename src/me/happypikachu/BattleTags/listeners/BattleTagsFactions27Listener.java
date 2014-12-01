package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.event.EventFactionsRelationChange;

public class BattleTagsFactions27Listener extends BattleTagsListener {
	
    public BattleTagsFactions27Listener(BattleTags plugin) {
    	super(plugin, "Factions");
    }
	
    @EventHandler
	public void onFPlayerJoin (EventFactionsMembershipChange e) {
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

	/**
	  * @see me.happypikachu.BattleTags.listeners.BattleTagsListener#getRelation(java.lang.String, java.lang.String)
	  */
	@Override
	public ChatColor getRelation(String viewer, String seen) {
		Faction upFaction = MPlayer.get(viewer).getFaction();
		Faction up2Faction = MPlayer.get(seen).getFaction();
		return upFaction.getColorTo(up2Faction);
	}
}