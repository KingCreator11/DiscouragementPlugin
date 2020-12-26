/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.permissions.PermissionAttachment;

/**
 * Reads and manages all permissions related to this plugin
 */
public class PermissionsManager {

	/**
	 * Pointer to the plugin
	 */
	private static App plugin;

	/**
	 * Sets the instance of the plugin pointer
	 * @param app The pointer to the plugin
	 */
	public static void setPlugin(App app) {
		plugin = app;
	}

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

	/**
	 * Sets a permission on a permissible
	 * @param permissible The permissible to give the perm to
	 * @param perm The perm to give
	 * @param value The value of the perm (`true` to give the perm `false` to remove it)
	 */
	public static void setPerm(Permissible permissible, String perm, boolean value) {
		boolean found = false;
		// Linear search through perm attachments
		for (PermissionAttachmentInfo attachmentInfo : permissible.getEffectivePermissions()) {
			// Check if attachmentInfo is null
			if (attachmentInfo == null) continue;
			// Get the attachment
			PermissionAttachment attachment = attachmentInfo.getAttachment();
			// Check if the attachment is null
			if (attachment == null) continue;
			// If the attachment isn't from this plugin we can ignore it
			if (attachment.getPlugin() != plugin) continue;

			found = true;
			// Set the permission
			attachment.setPermission(perm, value);
		}

		if (found) return;

		// The attachment hasn't been set yet so we have to do that manually
		PermissionAttachment attachment = permissible.addAttachment(plugin);
		attachment.setPermission(perm, value);
	}
}