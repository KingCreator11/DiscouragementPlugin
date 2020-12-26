/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

/**
 * Level 1 of discouragement implementation
 */
public class DiscouragementLvl1 extends Discouragement {
	/**
	 * An instance of this class to use throughout the plugin
	 */
	public static DiscouragementLvl1 instance = null;

	public DiscouragementLvl1(App plugin) {
		this.plugin = plugin;
		setPermissionString("discouragement.level.1");
		setChatDelay(0, 5);
		updatePlayerList();
	}

	public void updatePlayerList() {
		updatePlayerList("discouragement.level.1");
	}
}