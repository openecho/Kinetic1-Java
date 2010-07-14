/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.geometry;

/**
 *
 * @author openecho
 */
public class Circle implements Shape {

    public double mass;

    public Point2D centroid;

    public Point2D reference;

    public double radius;

    public double rotation;

    public Circle(Point2D centre, double radius) {
        this.centroid = centre;
        this.radius = radius;
        this.reference = new Point2D(centre.getX() + radius, centre.getY());
    }

    private Circle() {
        this.centroid = null;
        this.radius = -1;
        this.reference = null;
    }


    public Point2D getCentroid() {
        return centroid;
    }

    public Point2D getOrigin() {
        return centroid;
    }

    public Point2D[] getPoints() {
        return new Point2D[] {reference};
    }

    public double getRadius() {
        return radius;
    }

    public Shape applyShapeTransformation(ShapeTransformation t) {
        PointMatrix pointMatrix = new PointMatrix();
        Circle transformedShape = new Circle();
        transformedShape.centroid = Point2D.createFromTransformationMatrix(t.multiply(pointMatrix.setPointData(centroid)));
        transformedShape.reference = Point2D.createFromTransformationMatrix(t.multiply(pointMatrix.setPointData(reference)));
        transformedShape.radius = radius;
        return transformedShape;
    }



}
