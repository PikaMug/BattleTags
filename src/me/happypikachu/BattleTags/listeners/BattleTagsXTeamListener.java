/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import org.bukkit.event.EventHandler;

import me.happypikachu.BattleTags.BattleTags;
import me.protocos.xteam.api.event.TeamCreateEvent;
import me.protocos.xteam.api.event.TeamDisbandEvent;

/**
 * @author Brord
 *
 */
public class BattleTagsXTeamListener extends BattleTagListener {
	
    public BattleTagsXTeamListener(BattleTags plugin) {
    	super(plugin, "xTeam");
    }
    
    @EventHandler
    public void create(TeamCreateEvent e){
    	update(e.getTeam().getLeader());
    }

	@EventHandler
    public void disband(TeamDisbandEvent e){
		update(e.getTeam().getLeader());
    }
}
