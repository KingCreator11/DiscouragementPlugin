/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kingcreator11.discouragementplugin.PermissionsManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

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
	 * Calls when the command is run
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return true;
	}

	/**
	 * Called to get command completion
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// Filter out commands based on what perms the user has
		ArrayList<String> availableCommands = new ArrayList<>(Arrays.asList(commands));

		// Linear search through perms for commands
		for (int i = permissions.length - 1; i >= 0; i--) {
			// If the player has the perm continue
			if (PermissionsManager.hasPermMax(sender, permissions[i])) continue;
			// The player does not have the perm so remove the command
			else availableCommands.remove(i);
		}

		// Blank command - offer the list of sub commands
		if (args.length <= 1) {
			return availableCommands;
		}

		// Two argument - sub command
		if (args.length == 2) {
			// Perform linear search to check if the sub command is valid
			boolean found = false;
			for (String subCommand : availableCommands)
				if (subCommand.equals(args[0]))
					found = true;

			// Invalid sub command - no tab completion offered
			if (!found)
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