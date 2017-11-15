package edu.vt.alic.horses;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LocationAutoSaver extends BukkitRunnable {

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Horses.getInstance().saveDataToConfig(p, true);
		}
	}
	
	
}
