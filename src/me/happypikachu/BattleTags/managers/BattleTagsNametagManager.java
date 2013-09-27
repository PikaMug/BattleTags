package me.happypikachu.BattleTags.managers;

import com.haribo98.nametag.NameTagReceiveEvent;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class BattleTagsNametagManager extends TagsManager implements Listener {
	
    public BattleTagsNametagManager(BattleTags plugin) {
    	super(plugin);
    }
    
    public void onTagReceive(NameTagReceiveEvent e) {
        e.setNameTag(getTag(e.getReceiver(), e.getChanged()));
    }

	@Override
	public void update(Player player) {
		if (player != null){
			for (Player p : Bukkit.getServer().getOnlinePlayers()){
				if (p.getWorld().getName().equals(player.getWorld().getName()) && p.canSee(player)){
					new NameTagReceiveEvent(p, player);
				}
			}
		}
	}
}
