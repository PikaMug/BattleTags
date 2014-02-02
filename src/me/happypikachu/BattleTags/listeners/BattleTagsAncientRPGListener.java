/**
 * 
 */
package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.event.EventHandler;

import com.ancientshores.AncientRPG.API.AncientRPGPartyDisbandedEvent;
import com.ancientshores.AncientRPG.API.AncientRPGPartyJoinEvent;
import com.ancientshores.AncientRPG.API.AncientRPGPartyLeaveEvent;

/**
 * @author Brord
 *
 */
public class BattleTagsAncientRPGListener extends BattleTagListener {
	
    public BattleTagsAncientRPGListener(BattleTags plugin) {
    	super(plugin, "AncientRPG");
    }
    
    @EventHandler
    public void partyLeave(AncientRPGPartyJoinEvent e){
    	update(e.getPlayer());
    }
    
    @EventHandler
    public void partyLeave(AncientRPGPartyLeaveEvent e){
    	update(e.getPlayer());
    }
    
    @EventHandler
    public void partyLeave(AncientRPGPartyDisbandedEvent e){
    	
    }
}
