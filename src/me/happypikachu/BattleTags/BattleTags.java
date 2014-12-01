package me.happypikachu.BattleTags;

import me.happypikachu.BattleTags.compatibility.HealthBar;
import me.happypikachu.BattleTags.listeners.*;
import me.happypikachu.BattleTags.managers.BattleTagsAPIManager;
import me.happypikachu.BattleTags.managers.BattleTagsOwnManager;
import me.happypikachu.BattleTags.managers.BattleTagsOwnTabManager;
import me.happypikachu.BattleTags.managers.BattleTagsProtocolManager;
import me.happypikachu.BattleTags.managers.BattleTagsProtocolTabManager;
import me.happypikachu.BattleTags.managers.BattleTagsTabManager;
import me.happypikachu.BattleTags.managers.BattleTagsManager;
import me.happypikachu.BattleTags.managers.ListenerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BattleTags extends JavaPlugin {
	
	private String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_RED + "BattleTags" + ChatColor.GRAY + "] " + ChatColor.RESET;

	private ListenerManager listeners = new ListenerManager();
	
	public BattleTagsManager Namemanager;
	public BattleTagsManager Tabmanager;
	
	public boolean NametagsEnabled = true;
	public boolean ListEnabled = true;
	
	@Override
	public void onEnable() {
		load();
        getCommand("battletags").setExecutor(new BattleTagsCommandExecutor(this));
	}
	
	public void load(){
		Namemanager = null;
		Tabmanager = null;
		
		loadConfig();
        loadCore();
	}
	
	private void loadConfig(){
		saveDefaultConfig();
		updateConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        NametagsEnabled = getConfig().getBoolean("nametags", true);
        ListEnabled = getConfig().getBoolean("playerlist", true);
	}
	
	private void loadCore(){
		loadDependencies();
        loadNameTags(); 
        loadPlayerList();
        loadPlayers();
	}
	
	/**
	 * 
	 */
	private void updateConfig() {
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
        	if (pluginName.equalsIgnoreCase("healthbar")) continue;
        	
        	if (getServer().getPluginManager().getPlugin(pluginName) != null) {
        		for (World world: getServer().getWorlds()) {
        			getConfig().addDefault(pluginName + "." + world.getName(), true);
        		}
        	}
        }
	}

	/**
	 * Loads all our dependencies with the listeners
	 */
	private void loadDependencies() {
		Listener l;
		for (String p : getDescription().getSoftDepend()){
			l = Listener.getPluginListener(p);
			if (l == null) {
				//log(ChatColor.DARK_RED + "WARNING! Found a softdepend without listener(" + p + ")");
				//no big deal :)
			} else {
				listeners.add(add(l));
				//TODO priority listeners
			}
		}
		
		
        if (getServer().getPluginManager().isPluginEnabled("HealthBar")) {
        	getServer().getPluginManager().registerEvents(new HealthBar(this), this);
        	log("Added HealthBar " + getServer().getPluginManager().getPlugin("HealthBar").getDescription().getVersion() + " compatibility");
        }
	}
	
	private BattleTagsListener add(Listener l){
		BattleTagsListener listener;
		getServer().getPluginManager().registerEvents(listener = l.getListener(this), this);
		log("Added " + l.getName() + " " + getServer().getPluginManager().getPlugin(l.getName()).getDescription().getVersion() + " compatibility");
		if (l.hasWarning())log(l.getWarning());
		return listener;
	}

	/**
	 * loads our nametag managers
	 */
	private void loadNameTags() {
		 if (NametagsEnabled){
		    if (getServer().getPluginManager().isPluginEnabled("TagAPI")){
		    	getServer().getPluginManager().registerEvents(Namemanager = new BattleTagsAPIManager(this), this);
		    	log("Activated integration with TagAPI");
		    } else if (getServer().getPluginManager().isPluginEnabled("ProtocolLib")){
		        getServer().getPluginManager().registerEvents(Namemanager = new BattleTagsProtocolManager(this), this);
		        log("Activated Nametag integration with ProtocolLib");
		    } else {
		    	log("[WARNING] We do not yet have our own nametag manager! Please install ProtocolLib or TagAPI");
		        getServer().getPluginManager().registerEvents(Namemanager = new BattleTagsOwnManager(this), this);
		    }
	    }
	}

	/**
	 * loads our playerlist managers
	 */
	private void loadPlayerList() {
		if (ListEnabled){
        	if (getServer().getPluginManager().isPluginEnabled("TabAPI")){
        		getServer().getPluginManager().registerEvents(Tabmanager = new BattleTagsTabManager(this), this);
        		log("Activated integration with TabAPI");
        		log(ChatColor.DARK_RED + "Warning! TabAPI is outdate and tends to disfunction.");
		    } else if (getServer().getPluginManager().isPluginEnabled("ProtocolLib")){
		        getServer().getPluginManager().registerEvents(Tabmanager = new BattleTagsProtocolTabManager(this), this);
		        log("Activated Tablist integration with ProtocolLib");
		    } else {
		    	log("WARNING] We do not yet have our own tab manager! Please install ProtocolLib or TabAPI");
	        	getServer().getPluginManager().registerEvents(Tabmanager = new BattleTagsOwnTabManager(this), this);
	        }
        }
	}
	
	/**
	 * Logs to the console, attempts to use colors
	 * @param message The message to log
	 */
	public void log(String message) {
		try{
			Bukkit.getServer().getConsoleSender().sendMessage(prefix + message);
		} catch (Exception e){
			System.out.println(ChatColor.stripColor(prefix) + message);
		}
	}

	/**
	 * loops over all players and updates them
	 */
	private void loadPlayers() {
		Player[] players = getServer().getOnlinePlayers();
        
		for (Player p : players){
			if (ListEnabled){
				Tabmanager.clear(p);
			}
	        update(p);
	    }
	}

	@Override
	public void onDisable() {
		if (getServer().getPluginManager().isPluginEnabled("ProtocolLib")){
			if (Namemanager != null){
				Namemanager.shutdown();
			}
			if (Tabmanager != null){
				Tabmanager.shutdown();
			}
		}
	}
	
	public void update(final Player p){
		if (Tabmanager != null){
			Tabmanager.removePlayer(p.getName());
		}
		
		//delay for events to finish
		new BukkitRunnable(){
			@Override
			public void run() {
				if (Namemanager != null){
					Namemanager.update(p);
				}
				if (Tabmanager != null){
					Tabmanager.update(p);
				}
			}
		}.runTask(this);
	}
	
	/**
	 * @return the listeners
	 */
	public ListenerManager getListeners() {
		return listeners;
	}
}
