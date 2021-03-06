/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

/**
 * Level 3 of discouragement implementation
 */
public class DiscouragementLvl3 extends Discouragement {
	/**
	 * An instance of this class to use throughout the plugin
	 */
	public static DiscouragementLvl3 instance = null;

	public DiscouragementLvl3(App plugin) {
		this.plugin = plugin;
		setPermissionString("discouragement.level.3");
		setChatDelay(0, 30);
		blockFailChance = 0.5;
		updatePlayerList();
		startRandomTeleports(1, 10, 10, 20);
	}
}