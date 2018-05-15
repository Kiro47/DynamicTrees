package net.ddns.kiro47.dynamictrees.utils;

import net.ddns.kiro47.dynamictrees.DynamicTreesMain;
import net.md_5.bungee.api.ChatColor;

import java.util.logging.Level;

public class Logger
{
    private static DynamicTreesMain main = new DynamicTreesMain();

    private static java.util.logging.Logger logger = main.getLogger();

    private String logMessage =
            ChatColor.DARK_GREEN + "[DynamicTrees]" +
            ChatColor.RED +  " [%s]" +
            ChatColor.AQUA  + " %s" +
            ChatColor.RESET;

    private Logger()
    {
        logger.setLevel(Level.ALL);
    }

    private static Logger instance = new Logger();

    public static Logger getInstance()
    {
        return instance;
    }


    public void logWarning(String... message)
    {
        for (String line : message)
        {
            logger.log(Level.WARNING, String.format(logMessage, "WARNING", line));
        }
    }

    public void logError(String... message)
    {
        for (String line : message)
        {
            logger.log(Level.SEVERE, String.format(logMessage, "ERROR", line));
        }
    }

    public void logInfo(String... message)
    {
        for (String line : message)
        {
            logger.log(Level.INFO, String.format(logMessage, "INFO", line));
        }
    }
}
