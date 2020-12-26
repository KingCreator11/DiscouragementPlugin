/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

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
	private double minChatDelay = 0;

	/**
	 * The maximum possible chat delay
	 */
	private double maxChatDelay = 0;

	/**
	 * The queue of chat messages which are delayed
	 */
	private HashMap<UUID, Queue<String>> delayedChat = new HashMap<>();

	/**
	 * The polled chat messages
	 */
	private HashMap<UUID, ArrayList<String>> delayedChatPolled = new HashMap<>();

	/**
	 * Sets the chat delay range
	 * @param min
	 * @param max
	 */
	protected void setChatDelay(double min, double max) {
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
		delayedChat.put(player.getUniqueId(), new LinkedList<>());
		delayedChatPolled.put(player.getUniqueId(), new ArrayList<>());
	}

	/**
	 * Removes a player from discouragement
	 * @param player
	 */
	public void removePlayer(Player player) {
		playerList.remove(player);
		delayedChat.remove(player.getUniqueId());
		delayedChatPolled.remove(player.getUniqueId());
	}

	/**
	 * Updates the player list from the permission
	 * @param perm The permission string to check
	 */
	protected void updatePlayerList(String perm) {
		playerList = new ArrayList<>(PermissionsManager.getOnlinePlayersWithPerm(perm));

		for (Player player : playerList) {
			delayedChat.put(player.getUniqueId(), new LinkedList<>());
			delayedChatPolled.put(player.getUniqueId(), new ArrayList<>());
		}
	}

	/**
	 * Initiates random teleportations given the minimum and maximum time of each teleport and an interval
	 * @param minTime
	 * @param maxTime
	 */
	protected void startRandomTeleports(double minTime, double maxTime, double interval) {

	}

	/**
	 * Makes the player say a message at a random interval between the chat delay
	 * @param player
	 * @param message
	 */
	private void delayedChatMessage(Player player, String message) {
		Queue<String> playerQueue = delayedChat.get(player.getUniqueId());
		if (playerQueue == null) return;
		playerQueue.add(message);

		BukkitRunnable runnable = new BukkitRunnable() {
			@Override
			public void run() {
				Queue<String> playerQueue = delayedChat.get(player.getUniqueId());
				if (playerQueue == null) return;
				ArrayList<String> playerPolled = delayedChatPolled.get(player.getUniqueId());
				if (playerPolled == null) return;
				String polled = playerQueue.poll();
				player.chat(polled);
				playerPolled.add(polled);
			}
		};
		// **NOTE** - We are assuming 20 tps, if the tps is slower than 20 then the delay is even longer!
		double delay = minChatDelay + (Math.random() * (maxChatDelay - minChatDelay));
		runnable.runTaskLater(plugin, (long) delay * 20l);
	}

	/**
	 * Adds a player to the discouragement list
	 * @param event
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
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

		ArrayList<String> playerPolled = delayedChatPolled.get(player.getUniqueId());
		if (playerPolled == null) return;

		// Checking that the message isn't a delayed message from the computer
		String message = event.getMessage();
		if (playerPolled.contains(message)) {
			playerPolled.remove(message);
			return;
		}

		// Cancel the event
		event.setCancelled(true);

		this.delayedChatMessage(player, event.getMessage());
	}

	/**
	 * Command event handler
	 * @param event
	 */
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (!this.playerList.contains(player)) return;

		ArrayList<String> playerPolled = delayedChatPolled.get(player.getUniqueId());
		if (playerPolled == null) return;

		// Checking that the message isn't a delayed message from the computer
		String message = event.getMessage();
		if (playerPolled.contains(message)) {
			playerPolled.remove(message);
			return;
		}

		// Cancel the event
		event.setCancelled(true);

		this.delayedChatMessage(player, event.getMessage());
	}
}