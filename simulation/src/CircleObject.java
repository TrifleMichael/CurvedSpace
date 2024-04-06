public class CircleObject {
    CircleSpriteHandler circleSpriteHandler;
    NewtonPoint newtonPoint;

    public CircleObject(int x, int y, int r) {
        newtonPoint = new NewtonPoint(new Vector(x, y));
        circleSpriteHandler = new CircleSpriteHandler(this, r);
    }

    public void setSpeed(Vector speed) {
        newtonPoint.speed = speed;
    }
}
