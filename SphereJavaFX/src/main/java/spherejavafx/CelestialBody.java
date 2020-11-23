package spherejavafx;

public enum CelestialBody {
    SUN(75,0,0,"sun",false, 0, 0, 0),
    EARTH(30,250,0,"earth",true, 0, 0, 0),
    MOON(30,275,275,"moon",true, 0, 250, 0);

    private final double radius;
    private final double distanceFromXCenter;
    private final double distanceFromYCenter;
    private final String path;
    private final boolean hasPivot;
    private final double angle;
    private final double pivotX;
    private final double pivotY;


    CelestialBody(double radius, double distanceFromXCenter,
                  double distanceFromYCenter, String path,
                  boolean hasPivot, double angle, double pivotX,
                  double pivotY){

        this.radius = radius;
        this.distanceFromXCenter = distanceFromXCenter;
        this.distanceFromYCenter = distanceFromYCenter;
        this.path = path;
        this.hasPivot = hasPivot;
        this.angle = angle;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    public double getRadius() {
        return radius;
    }

    public double getDistanceFromXCenter() {
        return distanceFromXCenter;
    }

    public double getDistanceFromYCenter() {
        return distanceFromYCenter;
    }

    public String getPath() {
        return path;
    }

    public boolean isPivot() {
        return hasPivot;
    }

    public double getAngle() {
        return angle;
    }

    public double getPivotX() {
        return pivotX;
    }

    public double getPivotY() {
        return pivotY;
    }
}
