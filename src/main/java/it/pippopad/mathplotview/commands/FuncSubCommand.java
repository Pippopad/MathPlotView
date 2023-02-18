package it.pippopad.mathplotview.commands;

import it.pippopad.mathplotview.Plot;
import it.pippopad.mathplotview.PlotManager;
import it.pippopad.mathplotview.Utils;
import jdk.internal.joptsimple.internal.Strings;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FuncSubCommand extends SubCommand {
    @Override
    public String getName() {
        return "func";
    }

    @Override
    public String getUsage() {
        return getName() + " <plot_name> <func>";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) return false;

        Plot p = PlotManager.getPlot(args[0]);
        if (p != null) {
            p.setFunction(String.join("", Arrays.copyOfRange(args, 1, args.length)));
            sender.sendMessage(Utils.color("&2Function set!"));
            sender.sendMessage(p.getFunction());
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
