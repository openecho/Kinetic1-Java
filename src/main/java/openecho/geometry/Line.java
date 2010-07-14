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

import openecho.math.Vector;
import openecho.math.statistic.Mean;

/**
 *
 * @author openecho
 */
public class Line implements Shape {

    public Point2D a,b;

    public double mass;

    public Vector centroid;

    public double rotation;

    // Radius

    // Bounding Box

    // Bounding Hull
    
    public Line(Point2D a, Point2D b) {
        this.a = a;
        this.b = b;
    }

    public Line(Vector v) {
        this.a = new Point2D(0D,0D);
        this.b = new Point2D(v.getData()[Vector.X],v.getData()[Vector.Y]);
    }

    public Point2D getStartPoint() {
        return a;
    }

    public void setStartPoint(Point2D startPoint) {
        this.a = startPoint;
    }

    public Point2D getEndPoint() {
        return b;
    }

    public void setEndPoint(Point2D endPoint) {
        this.b = endPoint;
    }

    public Point2D getCentroid() {
        Mean x = new Mean();
        Mean y = new Mean();
        for(Point2D point: getPoints()) {
            x.addMoment(point.getX());
            y.addMoment(point.getY());
        }
        return new Point2D(x.evaulate(),y.evaulate());
    }

    public Point2D getOrigin() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point2D[] getPoints() {
        return new Point2D[] {a,b};
    }

    public double getRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Shape applyShapeTransformation(ShapeTransformation t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
