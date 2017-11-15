package edu.vt.alic.horses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import edu.vt.alic.horses.breeds.AmericanPaintHorse;
import edu.vt.alic.horses.breeds.Andalusian;
import edu.vt.alic.horses.breeds.Appaloosa;
import edu.vt.alic.horses.breeds.Appendix;
import edu.vt.alic.horses.breeds.Arabian;
import edu.vt.alic.horses.breeds.BelgiumDraft;
import edu.vt.alic.horses.breeds.Breed;
import edu.vt.alic.horses.breeds.Clydesdale;
import edu.vt.alic.horses.breeds.Cob;
import edu.vt.alic.horses.breeds.Connemara;
import edu.vt.alic.horses.breeds.Fjord;
import edu.vt.alic.horses.breeds.Friesian;
import edu.vt.alic.horses.breeds.GypsyVanner;
import edu.vt.alic.horses.breeds.Haflinger;
import edu.vt.alic.horses.breeds.Icelandic;
import edu.vt.alic.horses.breeds.Lippizzaner;
import edu.vt.alic.horses.breeds.Morgan;
import edu.vt.alic.horses.breeds.Mustang;
import edu.vt.alic.horses.breeds.NewForestPony;
import edu.vt.alic.horses.breeds.Oldenberg;
import edu.vt.alic.horses.breeds.PasoFino;
import edu.vt.alic.horses.breeds.Percheron;
import edu.vt.alic.horses.breeds.QuarterHorse;
import edu.vt.alic.horses.breeds.ShetlandPony;
import edu.vt.alic.horses.breeds.Shire;
import edu.vt.alic.horses.breeds.TennesseeWalker;
import edu.vt.alic.horses.breeds.Thoroughbred;
import edu.vt.alic.horses.breeds.Warmblood;
import edu.vt.alic.horses.breeds.WelshPony;

/**
 * Some reusable methods.
 */
public class Util {

	private Util() {}

	/**
	 * For lack of a better way, it creates an object based on the name.
	 * I do this because I store the breeds name in the horse's file.
	 * @param breed
	 * @return Breed object
	 */
	public static Breed getBreedByString(String breed) {
		switch (breed) {
		case "American Paint Horse": return new AmericanPaintHorse();
		case "Appaloosa": return new Appaloosa();
		case "Arabian": return new Arabian();
		case "Clydesdale": return new Clydesdale();
		case "Fjord": return new Fjord();
		case "Friesian": return new Friesian();
		case "Gypsy Vanner": return new GypsyVanner();
		case "Haflinger": return new Haflinger();
		case "Icelandic": return new Icelandic();
		case "Morgan": return new Morgan();
		case "Mustang": return new Mustang();
		case "Percheron": return new Percheron();
		case "Quarter Horse": return new QuarterHorse();
		case "Shire": return new Shire();
		case "Tennessee Walker": return new TennesseeWalker();
		case "Thoroughbred": return new Thoroughbred();
		case "Warmblood": return new Warmblood();
		case "Welsh Pony": return new WelshPony();
		case "Belgium Draft": return new BelgiumDraft();
		case "Paso Fino": return new PasoFino();
		case "Connemara": return new Connemara();
		case "New Forest Pony": return new NewForestPony();
		case "Shetland Pony": return new ShetlandPony();
		case "Andalusian": return new Andalusian();
		case "Lippizzaner": return new Lippizzaner();
		case "Oldenberg": return new Oldenberg();
		case "Appendix": return new Appendix();
		case "Cob": return new Cob();
		}
		return null;
	}

	public static ItemStack getChecker() {
		ItemStack checker = new ItemStack(Material.WOOD_HOE);
		ItemMeta checkerMeta = checker.getItemMeta();

		checkerMeta.setDisplayName(ChatColor.DARK_AQUA + "Checker");
		checkerMeta.setLore(Arrays.asList("Check if a horse is a stallion, geld, or mare."));
		checker.setItemMeta(checkerMeta);
		return checker;
	}

