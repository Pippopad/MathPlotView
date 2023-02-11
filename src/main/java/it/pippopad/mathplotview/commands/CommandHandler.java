package it.pippopad.mathplotview.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler implements TabExecutor {

    private Map<String, SubCommand> commands;

    public CommandHandler() {
        commands = new HashMap<String, SubCommand>();

        registerSubCommand(new CreateSubCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    private void registerSubCommand(SubCommand command) {
        commands.put(command.getName(), command);
    }
}
