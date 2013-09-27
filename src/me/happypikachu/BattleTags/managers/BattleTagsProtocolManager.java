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
	        	  packetContainer.getSpecificModifier(String.class).write(0, getTag(event.getPlayer(), Bukkit.getServer().getPlayer(packetContainer.getSpecificModifier(String.class).read(0))));
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