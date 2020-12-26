/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

/**
 * Level 3 of discouragement implementation
 */
public class DiscouragementLvl3 extends Discouragement {
	
	public DiscouragementLvl3() {
		setPermissionString("discouragement.level.3");
		updatePlayerList();
	}

	public void updatePlayerList() {
		updatePlayerList("discouragement.level.3");
	}
}