	public static ItemStack getGeldingTool() {
		ItemStack geldTool = new ItemStack(Material.SHEARS);
		ItemMeta geldToolMeta = geldTool.getItemMeta();

		geldToolMeta.setDisplayName(ChatColor.DARK_AQUA + "Gelding Tool");
		geldToolMeta.setLore(Arrays.asList("Change the gender of a horse from stallion to geld."));
		geldTool.setItemMeta(geldToolMeta);
		return geldTool;
	}

	/**
	 * GUI with all the breeds. This code will be used with the /h set breed and /h breedlist commands.
	 */
	public static Inventory getGui(Horse horse, String name) {
		Inventory gui = Bukkit.createInventory(null, 36, name);

		gui.setItem(0, getBreedItemStack(horse, getBreedByString("American Paint Horse")));
		gui.setItem(1, getBreedItemStack(horse, getBreedByString("Appaloosa")));
		gui.setItem(2, getBreedItemStack(horse, getBreedByString("Arabian")));
		gui.setItem(3, getBreedItemStack(horse, getBreedByString("Clydesdale")));
		gui.setItem(4, getBreedItemStack(horse, getBreedByString("Fjord")));
		gui.setItem(5, getBreedItemStack(horse, getBreedByString("Friesian")));
		gui.setItem(6, getBreedItemStack(horse, getBreedByString("Gypsy Vanner")));
		gui.setItem(7, getBreedItemStack(horse, getBreedByString("Haflinger")));
		gui.setItem(8, getBreedItemStack(horse, getBreedByString("Icelandic")));
		gui.setItem(9, getBreedItemStack(horse, getBreedByString("Morgan")));
		gui.setItem(10, getBreedItemStack(horse, getBreedByString("Mustang")));
		gui.setItem(11, getBreedItemStack(horse, getBreedByString("Percheron")));
		gui.setItem(12, getBreedItemStack(horse, getBreedByString("Quarter Horse")));
		gui.setItem(13, getBreedItemStack(horse, getBreedByString("Shire")));
		gui.setItem(14, getBreedItemStack(horse, getBreedByString("Tennessee Walker")));
		gui.setItem(15, getBreedItemStack(horse, getBreedByString("Thoroughbred")));
		gui.setItem(16, getBreedItemStack(horse, getBreedByString("Warmblood")));
		gui.setItem(17, getBreedItemStack(horse, getBreedByString("Welsh Pony")));
		gui.setItem(18, getBreedItemStack(horse, getBreedByString("Belgium Draft")));
		gui.setItem(19, getBreedItemStack(horse, getBreedByString("Paso Fino")));
		gui.setItem(20, getBreedItemStack(horse, getBreedByString("Connemara")));
		gui.setItem(21, getBreedItemStack(horse, getBreedByString("New Forest Pony")));
		gui.setItem(22, getBreedItemStack(horse, getBreedByString("Shetland Pony")));
		gui.setItem(23, getBreedItemStack(horse, getBreedByString("Andalusian")));
		gui.setItem(24, getBreedItemStack(horse, getBreedByString("Lippizzaner")));
		gui.setItem(25, getBreedItemStack(horse, getBreedByString("Oldenberg")));
		gui.setItem(26, getBreedItemStack(horse, getBreedByString("Appendix")));
		gui.setItem(27, getBreedItemStack(horse, getBreedByString("Cob")));

		ItemStack exit = new ItemStack(Material.BARRIER);
		ItemMeta exitMeta = exit.getItemMeta();
		exitMeta.setDisplayName(ChatColor.RED + "Exit");
		exit.setItemMeta(exitMeta);
		gui.setItem(35, exit);

		return gui;
	}

