package real.peha.fun;

import org.bukkit.command.CommandSender;

public interface BaseCommand {
    public Boolean execute(CommandSender sender, String alias, String[] args);
}
