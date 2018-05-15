package net.ddns.kiro47.dynamictrees;

import net.ddns.kiro47.dynamictrees.utils.ConfigurationManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class DynamicTreesCommand implements CommandExecutor
{
    private String prefix = ChatColor.DARK_GREEN + "[DynamicTrees] " + ChatColor.RESET;
    private ConfigurationManager configManager = ConfigurationManager.getInstance();
    private FileConfiguration config = configManager.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        // sender can be either console or player

        if (sender.hasPermission(Permissions.MAIN_COMMAND.getPermission()))
        {
            if (args[0] == null || args[0].isEmpty())
            {
                printDefaultMessage(sender);
            }
            else
            {
                switch (args[0])
                {
                    case "help":
                        printDefaultMessage(sender);
                        break;
                    case "version":
                        printVersion(sender);
                        break;
                    default:
                        printDefaultMessage(sender);
                        break;

                }
            }
        }
        else
        {

        }
        return true;
    }

    /**
     * Prints the default help message for the plugin
     *
     * @param sender Who to send the message to.
     */
    private void printDefaultMessage(CommandSender sender)
    {
        String pre = "/dyntrees";
        sender.sendMessage(prefix + ChatColor.AQUA + "Help Menu!");
        sender.sendMessage(ChatColor.DARK_GREEN + "--------------------------------------------------------------------------------");
        // add per command
        sender.sendMessage(ChatColor.DARK_GREEN + pre + " help -"  + ChatColor.AQUA + " Displays this menu");
        sender.sendMessage( pre + " version -" + ChatColor.AQUA + " Displays the version of the plugin");


        sender.sendMessage(ChatColor.DARK_GREEN + "--------------------------------------------------------------------------------");
        return;
    }

    /**
     * Sends version of plugin in formatted text
     *
     * @param sender Who to send the message to.
     */
    private void printVersion(CommandSender sender)
    {
        sender.sendMessage(prefix + ChatColor.AQUA + config.get("version"));
        return;
    }
}
