/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
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
	 * Reference to the plugin
	 */
	protected App plugin = null;

	/**
	 * The minimum possible chat delay
	 */
	private int minChatDelay = 0;

	/**
	 * The maximum possible chat delay
	 */
	private int maxChatDelay = 0;

	/**
	 * Sets the chat delay range
	 * @param min
	 * @param max
	 */
	protected void setChatDelay(int min, int max) {
		this.minChatDelay = min;
		this.maxChatDelay = max;
	}

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
		System.out.println(player.getPlayerListName());
		System.out.println(permission);
		System.out.println(player.hasPermission(permission));
		if (player.hasPermission(permission)) {
			addPlayer(player);
		}
	}

	/**
	 * Removes a player from the discouragement list
	 * @param event
	 */
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		removePlayer(event.getPlayer());
	}

	/**
	 * Chat event handler
	 * @param event
	 */
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (!this.playerList.contains(player)) return;

		// Cancel the event
		event.setCancelled(true);

		System.out.println(event.getMessage());
	}

	/**
	 * Command event handler
	 * @param event
	 */
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (!this.playerList.contains(player)) return;

		// Cancel the event
		event.setCancelled(true);

		System.out.println(event.getMessage());
	}
}