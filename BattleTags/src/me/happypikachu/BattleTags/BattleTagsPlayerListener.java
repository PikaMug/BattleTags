package me.happypikachu.BattleTags;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.massivecraft.factions.Conf;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Rel;
import com.tommytony.war.Team;

public class BattleTagsPlayerListener implements Listener {
	private BattleTags plugin;
    public BattleTagsPlayerListener(BattleTags plugin) {
            this.plugin = plugin;
    }
    
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onNameTag(PlayerReceiveNameTagEvent event) {
		if (plugin.getServer().getPluginManager().getPlugin("Factions") != null) {
			if (plugin.getConfig().getBoolean("Factions." + event.getNamedPlayer().getWorld().getName())) {
				Faction pFaction = FPlayers.i.get(event.getPlayer()).getFaction();
				Faction npFaction = FPlayers.i.get(event.getNamedPlayer()).getFaction();
				if (plugin.getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion().startsWith("1.7")) {
					if (pFaction.getRelationTo(npFaction).equals(Rel.ALLY)) {
						event.setTag(Conf.colorAlly + event.getNamedPlayer().getName());
					} else if (pFaction.getRelationTo(npFaction).equals(Rel.ENEMY)) {
						event.setTag(Conf.colorEnemy + event.getNamedPlayer().getName());
					} else if (pFaction.getRelationTo(npFaction).equals(Rel.MEMBER)) {
						event.setTag(Conf.colorMember + event.getNamedPlayer().getName());
					} else if (pFaction.getRelationTo(npFaction).equals(Rel.NEUTRAL)) {
						event.setTag(Conf.colorNeutral + event.getNamedPlayer().getName());
					}
				} else {
					if (pFaction.getRelationTo(npFaction).isAlly()) {
						event.setTag(Conf.colorAlly + event.getNamedPlayer().getName());
					} else if (pFaction.getRelationTo(npFaction).isEnemy()) {
						event.setTag(Conf.colorEnemy + event.getNamedPlayer().getName());
					} else if (pFaction.getRelationTo(npFaction).isMember()) {
						event.setTag(Conf.colorMember + event.getNamedPlayer().getName());
					} else if (pFaction.getRelationTo(npFaction).isNeutral()) {
						event.setTag(Conf.colorNeutral + event.getNamedPlayer().getName());
					}
				}
			}
		}
		if (plugin.getServer().getPluginManager().getPlugin("SimpleClans") != null) {
			SimpleClans sc = (SimpleClans) plugin.getServer().getPluginManager().getPlugin("SimpleClans");
			Clan pClan = sc.getClanManager().getClanByPlayerName(event.getPlayer().getName());
			Clan npClan = sc.getClanManager().getClanByPlayerName(event.getNamedPlayer().getName());
			if (pClan.isAlly(npClan.getTag())) {
				event.setTag(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("SimpleClans.ally")) + event.getNamedPlayer().getName());
			}
			if (pClan.isMember(event.getPlayer().getName())) {
				event.setTag(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("SimpleClans.member")) + event.getNamedPlayer().getName());
			}
			if (pClan.isRival(npClan.getTag())) {
				event.setTag(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("SimpleClans.rival")) + event.getNamedPlayer().getName());
			} else if (pClan.isWarring(npClan.getTag())) {
				event.setTag(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("SimpleClans.warring")) + event.getNamedPlayer().getName());
			}
		}
		if (plugin.getServer().getPluginManager().getPlugin("War") != null) {
			if (plugin.getConfig().getBoolean("War." + event.getNamedPlayer().getWorld().getName())) {
				if (Team.getTeamByPlayerName(event.getNamedPlayer().getName()) != null) {
					event.setTag(Team.getTeamByPlayerName(event.getNamedPlayer().getName()).getKind().getColor() + event.getNamedPlayer().getName());
				}
			}
		}
	}
}