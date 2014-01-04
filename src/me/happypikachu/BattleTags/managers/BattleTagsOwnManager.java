package me.happypikachu.BattleTags.managers;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BattleTagsOwnManager extends BattleTagsManager implements Listener {
	
	public BattleTagsOwnManager(BattleTags plugin) {
		super(plugin);
	}
	
	@EventHandler
	public void login(PlayerJoinEvent e){
		//loop over all playerssdvzc 
		  
	}

	@Override
	public void update(Player player) {
		// TODO Auto-generated method stub
		
	}
}
