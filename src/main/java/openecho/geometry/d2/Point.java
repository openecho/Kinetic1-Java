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

import openecho.math.MutableVector;
import openecho.math.Matrix;
/**
 *
 * @author openecho
 */
public class Point extends MutableVector implements Shape {

    public Point(double x, double y) {
        super(new double[] {x,y});
    }

    public Point(double[] data) {
        super(data);
    }

    public Point(int n) {
        super(n);
    }

    public double getX() {
        return data[X];
    }

    public double getY() {
        return data[Y];
    }

    public Point getCentroid() {
        return this;
    }

    public Point getOrigin() {
        return this;
    }

    public Point[] getPoints() {
        return new Point[] {this};
    }

    public double getRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point applyShapeTransformation(ShapeTransformation t) {
        PointMatrix pointMatrix = new PointMatrix();
        return Point.createFromTransformationMatrix(t.multiply(pointMatrix.setPointData(getCentroid())));
    }

    public static Point createFromTransformationMatrix(Matrix transformationMatrix) {
        if(transformationMatrix.getN() != 1 || transformationMatrix.getM() < 2) {
            throw new RuntimeException("Cannot Create Point2D from a Matrix unless its 1xM where M >= 2");
        }
        Point point = null;
        double[][] data = transformationMatrix.getData();
        point = new Point(data[0][0], data[1][0]);
        return point;
    }
}
