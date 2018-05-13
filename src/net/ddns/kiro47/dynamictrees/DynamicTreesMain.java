package net.ddns.kiro47.dynamictrees;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DynamicTreesMain extends JavaPlugin
{

    public void onEnable()
    {
        // Only runs if not exists, safes from default config.yml
        this.saveDefaultConfig();
    }

    public void onDisable()
    {

    }

}
