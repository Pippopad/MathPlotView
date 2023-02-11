package it.pippopad.mathplotview;

import it.pippopad.mathplotview.commands.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class MathPlotView extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("mathplotview").setExecutor(new CommandHandler());
        getCommand("mathplotview").setTabCompleter(new CommandHandler());
    }

    @Override
    public void onDisable() {
    }
}
