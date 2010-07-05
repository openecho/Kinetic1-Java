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
 * Vector Implementation. This version is immutable.
 *
 * Holds an n dimensional vector.
 *
 * @author openecho
 * @version 1.0.0
 */
public class ImmutableVector extends Vector {

    public ImmutableVector(int n) {
        super(n);
    }

    public ImmutableVector(double[] data) {
        super(data);
    }

    @Override
    public ImmutableVector negative() {
        ImmutableVector a = this;
        ImmutableVector b = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            a.data[i] = b.data[i] * -1;
        }
        return b;
    }
    @Override
    public Vector normalise() {
        ImmutableVector a = this;
        double m = magnitude();
        if (m == 0) {
            return Vector.zero();
        }
        ImmutableVector b = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            b.data[i] = a.data[i] / m;
        }
        return b;
    }
    @Override
    public Vector add(Vector b) {
        ImmutableVector a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        ImmutableVector c = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] + b.data[i];
        }
        return c;
    }
    @Override
    public Vector subtract(Vector b) {
        ImmutableVector a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        ImmutableVector c = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] - b.data[i];
        }
        return c;
    }
    
    @Override
    public Vector cross(Vector b) {
        ImmutableVector a = this;
        if (a.n != 3 || b.n != 3) {
            throw new RuntimeException("Vector dimensions are not both equal to three.");
        }
        double[] cData = new double[]{
            a.data[Y] * b.data[Z] - a.data[Z] * b.data[Y],
            a.data[Z] * b.data[X] - a.data[X] * b.data[Z],
            a.data[X] * b.data[Y] - a.data[Y] * b.data[X]
        };
        ImmutableVector c = new ImmutableVector(cData);
        return c;
    }
    @Override
    public Vector addScalar(double v) {
        ImmutableVector a = this;
        ImmutableVector c = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] + v;
        }
        return c;
    }
    @Override
    public Vector subtractScalar(double v) {
        ImmutableVector a = this;
        ImmutableVector c = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] - v;
        }
        return c;
    }
    @Override
    public Vector multiplyScalar(double v) {
        ImmutableVector a = this;
        ImmutableVector c = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] * v;
        }
        return c;
    }
    @Override
    public Vector divideScalar(double v) {
        if (v == 0) {
            throw new RuntimeException("Divide By Zero.");
        }
        ImmutableVector a = this;
        ImmutableVector c = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] / v;
        }
        return c;
    }
}
