/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

/**
 * Level 1 of discouragement implementation
 */
public class DiscouragementLvl1 extends Discouragement {

	public DiscouragementLvl1() {
		setPermissionString("discouragement.level.1");
		updatePlayerList();
	}

	public void updatePlayerList() {
		updatePlayerList("discouragement.level.1");
	}
}