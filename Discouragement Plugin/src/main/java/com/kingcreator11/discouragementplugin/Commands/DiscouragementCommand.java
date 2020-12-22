/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	 * Calls when the command is run
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		return false;
	}

	/**
	 * Called to get command completion
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// Sub commands list
		String[] commands = {"lvl1", "lvl2", "lvl3", "remove"};

		// Blank command - offer the list of sub commands
		if (args.length == 0) {
			return Arrays.asList(commands);
		}

		// One argument - sub command
		if (args.length == 1) {
			// Perform linear search to check if the sub command is valid
			boolean found = false;
			for (String subCommand : commands)
				if (subCommand == args[0])
					found = true;

			// Invalid sub command - no tab completion offered
			if (!found)
				return null;
			
			// All sub commands have a player as the second argument - offer player list as tab completion
			List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
			ArrayList<String> playerNames = new ArrayList<>();
			for (Player player : playerList) {
				playerNames.add(player.getPlayerListName());
			}
			return playerNames;
		}

		// More than one argument, no tab completion offered
		return null;
	}
}