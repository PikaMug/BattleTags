/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.UUID;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.API.AncientRPGPartyDisbandedEvent;
import com.ancientshores.AncientRPG.API.AncientRPGPartyJoinEvent;
import com.ancientshores.AncientRPG.API.AncientRPGPartyLeaveEvent;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

/**
 * @author Brord
 *
 */
public class BattleTagsAncientRPGUUIDListener extends BattleTagsListener {
	
    public BattleTagsAncientRPGUUIDListener(BattleTags plugin) {
    	super(plugin, "AncientRPG");
    }
    
    @EventHandler
    public void partyLeave(AncientRPGPartyJoinEvent e){
    	update(getPlayer(e));
    }
    
    @EventHandler
    public void partyLeave(AncientRPGPartyLeaveEvent e){
    	update(getPlayer(e));
    }
    
    @EventHandler
    public void partyLeave(AncientRPGPartyDisbandedEvent e){
    	for (Player p : getPlayers(e.getParty())){
    		update(p);
    	}
    }
    
    private Collection<Player> getPlayers(AncientRPGParty e){
    	Collection<Player> players = new java.util.LinkedList<Player>();
    	Object object;
    	
    	try {
    		object = e.getClass().getMethod("getPlayers").invoke(e);
    	} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex){
    		try {
    			object = e.getClass().getDeclaredField("Member").get(e);
    		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException ex2){return null;/*should happen versions handle this*/}
    	}
    	
    	if (!(object instanceof Collection))return players;
    	
    	for (Object o : (Collection<?>) object){
    		if (o instanceof UUID) players.add(Bukkit.getPlayer((UUID)o));
    		else if (o instanceof Player)players.add((Player)o);
    	}
    	
    	return players;
    }
    
    private Player getPlayer(Event e){
    	Method method;
    	Object object;
    	
    	try {
    		method = e.getClass().getMethod("getUUID");
    	} catch (NoSuchMethodException | SecurityException ex){
    		try {
    			method = e.getClass().getMethod("getPlayer");
    		} catch (NoSuchMethodException | SecurityException ex2){return null;/*should happen versions handle this*/}
    	}
    	
    	try {
			object = method.invoke(e);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {return null;}
    	
    	if (object instanceof Player) return (Player) object;
    	else if (object instanceof UUID) return Bukkit.getPlayer((UUID) object);
    	else return null;
    }

	/**
	  * @see me.happypikachu.BattleTags.listeners.BattleTagsListener#getRelation(java.lang.String, java.lang.String)
	  */
	@Override
	public ChatColor getRelation(String viewer, String seen) {
		if (getAncient("getPlayerGuild", viewer) != null){
			if (contains(((AncientRPGGuild)getAncient("getPlayerGuild", viewer)).gMember.keySet(), seen)){
				return ChatColor.getByChar(plugin.getConfig().getString("AncientRPG.guild"));
			}
		} else if (getAncient("getPlayerParty", viewer) != null){
			if (contains(getPlayers((AncientRPGParty)getAncient("getPlayerParty", viewer)), seen)){
				return ChatColor.getByChar(plugin.getConfig().getString("AncientRPG.guild"));
			}
		}
		return null;
	}
	
	public boolean contains(Collection<?> coll, String name){
		for (Object o : coll){
			if (o instanceof String){
				if (o.equals(name)) return true;
			} else if (o instanceof UUID){
				if (o.equals(Bukkit.getOfflinePlayer(name).getUniqueId())) return true;
			} else if (o instanceof Player){
				if (((Player)o).getName().equals(name)) return true;
			}
		}
		return false;
	}
	
	
	
	public Object getAncient(String method, String name){
		Method m;
		try {
			m = AncientRPG.getApiManager().getClass().getMethod(method, String.class);
			try {
				return (AncientRPGGuild) m.invoke(AncientRPG.getApiManager(), name);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				return null;
			}
		} catch (NoSuchMethodException | SecurityException e) {
			try {
				m = AncientRPG.getApiManager().getClass().getMethod(method, UUID.class);
				try {
					return (AncientRPGGuild) m.invoke(AncientRPG.getApiManager(), Bukkit.getPlayer(name).getUniqueId());
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
					return null;
				}
			} catch (NoSuchMethodException | SecurityException e1) {
				return null;
			}
		}
	}
}
