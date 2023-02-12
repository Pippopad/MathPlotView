package it.pippopad.mathplotview.commands;

import it.pippopad.mathplotview.MathPlotView;
import it.pippopad.mathplotview.Plot;
import it.pippopad.mathplotview.PlotManager;
import it.pippopad.mathplotview.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateSubCommand extends SubCommand {
    private enum ValidateArgsResult {
        VALID,
        INVALID_COORDS,
        INVALID_PLOTNAME_LENGTH,
        INVALID_PLOTNAME_CHARACTERS,
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getUsage() {
        return getName() + " <x1> <y1> <z1> <x2> <y2> <z2> <plot_name>";
    }

    @Override
    public boolean execute(CommandSender sender, String... args) {
        if (args.length != 7) return false;

        ValidateArgsResult res = areValidArgs(args);
        if (res != ValidateArgsResult.VALID)
        {
            switch (res) {
                case INVALID_COORDS:
                    sender.sendMessage(Utils.color("&cCoordinates must be numbers!"));
                    break;
                case INVALID_PLOTNAME_LENGTH:
                    sender.sendMessage(Utils.color("&cThe plot name's length must be between 3 and 64."));
                    break;
                case INVALID_PLOTNAME_CHARACTERS:
                    sender.sendMessage(Utils.color("&cInvalid name! Only letters, numbers, dash and underscore are allowed (must start with a letter)"));
                    break;
            }
            return true;
        }

        int x1 = Integer.parseInt(args[0]), y1 = Integer.parseInt(args[1]), z1 = Integer.parseInt(args[2]);
        int x2 = Integer.parseInt(args[3]), y2 = Integer.parseInt(args[4]), z2 = Integer.parseInt(args[5]);

        // Check if the surface is 1*y*z or x*y*1 (but not both)
        if ((Math.abs(x1 - x2) != 0 && Math.abs(z1 - z2) != 0) || Math.abs(x1 - x2) == Math.abs(z1 - z2)) {
            sender.sendMessage(Utils.color("&cInvalid surface size (should be 1*y*z or x*y*1, not both)!"));
            return true;
        }

        // Get surface width and height
        int width, height;

        if (Math.abs(x1 - x2) == 0) width = Math.abs(z1 - z2);
        else width = Math.abs(x1 - x2);
        height = Math.abs(y1 - y2);
        ///////////////////////////

        // Get minimum width and height and check if everything is fine
        int minWidth = MathPlotView.getInstance().getConfig().getInt("min-surface-width");
        minWidth = minWidth <= 5 ? 5 : minWidth;
        int minHeight = MathPlotView.getInstance().getConfig().getInt("min-surface-height");
        minHeight = minHeight <= 5 ? 5 : minHeight;

        if (width < minWidth || height < minHeight) {
            sender.sendMessage(Utils.color(String.format("&cInvalid surface size! (minimum width = %d & minimum height = %d)", minWidth, minHeight)));
            return true;
        }
        ////////////////////////////////////

        // Get maximum width and height and check if everything is fine
        int maxWidth = MathPlotView.getInstance().getConfig().getInt("max-surface-width");
        maxWidth = maxWidth != 0 && maxWidth <= minWidth ? minWidth : maxWidth;
        int maxHeight = MathPlotView.getInstance().getConfig().getInt("max-surface-height");
        maxHeight = maxHeight != 0 && maxHeight <= minWidth ? minWidth : maxHeight;

        if ((maxWidth != 0 && width > maxWidth) || (maxHeight != 0 && height > maxHeight)) {
            sender.sendMessage(Utils.color(String.format("&cInvalid surface size! (maximum width = %d & maximum height = %d)", maxWidth, maxHeight)));
            return true;
        }
        ////////////////////////////////////

        String name = args[6];
        Plot p = new Plot(name, x1, y1, z1, x2, y2, z2);

        if (PlotManager.addPlot(p)) {
            sender.sendMessage(Utils.color("&2Created!"));
        } else {
            sender.sendMessage(Utils.color("&cA plot with that name already exists!"));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        List<String> output = new ArrayList<>();
        Block target = ((Player) sender).getTargetBlockExact(5);
        if (target == null) return Collections.emptyList();

        Location targetLoc = target.getLocation();
        if (args.length < 7) {
            String op1 = Integer.toString(targetLoc.getBlockX());
            String op2 = String.format("%d %d", targetLoc.getBlockX(), targetLoc.getBlockY());
            String op3 = String.format("%d %d %d", targetLoc.getBlockX(), targetLoc.getBlockY(), targetLoc.getBlockZ());

            String op4 = Integer.toString(targetLoc.getBlockY());
            String op5 = String.format("%d %d", targetLoc.getBlockY(), targetLoc.getBlockZ());

            String op6 = Integer.toString(targetLoc.getBlockZ());

            if (args.length == 1 || args.length == 4) {
                output.add(op1);
                output.add(op2);
                output.add(op3);
            }
            if (args.length == 2 || args.length == 5) {
                output.add(op4);
                output.add(op5);
            }
            if (args.length == 3 || args.length == 6) output.add(op6);
        }

        return output;
    }

    private ValidateArgsResult areValidArgs(String... args) {
        // Check if coord values are numbers
        for (int i = 0; i < 7 - 1; i++) {
            try {
                Integer.parseInt(args[i]);
            } catch (Exception e) {
                return ValidateArgsResult.INVALID_COORDS;
            }
        }

        String name = args[6];
        if (name.length() < 3 || name.length() > 64) return ValidateArgsResult.INVALID_PLOTNAME_LENGTH;

        // Check if name is valid
        Pattern p = Pattern.compile("^[a-z][a-z0-9-_]+$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        if (!m.find()) return ValidateArgsResult.INVALID_PLOTNAME_CHARACTERS;

        return ValidateArgsResult.VALID;
    }
}
