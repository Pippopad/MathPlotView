package it.pippopad.mathplotview.commands;

import it.pippopad.mathplotview.Plot;
import it.pippopad.mathplotview.PlotManager;
import it.pippopad.mathplotview.Utils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ShowSubCommand extends SubCommand {
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getUsage() {
        return getName() + " [plotName]";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 1) return false;

        List<Plot> plots = PlotManager.getPlots();
        if (args.length == 0) {
            if (plots.size() == 0) {
                sender.sendMessage("No plots available.");
                return true;
            }

            sender.sendMessage("List of all plots:");
            for (Plot p : plots) {
                sender.sendMessage(p.getName());
            }
        } else {
            Plot p = PlotManager.getPlot(args[0]);
            if (p == null) {
                sender.sendMessage(Utils.color(String.format("&cA plot with name '%s' doesn't exist!", args[0])));
                return true;
            }

            sender.sendMessage("Name: " + p.getName());
            sender.sendMessage("Function: " + p.getFunction());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String... args) {
        List<Plot> plots = PlotManager.getPlots();
        List<String> output = new ArrayList<>();

        if (args.length == 1)
            for (Plot p : plots)
                output.add(p.getName());

        return output;
    }
}
