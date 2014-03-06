package me.happypikachu.BattleTags.managers;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.AncientRPGInfo;
import com.p000ison.dev.simpleclans2.api.SCCore;
import com.palmergames.bukkit.towny.utils.CombatUtil;
import com.tommytony.war.Team;

import mc.alk.arena.BattleArena;
import mc.alk.arena.competition.match.ArenaMatch;
import mc.alk.arena.controllers.PlayerStoreController;
import mc.alk.arena.controllers.TeamController;
import mc.alk.arena.objects.ArenaPlayer;
import mc.alk.arena.objects.scoreboard.base.ArenaBukkitScoreboard;
import me.happypikachu.BattleTags.BattleTags;
import me.happypikachu.BattleTags.events.BattleTagsCustomTagEvent;
import me.happypikachu.BattleTags.factionsconvertor.Factions1678convertor;
import me.happypikachu.BattleTags.factionsconvertor.Factions20convertor;
import me.happypikachu.BattleTags.factionsconvertor.FactionsConvertor;
import me.protocos.xteam.xTeam;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class BattleTagsManager implements Listener{
	protected BattleTags plugin;
	protected FactionsConvertor factions;

	public BattleTagsManager(BattleTags plugin) {
		this.plugin = plugin;
		setupFactions();
	}
	
	public void setupFactions(){
		if (plugin.getServer().getPluginManager().isPluginEnabled("Factions")){
			String version = plugin.getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion();
			if (version.startsWith("2.")){
				factions =  new Factions20convertor();
			} else if (version.startsWith("1.6") || version.startsWith("1.7") || version.startsWith("1.8")){
				factions =  new Factions1678convertor();
			}
		}
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
		ChatColor tag = ChatColor.WHITE;
		if (Bukkit.getPlayer(player)== null || Bukkit.getPlayer(seenPlayer) == null) return tag + seenPlayer;
		
		Player p = Bukkit.getPlayer(player);
		Player seen = Bukkit.getPlayer(seenPlayer);
		
		if (plugin.getServer().getPluginManager().isPluginEnabled("Factions")){
			String worldName = p.getWorld().getName();
			if (plugin.getConfig().getBoolean("Factions." + worldName)){
				tag = factions.getRelColor(p, seen);
			}
		} else if (plugin.getServer().getPluginManager().isPluginEnabled("SimpleClans")) {//SimpleClans by phaed420
			SimpleClans sc = (SimpleClans) plugin.getServer().getPluginManager().getPlugin("SimpleClans");
			Clan pClan = sc.getClanManager().getClanByPlayerName(player);
			Clan npClan = sc.getClanManager().getClanByPlayerName(seenPlayer);
			if (pClan.isAlly(npClan.getTag())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.ally"));
			} else if (pClan.isMember(seenPlayer)) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.member"));
			} else if (pClan.isRival(npClan.getTag())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.rival"));
			} else if (pClan.isWarring(npClan.getTag())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.warring"));
			}
		} else if (plugin.getServer().getPluginManager().isPluginEnabled("SimpleClans2")) { //SimpleClans2 by p000ison
			SCCore sc= (SCCore) plugin.getServer().getPluginManager().getPlugin("SimpleClans2");
			if (sc.getClanPlayerManager().getClanPlayer(player).getClan().isAlly(sc.getClanPlayerManager().getClanPlayer(seenPlayer).getClan())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.ally"));
			} else if (sc.getClanPlayerManager().getClanPlayer(player).getClan().isMember(sc.getClanPlayerManager().getClanPlayer(seenPlayer))) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.member"));
			} else if (sc.getClanPlayerManager().getClanPlayer(player).getClan().isRival(sc.getClanPlayerManager().getClanPlayer(seenPlayer).getClan())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.rival"));
			} else if (sc.getClanPlayerManager().getClanPlayer(player).getClan().isWarring(sc.getClanPlayerManager().getClanPlayer(seenPlayer).getClan())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.warring"));
			}
		} else if (plugin.getServer().getPluginManager().isPluginEnabled("Towny")) { //Towny by ElgarL
			if (CombatUtil.isAlly(player, seenPlayer)) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("Towny.ally"));
			} else if (CombatUtil.isEnemy(player, seenPlayer)) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("Towny.enemy"));
			}
		} else if (plugin.getServer().getPluginManager().isPluginEnabled("xTeam")) { //xTeam by protocos
			if (plugin.getConfig().getBoolean("xTeam." + p.getWorld().getName())) {
				if (xTeam.getInstance().getPlayerManager().getPlayer(player) != null) {
					if (!xTeam.getInstance().getPlayerManager().getPlayer(player).hasTeam()){
						tag = ChatColor.getByChar(plugin.getConfig().getString("xTeam.neutral"));
					} else if (xTeam.getInstance().getPlayerManager().getPlayer(player).getTeammates().contains(xTeam.getInstance().getPlayerManager().getPlayer(seenPlayer))){
						tag = ChatColor.getByChar(plugin.getConfig().getString("xTeam.ally"));
					} else {
						tag = ChatColor.getByChar(plugin.getConfig().getString("xTeam.enemy"));
					}
				}
			}
		} else if (plugin.getServer().getPluginManager().isPluginEnabled("AncientRPG")) { //AncientRPG by MysticCity
			if (AncientRPG.getApiManager().getPlayerGuild(player) != null){
				if (AncientRPG.getApiManager().getPlayerGuild(player).gMember.containsKey(seenPlayer)){
					tag = ChatColor.getByChar(plugin.getConfig().getString("AncientRPG.guild"));
				}
			} else if (AncientRPG.getApiManager().getPlayerParty(p) != null){
				if (AncientRPG.getApiManager().getPlayerParty(p).Member.contains(seen)){
					tag = ChatColor.getByChar(plugin.getConfig().getString("AncientRPG.guild"));
				}
			}
		} 
		
		//minigames, higher priority
		//War by tommytony
		if (plugin.getServer().getPluginManager().isPluginEnabled("War")) {
			if (plugin.getConfig().getBoolean("War." + Bukkit.getServer().getPlayer(player).getWorld().getName())) {
				if (Team.getTeamByPlayerName(seenPlayer) != null) {
					tag = Team.getTeamByPlayerName(seenPlayer).getKind().getColor();
				}
			}
		} else if (plugin.getServer().getPluginManager().isPluginEnabled("BattleArena")) { //BattleArena by alkarinv
//			if (BattleArena.getSelf().getConfig()){
				if(BattleArena.inArena(BattleArena.toArenaPlayer(p))){
					if (TeamController.getTeam(BattleArena.toArenaPlayer(p)).hasMember(BattleArena.toArenaPlayer(seen))){
						//teammate
						tag = ChatColor.getByChar(plugin.getConfig().getString("BattleArena.teammate"));
					} else {
						//enemy
						tag = ChatColor.getByChar(plugin.getConfig().getString("BattleArena.enemy"));
					}
				} 
//			}
		}
		
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
		return null;
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
