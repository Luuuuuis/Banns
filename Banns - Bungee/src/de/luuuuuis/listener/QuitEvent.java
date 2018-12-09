/*
 *
 * Created by Luis
 * 01.10.2018 at 19:52:31
 *
 */
package de.luuuuuis.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class QuitEvent implements Listener {
	
	
	@EventHandler
	public void onQuit(PlayerDisconnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		
		if(ChatEvent.mutedList.containsKey(p.getUniqueId())) {
			ChatEvent.mutedList.remove(p.getUniqueId());
		}
		
	}

}
