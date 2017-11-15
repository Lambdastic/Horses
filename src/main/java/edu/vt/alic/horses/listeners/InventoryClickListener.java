package edu.vt.alic.horses.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import edu.vt.alic.horses.HorseData;
import edu.vt.alic.horses.Horses;
import edu.vt.alic.horses.Messages;
import edu.vt.alic.horses.PlayerData;
import edu.vt.alic.horses.Util;
import edu.vt.alic.horses.breeds.Breed;

public class InventoryClickListener implements Listener {

	@EventHandler (ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) 
			return;
		
		if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()) {
			return;
		}

		Player p = (Player) e.getWhoClicked();

		if (e.getInventory().getName().contains("Breed List")) {
			if (e.getSlot() == 35) {
				p.closeInventory();
			}
			e.setCancelled(true);
			return;
		}
		
		if (!e.getInventory().getName().contains("Set Breed"))
			return;
		
		if (e.getSlot() == 35) {
			p.closeInventory();
			return;
		}

		e.setCancelled(true);
		String selectedBreed = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
		Breed breed = Util.getBreedByString(selectedBreed);
		
		Horse horse = (Horse) p.getVehicle();
		HorseData hData = null;
		
		for (Player pp : Bukkit.getOnlinePlayers()) {
			PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(pp);
			if (pData.getHorses().containsKey(horse.getUniqueId())) {
				hData = pData.getHorses().get(horse.getUniqueId());
				break;
			}
		}
		
		if (hData == null) {
			p.sendMessage(Messages.NOT_CLAIMED);
			return;
		}
		
		if (!breed.getSupportedColors().contains(horse.getColor())) {
			p.sendMessage(Messages.NOT_SUPPORTED_COLOR);
			p.closeInventory();
			return;
		}
		
		if (!breed.getSupportedStyles().contains(horse.getStyle())) {
			p.sendMessage(Messages.NOT_SUPPORTED_STYLE);
			p.closeInventory();
			return;
		}
		
		hData.setBreed(breed);
		hData.setBreedName(hData.getBreed().getName());
		p.sendMessage(Messages.CHANGED_BREED);
		p.closeInventory();
	}

}
