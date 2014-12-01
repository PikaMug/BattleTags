package me.happypikachu.BattleTags.listeners;

import com.tommytony.war.Team;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BattleTagsWarListener extends BattleTagsListener {
	
    public BattleTagsWarListener(BattleTags plugin) {
    	super(plugin, "War");
    }
    
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if (Team.getTeamByPlayerName(e.getPlayer().getName()) != null) {
			update(e.getPlayer());
		}
    }

	/**
	  * @see me.happypikachu.BattleTags.listeners.BattleTagsListener#getRelation(java.lang.String, java.lang.String)
	  */
	@Override
	public ChatColor getRelation(String viewer, String seen) {
		if (Team.getTeamByPlayerName(seen) != null) {
			return Team.getTeamByPlayerName(seen).getKind().getColor();
		}
		return null;
	}
}