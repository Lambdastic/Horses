package edu.vt.alic.horses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import edu.vt.alic.horses.breeds.Breed;

public class HorseData {

	private Player p;
	private Horse horse;
	private int id;
	private LevelHandler levelHandler;
	private String name;
	private int age;
	private Gender gender;
	private Breed breed;
	private String breedName;
	private String sire;
	private String dam;
	private int isProtected;
	private int isBrokenIn;
	private Date dateClaimed;
	private List<UUID> friends;

	public HorseData(Player p, Horse horse) {
		this.p = p;
		this.name = "UNKNOWN";
		this.sire = "UNKNOWN";
		this.dam = "UNKNOWN";
		this.breedName = "UNKNOWN";
		this.gender = (Math.random() > 0.5) ? Gender.STALLION : Gender.MARE;
		this.horse = horse;
		this.friends = new ArrayList<UUID>();
		this.levelHandler = new LevelHandler(horse);
		this.horse.setCustomName(name);
		this.horse.setCustomNameVisible(true);
		this.horse.setRemoveWhenFarAway(false);
	}


	public Player getPlayer() {
		return p;
	}

	public void setPlayer(Player p) {
		this.p = p;
	}

	public Horse getHorse() {
		return horse;
	}

	public String getHorseName() {
		return name;
	}

	public void setHorseName(String name) {
		this.name = name;
		horse.setCustomName(name);
	}

	public int getHorseId() {
		return id;
	}

	public void setHorseId(int id) {
		this.id = id;
	}

	public int getAge(Date currentDate) {
		return ((int) (TimeUnit.DAYS.convert(currentDate.getTime() - dateClaimed.getTime(), TimeUnit.MILLISECONDS) / 14) + age);
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public boolean isBreedable() {
		return (gender == Gender.GELD) ? false : true;
	}

	public Breed getBreed() {
		return breed;
	}

	public void setBreed(Breed breed) {
		this.breed = breed;
	}

	public void setBreed(String breed) {
		this.breed = Util.getBreedByString(breed);
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public String getBreedName() {
		return this.breedName;
	}

	public String getSire() {
		return sire;
	}

	public void setSire(String sire) {
		this.sire = sire;
	}

	public String getDam() {
		return dam;
	}	
	public void setDam(String dam) {
		this.dam = dam;
	}

	public boolean isProtectedBoolean() {
		return (isProtected == 1) ? true : false;
	}

	public int isProtected() {
		return isProtected;
	}

	public void setProtected(int isProtected) {
		this.isProtected = isProtected;
	}

	public boolean isBrokenInBoolean() {
		return (getAge(new Date()) > 0) ? true : false;
	}

	public int isBrokenIn() {
		return isBrokenIn;
	}

	public void setBrokenIn(int isBrokenIn) {
		this.isBrokenIn = isBrokenIn;
	}

	public Date getDateClaimed() {
		return dateClaimed;
	}

	public void setDateClaimed(Date dateClaimed) {
		this.dateClaimed = dateClaimed;
	}

	public List<UUID> getFriends() {
		return friends;
	}
	
	public LevelHandler getLevelHandler() {
		return levelHandler;
	}
}