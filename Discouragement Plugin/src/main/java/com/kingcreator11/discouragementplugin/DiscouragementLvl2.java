/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

/**
 * Level 2 of discouragement implementation
 */
public class DiscouragementLvl2 extends Discouragement {
	/**
	 * An instance of this class to use throughout the plugin
	 */
	public static DiscouragementLvl2 instance = null;

	public DiscouragementLvl2(App plugin) {
		this.plugin = plugin;
		setPermissionString("discouragement.level.2");
		setChatDelay(0, 15);
		updatePlayerList();
	}

	public void updatePlayerList() {
		updatePlayerList("discouragement.level.2");
	}
}