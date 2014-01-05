/**
 * 
 */
package me.happypikachu.BattleTags.compatibility;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.filoghost.healthbar.api.BarHideEvent;

/**
 * @author Brord
 *
 */
public class HealthBar implements Listener{
	
	BattleTags plugin;
	
	/**
	 * 
	 */
	public HealthBar(BattleTags plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void bar(BarHideEvent e){
		plugin.update(Bukkit.getPlayer(e.getOfflinePlayer().getName()));
	}
}
