package it.pippopad.mathplotview.commands;

import org.bukkit.command.CommandSender;

public class CreateSubCommand extends SubCommand {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        return false;
    }
}
