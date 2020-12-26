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
		// Log enabled message
		getLogger().info("Discouragement Plugin Enabled");
		// Setup perm manager
		PermissionsManager.setPlugin(this);
		// Setup command
		this.getCommand("discouragement").setExecutor(new DiscouragementCommand());

		// Setup discouragement levels
		DiscouragementLvl1.instance = new DiscouragementLvl1(this);
		DiscouragementLvl2.instance = new DiscouragementLvl2(this);
		DiscouragementLvl3.instance = new DiscouragementLvl3(this);

		// Setup event listeners
		getServer().getPluginManager().registerEvents(DiscouragementLvl1.instance, this);
		getServer().getPluginManager().registerEvents(DiscouragementLvl2.instance, this);
		getServer().getPluginManager().registerEvents(DiscouragementLvl3.instance, this);
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Discouragement Plugin Disabled");
	}
}