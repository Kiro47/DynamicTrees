package net.ddns.kiro47.dynamictrees.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class Configurations
{
    final private static FileConfiguration config = ConfigurationManager.getInstance().getConfig();

    public static boolean AUTO_REPLANT = false;
    public static int NEAREST_TREE = 0;
    public static int REPLANT_RANGE = 20;
    public static int REPLANT_TIME = 5;

    public static void loadValues()
    {
        AUTO_REPLANT = config.getBoolean("AutoReplant.Enabled");
        NEAREST_TREE = config.getInt("AutoReplant.NearestTreee");
        REPLANT_RANGE = config.getInt("AutoReplant.ReplantRange");
        REPLANT_TIME = config.getInt("AutoReplant.ReplantTime") * 20; // convert to ticks
    }

}
