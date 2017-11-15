package edu.vt.alic.horses.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import edu.vt.alic.horses.Gender;
import edu.vt.alic.horses.HorseData;
import edu.vt.alic.horses.Horses;
import edu.vt.alic.horses.Messages;
import edu.vt.alic.horses.PlayerData;
import edu.vt.alic.horses.Util;

/**
 *	Allows players to change the gender of a horse and check the gender + owner of the horse.
 */
public class EntityHitListener implements Listener {
	
	@EventHandler (ignoreCancelled = true)
	public void onEntityHit(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Horse) || !(event.getDamager() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getDamager();
		Horse horse = (Horse) event.getEntity();
		HorseData hData = null;
		
		// -- This code is used to find the horse from all player files.
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(onlinePlayer);
			if (pData.getHorses().containsKey(horse.getUniqueId())) {
				hData = pData.getHorses().get(horse.getUniqueId());
				break;
			}
		}
		
		// -- Not claimed
		if (hData == null) {
			return;
		}

		// -- The gelding tool turns a stallion into a Geld.
		if (player.getInventory().getItemInMainHand().isSimilar(Util.getGeldingTool())) {
			if (hData.getGender() == Gender.STALLION) {
				hData.setGender(Gender.GELD);
				player.sendMessage(Messages.GELDED_HORSE);
			} else if (hData.getGender() == Gender.MARE) {
				player.sendMessage(Messages.MUST_BE_STALLION);
			} else {
				player.sendMessage(Messages.ALREADY_GELD);
			}
			event.setCancelled(true);
		}
		
		// -- The checker tool shows the owner and gender of the horse.
		else if (player.getInventory().getItemInMainHand().isSimilar(Util.getChecker())) {
			player.sendMessage(Messages.OWNER + hData.getPlayer().getDisplayName() + ". " + Messages.GENDER + hData.getGender().toString());
			event.setCancelled(true);
		}
	}
}
