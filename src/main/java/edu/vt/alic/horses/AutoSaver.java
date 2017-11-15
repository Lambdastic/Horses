package edu.vt.alic.horses;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * I am saving all the players' horses' data to YML since all of the data
 * is cached into memory. The save interval is predetermined in the config file.
 * I do this so that I don't have to constantly read/write to YML files, and this piece of code
 * prevents file loss due to server crashes.
 */
public class AutoSaver extends BukkitRunnable {

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Horses.getInstance().saveDataToConfig(p, false);
		}
	}
}
