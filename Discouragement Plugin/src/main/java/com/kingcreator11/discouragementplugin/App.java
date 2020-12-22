/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

import com.kingcreator11.discouragementplugin.Commands.DiscouragementCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main App class
 */
public class App extends JavaPlugin {
	@Override
	public void onEnable() {
		getLogger().info("Discouragement Plugin Enabled");
		this.getCommand("discouragement").setExecutor(new DiscouragementCommand());
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Discouragement Plugin Disabled");
	}
}