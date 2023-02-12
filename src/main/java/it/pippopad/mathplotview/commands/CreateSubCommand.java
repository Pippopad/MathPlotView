package it.pippopad.mathplotview.commands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateSubCommand extends SubCommand {
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

        sender.sendMessage(args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        List<String> output = new ArrayList<String>();
        Block target = ((Player) sender).getTargetBlockExact(5);
        if (target == null) {
            if (args.length == 1 || args.length == 4) {
                output.add("~");
                output.add("~ ~");
                output.add("~ ~ ~");
            }
            if (args.length == 2 || args.length == 5) {
                output.add("~ ~");
                output.add("~ ~ ~");
            }
            if (args.length == 3 || args.length == 6) {
                output.add("~");
            }
            return output;
        }

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
}
