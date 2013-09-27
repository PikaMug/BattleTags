package me.happypikachu.BattleTags.factionsconvertor;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Factions20convertor extends FactionsConvertor {

	@Override
	public ChatColor getRelColor(Player player, Player namedPlayer) {
		Faction upFaction = UPlayer.get(player).getFaction();
		Faction up2Faction = UPlayer.get(namedPlayer).getFaction();
		ChatColor color = ChatColor.WHITE;
		
		try {
			color = upFaction.getColorTo(up2Faction);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		if (color != null){
			return color;
		} else {
			return ChatColor.WHITE;
		}
	}

}
