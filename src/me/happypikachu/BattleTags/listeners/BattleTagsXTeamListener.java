/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.happypikachu.BattleTags.BattleTags;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.event.TeamCreateEvent;
import me.protocos.xteam.event.TeamDisbandEvent;

/**
 * @author Brord
 *
 */
public class BattleTagsXTeamListener extends BattleTagsListener {
	
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
	
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void command(PlayerCommandPreprocessEvent e){
		if (e.isCancelled()) return;
		
		String[] message = e.getMessage().startsWith("/") && e.getMessage().length() > 1 ? e.getMessage().substring(1, e.getMessage().length()).split(" ") : null;
		if (message == null || message.length < 2) return;
		
		String arg = message[1];
		if (!message[0].equalsIgnoreCase("team")) return;
		if (arg == null || arg.equals("")) return;
		
		//is he changing teams?
		if (arg.equalsIgnoreCase("join") || arg.equalsIgnoreCase("leave") || arg.equalsIgnoreCase("accept")){
			update(e.getPlayer());
		}
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
