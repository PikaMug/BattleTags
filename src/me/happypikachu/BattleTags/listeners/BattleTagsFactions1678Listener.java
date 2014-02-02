package me.happypikachu.BattleTags.listeners;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.event.FactionRelationEvent;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.event.EventHandler;

public class BattleTagsFactions1678Listener extends BattleTagListener{
	
    public BattleTagsFactions1678Listener(BattleTags plugin) {
    	super(plugin, "Factions");
    }
	
	@EventHandler
	public void onFPlayerJoin (FPlayerJoinEvent e) {
		update(e.getFPlayer().getPlayer());
	}

	@EventHandler
	public void onFPlayerLeave (FPlayerLeaveEvent e) {
		update(e.getFPlayer().getPlayer());
	}

    @EventHandler
	public void onFactionDisband (FactionDisbandEvent e) {
    	update(e.getFPlayer().getPlayer());
	}

    @EventHandler
	public void onFactionRelation (FactionRelationEvent e) {
    	update(e.getFaction().getFPlayers().toArray(new FPlayer[1])[0].getPlayer());
	}
}
