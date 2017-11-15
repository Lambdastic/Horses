package edu.vt.alic.horses;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

/*
 * This class controls all the players' data and their respective PlayerData objects.
 * I access all PlayerData objects from this class using the UUID of the player.
 */
public class DataHandler {

	private HashMap<UUID, PlayerData> players;
	private HashMap<UUID, Location> horseLocations;
	
	public DataHandler(Horses plugin) {
		this.players = new HashMap<UUID, PlayerData>();
	}
	
	public void addPlayer(Player p, PlayerData playerData) {
		players.put(p.getUniqueId(), playerData);
	}
	
	public void removePlayer(Player p) {
		players.remove(p.getUniqueId());
	}
	
	public PlayerData getPlayerData(Player p) {
		// -- If for some reason the player didn't generate a PlayerData object on join
		//  this code will try again.
		if (players.get(p.getUniqueId()) == null) {
			addPlayer(p, new PlayerData(Horses.getInstance(), p));
		}
		
		return players.get(p.getUniqueId());
	}
	
	public void addHorse(Horse horse) {
		horseLocations.put(horse.getUniqueId(), horse.getLocation());
	}
	
	public void removeHorse(Horse horse) {
		horseLocations.remove(horse.getUniqueId());
	}
	
	public Location getHorseLocation(Horse horse) {
		return horseLocations.get(horse.getUniqueId());
	}
	
	public HashMap<UUID, Location> getHorseLocations() {
		return horseLocations;
	}
	
	/**
	 * This code will transfer a horse from one player to another
	 * by moving the horse file from the from player to the to player
	 * and this is accomplished with the renameTo method in the File class.
	 * 
	 */
	public void transferData(Horse horse, Player from, Player to) {
		PlayerData fromPlayerData = players.get(from.getUniqueId());
		HorseData hData = fromPlayerData.getHorses().get(horse.getUniqueId());
		PlayerData toPlayerData = players.get(to.getUniqueId());
		
		// -- This code here will set the ID to a new ID based on how many horses the player has.
		hData.setHorseId(toPlayerData.getTotalOwnedHorses() + 1);
		hData.setPlayer(to);
		
		File fromPlayerFile = new File(Horses.getInstance() 
				+ File.separator 
				+ "data"
				+ File.separator 
				+ from.getUniqueId().toString()
				+ File.separator
				+ hData.getHorse().getUniqueId().toString() + ".yml");
		
		File toPlayerFile = new File(Horses.getInstance() 
				+ File.separator 
				+ "data"
				+ File.separator 
				+ to.getUniqueId().toString()
				+ File.separator
				+ hData.getHorse().getUniqueId().toString() + ".yml");
		
		fromPlayerFile.renameTo(toPlayerFile);

		toPlayerData.getHorses().put(horse.getUniqueId(), hData);
		fromPlayerData.getHorses().remove(horse.getUniqueId());
	}
}
