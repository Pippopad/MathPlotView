package it.pippopad.mathplotview;

import java.util.ArrayList;
import java.util.List;

public class PlotManager {

    private static List<Plot> plots;

    public static void init() {
        plots = new ArrayList<Plot>();
    }

    public static boolean addPlot(Plot plot) {
        for (Plot p : plots)
            if (p.getName() == plot.getName()) return false;
        plots.add(plot);
        return true;
    }

    public static boolean removePlot(String name) {
        for (Plot p : plots)
            if (p.getName() == name) {
                plots.remove(p);
                return true;
            }
        return false;
    }

    public static Plot getPlot(String name) {
        for (Plot p : plots)
            if (p.getName() == name) return p;
        return null;
    }
}
