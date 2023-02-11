package it.pippopad.mathplotview.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    public abstract String getName();

    public String getPermission() {
        return "mathplotview.admin";
    }

    public abstract boolean execute(CommandSender sender, String[] args);
}
