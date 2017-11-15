package edu.vt.alic.horses.commands;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import edu.vt.alic.horses.Gender;
import edu.vt.alic.horses.HorseData;
import edu.vt.alic.horses.Horses;
import edu.vt.alic.horses.Messages;
import edu.vt.alic.horses.PlayerData;
import edu.vt.alic.horses.Util;

public class HCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("h")) {
			if (args.length == 0) {
				if (sender.hasPermission("horses.help")) 
					showHelpMenu(sender, 1);
				else sender.sendMessage(Messages.NO_PERM);
				return true;
			}

			if (args[0].equalsIgnoreCase("help")) {
				if (args.length > 2) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				
				int pageNumber = 0;
				
				if (args.length < 2) {
					pageNumber = 1;
					if (sender.hasPermission("horses.help"))
						showHelpMenu(sender, pageNumber);
					else sender.sendMessage(Messages.NO_PERM);
					return true;
				}
				
				try {
					pageNumber = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					sender.sendMessage(Messages.PAGE_MUST_BE_INT);
					return true;
				}
				
				if (sender.hasPermission("horses.help"))
					showHelpMenu(sender, pageNumber);
				else sender.sendMessage(Messages.NO_PERM);
			}

			if (args[0].equalsIgnoreCase("claim")) {
				if (args.length == 1) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (!(sender instanceof Player)) {
					sender.sendMessage(Messages.MUST_BE_PLAYER);
					return true;
				}
				if (sender.hasPermission("horses.claim"))
					claimHorse((Player) sender, args);
				else sender.sendMessage(Messages.NO_PERM);
			}

			else if (args[0].equalsIgnoreCase("rename")) {
				if (args.length != 2) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (!(sender instanceof Player)) {
					sender.sendMessage(Messages.MUST_BE_PLAYER);
					return true;
				}
				if (sender.hasPermission("horses.rename"))
					renameHorse((Player) sender, args);
				else sender.sendMessage(Messages.NO_PERM);
			}

			else if (args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("tpme")) {
				if (args.length != 2) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (!(sender instanceof Player)) {
					sender.sendMessage(Messages.MUST_BE_PLAYER);
					return true;
				}
				tpHorse((Player) sender, args);
			}

			else if (args[0].equalsIgnoreCase("claimlist") || args[0].equalsIgnoreCase("cl")) {
				if (args.length > 2) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (args.length == 1) { //this is for regular command /h cl
					if (!(sender instanceof Player)) {
						sender.sendMessage(Messages.MUST_BE_PLAYER);
						return true;
					}
					if (sender.hasPermission("horses.claimlist"))
						viewClaimList((Player) sender);
					else sender.sendMessage(Messages.NO_PERM);
				} else { //this is for admin command /h cl {username}
					if (Bukkit.getPlayer(args[1]) != null) {
						Player p = Bukkit.getPlayer(args[1]);
						if (sender.hasPermission("horses.claimlist.others"))
							viewClaimList(sender, p);
						else sender.sendMessage(Messages.NO_PERM);
					} else {
						sender.sendMessage(Messages.PLAYER_DOESNT_EXIST);
						return true;
					}
				}
			}

			else if (args[0].equalsIgnoreCase("kill")) {
				if (args.length != 3) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (Bukkit.getPlayer(args[1]) == null) {
					sender.sendMessage(Messages.PLAYER_DOESNT_EXIST);
					return true;
				}
				Player p = Bukkit.getPlayer(args[1]);

				if (sender.hasPermission("horses.kill")) 
					killHorse(sender, p, args);
				else sender.sendMessage(Messages.NO_PERM);
			}

			else if (args[0].equalsIgnoreCase("stats")) {
				if (args.length != 2) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (!(sender instanceof Player)) {
					sender.sendMessage(Messages.MUST_BE_PLAYER);
					return true;
				}
				if (sender.hasPermission("horses.stats")) {
					viewStats((Player) sender, args);
				}
				else sender.sendMessage(Messages.NO_PERM);
			}

