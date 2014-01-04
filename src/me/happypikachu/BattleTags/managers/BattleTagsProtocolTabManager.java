/**
 * 
 */
package me.happypikachu.BattleTags.managers;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

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
	          try {
	        	   
	        	  WrappedGameProfile profile = packetContainer.getGameProfiles().read(0);
	        	  WrappedDataWatcher field = packetContainer.getDataWatcherModifier().read(0);
	        	  
	        	  Entity e = field.getEntity();
	        	  
	        	  String temp = null;
	        	  if (e != null && e instanceof LivingEntity){
	        		  if (e instanceof Player){
	        			  temp = ((Player) e).getName();
	        		  } else {
	        			  temp = ((LivingEntity) e).getCustomName();
	        		  }
	        		  
	        	  } else {
	        		  return;
	        	  }
	        	  
	        	  final String seen = temp;
	        	  Player seenPlayer = Bukkit.getServer().getPlayer(seen);
	        	  if (seenPlayer == null) return;
	        	  packetContainer.getGameProfiles().write(0, profile.withName(getTag(event.getPlayer().getName(), profile.getName())));
	        	 
	           } catch (final FieldAccessException e) {
	               e.printStackTrace();
	           }
	       }
	   });
	}

	/**
	 * @see me.happypikachu.BattleTags.managers.TagsManager#update(org.bukkit.entity.Player)
	 */
	@Override
	public void update(Player player) {
		// TODO Auto-generated method stub

	}

}
