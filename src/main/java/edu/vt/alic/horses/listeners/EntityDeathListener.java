package edu.vt.alic.horses.listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import edu.vt.alic.horses.Horses;
import edu.vt.alic.horses.Messages;
import edu.vt.alic.horses.PlayerData;

/**
 * Removes a horse from memory if it dies.
 */
public class EntityDeathListener implements Listener {
	
	@EventHandler (ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent event) {
		if (!(event.getEntity() instanceof Horse)) {
			return;
		}
		
		Horse horse = (Horse) event.getEntity();
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(player);
			if (pData.getHorses().containsKey(horse.getUniqueId())) {
				pData.getHorses().remove(horse.getUniqueId());
				
				new File(Horses.getInstance().getDataFolder() 
						+ File.separator 
						+ "data" 
						+ File.separator
						+ player.getUniqueId().toString() 
						+ File.separator 
						+ horse.getUniqueId().toString() + ".yml").delete();
				
				player.sendMessage(Messages.HORSE_DEAD);
				break;
			}
		}
		Horses.getInstance().getDataHandler().removeHorse(horse);
	}
}
