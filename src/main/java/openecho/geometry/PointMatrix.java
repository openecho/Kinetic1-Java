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
public class PointMatrix extends MutableMatrix {

    public PointMatrix() {
        super(3,1);
    }

    public PointMatrix setPointData(Point2D point) {
        data[0][0] = point.getData()[Point2D.X];
        data[1][0] = point.getData()[Point2D.Y];
        data[2][0] = 1;
        return this;
    }
}
