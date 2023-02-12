package it.pippopad.mathplotview;

import it.pippopad.mathplotview.commands.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class MathPlotView extends JavaPlugin {

    private static MathPlotView instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getCommand("mathplotview").setExecutor(new CommandHandler());
        getCommand("mathplotview").setTabCompleter(new CommandHandler());

        PlotManager.init();
    }

    @Override
    public void onDisable() {
    }

    public static MathPlotView getInstance() {
        return instance;
    }
}
