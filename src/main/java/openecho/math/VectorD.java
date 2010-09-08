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
public abstract class VectorD extends Vector {

    /**
     * n dimension
     */
    final int n;
    /**
     * matrix data
     */
    double[] data;
    public static final int X, Y, Z;
    public static final ImmutableVectorD ZERO;

    static {
        X = 0;
        Y = 1;
        Z = 2;
        ZERO = new ImmutableVectorD(new double[]{0D, 0D, 0D});
    }

    public VectorD(int n) {
        this.n = n;
        this.data = new double[n];
    }

    public VectorD(double[] data) {
        n = data.length;
        this.data = new double[n];
        System.arraycopy(data, 0, this.data, 0, n);
    }

    public final int getN() {
        return n;
    }

    public abstract double[] getData();

    /**
     * Flag indicating if this version of the VectorD is mutable.
     * @return boolean VectorD mutable when true otherwise not mutable.
     */
    public abstract boolean isMutable();

    public final double magnitude() {
        VectorD a = this;
        double squaredSum = 0;
        for (int i = 0; i < n; i++) {
            squaredSum += Math.pow(a.data[i], 2);
        }
        return Math.sqrt(squaredSum);
    }

    public final double length() {
        return magnitude();
    }

    public abstract VectorD negative();

    public abstract VectorD normalise();

    public final boolean equals(VectorD b) {
        VectorD a = this;
        for (int i = 0; i < n; i++) {
            if (a.data[i] != b.data[i]) {
                return false;
            }
        }
        return true;
    }

    public abstract VectorD add(VectorD b);

    public abstract VectorD subtract(VectorD b);

    public double dot(VectorD b) {
        VectorD a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        double dotProduct = 0;
        for (int i = 0; i < n; i++) {
            dotProduct += a.data[i] * b.data[i];
        }
        return dotProduct;
    }

    public abstract VectorD cross(VectorD b);

    public abstract VectorD addScalar(double v);

    public abstract VectorD subtractScalar(double v);

    public abstract VectorD multiplyScalar(double v);

    public abstract VectorD divideScalar(double v);

    @Override
    public String toString() {
        String dataString = "{";
        for (int i = 0; i < n; i++) {
            dataString += data[i] + ((i < n - 1) ? ", " : "");
        }
        dataString += "}";
        return String.format("%s %s", super.toString(), dataString);
    }

    public static VectorD zero() {
        return VectorD.ZERO;
    }
}