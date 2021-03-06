package net.ddns.kiro47.dynamictrees;

import net.ddns.kiro47.dynamictrees.listeners.TreeReplanter;
import net.ddns.kiro47.dynamictrees.managers.ReplanterManagager;
import net.ddns.kiro47.dynamictrees.utils.Configurations;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class DynamicTreesMain extends JavaPlugin
{

    public void onEnable()
    {
        // Only runs if not exists, safes from default config.yml
        this.saveDefaultConfig();

        hookListeners(this.getServer().getPluginManager());
    }

    public void onDisable()
    {

    }

    private void hookListeners(PluginManager manager)
    {
        // Ideally if we only register listeners when it's to be used we'll save memory when it's not used.
        if (Configurations.AUTO_REPLANT == true)
        {
            manager.registerEvents(new TreeReplanter(), this);
            ReplanterManagager.getInstance().startScheduler(this);
        }

    }
}
