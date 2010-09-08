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
public class MutableVectorD extends VectorD {

    double xCross, yCross, zCross;

    public MutableVectorD(int n) {
        super(n);
    }

    public MutableVectorD(double[] data) {
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
    public final VectorD negative() {
        MutableVectorD a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] * -1;
        }
        return a;
    }

    @Override
    public final VectorD normalise() {
        MutableVectorD a = this;
        double m = magnitude();
        if (m == 0) {
            return VectorD.zero();
        }
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] / m;
        }
        return a;
    }

    @Override
    public final VectorD add(VectorD b) {
        MutableVectorD a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] + b.data[i];
        }
        return a;
    }

    @Override
    public final VectorD subtract(VectorD b) {
        MutableVectorD a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] - b.data[i];
        }
        return a;
    }

    @Override
    public final VectorD cross(VectorD b) {
        MutableVectorD a = this;
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
    public final VectorD addScalar(double v) {
        MutableVectorD a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] + v;
        }
        return a;
    }

    @Override
    public final VectorD subtractScalar(double v) {
        MutableVectorD a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] - v;
        }
        return a;
    }

    @Override
    public final VectorD multiplyScalar(double v) {
        MutableVectorD a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] * v;
        }
        return a;
    }

    @Override
    public final VectorD divideScalar(double v) {
        if (v == 0) {
            throw new RuntimeException("Divide By Zero.");
        }
        MutableVectorD a = this;
        for (int i = 0; i < n; i++) {
            a.data[i] = a.data[i] / v;
        }
        return a;
    }
}
