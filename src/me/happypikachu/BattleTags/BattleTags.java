package me.happypikachu.BattleTags;

import me.happypikachu.BattleTags.listeners.*;
import me.happypikachu.BattleTags.managers.BattleTagsAPIManager;
import me.happypikachu.BattleTags.managers.BattleTagsNametagManager;
import me.happypikachu.BattleTags.managers.BattleTagsOwnManager;
import me.happypikachu.BattleTags.managers.BattleTagsProtocolManager;
import me.happypikachu.BattleTags.managers.BattleTagsTabManager;
import me.happypikachu.BattleTags.managers.TagsManager;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BattleTags extends JavaPlugin {

	public TagsManager Namemanager;
	public TagsManager Tabmanager;
	
	public boolean NametagsEnabled = true;
	public boolean ListEnabled = true;
	
	@Override
	public void onEnable() {
		//Copy config.yml and update header
		saveDefaultConfig();
		getConfig().options().header("BattleTags+ v" + getDescription().getVersion() + " Configuration" + 
				"\nby HappyPikachu -aka- FlyingPikachu, Modified by kwek20" +
				"\n" + 
				"\nIf you experience a problem with this config when starting" +
				"\nyour server, make sure that you're using spaces and not tabs." + 
				"\nCheck that all apostrophes are escaped. For example, \"can't\"" + 
				"\nbecomes \"can\\'t\"." +
				"\n");
        getConfig().options().copyHeader(true);
        for (String pluginName : getDescription().getSoftDepend()) {
        	if (getServer().getPluginManager().getPlugin(pluginName) != null) {
        		for (World world: getServer().getWorlds()) {
        			getConfig().addDefault(pluginName + "." + world.getName(), true);
        		}
        	}
        }
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        getCommand("battletags").setExecutor(new BattleTagsCommandExecutor(this));
        if (getServer().getPluginManager().isPluginEnabled("Factions")) {
        	String version = getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion();
        	if (version.startsWith("2.")){
        		getServer().getPluginManager().registerEvents(new BattleTagsFactions2Listener(this), this);
        		getLogger().info("Hooked into Factions " + getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion());
        	} else if (version.startsWith("1.6") || version.startsWith("1.7") || version.startsWith("1.8")){
        		getServer().getPluginManager().registerEvents(new BattleTagsFactions1678Listener(this), this);
        		getLogger().info("Hooked into Factions " + getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion());
        	}
        }
        if (getServer().getPluginManager().isPluginEnabled("SimpleClans")) {
        	getServer().getPluginManager().registerEvents(new BattleTagsSimpleClansListener(this), this);
        	getLogger().info("Hooked into SimpleClans " + getServer().getPluginManager().getPlugin("SimpleClans").getDescription().getVersion());
        }
        if (getServer().getPluginManager().isPluginEnabled("SimpleClans2")) {
        	getServer().getPluginManager().registerEvents(new BattleTagsSimpleClans2Listener(this), this);
        	getLogger().info("Hooked into SimpleClans2 " + getServer().getPluginManager().getPlugin("SimpleClans2").getDescription().getVersion());
        }
        if (getServer().getPluginManager().isPluginEnabled("Towny")) {
        	getServer().getPluginManager().registerEvents(new BattleTagsTownyListener(this), this);
        	getLogger().info("Hooked into Towny " + getServer().getPluginManager().getPlugin("Towny").getDescription().getVersion());
        }
        if (getServer().getPluginManager().isPluginEnabled("War")) {
        	getServer().getPluginManager().registerEvents(new BattleTagsWarListener(this), this);
        	getLogger().info("Hooked into War " + getServer().getPluginManager().getPlugin("War").getDescription().getVersion());
        }
        
        if (NametagsEnabled){
	        if (getServer().getPluginManager().isPluginEnabled("TagAPI")){
	        	getServer().getPluginManager().registerEvents(Namemanager = new BattleTagsAPIManager(this), this);
	        	getLogger().info("Activated integration with TagAPI");
	        } else if (getServer().getPluginManager().isPluginEnabled("ProtocolLib")){
	        	getServer().getPluginManager().registerEvents(Namemanager = new BattleTagsProtocolManager(this), this);
	        	getLogger().info("Activated integration with ProtocolLib");
	        } else {
	        	getLogger().warning("We do not yet have our own nametag manager! Please install ProtocolLib or TagAPI");
	        	getServer().getPluginManager().registerEvents(Namemanager = new BattleTagsOwnManager(this), this);
	        }
        }
        
        if (ListEnabled){
        	if (getServer().getPluginManager().isPluginEnabled("TabAPI")){
        		getServer().getPluginManager().registerEvents(Tabmanager = new BattleTagsTabManager(this), this);
	        	getLogger().info("Activated integration with TabAPI");
        	} else {
	        	getLogger().warning("We do not yet have our own tab manager! Please install ProtocolLib or TabAPI");
	        	getServer().getPluginManager().registerEvents(Tabmanager = new BattleTagsOwnTabManager(this), this);
	        }
        }
        
        Player[] players = getServer().getOnlinePlayers();
        for (Player p : players){
        	update(p);
        }
	}
	
	@Override
	public void onDisable() {
		if (getServer().getPluginManager().isPluginEnabled("ProtocolLib")){
			Namemanager.shutdown();
		}
	}
	
	public void update(Player p){
		if (Namemanager != null){
			Namemanager.update(p);
		}
		if (Tabmanager != null){
			Tabmanager.update(p);
		}
	}
}
