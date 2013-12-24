package me.happypikachu.BattleTags.managers;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcsg.double0negative.tabapi.TabAPI;

public class BattleTagsTabManager extends TagsManager implements Listener {
	
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
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			update(p);
		}
	}
	
	@Override
	public void update(Player player) {
		TabAPI.clearTab(player);
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		if (grouping){
			
		} else {
			for (int i=0; i < players.length; i++){
				if (i > (TabAPI.getHorizSize()*TabAPI.getVertSize()) ) break;
				
				//i = 20, 20/3 == 
				System.out.println(Math.floor(i/3) + " | " + i%3);
				TabAPI.setTabString(plugin, players[i], (int)Math.floor(i/3), i%3, getTag(players[i], player));
			}
		}
		TabAPI.updateAll();
	}
	
}
