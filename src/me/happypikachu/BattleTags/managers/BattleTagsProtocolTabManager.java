/**
 * 
 */
package me.happypikachu.BattleTags.managers;

import java.lang.reflect.InvocationTargetException;

import me.happypikachu.BattleTags.BattleTags;
import me.happypikachu.BattleTags.events.BattleTagsCustomTagEvent;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;

/**
 * @author Brord
 *
 */
public class BattleTagsProtocolTabManager extends BattleTagsManager {

	PacketListener listener;
	
	/**
	 * @param plugin
	 */
	public BattleTagsProtocolTabManager(BattleTags plugin) {
		super(plugin);
		startup();
	}
	
	@EventHandler
	public void tag(BattleTagsCustomTagEvent e){
		if (e.getTag().length()>16)e.setTag(e.getTag().substring(0, 16));
	}
	
	@Override
 	public void shutdown() {
		if (listener != null)ProtocolLibrary.getProtocolManager().removePacketListener(listener);;
	}
	
	public void startup() {
	    ProtocolLibrary.getProtocolManager().addPacketListener(listener = new PacketAdapter(PacketAdapter.params(plugin, PacketType.Play.Server.PLAYER_INFO)) {
	       @Override
	       public void onPacketSending(PacketEvent event) {
	    	   if (event.isCancelled()) return;
	    	   //event.setCancelled(true);
	    	   
	    	   final PacketContainer packetContainer = event.getPacket();
	           final String seen = ChatColor.stripColor(packetContainer.getStrings().read(0));
	        	  
	           String tag = getTag(event.getPlayer().getName(), seen);
	           
	           packetContainer.getStrings().write(0, tag);
	           event.setPacket(packetContainer);
	           //getPL().log(event.getPlayer().getName() + " received info packet about: " + seen + " -> tag: " + tag);
	       }
	   });
	}

	@EventHandler
	public void leave(PlayerQuitEvent e){
		removePlayer(e.getPlayer().getName());
	}
	
	@Override
	public void removePlayer(String seenName){
		PacketContainer pc;
		Player[] players = plugin.getServer().getOnlinePlayers();
		
		for (Player pr : players){
			if (seenName.equals(pr.getName())) continue;
			
			pc = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
			pc.getBooleans().write(0, false);
			pc.getStrings().write(0, getTag(pr.getName(), seenName));
			pc.getIntegers().write(0, 0);
			
			try {
				ProtocolLibrary.getProtocolManager().sendServerPacket(pr, pc);
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@Override
	public void clear(Player p) {
		PacketContainer pc;
		Player[] players = plugin.getServer().getOnlinePlayers();
		for (Player pr : players){
			pc = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
			pc.getBooleans().write(0, false);
			pc.getStrings().write(0, getTag(p.getName(), pr.getName()));
			pc.getIntegers().write(0, 0);
			
			try {
				ProtocolLibrary.getProtocolManager().sendServerPacket(p, pc);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see me.happypikachu.BattleTags.managers.TagsManager#update(org.bukkit.entity.Player)
	 */
	@Override
	public void update(Player player) {
		//not needed, packets run every second
	}

}
