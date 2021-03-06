/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kingcreator11.discouragementplugin.DiscouragementLvl1;
import com.kingcreator11.discouragementplugin.DiscouragementLvl2;
import com.kingcreator11.discouragementplugin.DiscouragementLvl3;
import com.kingcreator11.discouragementplugin.PermissionsManager;
import com.kingcreator11.discouragementplugin.App;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

/**
 * The Main discouragement command, implements all sub commands as well.
 */
public class DiscouragementCommand implements CommandExecutor, TabCompleter {

	/**
	 * Array of commands
	 */
	public static final String[] commands = {
		"lvl1",
		"lvl2",
		"lvl3",
		"remove"
	};

	/**
	 * Array of permissions to match commands - indices match with commands as well.
	 */
	public static final String[] permissions = {
		"discouragement.command.level.1",
		"discouragement.command.level.2",
		"discouragement.command.level.3",
		"discouragement.command.level.remove"
	};

	/**
	 * Array of permissions to give discouragement. Indices match with commands
	 * `remove` has no discouragement perm
	 */
	public static final String[] discouragementPerms = {
		"discouragement.level.1",
		"discouragement.level.2",
		"discouragement.level.3"
	};

	/**
	 * Checks whether or not a sender has the perms to run a sub command
	 * @param sender The sender of the command
	 * @param command The sub command
	 * @return Whether or not the sender has the perms to execute the command
	 */
	private boolean hasPerms(Permissible sender, String command) {
		ArrayList<String> availableCommands = getAvailableCommands(sender);
		// Perform linear search to check if the sub command is valid
		boolean found = false;
		for (String subCommand : availableCommands)
			if (subCommand.equals(command))
				found = true;
		return found;
	}

	/**
	 * Returns a list of all available sub commands byc hecking through perms
	 * @param sender The sender of the command
	 * @return The list of sub commands
	 */
	private ArrayList<String> getAvailableCommands(Permissible sender) {
		// Filter out commands based on what perms the user has
		ArrayList<String> availableCommands = new ArrayList<>(Arrays.asList(commands));

		// Linear search through perms for commands
		for (int i = permissions.length - 1; i >= 0; i--) {
			// If the player has the perm continue
			if (PermissionsManager.hasPermMax(sender, permissions[i], 1, 3)) continue;
			// The player does not have the perm so remove the command
			else availableCommands.remove(i);
		}

		return availableCommands;
	}

	/**
	 * Calls when the command is run
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 2) {
			sender.sendMessage("§cInvalid arguments");
			return true;
		}

		// Check if the sender has sufficient Privileges
		if (!hasPerms(sender, args[0])) {
			sender.sendMessage("§cInsufficient Privileges");
			return true;
		}

		// Get the player object
		Player player = Bukkit.getPlayer(args[1]);
		if (player == null) {
			sender.sendMessage("§cPlayer not found");
			return true;
		}

		// Always remove all other discouragement perms before setting the next level just for cleanliness
		// This code also takes care of /discouragement remove ign
		for (int i = 0; i < discouragementPerms.length; i++) {
			App.getPermissions().playerRemove(player, discouragementPerms[i]);
		}

		// Send remove message
		if (args[0].equals("remove")) {
			sender.sendMessage("§2Reset discouragement permissions for "+player.getPlayerListName());
			// Remove player from all levels of discouragement
			// Calling .removePlayer on a player which isn't in the list will not cause error
			// Since the implementation uses ArrayList.remove()
			DiscouragementLvl1.instance.removePlayer(player);
			DiscouragementLvl2.instance.removePlayer(player);
			DiscouragementLvl3.instance.removePlayer(player);
			return true;
		}

		// Linear search through commands to set the level
		for (int i = 0; i < 3; i++) {
			// We can skip it if it isn't the command being used
			if (!commands[i].equals(args[0])) continue;
			// Set the permission to be true for the correct command
			App.getPermissions().playerAdd(player, discouragementPerms[i]);
			
			if (i == 0) DiscouragementLvl1.instance.addPlayer(player);
			if (i == 1) DiscouragementLvl2.instance.addPlayer(player);
			if (i == 2) DiscouragementLvl3.instance.addPlayer(player);

			sender.sendMessage("§2Set discouragement level "+(i+1)+" for "+player.getPlayerListName());
		}

		return true;
	}

	/**
	 * Called to get command completion
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// Blank command - offer the list of sub commands
		if (args.length <= 1) {
			return getAvailableCommands(sender);
		}

		// Two argument - sub command
		if (args.length == 2) {
			// Player doesn't have perms for this command
			if (!hasPerms(sender, args[0]))
				return new ArrayList<>();
			
			// All sub commands have a player as the second argument - offer player list as tab completion
			List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
			ArrayList<String> playerNames = new ArrayList<>();
			for (Player player : playerList) {
				playerNames.add(player.getPlayerListName());
			}
			return playerNames;
		}

		// More than one argument, no tab completion offered
		return new ArrayList<>();
	}
}