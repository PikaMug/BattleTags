/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Brord
 *
 */
public class BattleTagListener implements Listener {
	
	private BattleTags plugin;
	private String listener;
	
    public BattleTagListener(BattleTags plugin, String listener) {
            this.plugin = plugin;
            this.listener = listener;
    }
    
    protected void update(final Player p){
    	if (plugin.getConfig().getBoolean(listener + "." + p.getWorld().getName(), false)) {
    		new BukkitRunnable(){
    			@Override
    			public void run() {
    				plugin.update(p);
    			}
    		}.runTask(plugin);
		}
    }

    /**
   	 * @param name
   	 */
   	protected void update(String name) {
   		if (plugin.getServer().getPlayer(name) != null){
   			update(plugin.getServer().getPlayer(name));
   		}
   	}
}
