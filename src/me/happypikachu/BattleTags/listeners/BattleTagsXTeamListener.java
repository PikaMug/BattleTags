/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

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
	
	@EventHandler(priority = EventPriority.HIGH)
	public void command(PlayerCommandPreprocessEvent e){
		if (e.isCancelled()) return;
		
		String[] message = e.getMessage().startsWith("/") && e.getMessage().length() > 1 ? e.getMessage().substring(1, e.getMessage().length()).split(" ") : null;
		if (message == null) return;
		
		String cmd = message[0];
		if ((cmd = containsCommand(cmd)) == null) return;
		if (message[1] == null || message[1] == "") return;
		
		//is he changing teams?
		if (message[1].equals("join") || message[1].equals("leave") || message[1].equals("accept")){
			update(e.getPlayer());
		}
	}
	
	private String containsCommand(String cmd){
		Map<String, String[]> commands = plugin.getServer().getCommandAliases();
		for (String command : commands.keySet()){
			if (cmd.toLowerCase().equals(command.toLowerCase())) return command;
			for (String alias : commands.get(command)){
				if (cmd.toLowerCase().equals(alias.toLowerCase())) return command;
			}
		}
		return null;
	}
}
