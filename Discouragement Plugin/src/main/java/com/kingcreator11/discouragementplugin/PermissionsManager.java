/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.permissions.PermissionAttachment;

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
	 * @return The list of players with the permission
	 */
	public static List<Player> getPlayersWithPerm() {
		return null;
	}

	/**
	 * Retrieves the highest permission number from a certain permission
	 * Does **NOT** Support negative permissions
	 * 
	 * Example: 
	 * `getHighestPerm(entity, "discouragement.command.level")` -> 3
	 * When the entity has the following perms:
	 * `["discouragement.command.level.1", "discouragement.command.level.2", "discouragement.command.level.3"]`
	 * 
	 * @param permissible The entity to check
	 * @param perm The permission to check
	 * @return The highest permission number OR -1 if the perm was not found
	 */
	public static int getHighestPerm(Permissible permissible, String perm) {
		Set<PermissionAttachmentInfo> permsInfo = permissible.getEffectivePermissions();
		int highest = -1;

		// Linearly go through all attachments
		for (PermissionAttachmentInfo permsInfoObject : permsInfo) {
			PermissionAttachment attachment = permsInfoObject.getAttachment();
			// Null check
			if (attachment == null) continue;
			Map<String, Boolean> perms = attachment.getPermissions();

			// Linearly search all perms
			for (String permKey : perms.keySet()) {
				// If the permission is false we can ignore it
				if (!perms.get(permKey)) continue;
				// If the permissions bases are not equal then we can ignore it
				if (!stripPerm(permKey).equals(perm)) continue;
				
				try {
					// Retrieve the perm level from the number at the end of the string
					int permLevel = Integer.parseInt(permKey.substring(permKey.lastIndexOf('.')+1));
					// If the perm is greater than the highest till now set it
					if (permLevel > highest) highest = permLevel;
				}
				catch (NumberFormatException e) {
					// The perm wasn't a number, its possible that we ran into a perm with multiple endings including a number
					continue;
				}
			}
		}

		return highest;
	}

	/**
	 * Whether or not a permissible has a certain permission
	 * Does **NOT** Support negative permissions
	 * 
	 * Example:
	 * `hasPermMax(entity, "discouragement.command.level.2")` -> true
	 * When the entity has the following perms:
	 * `["discouragement.command.level.3"]`
	 * 
	 * @param permissible The entity to check
	 * @param perm The permission to check
	 * @return Whether or not the permissible has the perm
	 */
	public static boolean hasPermMax(Permissible permissible, String perm) {
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
		int highestPerm = getHighestPerm(permissible, stripped);

		return highestPerm >= permCheck;
	}
}