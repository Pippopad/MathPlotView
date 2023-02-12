package it.pippopad.mathplotview.commands;

import it.pippopad.mathplotview.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;

public class CommandHandler implements TabExecutor {

    private final Map<String, SubCommand> commands;

    public CommandHandler() {
        commands = new HashMap<>();

        registerSubCommand(new CreateSubCommand());
        registerSubCommand(new RemoveSubCommand());
        registerSubCommand(new ShowSubCommand());
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
            sender.sendMessage(Utils.color("&cUsage: /" + label + " " + handler.getUsage()));
        }

        return result;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Set<SubCommand> allowed = getAllowedCommands(sender);
        List<String> output = new ArrayList<>();

        if (args.length == 1) {
            if (!args[0].isEmpty()) {
                for (SubCommand sub : allowed) {
                    if (sub.getName().toLowerCase().startsWith(args[0].toLowerCase(Locale.ROOT))) {
                        output.add(sub.getName());
                    }
                }
            } else {
                for (SubCommand sub : allowed) {
                    output.add(sub.getName());
                }
            }
        } else if (args.length >= 2) {
            if (getSubCommand(args[0]) != null) {
                SubCommand sub = getSubCommand(args[0]);
                if (!allowed.contains(sub)) {
                    return output;
                }

                String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

                return sub.onTabComplete(sender, subArgs);
            }
        }
        return output;
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
        Set<SubCommand> allowed = new HashSet<>();
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
