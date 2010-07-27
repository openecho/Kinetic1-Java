/**
 * Copyright (C) 2010 openecho
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package openecho.geometry.d2;

/**
 *
 * @author openecho
 */
public class Circle implements Shape {

    public double mass;

    public Point centroid;

    public Point reference;

    public double radius;

    public double rotation;

    public Circle(Point centre, double radius) {
        this.centroid = centre;
        this.radius = radius;
        this.reference = new Point(centre.getX() + radius, centre.getY());
    }

    private Circle() {
        this.centroid = null;
        this.radius = -1;
        this.reference = null;
    }


    public Point getCentroid() {
        return centroid;
    }

    public Point getOrigin() {
        return centroid;
    }

    public Point[] getPoints() {
        return new Point[] {reference};
    }

    public double getRadius() {
        return radius;
    }

    public Shape applyShapeTransformation(ShapeTransformation t) {
        if(t.xScale != t.yScale) {
            throw new RuntimeException("Not Valid Transformation - Scale Error for Circle");
        }
        PointMatrix pointMatrix = new PointMatrix();
        Circle transformedShape = new Circle();
        transformedShape.centroid = Point.createFromTransformationMatrix(pointMatrix.setPointData(centroid).multiply(t));
        transformedShape.reference = Point.createFromTransformationMatrix(pointMatrix.setPointData(reference).multiply(t));
        transformedShape.radius = radius;
        return transformedShape;
    }



}
