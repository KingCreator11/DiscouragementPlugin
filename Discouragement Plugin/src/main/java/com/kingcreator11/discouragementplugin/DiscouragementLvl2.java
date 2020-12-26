/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

/**
 * Level 2 of discouragement implementation
 */
public class DiscouragementLvl2 extends Discouragement {
	
	public DiscouragementLvl2() {
		setPermissionString("discouragement.level.2");
		updatePlayerList();
	}

	public void updatePlayerList() {
		updatePlayerList("discouragement.level.2");
	}
}