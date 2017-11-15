package edu.vt.alic.horses.listeners;

import java.util.Random;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import edu.vt.alic.horses.HorseData;
import edu.vt.alic.horses.Horses;
import edu.vt.alic.horses.PlayerData;

/**
 * This class will add XP to a claimed horse's stats when a player rides the horse.
 * Due to a lack of a better way to handle leveling, there is a 1% chance of adding anywhere from 0 to 9 XP.
 * This modifier can change based on how fast the horse should level up.
 * The reason it's 1% is because the player move event is called so much that if it were any higher, the player would be getting too much XP.
 */
public class HorseTrainListener implements Listener {
	
	private Random rand;
	
	public HorseTrainListener() {
		this.rand = new Random();
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if (player.getVehicle() == null || !(player.getVehicle() instanceof Horse)) {
			return;
		}
		
		PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(player);
		Horse horse = (Horse) player.getVehicle();
		
		// -- Not the player's horse to train.
		if (pData.getHorses().get(horse.getUniqueId()) == null) {
			return;
		}
		
		HorseData hData = pData.getHorses().get(horse.getUniqueId());
		
		if (Math.random() > 0.99) {
			hData.getLevelHandler().addXp(player, hData.getBreed().getSpecialization(), rand.nextInt(10));
		}
	}
}
