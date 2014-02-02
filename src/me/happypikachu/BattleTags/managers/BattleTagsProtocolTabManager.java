/**
 * 
 */
package me.happypikachu.BattleTags.managers;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
	
	@Override
 	public void shutdown() {
		if (listener != null)ProtocolLibrary.getProtocolManager().removePacketListener(listener);;
	}
	
	public void startup() {
	    ProtocolLibrary.getProtocolManager().addPacketListener(listener = new PacketAdapter(PacketAdapter.params(plugin, PacketType.Play.Server.PLAYER_INFO)) {
	       @Override
	       public void onPacketSending(PacketEvent event) {
	    	   
	         
	          final PacketContainer packetContainer = event.getPacket();
	        	  final String seen = ChatColor.stripColor(packetContainer.getStrings().read(0));
	        	  
	        	  Player seenPlayer = Bukkit.getServer().getPlayer(seen);
	        	  if (seenPlayer == null) return;
	        	  
	        	  //System.out.println("Received info packet: " + seen + " " + event.getPlayer().getName() + " " + getTag(event.getPlayer().getName(), seen));
	        	  packetContainer.getStrings().write(0, getTag(event.getPlayer().getName(), seen));
	       }
	   });
	}

	/**
	 * @see me.happypikachu.BattleTags.managers.TagsManager#update(org.bukkit.entity.Player)
	 */
	@Override
	public void update(Player player) {
		//not needed, packets run every second
	}

}
