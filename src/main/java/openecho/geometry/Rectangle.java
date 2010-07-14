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
package openecho.geometry;

import openecho.math.Matrix;
import openecho.math.statistic.Mean;

/**
 *
 * @author openecho
 */
public class Rectangle implements Shape {

    public double mass;

    public Point2D centroid;

    public double rotation;

    public Point2D a,b,c,d;

    private Rectangle() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
    }

    public Rectangle(Point2D a, Point2D b, Point2D c, Point2D d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Point2D getCentroid() {
        if(centroid == null) {
            Mean x = new Mean();
            Mean y = new Mean();
            for(Point2D point: getPoints()) {
                x.addMoment(point.getX());
                y.addMoment(point.getY());
            }
            centroid = new Point2D(x.evaulate(),y.evaulate());
        }
        return centroid;
    }

    public Point2D getOrigin() {
        return this.a;
    }

    public Point2D[] getPoints() {
        return new Point2D[] {a,b,c,d};
    }

    public double getRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Shape applyShapeTransformation(ShapeTransformation t) {
        PointMatrix pointMatrix = new PointMatrix();
        Rectangle transformedShape = new Rectangle();
        transformedShape.a = Point2D.createFromTransformationMatrix(t.multiply(pointMatrix.setPointData(a)));
        transformedShape.b = Point2D.createFromTransformationMatrix(t.multiply(pointMatrix.setPointData(b)));
        transformedShape.c = Point2D.createFromTransformationMatrix(t.multiply(pointMatrix.setPointData(c)));
        transformedShape.d = Point2D.createFromTransformationMatrix(t.multiply(pointMatrix.setPointData(d)));
        transformedShape.centroid = Point2D.createFromTransformationMatrix(t.multiply(pointMatrix.setPointData(getCentroid())));
        return transformedShape;
    }
}
