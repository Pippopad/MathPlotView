package it.pippopad.mathplotview;

import java.util.ArrayList;
import java.util.List;

public class PlotManager {

    private static List<Plot> plots;

    public static void init() {
        plots = new ArrayList<>();
    }

    public static boolean addPlot(Plot plot) {
        for (Plot p : plots)
            if (p.getName().equalsIgnoreCase(plot.getName())) return false;
        plots.add(plot);
        return true;
    }

    public static boolean removePlot(String name) {
        for (Plot p : plots)
            if (p.getName().equalsIgnoreCase(name)) {
                plots.remove(p);
                return true;
            }
        return false;
    }

    public static List<Plot> getPlots() {
        return plots;
    }

    public static Plot getPlot(String name) {
        for (Plot p : plots)
            if (p.getName().equalsIgnoreCase(name)) return p;
        return null;
    }
}
