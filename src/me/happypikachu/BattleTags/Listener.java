package me.happypikachu.BattleTags;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import me.happypikachu.BattleTags.listeners.BattleTagsAncientRPGUUIDListener;
import me.happypikachu.BattleTags.listeners.BattleTagsBattleArenaListener;
import me.happypikachu.BattleTags.listeners.BattleTagsFactions1678Listener;
import me.happypikachu.BattleTags.listeners.BattleTagsFactions24Listener;
import me.happypikachu.BattleTags.listeners.BattleTagsFactions27Listener;
import me.happypikachu.BattleTags.listeners.BattleTagsListener;
import me.happypikachu.BattleTags.listeners.BattleTagsSimpleClans2Listener;
import me.happypikachu.BattleTags.listeners.BattleTagsSimpleClansListener;
import me.happypikachu.BattleTags.listeners.BattleTagsTownyListener;
import me.happypikachu.BattleTags.listeners.BattleTagsWarListener;
import me.happypikachu.BattleTags.listeners.BattleTagsXTeam18Listener;
import me.happypikachu.BattleTags.listeners.BattleTagsXTeamListener;

/**
 * Project BattleTags<br/>
 * Class me.happypikachu.BattleTags.Manager.java<br/>
 * @author Brord
 * @since 30 nov. 2014, 15:17:52
 */
public enum Listener {
	TOWNY("Towny", BattleTagsTownyListener.class),
	ARPG("AncientRPG", BattleTagsAncientRPGUUIDListener.class),
	BATTLE("BattleArena", BattleTagsBattleArenaListener.class),
	F1678("Factions", BattleTagsFactions1678Listener.class, 1.6, 1.9),
	F24("Factions", BattleTagsFactions24Listener.class, 2.0, 2.7),
	F27("Factions", BattleTagsFactions27Listener.class, 2.7),
	SC("SimpleClans", BattleTagsSimpleClansListener.class),
	SC2("SimpleClans2", BattleTagsSimpleClans2Listener.class),
	WAR("War", BattleTagsWarListener.class),
	XTeam("xTeam", BattleTagsXTeamListener.class, 1.0, 1.8),
	XTeam18("xTeam", BattleTagsXTeam18Listener.class, 1.8);
	
	private enum Warning {
		BATTLE(Listener.BATTLE, "!!! Remember to set useColoredNames to false !!!"),
		XTeam(Listener.XTeam, "xTeam has been updated! You shoud use the newest instead!(v1.8)");
		
		String w; Listener l;
		Warning(Listener l, String w) {this.w = w;}
	}

	private String name;
	private Double versionMin, versionMax;
	private Class<? extends BattleTagsListener> clazz;
	
	Listener(String name, Class<? extends BattleTagsListener> listener){
		this(name, listener, null);
	}
	
	Listener(String name, Class<? extends BattleTagsListener> listener, Double versionMin){
		this(name, listener, versionMin, null);
	}
	
	Listener(String name, Class<? extends BattleTagsListener> listener, Double versionMin, Double versionMax){
		this.name = name;
		this.clazz = listener;
		this.versionMin = versionMin;
		this.versionMax = versionMax;
	}
	
	public static Listener getPluginListener(String name){
		if (!Bukkit.getServer().getPluginManager().isPluginEnabled(name))return null;
		Plugin p = Bukkit.getServer().getPluginManager().getPlugin(name);
		double version = extractVersion(p.getDescription().getVersion());
		
		for (Listener l : values()){
			if (!l.name.equalsIgnoreCase(p.getName()))continue;
			if (l.versionMax == null && l.versionMin == null) return l;
			
			if (l.versionMin != null && l.versionMin >= version) continue;
			if (l.versionMax != null && l.versionMax < version) continue;
			return l;
		}
		
		return null;
	}
	
	/**
	 * @param version
	 * @return
	 */
	private static double extractVersion(String version) {
		version = version.replaceAll("[^0-9//.]", "");
		String[] split = version.split("\\.");
		if (split.length > 1) version = split[0] + "." + split[1];
		return Double.parseDouble(version);
	}

	public boolean hasWarning(){
		try{return Warning.valueOf(name()) != null;
		} catch (Exception e){return false;}
	}
	
	public String getWarning(){
		return Warning.valueOf(name()).w;
	}
	
	/**
	 * @return the versionMin
	 */
	public Double getVersionMin() {
		return versionMin;
	}
	
	/**
	 * @return the versionMax
	 */
	public Double getVersionMax() {
		return versionMax;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	  * @see java.lang.Enum#toString()
	  */
	@Override
	public String toString() {
		return name + (getVersionMin() != null ? " " + getVersionMin() : "") + (getVersionMax() != null ? " " + getVersionMax() : "");
	}
	
	/**
	 * @return the clazz
	 */
	public Class<? extends BattleTagsListener> getClazz() {
		return clazz;
	}
	
	public BattleTagsListener getListener(BattleTags plugin){
		try {
			return (BattleTagsListener) clazz.getConstructors()[0].newInstance(plugin);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}
