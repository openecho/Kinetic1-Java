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
public class ImmutableVector {

    /**
     * n dimension
     */
    private final int n;
    /**
     * matrix data
     */
    private final double[] data;
    public static final int X, Y, Z;

    static {
        X = 0;
        Y = 1;
        Z = 2;
    }

    public ImmutableVector(int n) {
        this.n = n;
        this.data = new double[n];
    }

    public ImmutableVector(double[] data) {
        n = data.length;
        this.data = new double[n];
        System.arraycopy(data, 0, this.data, 0, n);
    }

    public int getN() {
        return n;
    }

    public double[] getData() {
        double[] output = new double[n];
        System.arraycopy(this.data, 0, output, 0, n);
        return output;
    }

    public double magnitude() {
        ImmutableVector a = this;
        double squaredSum = 0;
        for (int i = 0; i < n; i++) {
            squaredSum += Math.pow(a.data[i], 2);
        }
        return Math.sqrt(squaredSum);
    }

    public double length() {
        return magnitude();
    }

    public ImmutableVector negative() {
        ImmutableVector a = this;
        ImmutableVector b = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            a.data[i] = b.data[i] * -1;
        }
        return b;
    }

    public boolean equals(ImmutableVector b) {
        ImmutableVector a = this;
        for (int i = 0; i < n; i++) {
            if (a.data[i] != b.data[i]) {
                return false;
            }
        }
        return true;
    }

    public ImmutableVector add(ImmutableVector b) {
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

    public ImmutableVector subtract(ImmutableVector b) {
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

    public double dot(ImmutableVector b) {
        ImmutableVector a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        double dotProduct = 0;
        for (int i = 0; i < n; i++) {
            dotProduct += a.data[i] * b.data[i];
        }
        return dotProduct;
    }

    public ImmutableVector cross(ImmutableVector b) {
        ImmutableVector a = this;
        if (a.n != 3 || b.n != 3) {
            throw new RuntimeException("Vector dimensions both dont equal three.");
        }
        double[] cData = new double[]{
            a.data[Y] * b.data[Z] - a.data[Z] * b.data[Y],
            a.data[Z] * b.data[X] - a.data[X] * b.data[Z],
            a.data[X] * b.data[Y] - a.data[Y] * b.data[X]
        };
        ImmutableVector c = new ImmutableVector(cData);
        return c;
    }

    public ImmutableVector addScalar(double v) {
        ImmutableVector a = this;
        ImmutableVector c = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] + v;
        }
        return c;
    }

    public ImmutableVector subtractScalar(double v) {
        ImmutableVector a = this;
        ImmutableVector c = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] - v;
        }
        return c;
    }

    public ImmutableVector multiplyScalar(double v) {
        ImmutableVector a = this;
        ImmutableVector c = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] * v;
        }
        return c;
    }

    public ImmutableVector divideScalar(double v) {
        ImmutableVector a = this;
        ImmutableVector c = new ImmutableVector(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = a.data[i] / v;
        }
        return c;
    }

    @Override
    public String toString() {
        String dataString = "{";
        for(int i=0;i<n;i++) {
            dataString+=data[i]+((i<n-1)?",":"");
        }
        dataString+="}";
        return String.format("%s %s", super.toString(),dataString);
    }

    public static ImmutableVector zero() {
        return new ImmutableVector(new double[]{0D,0D,0D});
    }
}
