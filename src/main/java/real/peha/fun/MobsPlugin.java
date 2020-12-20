package real.peha.fun;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import real.peha.fun.commands.*;

import real.peha.fun.listeners.*;

public class MobsPlugin extends JavaPlugin {
    private CommandsHandler commandsHandler = new CommandsHandler();
    private static MobsPlugin instance;

    public static MobsPlugin getInstance() {
        return instance;
    }

    private static void setInstance(MobsPlugin plugin) {
        MobsPlugin.instance = plugin;
    }

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        
        setInstance(this);

        // Register listeners
        new SpawnMobListener(this);

        // Register commands
        commandsHandler.register(new String[]{"rp"}, new MobsPluginCommand());

        getLogger().info("Started");
    }

    @Override
    public void onDisable() {
        getLogger().info("Goodbye");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        String command = cmd.getName().toLowerCase();

        return commandsHandler.execute(sender, command, alias, args);
    }
}
