/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;
import me.protocos.xteam.api.event.TeamCreateEvent;
import me.protocos.xteam.api.event.TeamDisbandEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Brord
 *
 */
public class BattleTagsXTeamListener implements Listener {
	
	private BattleTags plugin;
	
	private Runnable updateTask = new Runnable() {
		@Override
		public void run() {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (plugin.getConfig().getBoolean("xTeam." + p.getWorld().getName())) {
					plugin.update(p);
				}
			}
		}
	};
	
    public BattleTagsXTeamListener(BattleTags plugin) {
            this.plugin = plugin;
    }
    
    @EventHandler
    public void create(TeamCreateEvent e){
    	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, updateTask);
    }
    
    @EventHandler
    public void disband(TeamDisbandEvent e){
    	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, updateTask);
    }
}
