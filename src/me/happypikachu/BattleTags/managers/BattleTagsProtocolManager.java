package me.happypikachu.BattleTags.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.happypikachu.BattleTags.BattleTags;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

public class BattleTagsProtocolManager extends BattleTagsManager{
	
	private PacketListener listener;

	public BattleTagsProtocolManager(BattleTags plugin) {
		super(plugin);
		startup();
	}

	@Override
 	public void shutdown() {
	    if (listener != null)ProtocolLibrary.getProtocolManager().removePacketListener(listener);
	}
	
	public void startup() {
	    ProtocolLibrary.getProtocolManager().addPacketListener(listener = new PacketAdapter(PacketAdapter.params(plugin, PacketType.Play.Server.NAMED_ENTITY_SPAWN)) {
	       @Override
	       public void onPacketSending(PacketEvent event) {
	    	   
	         
	          final PacketContainer packetContainer = event.getPacket();
	          try {
	        	   
	        	  WrappedGameProfile profile = packetContainer.getGameProfiles().read(0);
	        	  WrappedDataWatcher field = packetContainer.getDataWatcherModifier().read(0);
	        	  
	        	  Entity e = field.getEntity();
	        	  
	        	  String temp = null;
	        	  if (e instanceof LivingEntity){
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

	@Override
	public void update(Player player) {
		if (player != null){
			for (Player p : Bukkit.getServer().getOnlinePlayers()){
				if (p.getWorld().getName().equals(player.getWorld().getName())){
					player.hidePlayer(p);
					player.showPlayer(p);
				}
			}
		}
	}
}