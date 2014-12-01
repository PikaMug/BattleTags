package me.happypikachu.BattleTags.managers;

import me.happypikachu.BattleTags.BattleTags;
import me.happypikachu.BattleTags.events.BattleTagsCustomTagEvent;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class BattleTagsManager implements Listener{
	protected BattleTags plugin;

	public BattleTagsManager(BattleTags plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * 
	 * @param player
	 * @param seenPlayer
	 * @return
	 */
	protected String getTag(Player player, Player seenPlayer) {
		return getTag(player.getName(), seenPlayer.getName());
	}
	
	public String getTag(String player, String seenPlayer){
		ChatColor tag = plugin.getListeners().getTag(player, seenPlayer);
		
		//Allow integration from other plugins
		BattleTagsCustomTagEvent tagEvent = new BattleTagsCustomTagEvent(player, seenPlayer, tag);
		plugin.getServer().getPluginManager().callEvent(tagEvent);
		return tagEvent.getTag() + tagEvent.getSeenPlayer();
	}

	/**
	 * @param player
	 */
	public void update(Player player) {
	}

	/**
	 * 
	 */
	public void shutdown() {
	}
	
	/**
	 * @return
	 */
	protected BattleTags getPL() {
		return plugin;
	}

	/**
	 * @param p
	 */
	public void clear(Player p) {
	}

	/**
	 * @param name
	 */
	public void removePlayer(String name) {
	}
}
