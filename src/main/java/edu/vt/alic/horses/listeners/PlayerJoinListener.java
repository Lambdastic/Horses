package edu.vt.alic.horses.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import edu.vt.alic.horses.Horses;
import edu.vt.alic.horses.PlayerData;

/**
 * Adds the player to memory along with his data that is loaded with PlayerData's constructor.
 */
public class PlayerJoinListener implements Listener {
	
	@EventHandler (ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Horses.getInstance().getDataHandler().addPlayer(player, new PlayerData(Horses.getInstance(), player));
	}
}
