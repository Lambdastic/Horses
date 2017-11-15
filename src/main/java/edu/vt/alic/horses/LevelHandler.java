package edu.vt.alic.horses;

import java.util.Random;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class LevelHandler {
		
	private Horse horse;
	
	private double jumpLevel;
	private double maxJumpLevel;
	private int jumpXp;
	
	private int speedLevel;
	private int maxSpeedLevel;
	private int speedXp;
	
	private int staminaLevel;
	
	public LevelHandler(Horse horse) {
		this.jumpLevel = 1.5;
		this.speedLevel = 2;
		this.staminaLevel = 5;
		
		Random rand = new Random();
		this.maxJumpLevel = rand.nextInt(4) + 2;
		this.maxSpeedLevel = rand.nextInt(6) + 5;
		
		horse.setJumpStrength((jumpLevel - 1.5D) * 0.6D / 4.0D + 0.50D);
		horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.02319587628865979381 * speedLevel);
	}
	
	public double getJumpLevel() {
		return jumpLevel;
	}
	
	public void setJumpLevel(double jumpLevel) {
		if (jumpLevel > maxJumpLevel) {
			return;
		}
		this.jumpLevel = jumpLevel;
		horse.setJumpStrength((jumpLevel - 1.5D) * 0.6D / 4.0D + 0.50D);
	}
	
	public double getMaxJumpLevel() {
		return maxJumpLevel;
	}
	
	public void setMaxJumpLevel(double maxJumpLevel) {
		this.maxJumpLevel = maxJumpLevel;
	}
	
	public int getJumpXp() {
		return jumpXp;
	}
	
	public void setJumpXp(int jumpXp) {
		this.jumpXp = jumpXp;
	}
	
	public int getSpeedLevel() {
		return speedLevel;
	}
	
	public void setSpeedLevel(int speedLevel) {
		if (speedLevel > maxSpeedLevel) {
			return;
		}
		this.speedLevel = speedLevel;
		horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.02319587628865979381 * speedLevel);
	}
	
	public int getMaxSpeedLevel() {
		return maxSpeedLevel;
	}
	
	public void setMaxSpeedLevel(int maxSpeedLevel) {
		this.maxSpeedLevel = maxSpeedLevel;
	}
	
	public int getSpeedXp() {
		return speedXp;
	}
	
	public void setSpeedXp(int speedXp) {
		this.speedXp = speedXp;
	}
	
	public int getStaminaLevel() {
		return staminaLevel;
	}
	
	public void setStaminaLevel(int staminaLevel) {
		this.staminaLevel = staminaLevel;
	}
	
	public void addXp(Player p, Specialization specialization, int xp) {
		if (specialization == Specialization.JUMP) {
			jumpXp += (xp * 2);
			speedXp += xp;
		} else if (specialization == Specialization.SPEED) {
			speedXp += (xp * 2);
			jumpXp += xp;
		} else {
			speedXp += xp;
			jumpXp += xp;
		}

		double nextJumpLevel = jumpLevel + 0.5;
		int nextSpeedLevel = speedLevel + 1;

		if (nextJumpLevel <= maxJumpLevel) {
			if (Horses.getInstance().getJumpLevels().get(nextJumpLevel) == null) {
				return;
			}

			if (this.jumpXp >= Horses.getInstance().getJumpLevels().get(nextJumpLevel)) {
				jumpLevel += 0.5;
				jumpXp = 0;
				p.sendMessage(Messages.JUMP_LEVEL_UP);
			}
		}

		if (nextSpeedLevel <= maxSpeedLevel) {
			if (Horses.getInstance().getSpeedLevels().get(nextSpeedLevel) == null) {
				return;
			}
			if (this.speedXp >= Horses.getInstance().getSpeedLevels().get(nextSpeedLevel)) {
				speedLevel += 1;
				speedXp = 0;
				p.sendMessage(Messages.SPEED_LEVEL_UP);
			}
		}
	}
}
