package me.happypikachu.BattleTags.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.happypikachu.BattleTags.BattleTags;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;

public class BattleTagsProtocolManager extends TagsManager{
	
	public BattleTagsProtocolManager(BattleTags plugin) {
		super(plugin);
		startup();
	}

	@Override
 	public void shutdown() {
	    ProtocolLibrary.getProtocolManager().removePacketListeners(this.plugin);
	}

	public void startup() {
	    ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.plugin, ConnectionSide.SERVER_SIDE, ListenerPriority.HIGH, Packets.Server.NAMED_ENTITY_SPAWN) {
	       @Override
	       public void onPacketSending(PacketEvent event) {
	          if (event.getPacketID() != Packets.Server.NAMED_ENTITY_SPAWN) {
	              return;
	          }
	          final PacketContainer packetContainer = event.getPacket();
	          try {
	        	  final String seen = packetContainer.getSpecificModifier(String.class).read(0);
	        	  Player seenPlayer = Bukkit.getServer().getPlayer(seen);
	        	  
	        	  // Bukkit.getPlayer returns null when the player is spawing while teleporting but the player is online
	        	  // so when this happens, we schedule a hide and show player in the next tick
	        	  if(seenPlayer == null){
	        		  final Player player = event.getPlayer();
	        		  plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							Player seenPlayer = Bukkit.getServer().getPlayer(seen);
							if(seenPlayer == null){
								plugin.getLogger().warning("Failed to change the tag of "+seen);
								return;
							}
							player.hidePlayer(seenPlayer);
							player.showPlayer(seenPlayer);
						}
	        		  });
	        		  return;
	        	  }
	        	  
	        	  packetContainer.getSpecificModifier(String.class).write(0, getTag(event.getPlayer(), seenPlayer));
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
					p.hidePlayer(player);
					player.showPlayer(p);
					p.showPlayer(player);
				}
			}
		}
	}
}