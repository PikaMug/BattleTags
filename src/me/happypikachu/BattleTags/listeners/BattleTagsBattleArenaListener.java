/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import org.bukkit.event.EventHandler;

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
public class BattleTagsBattleArenaListener extends BattleTagListener {

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
		System.out.println("arenaenter update");
		update(e.getPlayer().getPlayer());
	}
	
	@EventHandler
	public void arenaFinish(MatchFinishedEvent e){
		update(e.getMatch().getPlayers().toArray(new ArenaPlayer[0])[0].getPlayer());
	}
}
