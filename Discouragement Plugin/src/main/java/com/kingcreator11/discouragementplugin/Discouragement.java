/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Discouragement abstract class - all discouragement level implementations must extend this
 */
public abstract class Discouragement implements Listener {
	/**
	 * Player list of discouraged players
	 */
	private ArrayList<Player> playerList = null;

	/**
	 * The permission string of this discouragement level
	 */
	private String permission = null;

	/**
	 * An instance of this class to use throughout the plugin
	 */
	public static Discouragement instance = null;

	/**
	 * Gets the permission string of this discouragement level
	 * @return The permission string of this discouragement level
	 */
	public String getPermissionString() {
		return permission;
	}

	/**
	 * Sets the permission string of this discouragement level
	 * @param permissionString The permission string of this discouragement level
	 */
	protected void setPermissionString(String permissionString) {
		permission = permissionString;
	}

	/**
	 * Adds a player to discouragement
	 * @param player
	 */
	public void addPlayer(Player player) {
		playerList.add(player);
	}

	/**
	 * Removes a player from discouragement
	 * @param player
	 */
	public void removePlayer(Player player) {
		playerList.remove(player);
	}

	/**
	 * Updates the player list from the permission
	 * @param perm The permission string to check
	 */
	protected void updatePlayerList(String perm) {
		playerList = new ArrayList<>(PermissionsManager.getOnlinePlayersWithPerm(perm));
	}

	/**
	 * Adds a player to the discouragement list
	 * @param event
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission(permission))
			addPlayer(player);
	}

	/**
	 * Removes a player from the discouragement list
	 * @param event
	 */
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		removePlayer(event.getPlayer());
	}
}