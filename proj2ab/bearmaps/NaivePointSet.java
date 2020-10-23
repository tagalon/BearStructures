package bearmaps;
import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> pointList;

    /* This is a constructor for NaivePointSet, which takes in a List<Point> points */
    public NaivePointSet(List<Point> points) {
        pointList = points;
    }

    /* This method returns the nearest point by searching the arguments in constant runtime */
    /* This method takes in two doubles as arguments: x and y */
    @Override
//    public Point nearest(double x, double y) {
//        double bestDistance = distance(pointList.get(0), new Point(x, y));
//        int bestIndex = 0;
//        for (int index = 1; index < pointList.size(); index ++) {
//            double testDistance = distance(pointList.get(index), new Point(x, y));
//            if (testDistance <= bestDistance) {
//                bestIndex = index;
//                bestDistance = testDistance;
//            }
//        }
//        return pointList.get(bestIndex);
//    }
    public Point nearest(double x, double y) {
        Point bestPoint = pointList.get(0);
        Point testPoint = new Point(x, y);
        for (int index = 1; index < pointList.size(); index++) {
            if (distance(pointList.get(index), testPoint) <= distance(bestPoint, testPoint)) {
                bestPoint = pointList.get(index);
                double bestDistance = distance(pointList.get(0), new Point(x, y));
                int bestIndex = 0;
                for (int i = 1; index < pointList.size(); i++) {
                    double testDistance = distance(pointList.get(i), new Point(x, y));
                    if (testDistance <= bestDistance) {
                        bestIndex = i;
                        bestDistance = testDistance;
                    }
                }
            }
        }
        return bestPoint;
    }

            /* This method calculates and returns distance as a double */
            /* This method takes two Point arguments */
            private double distance(Point p1, Point p2) {
                double x = p1.getX() - p2.getX();
                double y = p1.getY() - p2.getY();
                return Math.sqrt(x * x + y * y);
            }
}