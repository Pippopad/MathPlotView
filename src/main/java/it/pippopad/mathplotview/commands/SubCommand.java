package it.pippopad.mathplotview.commands;

import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public abstract class SubCommand {
    public abstract String getName();

    public String getPermission() {
        return "mathplotview.admin";
    }
    public String getUsage() {
        return getName();
    }

    public abstract boolean execute(CommandSender sender, String[] args);

    public List<String> onTabComplete(CommandSender sender, String[] subArgs) {
        return Collections.emptyList();
    }
}
