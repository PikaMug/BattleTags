package me.happypikachu.BattleTags.managers;

import org.bukkit.ChatColor;

import me.happypikachu.BattleTags.listeners.BattleTagsListener;

/**
 * Project BattleTags<br/>
 * Class me.happypikachu.BattleTags.managers.ListenerManager.java<br/>
 * @author Brord
 * @since 30 nov. 2014, 17:15:32
 */
public class ListenerManager extends Manager<BattleTagsListener>{

	public ListenerManager() {super(BattleTagsListener.class);}

	public ChatColor getTag(String player, String seen){
		ChatColor c = ChatColor.WHITE, temp;
		for (BattleTagsListener l : getAll()){
			temp = l.getRelation(player, seen);
			//Starting off with white, so we dont care
			if (temp != null && temp != ChatColor.WHITE) c = temp;
		}
		return c;
	}
}
