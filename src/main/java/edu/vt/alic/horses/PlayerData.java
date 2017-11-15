package edu.vt.alic.horses;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class PlayerData {

	private Player p;
	private HashMap<UUID, HorseData> horses;
	private int totalOwnedHorses;

	public PlayerData(Horses plugin, Player p) {
		this.p = p;
		this.horses = new HashMap<UUID, HorseData>();
		
		init(plugin, p);
	}

	private void init(Horses plugin, Player p) {
		File folder = new File(plugin.getDataFolder() + File.separator + "data" + File.separator + p.getUniqueId());

		if (!folder.exists()) {
			folder.mkdirs();
			return;
		}

		File[] playerFiles = folder.listFiles();

		for (File pFile : playerFiles) {
			FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);	

			World world = Bukkit.getWorld(pConfig.getString("Location.World"));	
			int chunkX = pConfig.getInt("Location.Chunk.X");
			int chunkZ = pConfig.getInt("Location.Chunk.Z");

			Chunk chunk = world.getChunkAt(chunkX, chunkZ);

			if (!chunk.isLoaded()) {
				chunk.load();
			}
			
			UUID horseUUID = UUID.fromString(pFile.getName().replace(".yml", ""));		
			Horse horse = (Horse) Bukkit.getEntity(horseUUID);
			
			if (horse == null) {
				continue;
			}
			
			System.out.println(pConfig.getDouble("Jump"));
			System.out.println(pConfig.getInt("Speed"));

			HorseData hData = new HorseData(p, horse);
			hData.setHorseId(pConfig.getInt("ID"));
			hData.setHorseName(pConfig.getString("Name"));
			hData.getLevelHandler().setMaxJumpLevel(pConfig.getDouble("MaxJump"));
			hData.getLevelHandler().setJumpLevel(pConfig.getDouble("Jump"));
			hData.getLevelHandler().setMaxSpeedLevel(pConfig.getInt("MaxSpeed"));
			hData.getLevelHandler().setSpeedLevel(pConfig.getInt("Speed"));
			hData.getLevelHandler().setStaminaLevel(pConfig.getInt("Stamina"));
			hData.getLevelHandler().setSpeedXp(pConfig.getInt("SpeedXP"));
			hData.getLevelHandler().setJumpXp(pConfig.getInt("JumpXP"));
			hData.setAge(pConfig.getInt("Age"));
			hData.setGender(Gender.valueOf(pConfig.getString("Gender").toUpperCase()));
			hData.setBreed(pConfig.getString("Breed"));

			if (hData.getBreed() != null) {
				hData.setBreedName(hData.getBreed().getName());
			}

			hData.setSire(pConfig.getString("Sire"));
			hData.setDam(pConfig.getString("Dam"));
			hData.setBrokenIn(pConfig.getInt("BrokenIn"));
			hData.setProtected(pConfig.getInt("Protected"));
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			try {
				hData.setDateClaimed(dateFormat.parse(pConfig.getString("DateClaimed")));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			for (String friend : pConfig.getStringList("Friends")) {
				hData.getFriends().add(UUID.fromString(friend));
			}
			
			totalOwnedHorses = playerFiles.length;
			horses.put(horse.getUniqueId(), hData);
			Horses.getInstance().getDataHandler().addHorse(horse);
		}
	}

	public void claimHorse(Horse horse, String name) {
		totalOwnedHorses++;
		HorseData playerHorse = new HorseData(p, horse);
		playerHorse.setHorseId(totalOwnedHorses);
		playerHorse.setHorseName(name);
		playerHorse.setDateClaimed(new Date());

		horses.put(playerHorse.getHorse().getUniqueId(), playerHorse);
		Horses.getInstance().getDataHandler().addHorse(horse);
	}
	
	public Horse getHorse(int id) {
		for (HorseData hData : horses.values()) {
			if (hData.getHorseId() == id) {
				return (Horse) hData.getHorse();
			}
		}
		return null;
	}
	
	public Player getPlayer() {
		return p;
	}

	public void setPlayer(Player p) {
		this.p = p;
	}
	
	public int getTotalOwnedHorses() {
		return totalOwnedHorses;
	}

	public HashMap<UUID, HorseData> getHorses() {
		return horses;
	}
}
