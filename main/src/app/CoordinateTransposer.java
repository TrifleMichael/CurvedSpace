package app;

import utility.Settings;
import utility.Vector;

public class CoordinateTransposer {

    public Vector shipPosition;

    public Vector physicalToVisual(Vector vector) {
        Vector vec = new Vector(vector);
        vec.subtract(shipPosition);
        vec.add(new Vector(Settings.windowX / 2, Settings.windowY / 2));
       return vec;
    }

    public Vector visualToPhysical(Vector vector) {
        Vector vec = new Vector(vector);
        vec.y = Settings.windowY - vec.y;
        vec.subtract(new Vector(Settings.windowX / 2, Settings.windowY / 2));
        vec.add(shipPosition);
        return vec;
    }
}
