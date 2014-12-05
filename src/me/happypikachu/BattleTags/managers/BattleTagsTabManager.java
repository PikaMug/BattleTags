package me.happypikachu.BattleTags.managers;

import java.util.Arrays;
import java.util.Collection;
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
	
	@Override
	public void clear(Player p) {
		TabAPI.clearTab(p);
	}
	
	@Override
	public void update(Player player) {
		clear(player);
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		
		List<Player> orderedplayers = new LinkedList<Player>();
		if (grouping){
			orderedplayers.addAll(groupMe(player, players));
		} else {
			orderedplayers = Arrays.asList(players);
		}
		
		for (int i=0; i < orderedplayers.size(); i++){
			if (i > (TabAPI.getHorizSize()*TabAPI.getVertSize()) ) break;
			TabAPI.setTabString(plugin, player, ((int)Math.floor(i/3)), i%3, getTag(player, orderedplayers.get(i)));
		}
		TabAPI.updatePlayer(player);
	}

	/**
	 * @param players
	 * @return
	 */
	private Collection<Player> groupMe(Player orig, Player[] players) {
		java.util.List<Player> pls = new java.util.LinkedList<Player>();
		Player lp;
		for (Player p : players){
			for (int i = 0; i < pls.size(); i++) {
				lp = pls.get(i);
				if (getTag(orig, lp) == getTag(orig, p)) pls.add(i, p);
			}
			pls.add(p);
		}
		return pls;
	}
	
}
