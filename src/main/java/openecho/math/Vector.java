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
public abstract class Vector {

    /**
     * n dimension
     */
    final int n;
    /**
     * matrix data
     */
    protected double[] data;
    public static final int X, Y, Z;
    public static final ImmutableVector ZERO;

    static {
        X = 0;
        Y = 1;
        Z = 2;
        ZERO = new ImmutableVector(new double[]{0D, 0D, 0D});
    }

    public Vector(int n) {
        this.n = n;
        this.data = new double[n];
    }

    public Vector(double[] data) {
        n = data.length;
        this.data = new double[n];
        System.arraycopy(data, 0, this.data, 0, n);
    }

    public final int getN() {
        return n;
    }

    public abstract double[] getData();

    /**
     * Flag indicating if this version of the Vector is mutable.
     * @return boolean Vector mutable when true otherwise not mutable.
     */
    public abstract boolean isMutable();

    public final double magnitude() {
        Vector a = this;
        double squaredSum = 0;
        for (int i = 0; i < n; i++) {
            squaredSum += Math.pow(a.data[i], 2);
        }
        return Math.sqrt(squaredSum);
    }

    public final double length() {
        return magnitude();
    }

    public abstract Vector negative();

    public abstract Vector normalise();

    public final boolean equals(Vector b) {
        Vector a = this;
        for (int i = 0; i < n; i++) {
            if (a.data[i] != b.data[i]) {
                return false;
            }
        }
        return true;
    }

    public abstract Vector add(Vector b);

    public abstract Vector subtract(Vector b);

    public double dot(Vector b) {
        Vector a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        double dotProduct = 0;
        for (int i = 0; i < n; i++) {
            dotProduct += a.data[i] * b.data[i];
        }
        return dotProduct;
    }

    public abstract Vector cross(Vector b);

    public abstract Vector addScalar(double v);

    public abstract Vector subtractScalar(double v);

    public abstract Vector multiplyScalar(double v);

    public abstract Vector divideScalar(double v);

    @Override
    public String toString() {
        String dataString = "{";
        for (int i = 0; i < n; i++) {
            dataString += data[i] + ((i < n - 1) ? ", " : "");
        }
        dataString += "}";
        return String.format("%s %s", super.toString(), dataString);
    }

    public static Vector zero() {
        return Vector.ZERO;
    }
}