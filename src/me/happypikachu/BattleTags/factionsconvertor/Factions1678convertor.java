package me.happypikachu.BattleTags.factionsconvertor;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Factions1678convertor extends FactionsConvertor {

	@Override
	public ChatColor getRelColor(Player player, Player namedPlayer) {
		Faction pFaction = FPlayers.i.get(player).getFaction();
		Faction npFaction = FPlayers.i.get(namedPlayer).getFaction();
		
		return pFaction.getColorTo(npFaction);
	}

}