	/**
	 * This code here will make the ItemStack's in the GUI display information about breeds and
	 * possibilities of changing breeds. This code here is mainly used in the /h set breed command.
	 */
	public static ItemStack getBreedItemStack(Horse horse, Breed breed) {
		ItemStack breedItem = new ItemStack(Material.IRON_BARDING);

		ItemMeta breedItemMeta = breedItem.getItemMeta();
		breedItemMeta.setDisplayName(ChatColor.AQUA + breed.getName());

		List<String> lore = new ArrayList<String>();

		boolean canSet = true;

		// -- This code here will show the player if the player can change the breed based on the
		//    current horse's style and color. A black horse with paint cannot be changed to an Icelandic.
		if (!breed.getSupportedColors().contains(horse.getColor())
				|| !breed.getSupportedStyles().contains(horse.getStyle())) {
			canSet = false;
		}

		if (canSet) {
			lore.add("Can Set Breed: " + ChatColor.GREEN + canSet);
		} else {
			lore.add("Can Set Breed: " + ChatColor.RED + canSet);
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		lore.add("Height: " + ChatColor.WHITE + breed.getHeight());
		lore.add("Specialization: " + ChatColor.WHITE + breed.getSpecialization().toString());
		lore.add("Colors: ");

		for (int i = 0; i < breed.getSupportedColors().size(); i++) {
			switch (breed.getSupportedColors().get(i).toString()) {
			case "BROWN": lore.add(ChatColor.WHITE + "  - Bay"); break;
			case "BLACK": lore.add(ChatColor.WHITE + "  - Black"); break;
			case "DARK_BROWN": lore.add(ChatColor.WHITE + "  - Dark Bay"); break;
			case "CREAMY": lore.add(ChatColor.WHITE + "  - Palomino"); break;
			case "GRAY": lore.add(ChatColor.WHITE + "  - Gray"); break;
			case "WHITE": lore.add(ChatColor.WHITE + "  - White"); break;
			case "CHESTNUT": lore.add(ChatColor.WHITE + "  - Chestnut"); break;
			}
		}

		lore.add("Styles: ");
		for (int i = 0; i < breed.getSupportedStyles().size(); i++) {
			switch (breed.getSupportedStyles().get(i).toString()) {
			case "NONE": lore.add(ChatColor.WHITE + "  - None"); break;
			case "BLACK_DOTS": lore.add(ChatColor.WHITE + "  - Sooty"); break;
			case "WHITE": lore.add(ChatColor.WHITE + "  - Socks"); break;
			case "WHITE_DOTS": lore.add(ChatColor.WHITE + "  - Appaloosa"); break;
			case "WHITEFIELD": lore.add(ChatColor.WHITE + "  - Paint"); break;
			}
		}


		breedItemMeta.setLore(lore);
		breedItem.setItemMeta(breedItemMeta);

		return breedItem;
	}

	/**
	 * The server this plugin is meant for uses different color names than MC.
	 * @param color
	 * @return Horse Color object
	 */
	public static Color getColor(String color) {
		if (color.equalsIgnoreCase("black")) {
			return Color.BLACK;
		} else if (color.equalsIgnoreCase("chestnut")) {
			return Color.CHESTNUT;
		} else if (color.equalsIgnoreCase("palomino")) {
			return Color.CREAMY;
		} else if (color.equalsIgnoreCase("bay")) {
			return Color.BROWN;
		} else if (color.equalsIgnoreCase("darkbay")) {
			return Color.DARK_BROWN;
		} else if (color.equalsIgnoreCase("gray")) {
			return Color.GRAY;
		} else if (color.equalsIgnoreCase("white")) {
			return Color.WHITE;
		}
		return null;
	}

	/**
	 * The server this plugin is meant for uses different style names than MC.
	 * @param style
	 * @return Horse Style object
	 */
	public static Style getStyle(String style) {
		if (style.equalsIgnoreCase("paint")) {
			return Style.WHITEFIELD;
		} else if (style.equalsIgnoreCase("appaloosa")) {
			return Style.WHITE_DOTS;
		} else if (style.equalsIgnoreCase("socks")) {
			return Style.WHITE;
		} else if (style.equalsIgnoreCase("none")) {
			return Style.NONE;
		} else if (style.equalsIgnoreCase("sooty")) {
			return Style.BLACK_DOTS;
		}
		return null;
	}	
}