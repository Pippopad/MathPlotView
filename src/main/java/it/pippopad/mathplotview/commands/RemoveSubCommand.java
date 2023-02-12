package it.pippopad.mathplotview.commands;

import it.pippopad.mathplotview.Plot;
import it.pippopad.mathplotview.PlotManager;
import it.pippopad.mathplotview.Utils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class RemoveSubCommand extends SubCommand {
    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getUsage() {
        return getName() + " <plot_name>";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1) return false;

        if (PlotManager.removePlot(args[0])) {
            sender.sendMessage(Utils.color("&2Removed!"));
        } else {
            sender.sendMessage(Utils.color(String.format("&cA plot with name '%s' doesn't exist!", args[0])));
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
