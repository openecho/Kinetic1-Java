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

    public MutableVector(int n) {
        super(n);
    }

    public MutableVector(double[] data) {
        super(data);
    }

    @Override
    public Vector negative() {
        MutableVector a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] * -1;
        }
        return a;
    }

    @Override
    public Vector normalise() {
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
    public Vector add(Vector b) {
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
    public Vector subtract(Vector b) {
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
    public Vector cross(Vector b) {
        MutableVector a = this;
        if (a.n != 3 || b.n != 3) {
            throw new RuntimeException("Vector dimensions are not both equal to three.");
        }
        // TODO: Make these global.
        double x = a.data[Y] * b.data[Z] - a.data[Z] * b.data[Y];
        double y = a.data[Z] * b.data[X] - a.data[X] * b.data[Z];
        double z = a.data[X] * b.data[Y] - a.data[Y] * b.data[X];
        a.data[X] = x;
        a.data[Y] = y;
        a.data[Z] = z;
        return a;
    }

    @Override
    public Vector addScalar(double v) {
        MutableVector a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] + v;
        }
        return a;
    }

    @Override
    public Vector subtractScalar(double v) {
        MutableVector a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] - v;
        }
        return a;
    }

    @Override
    public Vector multiplyScalar(double v) {
        MutableVector a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] * v;
        }
        return a;
    }

    @Override
    public Vector divideScalar(double v) {
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
