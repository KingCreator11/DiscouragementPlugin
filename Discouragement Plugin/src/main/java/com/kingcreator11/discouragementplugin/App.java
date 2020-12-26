/**
 * @author KingCreator11
 * @version 12/21/2020
 */

package com.kingcreator11.discouragementplugin;

import com.kingcreator11.discouragementplugin.Commands.DiscouragementCommand;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * The main App class
 */
public class App extends JavaPlugin {
	private static Permission perms;

	@Override
	public void onEnable() {
		// Log enabled message
		getLogger().info("Discouragement Plugin Enabled");
		// Vault permissions setup
		setupPermissions();
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

	private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
	}
	
	public static Permission getPermissions() {
        return perms;
    }
	
	@Override
	public void onDisable() {
		getLogger().info("Discouragement Plugin Disabled");
	}
}