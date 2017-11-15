package edu.vt.alic.horses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import edu.vt.alic.horses.commands.HCommand;
import edu.vt.alic.horses.listeners.EntityDamageListener;
import edu.vt.alic.horses.listeners.EntityHitListener;
import edu.vt.alic.horses.listeners.EntityMountListener;
import edu.vt.alic.horses.listeners.HorseTrainListener;
import edu.vt.alic.horses.listeners.InventoryClickListener;
import edu.vt.alic.horses.listeners.PlayerJoinListener;
import edu.vt.alic.horses.listeners.PlayerQuitListener;

public class Horses extends JavaPlugin {

	private static Horses instance;

	public static Horses getInstance() {
		return instance;
	}

	private DataHandler dataHandler;
	private BukkitTask autoSaver;
	private BukkitTask locationAutoSaver;

	private FileConfiguration speedConfig;
	private File speedFile;
	private FileConfiguration jumpConfig;
	private File jumpFile;

	private HashMap<Double, Integer> jumpLevels;
	private HashMap<Integer, Integer> speedLevels;

	@Override
	public void onEnable() {
		instance = this;

		dataHandler = new DataHandler(this);
		jumpLevels = new HashMap<Double, Integer>();
		speedLevels = new HashMap<Integer, Integer>();

		speedFile = new File(getDataFolder(), "speedxp.yml");
		jumpFile = new File(getDataFolder(), "jumpxp.yml");

		speedConfig = YamlConfiguration.loadConfiguration(speedFile);
		jumpConfig = YamlConfiguration.loadConfiguration(jumpFile);

		populateLevels();
		setupListeners();
		setupCommands();
		setupTask();
		setupConfig();
	}

	@Override
	public void onDisable() {
		autoSaver.cancel();
		locationAutoSaver.cancel();

		for (Player p : Bukkit.getOnlinePlayers()) {
			saveDataToConfig(p, false);
			if (p.getVehicle() instanceof Horse) {
				Horse horse = (Horse) p.getVehicle();
				horse.eject();
			}
		}
	}

	private void populateLevels() {
		for (String path : speedConfig.getConfigurationSection("Levels").getKeys(false)) {
			speedLevels.put(Integer.parseInt(path), speedConfig.getInt("Levels." + path));
		}

		for (String path : jumpConfig.getConfigurationSection("Levels").getKeys(false)) {
			jumpLevels.put(Double.parseDouble(path.replace(",", ".")), jumpConfig.getInt("Levels." + path));
		}
	}

	private void setupListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		pm.registerEvents(new InventoryClickListener(), this);
		pm.registerEvents(new EntityHitListener(), this);
		pm.registerEvents(new EntityDamageListener(), this);
		pm.registerEvents(new HorseTrainListener(), this);
		pm.registerEvents(new EntityMountListener(), this);
	}

	private void setupCommands() {
		getCommand("h").setExecutor(new HCommand());
	}

	private void setupTask() {
		autoSaver = new AutoSaver().runTaskTimer(this, 20, (long) getConfig().getInt("Save Interval") * 60 * 20);
		locationAutoSaver = new AutoSaver().runTaskTimer(this, 20, (long) 100);
	}

	private void setupConfig() {
		if (!new File(getDataFolder(), "config.yml").exists()) {
			getConfig().getDefaults();
			getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}

	public void saveDataToConfig(Player p, boolean updateOnlyLocations) {
		PlayerData pData = getDataHandler().getPlayerData(p);
		File folder = new File(getDataFolder() + File.separator + "data" + File.separator + p.getUniqueId());

		if (!folder.exists()) {
			folder.mkdir();
			return;
		}

		for (HorseData hData : pData.getHorses().values()) {
			if (updateOnlyLocations) {
				getDataHandler().getHorseLocations().put(hData.getHorse().getUniqueId(), hData.getHorse().getLocation());
				continue;
			}
			
			File pFile = new File(getDataFolder() 
					+ File.separator 
					+ "data" 
					+ File.separator 
					+ p.getUniqueId().toString() 
					+ File.separator 
					+ hData.getHorse().getUniqueId().toString() + ".yml");

			if (!pFile.exists()) {
				try {
					pFile.createNewFile();
				} catch (IOException e) {
					getLogger().severe("Error with creating YML file for horse for certain player.");
					getServer().getPluginManager().disablePlugin(this);
				}
			}

			FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
			
			pConfig.set("ID", hData.getHorseId());
			pConfig.set("Name", hData.getHorseName());
			pConfig.set("JumpXP", hData.getLevelHandler().getJumpXp());
			pConfig.set("Jump", hData.getLevelHandler().getJumpLevel());
			pConfig.set("MaxJump", hData.getLevelHandler().getMaxJumpLevel());
			pConfig.set("SpeedXP", hData.getLevelHandler().getSpeedXp());
			pConfig.set("Speed", hData.getLevelHandler().getSpeedLevel());
			pConfig.set("MaxSpeed", hData.getLevelHandler().getMaxSpeedLevel());
			pConfig.set("Stamina", hData.getLevelHandler().getStaminaLevel());
			pConfig.set("Age", hData.getAge(new Date()));
			pConfig.set("Gender", hData.getGender().toString());
			pConfig.set("Breed", hData.getBreedName());
			pConfig.set("Sire", hData.getSire());
			pConfig.set("Dam", hData.getDam());
			pConfig.set("BrokenIn", hData.isBrokenIn());
			pConfig.set("Protected", hData.isProtected());
			pConfig.set("Location.World", getDataHandler().getHorseLocation(hData.getHorse()).getWorld());
			pConfig.set("Location.Chunk.X", getDataHandler().getHorseLocation(hData.getHorse()).getChunk().getX());
			pConfig.set("Location.Chunk.Z", getDataHandler().getHorseLocation(hData.getHorse()).getChunk().getZ());

			List<String> friends = new ArrayList<String>();
			for (UUID uuid : hData.getFriends()) {
				friends.add(uuid.toString());
			}
			pConfig.set("Friends", friends);

			try {
				pConfig.save(pFile);
			} catch (IOException e) {
				getLogger().severe("Error with saving YamlConfiguration.");
				getServer().getPluginManager().disablePlugin(this);
			}

		}
	}

	public HashMap<Integer, Integer> getSpeedLevels() {
		return speedLevels;
	}

	public HashMap<Double, Integer> getJumpLevels() {
		return jumpLevels;
	}

	public DataHandler getDataHandler() {
		return dataHandler;
	}
}




































