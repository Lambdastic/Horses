package edu.vt.alic.horses;

import org.bukkit.ChatColor;

/**
 * Did not care to make a Messages.yml file since the owner didn't care.
 * Will add a separate YML file in the future to make the plugin more modular.
 */
public class Messages {

	private Messages() {}
	
	// -- This line here is the prefix to all the messages.
	private static String TAG = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Horse Manager" + ChatColor.DARK_AQUA + "] ";
	
	public static String NO_PERM = TAG + ChatColor.RED + "You do not have the permission to perform that command.";
	public static String MUST_BE_PLAYER = TAG + ChatColor.RED + "You must be a player to perform that command.";
	public static String PLAYER_DOESNT_EXIST = TAG + ChatColor.RED + "That player does not exist or is not online.";
	public static String INVALID_COMMAND = TAG + ChatColor.RED + "Incorrect syntax. Type /h for help.";
	public static String MUST_BE_RIDING_HORSE = TAG + ChatColor.RED + "You must be riding a horse to perform that.";
	public static String NOT_CLAIMED = TAG + ChatColor.RED + "That horse is not yours to perform that command.";
	public static String MUST_BE_TAMED = TAG + ChatColor.RED + "That horse must be tamed to be claimed.";
	public static String DOESNT_EXIST = TAG + ChatColor.RED + "That horse ID does not exist.";
	public static String CHECKER_TOOL_ADDED = TAG + ChatColor.AQUA + "The gender checker tool has been added to your inventory.";
	public static String GELD_TOOL_ADDED = TAG + ChatColor.AQUA + "The geld tool has been added to your inventory.";
	public static String CLAIMED_HORSE = TAG + ChatColor.AQUA + "You have claimed that horse.";
	public static String RENAMED_HORSE = TAG + ChatColor.AQUA + "You have renamed your horse.";
	public static String STATS_CHANGED = TAG + ChatColor.AQUA + "You have changed the stat for that horse.";
	public static String CHANGED_BREED = TAG + ChatColor.AQUA + "You have changed the breed on that horse.";
	public static String KILLED_HORSE = TAG + ChatColor.AQUA + "You have killed your horse.";
	public static String PROTECTED_HORSE = TAG + ChatColor.AQUA + "You have protected your horse.";
	public static String UNPROTECTED_HORSE = TAG + ChatColor.AQUA + "You have removed protection from your horse.";
	public static String GELDED_HORSE = TAG + ChatColor.AQUA + "You have gelded the horse.";
	public static String GENDER = TAG + ChatColor.AQUA + "The gender of that horse is ";
	public static String OWNER = TAG + ChatColor.AQUA + "The owner of that horse is ";
	public static String ALREADY_GELD = TAG + ChatColor.RED + "That horse is already a geld.";
	public static String MUST_BE_STALLION = TAG + ChatColor.RED + "That horse must be a stallion to geld.";
	public static String NOT_SUPPORTED_COLOR = TAG + ChatColor.RED + "That breed cant have the color.";
	public static String NOT_SUPPORTED_STYLE = TAG + ChatColor.RED + "That breed cant have the style.";
	public static String BREED_DOESNT_EXIST = TAG + ChatColor.RED + "That horse breed doesn't exist.";
	public static String CANNOT_SET_LEVEL = TAG + ChatColor.RED + "You cannot set the level of that stat to something higher than the maximum level.";
	public static String COLOR_DOESNT_EXIST = TAG + ChatColor.RED + "That horse color doesn't exist.";
	public static String GENDER_DOESNT_EXIST = TAG + ChatColor.RED + "That horse gender doesn't exist.";
	public static String STYLE_DOESNT_EXIST = TAG + ChatColor.RED + "That horse style doesn't exist.";
	public static String BREED_DOESNT_USE_COLOR = TAG + ChatColor.RED + "You cannot set that horse to a color not in its breed. Change the breed first.";
	public static String BREED_DOESNT_USE_STYLE = TAG + ChatColor.RED + "You cannot set that horse to a style not in its breed. Change the breed first.";
	public static String ID_MUST_BE_INT = TAG + ChatColor.RED + "Horse ID must be an integer.";
	public static String HORSE_ID_DOESNT_EXIST = TAG + ChatColor.RED + "That horse ID does not exist in your claimlist.";
	public static String LEVEL_MUST_BE_INT = TAG + ChatColor.RED + "Level must be an integer.";
	public static String AGE_MUST_BE_INT = TAG + ChatColor.RED + "Age must be an integer.";
	public static String PAGE_MUST_BE_INT = TAG + ChatColor.RED + "Page number must be an integer.";
	public static String MAX_PAGE = TAG + ChatColor.RED + "There are only pages 1, 2, and 3.";
	public static String LEVEL_MUST_BE_DECIMAL = TAG + ChatColor.RED + "Level must be an decimal.";
	public static String STAT_DOESNT_EXIST = TAG + ChatColor.RED + "That stat does not exist.";
	public static String HORSE_COLOR_SET = TAG + ChatColor.BLUE + "Horse color successfully changed.";
	public static String HORSE_STYLE_SET = TAG + ChatColor.BLUE + "Horse style successfully changed.";
	public static String HORSE_GENDER_SET = TAG + ChatColor.BLUE + "Horse gender successfully changed.";
	public static String HORSE_DEAD = TAG + ChatColor.BLUE + "Your horse has died somehow.";
	public static String JUMP_LEVEL_UP = TAG + ChatColor.AQUA + "The jump stat on your horse has gone up!";
	public static String SPEED_LEVEL_UP = TAG + ChatColor.AQUA + "The speed stat on your horse has gone up!";
	public static String ADDED_FRIEND = TAG + ChatColor.AQUA + "You have added a friend to that horse!";
	public static String REMOVED_FRIEND = TAG + ChatColor.AQUA + "You have removed a friend from that horse!";
	public static String THAT_PLAYER_IS_NOT_FRIEND = TAG + ChatColor.AQUA + "That player is not a friend of that horse to be unfriended.";
	public static String CANNOT_RIDE_HORSE = TAG + ChatColor.RED + "You do not own that horse. In order to ride it, ask the owner to add you as a friend to the horse.";
	public static String TRANSFER_HORSE = TAG + ChatColor.AQUA + "You have transferred your horse to " + ChatColor.DARK_AQUA;
	public static String AGE_CHANGED = TAG + ChatColor.AQUA + "You have changed the age of that horse.";
	public static String FIXED_HORSES = TAG + ChatColor.AQUA + "Plugin found some missing horses! Type /h cl to verify.";
	public static String DIDNT_FIX = TAG + ChatColor.RED + "Plugin was unable to find any of your horses.";
	public static String REGION_NOT_SELECTED = TAG + ChatColor.RED + "You must first select a region with WorldEdit to use that command.";
	public static String REGION_SET = TAG + ChatColor.GREEN + "That region has successfully been added to the config file.";

}
