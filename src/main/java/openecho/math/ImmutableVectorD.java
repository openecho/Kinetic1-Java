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
 * VectorD Implementation. This version is immutable.
 *
 * Holds an n dimensional vector.
 *
 * @author openecho
 * @version 1.0.0
 */
public class ImmutableVectorD extends VectorD {

    public ImmutableVectorD(int n) {
        super(n);
    }

    public ImmutableVectorD(double[] data) {
        super(data);
    }

    @Override
    public final double[] getData() {
        double[] returnData = new double[n];
        System.arraycopy(returnData, 0, data, 0, n);
        return returnData;
    }

    @Override
    public final boolean isMutable() {
        return false;
    }

    @Override
    public final ImmutableVectorD negative() {
        ImmutableVectorD a = this;
        ImmutableVectorD b = new ImmutableVectorD(n);
        for (int i = 0; i < n; i++) {
            a.data[i] = b.data[i] * -1;
        }
        return b;
    }
    @Override
    public final VectorD normalise() {
        ImmutableVectorD a = this;
        double m = magnitude();
        if (m == 0) {
            return VectorD.zero();
        }
        ImmutableVectorD b = new ImmutableVectorD(n);
        for (int i = 0; i < n; i++) {
            b.data[i] = a.data[i] / m;
        }
        return b;
    }
    @Override
    public final VectorD add(VectorD b) {
        ImmutableVectorD a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        ImmutableVectorD c = new ImmutableVectorD(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] + b.data[i];
        }
        return c;
    }
    @Override
    public final VectorD subtract(VectorD b) {
        ImmutableVectorD a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        ImmutableVectorD c = new ImmutableVectorD(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] - b.data[i];
        }
        return c;
    }
    
    @Override
    public final VectorD cross(VectorD b) {
        ImmutableVectorD a = this;
        if (a.n != 3 || b.n != 3) {
            throw new RuntimeException("Vector dimensions are not both equal to three.");
        }
        double[] cData = new double[]{
            a.data[Y] * b.data[Z] - a.data[Z] * b.data[Y],
            a.data[Z] * b.data[X] - a.data[X] * b.data[Z],
            a.data[X] * b.data[Y] - a.data[Y] * b.data[X]
        };
        ImmutableVectorD c = new ImmutableVectorD(cData);
        return c;
    }
    @Override
    public final VectorD addScalar(double v) {
        ImmutableVectorD a = this;
        ImmutableVectorD c = new ImmutableVectorD(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] + v;
        }
        return c;
    }
    @Override
    public final VectorD subtractScalar(double v) {
        ImmutableVectorD a = this;
        ImmutableVectorD c = new ImmutableVectorD(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] - v;
        }
        return c;
    }
    @Override
    public final VectorD multiplyScalar(double v) {
        ImmutableVectorD a = this;
        ImmutableVectorD c = new ImmutableVectorD(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] * v;
        }
        return c;
    }
    @Override
    public final VectorD divideScalar(double v) {
        if (v == 0) {
            throw new RuntimeException("Divide By Zero.");
        }
        ImmutableVectorD a = this;
        ImmutableVectorD c = new ImmutableVectorD(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] / v;
        }
        return c;
    }
}
