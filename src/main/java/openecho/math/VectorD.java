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

    public static final ArrayVectorD ZERO;

    static {
        ZERO = new ArrayVectorD(new Double[]{0D, 0D, 0D});
    }

    public VectorD(int n) {
        this(n, false);
    }

    public VectorD(int n, boolean mutable) {
        this.n = n;
        this.mutate = mutable;
    }

    public VectorD(Number[] data) {
        this(data, false);
    }

    public VectorD(Number[] data, boolean mutable) {
        n = data.length;
        this.mutate = mutable;
    }

    protected abstract void initData(Number[] data);

    protected abstract void initData(int i, Number data);

    public abstract Double[] getData();

    public abstract Double getData(int i);

    public abstract void setData(Number[] data);

    public abstract void setData(int i, Number data);

    public final Double magnitude() {
        VectorD a = this;
        double squaredSum = 0;
        for (int i = 0; i < n; i++) {
            squaredSum += Math.pow(a.getData(i), 2);
        }
        return Math.sqrt(squaredSum);
    }

    @Override
    public final Double length() {
        return magnitude();
    }

    public abstract VectorD negative();

    public abstract VectorD normalise();

    public final boolean equals(Vector b) {
        Vector a = this;
        for (int i = 0; i < n; i++) {
            if (!a.getData(i).equals(b.getData(i))) {
                return false;
            }
        }
        return true;
    }

    public abstract VectorD add(Vector b);

    public abstract VectorD subtract(Vector b);

    public Double dot(Vector b) {
        if (n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        double dotProduct = 0;
        for (int i = 0; i < n; i++) {
            dotProduct += getData(i) * b.getData(i).doubleValue();
        }
        return dotProduct;
    }

    public abstract VectorD cross(Vector b);

    public abstract VectorD addScalar(Number v);

    public abstract VectorD subtractScalar(Number v);

    public abstract VectorD multiplyScalar(Number v);

    public abstract VectorD divideScalar(Number v);

    @Override
    public String toString() {
        String dataString = "{";
        for (int i = 0; i < n; i++) {
            dataString += getData(i) + ((i < n - 1) ? ", " : "");
        }
        dataString += "}";
        return String.format("%s %s", super.toString(), dataString);
    }

    public static VectorD empty(int i) {
        VectorD e = new ArrayVectorD(i);
        for(int j=0;j<i;j++) {
            e.setData(i, 0D);
        }
        return e;
    }

    public static VectorD random(int i) {
        VectorD r = new ArrayVectorD(i);
        for(int j=0;j<i;j++) {
            r.setData(i, QuickMath.random());
        }
        return r;
    }

    public static VectorD zero() {
        return VectorD.ZERO;
    }
}
