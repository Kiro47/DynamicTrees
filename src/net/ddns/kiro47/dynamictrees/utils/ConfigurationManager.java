package net.ddns.kiro47.dynamictrees.utils;

import net.ddns.kiro47.dynamictrees.DynamicTreesMain;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

/*
 *  Singleton
 */
public class ConfigurationManager
{
    private DynamicTreesMain main = new DynamicTreesMain();

    private File configFile;
    private FileConfiguration config;

    private Logger log = Logger.getInstance();

    private ConfigurationManager()
    {
        config = main.getConfig();
        configFile = new File(main.getDataFolder(), "config.yml");
    }

    private static ConfigurationManager instance = new ConfigurationManager();

    public static ConfigurationManager getInstance()
    {
        return instance;
    }


    /**
     * Hot reloads config
     */
    public void reload()
    {
        if (config == null || configFile == null)
        {
            try
            {
                main.saveDefaultConfig();
            }
            catch (Exception exception)
            {
                log.logError("config and config file do not exist! Consider contacting @dev about this issue.");
                return;
            }
        }
        else
        {
            FileConfiguration cache = YamlConfiguration.loadConfiguration(configFile);
            // Check for existing
            Reader defaultStream = new InputStreamReader(main.getResource(configFile.getName()));
            if (defaultStream != null)
            {
                YamlConfiguration defaultsConfiguration = YamlConfiguration.loadConfiguration(defaultStream);
                config.setDefaults(defaultsConfiguration);
            }
            else
            {
                throw new NullPointerException();
            }
        }
    }

    public void save()
    {
        if (config == null)
        {
            throw new NullPointerException();
        }
        else
        {
            try
            {
                config.save("config.yml");
            }
            catch (IOException exception)
            {
                log.logError("config.yml was unable to be saved! Please consider checking file permissions if this persists");
            }
        }
    }

    /**
     * Retrieves configuration file
     *
     * @return Configuration File
     */
    public FileConfiguration getConfig()
    {
        if (config == null)
        {
            reload();
        }
        return config;
    }
}
