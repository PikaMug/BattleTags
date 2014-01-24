package me.happypikachu.BattleTags.managers;

import com.p000ison.dev.simpleclans2.api.SCCore;
import com.palmergames.bukkit.towny.utils.CombatUtil;
import com.tommytony.war.Team;

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
	
	protected String getTag(Player player, Player namedPlayer) {
		return getTag(player.getName(), namedPlayer.getName());
	}
	
	public String getTag(String player, String seenPlayer){
		String tag = seenPlayer;
		if (plugin.getServer().getPluginManager().isPluginEnabled("Factions")){
			String worldName = Bukkit.getPlayer(player).getWorld().getName();
			if (plugin.getConfig().getBoolean("Factions." + worldName)){
				tag = factions.getRelColor(Bukkit.getPlayer(player), Bukkit.getPlayer(seenPlayer)) + seenPlayer;
			}
		}
		
		//SimpleClans by phaed420
		if (plugin.getServer().getPluginManager().isPluginEnabled("SimpleClans")) {
			SimpleClans sc = (SimpleClans) plugin.getServer().getPluginManager().getPlugin("SimpleClans");
			Clan pClan = sc.getClanManager().getClanByPlayerName(player);
			Clan npClan = sc.getClanManager().getClanByPlayerName(seenPlayer);
			if (pClan.isAlly(npClan.getTag())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.ally")) + seenPlayer;
			} else if (pClan.isMember(seenPlayer)) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.member")) + seenPlayer;
			} else if (pClan.isRival(npClan.getTag())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.rival")) + seenPlayer;
			} else if (pClan.isWarring(npClan.getTag())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans.warring")) + seenPlayer;
			}
		}
				
		//SimpleClans2 by p000ison
		if (plugin.getServer().getPluginManager().isPluginEnabled("SimpleClans2")) {
			SCCore sc= (SCCore) plugin.getServer().getPluginManager().getPlugin("SimpleClans2");
			if (sc.getClanPlayerManager().getClanPlayer(player).getClan().isAlly(sc.getClanPlayerManager().getClanPlayer(seenPlayer).getClan())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.ally")) + seenPlayer;
			} else if (sc.getClanPlayerManager().getClanPlayer(player).getClan().isMember(sc.getClanPlayerManager().getClanPlayer(seenPlayer))) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.member")) + seenPlayer;
			} else if (sc.getClanPlayerManager().getClanPlayer(player).getClan().isRival(sc.getClanPlayerManager().getClanPlayer(seenPlayer).getClan())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.rival")) +seenPlayer;
			} else if (sc.getClanPlayerManager().getClanPlayer(player).getClan().isWarring(sc.getClanPlayerManager().getClanPlayer(seenPlayer).getClan())) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("SimpleClans2.warring")) + seenPlayer;
			}
		}
		
		//Towny by ElgarL
		if (plugin.getServer().getPluginManager().isPluginEnabled("Towny")) {
			if (CombatUtil.isAlly(player, seenPlayer)) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("Towny.ally")) + seenPlayer;
			} else if (CombatUtil.isEnemy(player, seenPlayer)) {
				tag =  ChatColor.getByChar(plugin.getConfig().getString("Towny.enemy")) + seenPlayer;
			}
		}
				
		//War by tommytony
		if (plugin.getServer().getPluginManager().isPluginEnabled("War")) {
			if (plugin.getConfig().getBoolean("War." + Bukkit.getServer().getPlayer(player).getWorld().getName())) {
				if (Team.getTeamByPlayerName(seenPlayer) != null) {
					tag =  Team.getTeamByPlayerName(seenPlayer).getKind().getColor() + seenPlayer;
				}
			}
		}
		
		//xTeam by 
		if (plugin.getServer().getPluginManager().isPluginEnabled("xTeam")) {
			if (plugin.getConfig().getBoolean("xTeam." + Bukkit.getServer().getPlayer(player).getWorld().getName())) {
				if (xTeam.getInstance().getPlayerManager().getPlayer(player) != null) {
					if (!xTeam.getInstance().getPlayerManager().getPlayer(player).hasTeam()){
						tag = ChatColor.getByChar(plugin.getConfig().getString("xTeam.neutral")) + seenPlayer;
					} else if (xTeam.getInstance().getPlayerManager().getPlayer(player).getTeammates().contains(xTeam.getInstance().getPlayerManager().getPlayer(seenPlayer))){
						tag = ChatColor.getByChar(plugin.getConfig().getString("xTeam.ally")) + seenPlayer;
					} else {
						tag = ChatColor.getByChar(plugin.getConfig().getString("xTeam.enemy")) + seenPlayer;
					}
				}
			}
		}
		
		//Allow integration from other plugins
		BattleTagsCustomTagEvent tagEvent = new BattleTagsCustomTagEvent(player, seenPlayer, tag);
		plugin.getServer().getPluginManager().callEvent(tagEvent);
		return tagEvent.getTag();
	}
	
	public void update(String player){
		update(Bukkit.getServer().getPlayer(player));
	}
	
	public abstract void update(Player player);

	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
}
