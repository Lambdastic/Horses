package edu.vt.alic.horses.listeners;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import edu.vt.alic.horses.Horses;

/**
 * Saves the player's data to his flat file.
 * Ejects the horse to avoid potential bugs in loading the horse.
 */
public class PlayerQuitListener implements Listener {

	@EventHandler (ignoreCancelled = true)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		Horses.getInstance().saveDataToConfig(player, false);
		
		if (player.getVehicle() instanceof Horse) {
			Horse horse = (Horse) player.getVehicle();
			horse.eject();
		}
	}
}
