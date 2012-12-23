package me.happypikachu.BattleTags;

import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Conf;

public class BattleTags extends JavaPlugin {
	
	@Override
	public void onEnable() {
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
        for (World world: getServer().getWorlds()) {
        	if (getServer().getPluginManager().getPlugin("Factions") != null) {
        		getConfig().addDefault("Factions." + world.getName(), Conf.worldsNoClaiming.contains(world.getName()) ? false:true);
        	}
        	if (getServer().getPluginManager().getPlugin("SimpleClans") != null) {
        		getConfig().addDefault("SimpleClans." + world.getName(), SimpleClans.getInstance().getSettingsManager().isBlacklistedWorld(world.getName()) ? true:false);
        	}
        	if (getServer().getPluginManager().getPlugin("War") != null) {
        		getConfig().addDefault("War." + world.getName(), true);
        	}
		}
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        if (getServer().getPluginManager().getPlugin("Factions") != null) {
        	getServer().getPluginManager().registerEvents(new BattleTagsFactionsListener(this), this);
        	getLogger().info("Hooked into Factions.");
        }
        if (getServer().getPluginManager().getPlugin("SimpleClans") != null) {
        	getLogger().info("Hooked into SimpleClans (beta support).");
        }
        if (getServer().getPluginManager().getPlugin("War") != null) {
        	getServer().getPluginManager().registerEvents(new BattleTagsWarListener(this), this);
        	getLogger().info("Hooked into War.");
        }
        try {
        	getServer().getPluginManager().registerEvents(new BattleTagsPlayerListener(this), this);
        } catch (NoClassDefFoundError e) {
        	//ignore
        }
	}
	
	@Override
	public void onDisable() {
	}
}
