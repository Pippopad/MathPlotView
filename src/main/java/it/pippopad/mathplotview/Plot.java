package it.pippopad.mathplotview;

public class Plot {
    private String name;
    private int x1, y1, z1;
    private int x2, y2, z2;

    private String func;

    public Plot(String name, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.name = name;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;

        this.func = null;
    }

    public String getName() {
        return name;
    }

    public void setFunction(String func) {
        this.func = func;
    }

    public String getFunction() {
        return func;
    }
}
