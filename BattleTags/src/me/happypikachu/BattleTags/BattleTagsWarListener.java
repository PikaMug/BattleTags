package me.happypikachu.BattleTags;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.kitteh.tag.TagAPI;

import com.tommytony.war.Team;
import com.tommytony.war.Warzone;

public class BattleTagsWarListener implements Listener {
	private BattleTags plugin;
    public BattleTagsWarListener(BattleTags plugin) {
            this.plugin = plugin;
    }
    
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (Team.getTeamByPlayerName(event.getPlayer().getName()) != null) {
			for (Team team : Warzone.getZoneByPlayerName(event.getPlayer().getName()).getTeams()) {
				for (Player p : team.getPlayers()) {
					if (plugin.getConfig().getBoolean("War." + p.getWorld().getName())) {
						TagAPI.refreshPlayer(p);
					}
				}
			}
		}
    }
}