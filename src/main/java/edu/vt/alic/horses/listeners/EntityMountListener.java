package edu.vt.alic.horses.listeners;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import edu.vt.alic.horses.HorseData;
import edu.vt.alic.horses.Horses;
import edu.vt.alic.horses.Messages;
import edu.vt.alic.horses.PlayerData;

/**
 * Handles bucking off players if the horse is not at least a year old.
 * And it bucks off players who are not friends with the horse.
 * A player can be added as a friend to a horse with the command /h friend {player} while riding the horse.
 */
public class EntityMountListener implements Listener {

	@EventHandler (ignoreCancelled = true)
	public void onHorseMount(VehicleEnterEvent event) {
		if (!(event.getEntered() instanceof Player) || !(event.getVehicle() instanceof Horse)) {
			return;
		}

		Player player = (Player) event.getEntered();
		Horse horse = (Horse) event.getVehicle();
		HorseData hData = null;

		// -- This code is used to find the horse from all player files.
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(onlinePlayer);
			if (pData.getHorses().containsKey(horse.getUniqueId())) {
				hData = pData.getHorses().get(horse.getUniqueId());
				break;
			}
		}

		// -- Not claimed.
		if (hData == null) {
			return;
		}

		if (!hData.getFriends().contains(player.getUniqueId()) && !player.equals(hData.getPlayer())) {
			if (!player.hasPermission("horses.friend.bypass")) {
				player.sendMessage(Messages.CANNOT_RIDE_HORSE);
				horse.eject();
			}
		}

		if (hData.getAge(new Date()) == 0) {
			// -- There is a delayed task here to make the horse's bucking a bit more realistic and similar to default bucking.
			Horses.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Horses.getInstance(), new Runnable() {
				@Override
				public void run() {
					horse.eject();
				}
			}, 75);
		}
	}
}