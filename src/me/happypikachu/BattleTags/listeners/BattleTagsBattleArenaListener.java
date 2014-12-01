/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

import mc.alk.arena.BattleArena;
import mc.alk.arena.controllers.TeamController;
import mc.alk.arena.events.matches.MatchFinishedEvent;
import mc.alk.arena.events.players.ArenaPlayerEnterEvent;
import mc.alk.arena.events.players.ArenaPlayerJoinEvent;
import mc.alk.arena.events.players.ArenaPlayerLeaveEvent;
import mc.alk.arena.objects.ArenaPlayer;
import me.happypikachu.BattleTags.BattleTags;

/**
 * @author Brord
 *
 */
public class BattleTagsBattleArenaListener extends BattleTagsListener {

	/**
	 * @param plugin
	 * @param listener
	 */
	public BattleTagsBattleArenaListener(BattleTags plugin) {
		super(plugin, "BattleArena");
	}

	@EventHandler
	public void arenaJoin(ArenaPlayerJoinEvent e){
		update(e.getPlayer().getPlayer());
	}
	
	@EventHandler
	public void arenaLeave(ArenaPlayerLeaveEvent e){
		update(e.getPlayer().getPlayer());
	}
	
	@EventHandler
	public void arenaEnter(ArenaPlayerEnterEvent e){
		update(e.getPlayer().getPlayer());
	}
	
	@EventHandler
	public void arenaFinish(MatchFinishedEvent e){
		update(e.getMatch().getPlayers().toArray(new ArenaPlayer[0])[0].getPlayer());
	}

	/**
	  * @see me.happypikachu.BattleTags.listeners.BattleTagsListener#getRelation(java.lang.String, java.lang.String)
	  */
	@Override
	public ChatColor getRelation(String viewer, String seen) {
		if(BattleArena.inArena(BattleArena.toArenaPlayer(Bukkit.getPlayer(viewer)))){
			if (TeamController.getTeam(BattleArena.toArenaPlayer(Bukkit.getPlayer(viewer))).hasMember(BattleArena.toArenaPlayer(Bukkit.getPlayer(seen)))){
				//teammate
				return ChatColor.getByChar(plugin.getConfig().getString("BattleArena.teammate"));
			} else {
				//enemy
				return ChatColor.getByChar(plugin.getConfig().getString("BattleArena.enemy"));
			}
		} 
		return null;
	}
}
