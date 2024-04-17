public class CircleObject {
    CircleSpriteHandler circleSpriteHandler;
    NewtonPoint newtonPoint;

    public CircleObject(int x, int y, int r, CoordinateTransposer coordinateTransposer) {
        newtonPoint = new NewtonPoint(new Vector(x, y));
        circleSpriteHandler = new CircleSpriteHandler(this, r, coordinateTransposer);
    }

    public void setSpeed(Vector speed) {
        newtonPoint.speed = speed;
    }
}
