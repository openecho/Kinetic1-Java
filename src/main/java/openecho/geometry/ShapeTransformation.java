/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.geometry;

import openecho.math.MutableMatrix;

/**
 *
 * @author jmarsden
 */
public class ShapeTransformation extends MutableMatrix {

    public ShapeTransformation(double[][] data) {
        super(data);
    }

    public ShapeTransformation(int m, int n) {
        super(m,n);
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
        return new ShapeTransformation(transformation);
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
        return new ShapeTransformation(transformation);
    }

    public static ShapeTransformation createAffineTranslationMatrix(double xTransform, double yTransform) {
        double[][] transformation = new double[3][3];
        transformation[0][0] = 1;
        transformation[0][1] = 0;
        transformation[0][2] = xTransform;
        transformation[1][0] = 0;
        transformation[1][1] = 1;
        transformation[1][2] = yTransform;
        transformation[2][0] = 0;
        transformation[2][1] = 0;
        transformation[2][2] = 1;
        return new ShapeTransformation(transformation);
    }
}
