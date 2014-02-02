package me.happypikachu.BattleTags.listeners;

import com.tommytony.war.Team;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BattleTagsWarListener extends BattleTagListener {
	
    public BattleTagsWarListener(BattleTags plugin) {
    	super(plugin, "War");
    }
    
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if (Team.getTeamByPlayerName(e.getPlayer().getName()) != null) {
			update(e.getPlayer());
		}
    }
}