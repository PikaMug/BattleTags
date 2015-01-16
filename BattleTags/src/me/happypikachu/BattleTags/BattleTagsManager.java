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
import com.p000ison.dev.simpleclans2.api.SCCore;
import com.palmergames.bukkit.towny.utils.CombatUtil;
import com.tommytony.war.Team;

public class BattleTagsManager implements Listener {
	private BattleTags plugin;
	public BattleTagsManager(BattleTags plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onNameTag(PlayerReceiveNameTagEvent event) {
		//Factions by Brettflan
		if (plugin.getServer().getPluginManager().isPluginEnabled("Factions")) {
			if (plugin.getConfig().getBoolean("Factions." + event.getNamedPlayer().getWorld().getName())) {
				Faction pFaction = FPlayers.i.get(event.getPlayer()).getFaction();
				Faction npFaction = FPlayers.i.get(event.getNamedPlayer()).getFaction();
				if (plugin.getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion().startsWith("1.7")
						|| plugin.getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion().startsWith("1.8")) {
					if pFaction == null {
						event.setTag(Conf.colorNeutral + event.getNamedPlayer().getName());
					}
					if pFaction == Wilderness {
						event.setTag(Conf.colorNeutral + event.getNamedPlayer().getName());
					}
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
					plugin.getLogger().warning("Hooked into unexpected Factions version (" + plugin.getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion() + ")." +
							" Try a different version of Factions or BattleTags? Disable support in all worlds to remove this message.");
				}
			}
		}

		//SimpleClans by phaed420
		if (plugin.getServer().getPluginManager().isPluginEnabled("SimpleClans")) {
			SimpleClans sc = (SimpleClans) plugin.getServer().getPluginManager().getPlugin("SimpleClans");
			Clan pClan = sc.getClanManager().getClanByPlayerName(event.getPlayer().getName());
			Clan npClan = sc.getClanManager().getClanByPlayerName(event.getNamedPlayer().getName());
			if (pClan.isAlly(npClan.getTag())) {
				event.setTag(ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.ally")) + event.getNamedPlayer().getName());
			} else if (pClan.isMember(event.getPlayer().getName())) {
				event.setTag(ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.member")) + event.getNamedPlayer().getName());
			} else if (pClan.isRival(npClan.getTag())) {
				event.setTag(ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.rival")) + event.getNamedPlayer().getName());
			} else if (pClan.isWarring(npClan.getTag())) {
				event.setTag(ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.warring")) + event.getNamedPlayer().getName());
			}
		}

		//SimpleClans2 by p000ison
		if (plugin.getServer().getPluginManager().isPluginEnabled("SimpleClans2")) {
			SCCore sc= (SCCore) plugin.getServer().getPluginManager().getPlugin("SimpleClans2");
			if (sc.getClanPlayerManager().getClanPlayer(event.getPlayer().getName()).getClan().isAlly(sc.getClanPlayerManager().getClanPlayer(event.getNamedPlayer().getName()).getClan())) {
				event.setTag(ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.ally")) + event.getNamedPlayer().getName());
			} else if (sc.getClanPlayerManager().getClanPlayer(event.getPlayer().getName()).getClan().isMember(sc.getClanPlayerManager().getClanPlayer(event.getNamedPlayer().getName()))) {
				event.setTag(ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.member")) + event.getNamedPlayer().getName());
			} else if (sc.getClanPlayerManager().getClanPlayer(event.getPlayer().getName()).getClan().isRival(sc.getClanPlayerManager().getClanPlayer(event.getNamedPlayer().getName()).getClan())) {
				event.setTag(ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.rival")) + event.getNamedPlayer().getName());
			} else if (sc.getClanPlayerManager().getClanPlayer(event.getPlayer().getName()).getClan().isWarring(sc.getClanPlayerManager().getClanPlayer(event.getNamedPlayer().getName()).getClan())) {
				event.setTag(ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.warring")) + event.getNamedPlayer().getName());
			}
		}

		//Towny by ElgarL
		if (plugin.getServer().getPluginManager().isPluginEnabled("Towny")) {
			if (CombatUtil.isAlly(event.getPlayer().getName(), event.getNamedPlayer().getName())) {
				event.setTag(ChatColor.getByChar(plugin.getConfig().getString("Towny.ally")) + event.getNamedPlayer().getName());
			} else if (CombatUtil.isEnemy(event.getPlayer().getName(), event.getNamedPlayer().getName())) {
				event.setTag(ChatColor.getByChar(plugin.getConfig().getString("Towny.enemy")) + event.getNamedPlayer().getName());
			}
		}

		//War by tommytony
		if (plugin.getServer().getPluginManager().isPluginEnabled("War")) {
			if (plugin.getConfig().getBoolean("War." + event.getNamedPlayer().getWorld().getName())) {
				if (Team.getTeamByPlayerName(event.getNamedPlayer().getName()) != null) {
					event.setTag(Team.getTeamByPlayerName(event.getNamedPlayer().getName()).getKind().getColor() + event.getNamedPlayer().getName());
				}
			}
		}
	}
}