			else if (args[0].equalsIgnoreCase("checker")) {
				if (args.length != 1) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (!(sender instanceof Player)) {
					sender.sendMessage(Messages.MUST_BE_PLAYER);
					return true;
				}

				if (sender.hasPermission("horses.checker")) {
					((Player) sender).getInventory().addItem(Util.getChecker());
					((Player) sender).sendMessage(Messages.CHECKER_TOOL_ADDED);
				}
				else sender.sendMessage(Messages.NO_PERM);
			}

			else if (args[0].equalsIgnoreCase("geld")) {
				if (args.length != 1) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (!(sender instanceof Player)) {
					sender.sendMessage(Messages.MUST_BE_PLAYER);
					return true;
				}

				if (sender.hasPermission("horses.geld")) {
					((Player) sender).getInventory().addItem(Util.getGeldingTool());
					((Player) sender).sendMessage(Messages.GELD_TOOL_ADDED);
				}
				else sender.sendMessage(Messages.NO_PERM);
			}

			else if (args[0].equalsIgnoreCase("protect")) {
				if (args.length != 1) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (!(sender instanceof Player)) {
					sender.sendMessage(Messages.MUST_BE_PLAYER);
					return true;
				}
				
				if (sender.hasPermission("horses.protect")) {
					protectHorse((Player) sender);
				}
				else sender.sendMessage(Messages.NO_PERM);
			}

			else if (args[0].equalsIgnoreCase("set")) {
				if (args.length > 4) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (!(sender instanceof Player)) {
					sender.sendMessage(Messages.MUST_BE_PLAYER);
					return true;
				}
				
				if (!sender.hasPermission("horses.set")) {
					sender.sendMessage(Messages.NO_PERM);
					return true;
				}

				if (args[1].equalsIgnoreCase("max")) {
					if (args[2].equalsIgnoreCase("jump") && args.length == 4) {
						setMaxStat((Player) sender, args);
					} else if (args[2].equalsIgnoreCase("speed") && args.length == 4) {
						setMaxStat((Player) sender, args);
					}
				} else if (args[1].equalsIgnoreCase("breed") && args.length == 2) {
					setBreed((Player) sender);
				} else if (args[1].equalsIgnoreCase("color") && args.length == 3) {
					setHorseColor((Player) sender, args);
				} else if (args[1].equalsIgnoreCase("style") && args.length == 3) {
					setHorseStyle((Player) sender, args);
				} else if (args[1].equalsIgnoreCase("gender") && args.length == 3) {
					setHorseGender((Player) sender, args);
				} else if (args[1].equalsIgnoreCase("jump") && args.length == 3) {
					setStat((Player) sender, args);
				} else if (args[1].equalsIgnoreCase("speed") && args.length == 3) {
					setStat((Player) sender, args);
				} else if (args[1].equalsIgnoreCase("stamina") && args.length == 3) {
					setStat((Player) sender, args);
				} else if (args[1].equalsIgnoreCase("age") && args.length == 3) {
					setHorseAge((Player) sender, args);
				}
				else {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
			}

			else if (args[0].equalsIgnoreCase("friend")) {
				if (args.length != 2) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (sender.hasPermission("horses.friend")) {
					addFriend((Player) sender, args);
				}
				else sender.sendMessage(Messages.NO_PERM);
			}
			
			else if (args[0].equalsIgnoreCase("unfriend")) {
				if (args.length != 2) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (sender.hasPermission("horses.friend")) {
					removeFriend((Player) sender, args);
				}
				else sender.sendMessage(Messages.NO_PERM);
			}

			else if (args[0].equalsIgnoreCase("breedlist") || args[0].equalsIgnoreCase("bl")) {
				if (args.length != 1) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (sender.hasPermission("horses.breedlist")) {
					showBreedList((Player) sender);
				}
				else sender.sendMessage(Messages.NO_PERM);
			}
			
			else if (args[0].equalsIgnoreCase("transfer")) {
				if (args.length != 2) {
					sender.sendMessage(Messages.INVALID_COMMAND);
					return true;
				}
				if (sender.hasPermission("horses.transfer")) {
					transferHorse((Player) sender, args);
				}
				else sender.sendMessage(Messages.NO_PERM);
			}
			else {
				sender.sendMessage(Messages.INVALID_COMMAND);
			}
		}

