/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

/**
 * Reads and manages all permissions related to this plugin
 */
public class PermissionsManager {
	/**
	 * Removes the numbers at the end of a permission for checking permission values
	 * 
	 * Example:
	 * `stripPerm("discouragement.command.level.3)` -> `"discouragement.command.level"`
	 * 
	 * @param perm The permission to strip
	 * @return The permission string without the numbers and dot at the end
	 */
	public static String stripPerm(String perm) {
		int i = perm.lastIndexOf('.');
		// Perm has no '.' in it if `i` is negative
		return i > 0 ? perm.substring(0, i) : perm;
	}

	/**
	 * Gets a list of all players with a permission
	 * @param perm The permission to check for
	 * @return The list of players with the permission
	 */
	public static List<Player> getOnlinePlayersWithPerm(String perm) {
		return Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission(perm)).collect(Collectors.toList());
	}

	/**
	 * Retrieves the highest permission number from a certain permission
	 * Does **NOT** Support negative permissions
	 * 
	 * Example: 
	 * `getHighestPerm(entity, "discouragement.command.level", 1, 3)` -> 2
	 * When the entity has the following perms:
	 * `["discouragement.command.level.1", "discouragement.command.level.2"]`
	 * 
	 * @param permissible The entity to check
	 * @param perm The permission to check
	 * @param min The minimum possible perm number
	 * @param max The highest possible perm number
	 * @return The highest permission number OR -1 if the perm was not found
	 */
	public static int getHighestPerm(Permissible permissible, String perm, int min, int max) {
		int highest = -1;

		for (int i = min; i <= max; i++)
			if (permissible.hasPermission(perm + "." + i))
				highest = i;

		return highest;
	}

	/**
	 * Whether or not a permissible has a certain permission
	 * Does **NOT** Support negative permissions
	 * 
	 * Example:
	 * `hasPermMax(entity, "discouragement.command.level.2", 1, 3)` -> true
	 * When the entity has the following perms:
	 * `["discouragement.command.level.3"]`
	 * 
	 * @param permissible The entity to check
	 * @param perm The permission to check
	 * @param min The minimum possible perm number
	 * @param max The maximum possible perm number
	 * @return Whether or not the permissible has the perm
	 */
	public static boolean hasPermMax(Permissible permissible, String perm, int min, int max) {
		// If it has the permission then return true
		if (permissible.hasPermission(perm)) {
			return true;
		}
		
		// Otherwise call getHighestPerm and check if the highest perm the permissible has is
		// greater than the last digit
		int permCheck = 0;
		try {
			permCheck = Integer.parseInt(perm.substring(perm.lastIndexOf('.')+1));
		}
		catch (NumberFormatException e) {
			// Last part of perm isn't a number
			return false;
		}

		String stripped = stripPerm(perm);
		int highestPerm = getHighestPerm(permissible, stripped, min, max);

		return highestPerm >= permCheck;
	}
}