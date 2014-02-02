package me.happypikachu.BattleTags.managers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcsg.double0negative.tabapi.TabAPI;

public class BattleTagsTabManager extends BattleTagsManager implements Listener {
	
	private boolean grouping = false;
	
	public BattleTagsTabManager(BattleTags plugin) {
		super(plugin);
		grouping = plugin.getConfig().getBoolean("group", false);
	}
	
	@EventHandler
	public void login(PlayerLoginEvent e){
		new BukkitRunnable(){
			@Override
			public void run() {
				for (Player p : Bukkit.getServer().getOnlinePlayers()){
					update(p);
				}
			}
		}.runTask(plugin);
		
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e){
		new BukkitRunnable(){
			@Override
			public void run() {
				for (Player p : Bukkit.getServer().getOnlinePlayers()){
					update(p);
				}
			}
		}.runTask(plugin);
	}
	
	/* (non-Javadoc)
	 * @see me.happypikachu.BattleTags.managers.BattleTagsManager#clear(org.bukkit.entity.Player)
	 */
	@Override
	public void clear(Player p) {
		TabAPI.clearTab(p);
	}
	
	@Override
	public void update(Player player) {
		TabAPI.clearTab(player);
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		
		List<Player> orderedplayers = new LinkedList<Player>();
		if (grouping){
			
		} else {
			orderedplayers = Arrays.asList(players);
		}
		
		for (int i=0; i < orderedplayers.size(); i++){
			if (i > (TabAPI.getHorizSize()*TabAPI.getVertSize()) ) break;
			TabAPI.setTabString(plugin, player, ((int)Math.floor(i/3)), i%3, getTag(player, orderedplayers.get(i)));
		}
		TabAPI.updatePlayer(player);
	}
	
}
