package me.happypikachu.BattleTags.listeners;

import com.tommytony.war.Team;
import com.tommytony.war.Warzone;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BattleTagsWarListener extends BattleTagListener {
	private BattleTags plugin;
	
    public BattleTagsWarListener(BattleTags plugin) {
    	super(plugin, "War");
    }
    
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if (Team.getTeamByPlayerName(e.getPlayer().getName()) != null) {
			for (Team team : Warzone.getZoneByPlayerName(e.getPlayer().getName()).getTeams()) {
				for (Player p : team.getPlayers()) {
					if (plugin.getConfig().getBoolean("War." + p.getWorld().getName())) {
						plugin.update(p);
					}
				}
			}
		}
    }
}