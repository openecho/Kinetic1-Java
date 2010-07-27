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

import openecho.math.Vector;
import openecho.math.statistic.Mean;

/**
 *
 * @author openecho
 */
public class Line implements Shape {

    public Point a,b;

    public double mass;

    public Vector centroid;

    public double rotation;

    // Radius

    // Bounding Box

    // Bounding Hull
    
    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Line(Vector v) {
        this.a = new Point(0D,0D);
        this.b = new Point(v.getData()[Vector.X],v.getData()[Vector.Y]);
    }

    public Point getStartPoint() {
        return a;
    }

    public void setStartPoint(Point startPoint) {
        this.a = startPoint;
    }

    public Point getEndPoint() {
        return b;
    }

    public void setEndPoint(Point endPoint) {
        this.b = endPoint;
    }

    public Point getCentroid() {
        Mean x = new Mean();
        Mean y = new Mean();
        for(Point point: getPoints()) {
            x.addMoment(point.getX());
            y.addMoment(point.getY());
        }
        return new Point(x.evaulate(),y.evaulate());
    }

    public Point getOrigin() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point[] getPoints() {
        return new Point[] {a,b};
    }

    public double getRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Shape applyShapeTransformation(ShapeTransformation t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
