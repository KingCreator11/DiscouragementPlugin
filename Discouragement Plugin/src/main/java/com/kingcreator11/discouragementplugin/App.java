package com.kingcreator11.discouragementplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
	@Override
	public void onEnable() {
		getLogger().info("Discouragement Plugin Enabled");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Discouragement Plugin Disabled");
	}
}