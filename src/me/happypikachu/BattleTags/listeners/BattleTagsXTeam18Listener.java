/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

import me.happypikachu.BattleTags.BattleTags;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.event.TeamCreateEvent;
import me.protocos.xteam.event.TeamDisbandEvent;
import me.protocos.xteam.event.TeamJoinEvent;
import me.protocos.xteam.event.TeamLeaveEvent;

/**
 * @author Brord
 *
 */
public class BattleTagsXTeam18Listener extends BattleTagsListener {
	
    public BattleTagsXTeam18Listener(BattleTags plugin) {
    	super(plugin, "xTeam");
    }
    
    @EventHandler
    public void create(TeamCreateEvent e){
    	update(e.getTeam().getLeader());
    }
    
    @EventHandler
    public void disband(TeamDisbandEvent e){
    	for (TeamPlayer t : e.getTeam().getOnlineTeammates()){
    		update(t.getName());
    	}
    }

	@EventHandler
    public void join(TeamJoinEvent e){
		update(e.getPlayer().getName());
    }
	
	@EventHandler
    public void leave(TeamLeaveEvent e){
		update(e.getPlayer().getName());
    }

	/**
	  * @see me.happypikachu.BattleTags.listeners.BattleTagsListener#getRelation(java.lang.String, java.lang.String)
	  */
	@Override
	public ChatColor getRelation(String viewer, String seen) {
		XTeam xteam= (XTeam) plugin.getServer().getPluginManager().getPlugin("xTeam");
		if (xteam.getPlayerFactory().getPlayer(viewer) != null) {
			if (!xteam.getPlayerFactory().getPlayer(viewer).hasTeam()){
				return ChatColor.getByChar(plugin.getConfig().getString("xTeam.neutral"));
			} else if (xteam.getPlayerFactory().getPlayer(viewer).getTeammates().contains(xteam.getPlayerFactory().getPlayer(seen))){
				return ChatColor.getByChar(plugin.getConfig().getString("xTeam.ally"));
			} else {
				return ChatColor.getByChar(plugin.getConfig().getString("xTeam.enemy"));
			}
		} else {
			return null;
		}
	}
}
