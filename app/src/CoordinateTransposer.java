public class CoordinateTransposer {
    public static Vector physicalToVisual(Vector vector) {
       return new Vector(vector.x, vector.y);
    }

    public static Vector visualToPhysical(Vector vector) {
        return new Vector(vector.x, Settings.windowY-vector.y);
    }
}
