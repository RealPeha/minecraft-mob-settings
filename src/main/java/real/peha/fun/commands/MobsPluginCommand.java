package real.peha.fun.commands;

import org.bukkit.command.CommandSender;

import real.peha.fun.MobsPlugin;
import real.peha.fun.BaseCommand;

public class MobsPluginCommand implements BaseCommand {
    public Boolean execute(CommandSender sender, String alias, String[] args) {
        MobsPlugin plugin = MobsPlugin.getInstance();

        if (args.length == 0) {
            sender.sendMessage("Yes");

            return true;
        }
        
        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("mobsplugin.reload")) {
                plugin.reloadConfig();
                sender.sendMessage("Настройки обновлены");
            } else {
                sender.sendMessage(plugin.getConfig().getString("no-permission-message").replaceAll("&", "§"));
            }
        }

        return true;
    }
}
