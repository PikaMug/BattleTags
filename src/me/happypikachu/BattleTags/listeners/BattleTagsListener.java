/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;
import me.happypikachu.BattleTags.managers.Managable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * @author Brord
 *
 */
public abstract class BattleTagsListener implements Listener, Managable{
	
	protected BattleTags plugin;
	protected String listener;
	
    public BattleTagsListener(BattleTags plugin, String listener) {
            this.plugin = plugin;
            this.listener = listener;
    }
    
    public abstract ChatColor getRelation(String viewer, String seen);
    
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

	/**
	  * @see me.happypikachu.BattleTags.managers.Managable#name()
	  */
	@Override
	public String name() {
		return listener;
	}
}
