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
package openecho.math;

/**
 *
 * @author openecho
 */
public class MutableVector extends Vector {

    double xCross, yCross, zCross;

    public MutableVector(int n) {
        super(n);
    }

    public MutableVector(double[] data) {
        super(data);
    }

    @Override
    public final double[] getData() {
        return data;
    }

    @Override
    public final boolean isMutable() {
        return true;
    }

    @Override
    public final Vector negative() {
        MutableVector a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] * -1;
        }
        return a;
    }

    @Override
    public final Vector normalise() {
        MutableVector a = this;
        double m = magnitude();
        if (m == 0) {
            return Vector.zero();
        }
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] / m;
        }
        return a;
    }

    @Override
    public final Vector add(Vector b) {
        MutableVector a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] + b.data[i];
        }
        return a;
    }

    @Override
    public final Vector subtract(Vector b) {
        MutableVector a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] - b.data[i];
        }
        return a;
    }

    @Override
    public final Vector cross(Vector b) {
        MutableVector a = this;
        if (a.n != 3 || b.n != 3) {
            throw new RuntimeException("Vector dimensions are not both equal to three.");
        }
        xCross = a.data[Y] * b.data[Z] - a.data[Z] * b.data[Y];
        yCross = a.data[Z] * b.data[X] - a.data[X] * b.data[Z];
        zCross = a.data[X] * b.data[Y] - a.data[Y] * b.data[X];
        a.data[X] = xCross;
        a.data[Y] = yCross;
        a.data[Z] = zCross;
        return a;
    }

    @Override
    public final Vector addScalar(double v) {
        MutableVector a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] + v;
        }
        return a;
    }

    @Override
    public final Vector subtractScalar(double v) {
        MutableVector a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] - v;
        }
        return a;
    }

    @Override
    public final Vector multiplyScalar(double v) {
        MutableVector a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] * v;
        }
        return a;
    }

    @Override
    public final Vector divideScalar(double v) {
        if (v == 0) {
            throw new RuntimeException("Divide By Zero.");
        }
        MutableVector a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] / v;
        }
        return a;
    }
}
