package it.pippopad.mathplotview.commands;

import it.pippopad.mathplotview.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;

public class CommandHandler implements TabExecutor {

    private Map<String, SubCommand> commands;

    public CommandHandler() {
        commands = new HashMap<String, SubCommand>();

        registerSubCommand(new CreateSubCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Utils.color("&cSpecify a subcommand!"));
            return false;
        }

        if (!hasSubCommand(args[0])) {
            sender.sendMessage(Utils.color("&cThis command does not exist."));
            return false;
        }

        SubCommand handler = getSubCommand(args[0]);

        if (!hasPermission(sender, handler.getPermission())) {
            sender.sendMessage(Utils.color("&cYou are not allowed to use this command!"));
            return false;
        }

        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        boolean result = handler.execute(sender, subArgs);
        if (!result) {
            sender.sendMessage("Invalid syntax!");
        }

        return result;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    private void registerSubCommand(SubCommand command) {
        commands.put(command.getName().toLowerCase(Locale.ROOT), command);
    }

    private boolean hasSubCommand(String name) {
        return commands.containsKey(name.toLowerCase(Locale.ROOT));
    }

    public SubCommand getSubCommand(String name) {
        return commands.get(name.toLowerCase(Locale.ROOT));
    }

    private Set<SubCommand> getAllowedCommands(CommandSender sender) {
        Set<SubCommand> allowed = new HashSet<SubCommand>();
        for (SubCommand sub : commands.values()) {
            if (hasPermission(sender, sub.getPermission())) {
                allowed.add(sub);
            }
        }
        return allowed;
    }

    private boolean hasPermission(CommandSender sender, String permission) {
        return permission == null || sender.hasPermission(permission);
    }
}
