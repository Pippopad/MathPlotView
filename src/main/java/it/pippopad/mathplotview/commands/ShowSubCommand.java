package it.pippopad.mathplotview.commands;

import it.pippopad.mathplotview.Plot;
import it.pippopad.mathplotview.PlotManager;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ShowSubCommand extends SubCommand {
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 0) return false;

        List<Plot> plots = PlotManager.getPlots();
        if (plots.size() == 0) {
            sender.sendMessage("No plots available.");
            return true;
        }

        sender.sendMessage("List of all plots:");
        for (Plot p : plots) {
            sender.sendMessage(p.getName());
        }

        return true;
    }
}