		return false;
	}

	private void showHelpMenu(CommandSender sender, int pageNumber) {	
		if (pageNumber > 3 || pageNumber < 1) {
			sender.sendMessage(Messages.MAX_PAGE);
			return;
		}
		
		sender.sendMessage(ChatColor.DARK_AQUA + "|" + ChatColor.GRAY + "---------------" + ChatColor.DARK_AQUA + "["
				+ ChatColor.AQUA + "Horses Help Menu | " + pageNumber 
				+ ChatColor.DARK_AQUA + "]" + ChatColor.GRAY + "---------------" + ChatColor.DARK_AQUA + "|");
		
		if (pageNumber == 1) {
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h [help]" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Show this awesome help menu.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h claim {name}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Claim a horse.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h rename {name}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Rename a horse.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h tp {horseID}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Teleport a horse to yourself.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h tpme {horseID}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Teleport to a horse.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h claimlist(cl)" + ChatColor.WHITE + " - " + ChatColor.GRAY + "List of all claimed horses.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h claimlist(cl) {player}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "List of all the player's claimed horses.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h kill {user} {horseID}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Kill a horse.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h stats {horseID}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "View a horse's stats.");
		}
		
		else if (pageNumber == 2) {
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h checker" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Get a checker tool.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h geld" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Get a geld tool.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h stats {horseID}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "View a horse's stats.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h protect" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Protect the horse you are riding.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h friend {player}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Add friend to horse.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h unfriend {player}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Remove friend to horse.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h breedlist(bl)" + ChatColor.WHITE + " - " + ChatColor.GRAY + "List of breeds.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h set breed" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Set the breed of the horse you are riding.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h set gender {gender}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Set the gender of the horse you are riding.");
		}
		
		else if (pageNumber == 3) {
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h set age {age}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Set the age of the horse you are riding.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h set color {color}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Set the color of the horse you are riding.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h set style {style}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Set the style of the horse you are riding.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h set jump {level}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Set the jump stat of the horse you are riding.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h set max jump {level}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Set the max jump stat of the horse you are riding.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h set speed {level}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Set the speed stat of the horse you are riding.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h set max speed {level}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Set the max speed stat of the horse you are riding.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h set stamina {level}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Set the stamina stat of the horse you are riding.");
			sender.sendMessage("  " + ChatColor.DARK_AQUA + "/h transfer {player}" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Transfer your horse to another player.");
		}

	}

	private void claimHorse(Player p, String[] args) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

		Horse horse = (Horse) p.getVehicle();
		if (!horse.isTamed()) {
			p.sendMessage(Messages.MUST_BE_TAMED);
			return;
		}

		String name = "";

		for (int i = 1; i < args.length; i++) {
			name = name.concat(args[i]).concat(" ");
		}
		name = name.trim();
		Horses.getInstance().getDataHandler().getPlayerData(p).claimHorse(horse, name);
		p.sendMessage(Messages.CLAIMED_HORSE);
	}

	private void renameHorse(Player p, String[] args) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

		Horse horse = (Horse) p.getVehicle();
		if (!Horses.getInstance().getDataHandler().getPlayerData(p).getHorses().containsKey(horse.getUniqueId())) {
			p.sendMessage(Messages.NOT_CLAIMED);
			return;
		}

		String name = "";

		for (int i = 1; i < args.length; i++) {
			name = name.concat(args[i]).concat(" ");
		}
		name = name.trim();

		Horses.getInstance().getDataHandler().getPlayerData(p).getHorses().get(horse.getUniqueId()).setHorseName(name);
		p.sendMessage(Messages.RENAMED_HORSE);
	}

	private void tpHorse(Player p, String[] args) {
		int horseId = 0;
		try {
			horseId = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			p.sendMessage(Messages.ID_MUST_BE_INT);
			return;
		}

		Horse horse = Horses.getInstance().getDataHandler().getPlayerData(p).getHorse(horseId);

		if (horse == null) {
			p.sendMessage(Messages.DOESNT_EXIST);
			return;
		}

		if (args[0].equalsIgnoreCase("tp")) {
			if (p.hasPermission("horses.tp"))
				horse.teleport(p);
			else p.sendMessage(Messages.NO_PERM);
		} else if (args[0].equalsIgnoreCase("tpme")) {
			if (p.hasPermission("horses.tpme"))
				p.teleport(horse);
			else p.sendMessage(Messages.NO_PERM);
		}
	}

	private void viewClaimList(CommandSender sender, Player p) {
		PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(p);
		sender.sendMessage(ChatColor.DARK_AQUA + "|" + ChatColor.GRAY + "---------------" + ChatColor.DARK_AQUA + "["
				+ ChatColor.AQUA + "Their Claimed Horses" 
				+ ChatColor.DARK_AQUA + "]" + ChatColor.GRAY + "---------------" + ChatColor.DARK_AQUA + "|");

		for (HorseData hData : pData.getHorses().values()) {
			sender.sendMessage(hData.getHorseId() + ""
					+ ChatColor.DARK_AQUA + " | " + ChatColor.WHITE + ""
					+ hData.getHorseName() 
					+ ChatColor.DARK_AQUA + " | " + ChatColor.WHITE + ""
					+ hData.getBreedName()
					+ ChatColor.DARK_AQUA + " | " + ChatColor.WHITE + ""
					+ hData.getGender().toString());
		}
	}

	private void viewClaimList(Player p) {
		PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(p);
		p.sendMessage(ChatColor.DARK_AQUA + "|" + ChatColor.GRAY + "---------------" + ChatColor.DARK_AQUA + "["
				+ ChatColor.AQUA + "Your Claimed Horses" 
				+ ChatColor.DARK_AQUA + "]" + ChatColor.GRAY + "---------------" + ChatColor.DARK_AQUA + "|");

		for (HorseData hData : pData.getHorses().values()) {
			p.sendMessage(hData.getHorseId() + ""
					+ ChatColor.DARK_AQUA + " | " + ChatColor.WHITE + ""
					+ hData.getHorseName() 
					+ ChatColor.DARK_AQUA + " | " + ChatColor.WHITE + ""
					+ hData.getBreedName()
					+ ChatColor.DARK_AQUA + " | " + ChatColor.WHITE + ""
					+ hData.getGender().toString());
		}
	}

	private void killHorse(CommandSender sender, Player p, String[] args) {
		PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(p);

		int horseId = 0;
		try {
			horseId = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			sender.sendMessage(Messages.ID_MUST_BE_INT);
			return;
		}
		Horse horse = pData.getHorse(horseId);

		if (horse == null) {
			p.sendMessage(Messages.DOESNT_EXIST);
			return;
		}
		horse.remove();
		new File(Horses.getInstance().getDataFolder() 
				+ File.separator 
				+ "data" 
				+ File.separator
				+ p.getUniqueId().toString() 
				+ File.separator 
				+ horse.getUniqueId().toString() + ".yml").delete();
		pData.getHorses().remove(horse.getUniqueId());
		Horses.getInstance().getDataHandler().removeHorse(horse);
		sender.sendMessage(Messages.KILLED_HORSE);
	}

	private void viewStats(Player p, String[] args) {
		PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(p);

		int horseId = 0;
		try {
			horseId = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			p.sendMessage(Messages.ID_MUST_BE_INT);
			return;
		}

		if (pData.getHorse(horseId) == null) {
			p.sendMessage(Messages.HORSE_ID_DOESNT_EXIST);
			return;
		}
		
		Horse horse = pData.getHorse(horseId);
		HorseData hData = pData.getHorses().get(horse.getUniqueId());

		p.sendMessage(ChatColor.DARK_AQUA + "|" + ChatColor.GRAY + "---------------" + ChatColor.DARK_AQUA + "["
				+ ChatColor.AQUA + hData.getHorseName() + "'s Stats" 
				+ ChatColor.DARK_AQUA + "]" + ChatColor.GRAY + "---------------" + ChatColor.DARK_AQUA + "|");

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		p.sendMessage(ChatColor.AQUA + "Date Claimed: " + ChatColor.WHITE + dateFormat.format(hData.getDateClaimed()));
		p.sendMessage(ChatColor.AQUA + "JumpXP: " + ChatColor.WHITE + hData.getLevelHandler().getJumpXp() + "/" + Horses.getInstance().getJumpLevels().get(hData.getLevelHandler().getJumpLevel() + 0.5));
		p.sendMessage(ChatColor.AQUA + "Jump: " + ChatColor.WHITE + hData.getLevelHandler().getJumpLevel() + "/" + hData.getLevelHandler().getMaxJumpLevel());
		p.sendMessage(ChatColor.AQUA + "SpeedXP: " + ChatColor.WHITE + hData.getLevelHandler().getSpeedXp() + "/" + Horses.getInstance().getSpeedLevels().get(hData.getLevelHandler().getSpeedLevel() + 1));
		p.sendMessage(ChatColor.AQUA + "Speed: " + ChatColor.WHITE + hData.getLevelHandler().getSpeedLevel() + "/" + hData.getLevelHandler().getMaxSpeedLevel());
		p.sendMessage(ChatColor.AQUA + "Health: " + ChatColor.WHITE + ((int)hData.getHorse().getHealth()) + "/30"); //TODO fix this
		p.sendMessage(ChatColor.AQUA + "Stamina: " + ChatColor.WHITE + hData.getLevelHandler().getStaminaLevel());
		p.sendMessage(ChatColor.AQUA + "Age: " + ChatColor.WHITE + hData.getAge(new Date()));
		p.sendMessage(ChatColor.AQUA + "Breed: " + ChatColor.WHITE + hData.getBreedName());

		if (hData.getBreed() == null) {
			p.sendMessage(ChatColor.AQUA + "Specialization: " + ChatColor.WHITE + "UNKNOWN");
			p.sendMessage(ChatColor.AQUA + "Height: " + ChatColor.WHITE + "UNKNOWN");
		} else {
			p.sendMessage(ChatColor.AQUA + "Specialization: " + ChatColor.WHITE + hData.getBreed().getSpecialization().toString());
			p.sendMessage(ChatColor.AQUA + "Height: " + ChatColor.WHITE + hData.getBreed().getHeight() + "hh");
		}

		p.sendMessage(ChatColor.AQUA + "Gender: " + ChatColor.WHITE + hData.getGender().toString());
		p.sendMessage(ChatColor.AQUA + "Can Breed: " + ChatColor.WHITE + hData.isBreedable());
		p.sendMessage(ChatColor.AQUA + "Broken In: " + ChatColor.WHITE + hData.isBrokenInBoolean());
		p.sendMessage(ChatColor.AQUA + "Protected: " + ChatColor.WHITE + hData.isProtectedBoolean());
		p.sendMessage(ChatColor.AQUA + "Sire: " + ChatColor.WHITE + hData.getSire());
		p.sendMessage(ChatColor.AQUA + "Dam: " + ChatColor.WHITE + hData.getDam());	
	}

	private void protectHorse(Player p) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}
		PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(p);
		Horse horse = (Horse) p.getVehicle();

		if (!pData.getHorses().containsKey(horse.getUniqueId())) {
			p.sendMessage(Messages.NOT_CLAIMED);
			return;
		}

		if (pData.getHorses().get(horse.getUniqueId()).isProtectedBoolean()) {
			pData.getHorses().get(horse.getUniqueId()).setProtected(0);
			p.sendMessage(Messages.UNPROTECTED_HORSE);
		} else {
			pData.getHorses().get(horse.getUniqueId()).setProtected(1);
			p.sendMessage(Messages.PROTECTED_HORSE);
		}
	}

	private void setHorseColor(Player p, String[] args) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

		Horse horse = (Horse) p.getVehicle();
		HorseData hData = null;

		for (Player pp : Bukkit.getOnlinePlayers()) {
			PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(pp);
			if (pData.getHorses().containsKey(horse.getUniqueId())) {
				hData = pData.getHorses().get(horse.getUniqueId());
				break;
			}
		}

		if (hData == null || hData.getBreed() == null) {
			if (Util.getColor(args[2]) == null) {
				p.sendMessage(Messages.COLOR_DOESNT_EXIST);
				return;
			}
			horse.setColor(Util.getColor(args[2]));
		} else {
			if (Util.getColor(args[2]) == null) {
				p.sendMessage(Messages.COLOR_DOESNT_EXIST);
				return;
			}
			if (!hData.getBreed().getSupportedColors().contains(Util.getColor(args[2]))) {
				p.sendMessage(Messages.BREED_DOESNT_USE_COLOR);
				return;
			}
			horse.setColor(Util.getColor(args[2]));
		}
		p.sendMessage(Messages.HORSE_COLOR_SET);
	}

	private void setHorseStyle(Player p, String[] args) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

		Horse horse = (Horse) p.getVehicle();
		HorseData hData = null;

		for (Player pp : Bukkit.getOnlinePlayers()) {
			PlayerData pData = Horses.getInstance().getDataHandler().getPlayerData(pp);
			if (pData.getHorses().containsKey(horse.getUniqueId())) {
				hData = pData.getHorses().get(horse.getUniqueId());
				break;
			}
		}

		if (hData == null || hData.getBreed() == null) {
			if (Util.getStyle(args[2]) == null) {
				p.sendMessage(Messages.STYLE_DOESNT_EXIST);
				return;
			}
			horse.setStyle(Util.getStyle(args[2]));
		} else {
			if (Util.getStyle(args[2]) == null) {
				p.sendMessage(Messages.STYLE_DOESNT_EXIST);
				return;
			}
			if (!hData.getBreed().getSupportedStyles().contains(Util.getStyle(args[2]))) {
				p.sendMessage(Messages.BREED_DOESNT_USE_STYLE);
				return;
			}
			horse.setStyle(Util.getStyle(args[2]));
		}
		p.sendMessage(Messages.HORSE_STYLE_SET);
	}
	
	private void setHorseAge(Player p, String[] args) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

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
		
		int age = 0;
		
		try {
			age = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			p.sendMessage(Messages.AGE_MUST_BE_INT);
			return;
		}
		
		hData.setAge(age);
		p.sendMessage(Messages.AGE_CHANGED);
	}

	//TODO discuss issue
	private void setHorseGender(Player p, String[] args) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

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
		

		if (args[2].equalsIgnoreCase("mare")) {
			hData.setGender(Gender.MARE);
		} else if (args[2].equalsIgnoreCase("stallion")){
			hData.setGender(Gender.STALLION);
		} else if (args[2].equalsIgnoreCase("geld")) {
			hData.setGender(Gender.GELD);
		} else {
			p.sendMessage(Messages.GENDER_DOESNT_EXIST);
			return;
		}
		
		p.sendMessage(Messages.HORSE_GENDER_SET);
	}

	private void setStat(Player p, String[] args) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

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

		String stat = args[1];

		if (stat.equalsIgnoreCase("jump")) {
			double level = 0;

			try {
				level = Double.parseDouble(args[2]);
			} catch (NumberFormatException e) {
				p.sendMessage(Messages.LEVEL_MUST_BE_DECIMAL);
			}
			if (hData.getLevelHandler().getMaxJumpLevel() < level) {
				p.sendMessage(Messages.CANNOT_SET_LEVEL);
				return;
			}
			hData.getLevelHandler().setJumpLevel(level);
		} else if (stat.equalsIgnoreCase("speed")) {
			int level = 0;
			try {
				level = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				p.sendMessage(Messages.LEVEL_MUST_BE_INT);
			}

			if (hData.getLevelHandler().getMaxSpeedLevel() < level) {
				p.sendMessage(Messages.CANNOT_SET_LEVEL);
				return;
			}
			hData.getLevelHandler().setSpeedLevel(level);
		} else {
			p.sendMessage(Messages.STAT_DOESNT_EXIST);
		}

		p.sendMessage(Messages.STATS_CHANGED);
	}

	private void setMaxStat(Player p, String[] args) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

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

		String stat = args[2];



		if (stat.equalsIgnoreCase("jump")) {
			double level = 0;
			try {
				level = Double.parseDouble(args[3]);
			} catch (NumberFormatException e) {
				p.sendMessage(Messages.LEVEL_MUST_BE_INT);
				return;
			}
			hData.getLevelHandler().setMaxJumpLevel(level);
		} else if (stat.equalsIgnoreCase("speed")) {
			int level = 0;
			try {
				level = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				p.sendMessage(Messages.LEVEL_MUST_BE_INT);
				return;
			}
			hData.getLevelHandler().setMaxSpeedLevel(level);
		} else {
			p.sendMessage(Messages.STAT_DOESNT_EXIST);
		}

		p.sendMessage(Messages.STATS_CHANGED);
	}

	private void setBreed(Player p) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

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

		p.openInventory(Util.getGui(horse, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Set Breed"));
	}

	private void showBreedList(Player p) {
		p.openInventory(Util.getGui(null, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Breed List"));
	}

	private void addFriend(Player p, String[] args) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

		Horse horse = (Horse) p.getVehicle();
		if (!Horses.getInstance().getDataHandler().getPlayerData(p).getHorses().containsKey(horse.getUniqueId())) {
			p.sendMessage(Messages.NOT_CLAIMED);
			return;
		}

		HorseData hData = Horses.getInstance().getDataHandler().getPlayerData(p).getHorses().get(horse.getUniqueId());

		if (Bukkit.getPlayer(args[1]) == null) {
			p.sendMessage(Messages.PLAYER_DOESNT_EXIST);
			return;
		}

		Player friend = Bukkit.getPlayer(args[1]);

		hData.getFriends().add(friend.getUniqueId());
		p.sendMessage(Messages.ADDED_FRIEND);
	}
	
	private void removeFriend(Player p, String[] args) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

		Horse horse = (Horse) p.getVehicle();
		if (!Horses.getInstance().getDataHandler().getPlayerData(p).getHorses().containsKey(horse.getUniqueId())) {
			p.sendMessage(Messages.NOT_CLAIMED);
			return;
		}

		HorseData hData = Horses.getInstance().getDataHandler().getPlayerData(p).getHorses().get(horse.getUniqueId());

		if (Bukkit.getPlayer(args[1]) == null) {
			p.sendMessage(Messages.PLAYER_DOESNT_EXIST);
			return;
		}

		Player friend = Bukkit.getPlayer(args[1]);

		if (hData.getFriends().contains(friend.getUniqueId())) {
			hData.getFriends().remove(friend.getUniqueId());
		} else {
			p.sendMessage(Messages.THAT_PLAYER_IS_NOT_FRIEND);
			return;
		}
		p.sendMessage(Messages.ADDED_FRIEND);
	}
	
	private void transferHorse(Player p, String[] args) {
		if (!(p.getVehicle() instanceof Horse)) {
			p.sendMessage(Messages.MUST_BE_RIDING_HORSE);
			return;
		}

		Horse horse = (Horse) p.getVehicle();
		if (!Horses.getInstance().getDataHandler().getPlayerData(p).getHorses().containsKey(horse.getUniqueId())) {
			p.sendMessage(Messages.NOT_CLAIMED);
			return;
		}

		HorseData hData = Horses.getInstance().getDataHandler().getPlayerData(p).getHorses().get(horse.getUniqueId());

		File pFile = new File(Horses.getInstance().getDataFolder() 
				+ File.separator 
				+ "data" 
				+ File.separator 
				+ p.getUniqueId().toString() 
				+ File.separator 
				+ hData.getHorse().getUniqueId().toString() + ".yml");
		
		
		if (Bukkit.getPlayer(args[1]) == null) {
			p.sendMessage(Messages.PLAYER_DOESNT_EXIST);
			return;
		}

		Player playerToTransfer = Bukkit.getPlayer(args[1]);
		Horses.getInstance().getDataHandler().transferData(horse, p, playerToTransfer);
		
		if (pFile.exists()) {
			pFile.delete();
		}
		p.sendMessage(Messages.TRANSFER_HORSE + playerToTransfer.getDisplayName() + ChatColor.AQUA + ".");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
