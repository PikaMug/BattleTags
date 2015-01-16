package me.happypikachu.BattleTags;

import me.happypikachu.BattleTags.listeners.BattleTagsFactionsListener;
import me.happypikachu.BattleTags.listeners.BattleTagsSimpleClans2Listener;
import me.happypikachu.BattleTags.listeners.BattleTagsSimpleClansListener;
import me.happypikachu.BattleTags.listeners.BattleTagsTownyListener;
import me.happypikachu.BattleTags.listeners.BattleTagsWarListener;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class BattleTags extends JavaPlugin {

	@Override
	public void onEnable() {
		//Copy config.yml and update header
		saveDefaultConfig();
		getConfig().options().header("BattleTags+ v" + getDescription().getVersion() + " Configuration" + 
				"\nby HappyPikachu -aka- FlyingPikachu" +
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
        	getServer().getPluginManager().registerEvents(new BattleTagsFactionsListener(this), this);
        	getLogger().info("Hooked into Factions " + getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion());
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
        try {
        	getServer().getPluginManager().registerEvents(new BattleTagsManager(this), this);
        } catch (NoClassDefFoundError e) {
        	//ignore
        }
	}
	
	@Override
	public void onDisable() {
	}
}
