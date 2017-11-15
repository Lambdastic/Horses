package edu.vt.alic.horses.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import edu.vt.alic.horses.HorseData;
import edu.vt.alic.horses.Horses;
import edu.vt.alic.horses.PlayerData;

/**
 * Cancels all damage to the horse if the horse is protected.
 */
public class EntityDamageListener implements Listener {

	@EventHandler (ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Horse)) {
			return;
		}

		Horse horse = (Horse) event.getEntity();
		HorseData hData = null;

		// -- This code is used to find the horse from all player files.
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(player);
			if (pData.getHorses().containsKey(horse.getUniqueId())) {
				hData = pData.getHorses().get(horse.getUniqueId());
				break;
			}
		}

		// -- Not claimed
		if (hData == null) {
			return;
		}
		
		if (hData.isProtectedBoolean()) {
			event.setCancelled(true);
		}
	}
}
