/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.geometry;

/**
 *
 * @author openecho
 */
public class ShapeFactory {

    public static Point2D createCentreOriginPoint() {
        return new Point2D(0D,0D);
    }

    public static Line createCentreOriginLine(double length) {
        double halfLength = length/2D;
        Line line = new Line(
                new Point2D(-halfLength, 0),
                new Point2D(halfLength, 0)
        );
        return line;
    }

    public static Rectangle createCentreOriginRectangle(double width, double height) {
        double halfWidth = width/2D;
        double halfHeight = height/2D;
        Rectangle output = new Rectangle(
                new Point2D(-halfWidth,-halfHeight), 
                new Point2D(-halfWidth,halfHeight),
                new Point2D(halfWidth,halfHeight), 
                new Point2D(halfWidth,-halfHeight)
        );
        return output;
    }

    public static Circle createCentreOriginCircle(double radius) {
        Circle output = new Circle(new Point2D(0D,0D), radius);
        return output;
    }
}
