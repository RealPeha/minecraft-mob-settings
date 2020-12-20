package real.peha.fun;

import java.util.HashMap;
import org.bukkit.command.CommandSender;

public class CommandsHandler {
    private HashMap<String, BaseCommand> commands = new HashMap<>();

    public void register(String command, BaseCommand handler) {
        commands.put(command, handler);
    }

    public void register(String[] commandList, BaseCommand handler) {
        for (String command : commandList) {
            register(command, handler);
        }
    }

    public Boolean execute(CommandSender sender, String command, String alias, String[] args) {
        if (!commands.containsKey(command)) {
            return true;
        }

        return commands.get(command).execute(sender, alias, args);
    }
}
