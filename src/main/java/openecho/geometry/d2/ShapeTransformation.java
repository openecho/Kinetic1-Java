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

import openecho.math.Matrix;
import openecho.math.MutableMatrix;

/**
 *
 * @author openecho
 */
public class ShapeTransformation extends MutableMatrix {

    public double rotation, xScale, yScale, xTranslation, yTranslation;

    public ShapeTransformation(double[][] data) {
        super(data);
        rotation = xTranslation = yTranslation = 0;
        xScale = yScale = 1;
    }

    public ShapeTransformation(int m, int n) {
        super(m,n);
        rotation = xTranslation = yTranslation = 0;
        xScale = yScale = 1;
    }

    public ShapeTransformation multiply(ShapeTransformation b) {
        if(getM() != b.getM() || getN() != b.getN()) {
            this.multiply((Matrix) b);
        }
        Matrix output = super.multiply(b);
        this.data = output.getData();
        this.rotation += b.rotation;
        this.xScale *= b.xScale;
        this.yScale *= b.yScale;
        this.xTranslation += b.xTranslation;
        this.yTranslation += b.yTranslation;
        return this;
    }

    public static ShapeTransformation createAffineRotationMatrix(double angle) {
        double[][] transformation = new double[3][3];
        double sinAngle = Math.sin(angle);
        double cosAngle = Math.cos(angle);
        transformation[0][0] = cosAngle;
        transformation[0][1] = sinAngle*-1;
        transformation[0][2] = 0;
        transformation[1][0] = sinAngle;
        transformation[1][1] = cosAngle;
        transformation[1][2] = 0;
        transformation[2][0] = 0;
        transformation[2][1] = 0;
        transformation[2][2] = 1;
        ShapeTransformation shapeTransformation = null;
        shapeTransformation = new ShapeTransformation(transformation);
        shapeTransformation.rotation = angle;
        return shapeTransformation;
    }

    public static ShapeTransformation createAffineScaleMatrix(double xScale, double yScale) {
        double[][] transformation = new double[3][3];
        transformation[0][0] = xScale;
        transformation[0][1] = 0;
        transformation[0][2] = 0;
        transformation[1][0] = 0;
        transformation[1][1] = yScale;
        transformation[1][2] = 0;
        transformation[2][0] = 0;
        transformation[2][1] = 0;
        transformation[2][2] = 1;
        ShapeTransformation shapeTransformation = null;
        shapeTransformation = new ShapeTransformation(transformation);
        shapeTransformation.xScale = xScale;
        shapeTransformation.yScale = yScale;
        return shapeTransformation;
    }

    public static ShapeTransformation createAffineTranslationMatrix(double xTranslation, double yTranslation) {
        double[][] transformation = new double[3][3];
        transformation[0][0] = 1;
        transformation[0][1] = 0;
        transformation[0][2] = xTranslation;
        transformation[1][0] = 0;
        transformation[1][1] = 1;
        transformation[1][2] = yTranslation;
        transformation[2][0] = 0;
        transformation[2][1] = 0;
        transformation[2][2] = 1;
        ShapeTransformation shapeTransformation = null;
        shapeTransformation = new ShapeTransformation(transformation);
        shapeTransformation.xTranslation = xTranslation;
        shapeTransformation.yTranslation = yTranslation;
        return shapeTransformation;
    }
}
