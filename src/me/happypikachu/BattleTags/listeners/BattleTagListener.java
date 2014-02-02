/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

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
    	if (p == null) return;
    	
    	if (plugin.getConfig().getBoolean(listener + "." + p.getWorld().getName(), false)) {
    		plugin.update(p);
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
