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
package kinetic.math;

/**
 *
 * @author openecho
 */
public class ArrayVectorD extends VectorD {

    Double[] data;

    double xCross, yCross, zCross;

    public ArrayVectorD(int n) {
        super(n);
        data = new Double[n];
        for (int i = 0; i < n; i++) {
            data[i] = 0D;
        }
    }

    public ArrayVectorD(int n, boolean mutable) {
        super(n, mutable);
        data = new Double[n];
        for (int i = 0; i < n; i++) {
            data[i] = 0D;
        }
    }

    public ArrayVectorD(Number[] data) {
        super(data);
    }

    public ArrayVectorD(Number[] data, boolean mutable) {
        super(data, mutable);
    }

    protected void initData(Number[] data) {
        n = data.length;
        if (data instanceof Double[]) {
            this.data = (Double[]) data;
        } else {
            for (int i = 0; i < n; i++) {
                this.data[i] = data[i].doubleValue();
            }
        }
    }

    protected void initData(int i, Number data) {
        if (data instanceof Double) {
            this.data[i] = (Double) data;
        } else {
            this.data[i] = data.doubleValue();
        }
    }

    @Override
    public final Double[] getData() {
        if (mutate) {
            return data;
        } else {
            Double[] output = new Double[n];
            System.arraycopy(this.data, 0, output, 0, n);
            return output;
        }
    }

    @Override
    public Double getData(int i) {
        if (!(i < n)) {
            throw new IndexOutOfBoundsException(String.format("i value of %s is not < then n of %s", i, n));
        }
        if (mutate) {
            return data[i];
        } else {
            Double output = new Double(data[i]);
            return output;
        }
    }

    @Override
    public void setData(Number[] data) {
        n = data.length;
        if (data instanceof Double[]) {
            this.data = (Double[]) data;
        } else {
            for (int i = 0; i < n; i++) {
                this.data[i] = data[i].doubleValue();

            }
        }
    }

    @Override
    public void setData(int i, Number data) {
        if (data instanceof Double) {
            this.data[i] = (Double) data;
        } else {
            this.data[i] = data.doubleValue();
        }
    }

    @Override
    public final VectorD negative() {
        ArrayVectorD c;
        if(mutate) {
            c = this;
        } else {
            c = new ArrayVectorD(n);
        }
        for (int i = 0; i < n; i++) {
            c.data[i] = data[i] * -1;
        }
        return c;
    }

    @Override
    public final VectorD normalise() {
        ArrayVectorD c;
        if(mutate) {
            c = this;
        } else {
            c = new ArrayVectorD(n);
        }
        double m = magnitude();
        if (m == 0) {
            return VectorD.zero();
        }
        for (int i = 0; i < n; i++) {
            c.data[i] = data[i] / m;
        }
        return c;
    }

    @Override
    public final VectorD add(Vector b) {
        ArrayVectorD c;
        if(mutate) {
            c = this;
        } else {
            c = new ArrayVectorD(n);
        }
        if (c.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        for (int i = 0; i < n; i++) {
            c.setData(i, getData(i) + b.getData(i).doubleValue());
        }
        return c;
    }

    @Override
    public final VectorD subtract(Vector b) {
        ArrayVectorD a;
        if(mutate) {
            a = this;
        } else {
            a = new ArrayVectorD(n);
        }
        if (a.n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        for (int i = 0; i < n; i++) {
            a.setData(i, getData(i) - b.getData(i).doubleValue());
        }
        return a;
    }

    @Override
    public synchronized final VectorD cross(Vector b) {
        ArrayVectorD c;
        if(mutate) {
            c = this;
        } else {
            c = new ArrayVectorD(n);
        }
        if (n != 3 || b.n != 3) {
            throw new RuntimeException("Vector dimensions are not both equal to three.");
        }
        xCross = data[Y] * b.getData(Z).doubleValue() - data[Z] * b.getData(Y).doubleValue();
        yCross = data[Z] * b.getData(X).doubleValue() - data[X] * b.getData(Z).doubleValue();
        zCross = data[X] * b.getData(Y).doubleValue() - data[Y] * b.getData(X).doubleValue();
        c.data[X] = xCross;
        c.data[Y] = yCross;
        c.data[Z] = zCross;
        return c;
    }

    @Override
    public final VectorD addScalar(Number v) {
        ArrayVectorD c;
        if(mutate) {
            c = this;
        } else {
            c = new ArrayVectorD(n);
        }
        for (int i = 0; i < n; i++) {
            c.data[i] = data[i] + v.doubleValue();
        }
        return c;
    }

    @Override
    public final VectorD subtractScalar(Number v) {
        ArrayVectorD c;
        if(mutate) {
            c = this;
        } else {
            c = new ArrayVectorD(n);
        }
        for (int i = 0; i < n; i++) {
            c.data[i] = data[i] - v.doubleValue();
        }
        return c;
    }

    @Override
    public final VectorD multiplyScalar(Number v) {
        ArrayVectorD c;
        if(mutate) {
            c = this;
        } else {
            c = new ArrayVectorD(n);
        }
        for (int i = 0; i < n; i++) {
            c.data[i] = data[i] * v.doubleValue();
        }
        return c;
    }

    @Override
    public final VectorD divideScalar(Number v) {
        if (v.intValue() == 0) {
            throw new RuntimeException("Divide By Zero.");
        }
        ArrayVectorD c;
        if(mutate) {
            c = this;
        } else {
            c = new ArrayVectorD(n);
        }
        for (int i = 0; i < n; i++) {
            c.data[i] = data[i] / v.doubleValue();
        }
        return c;
    }

    @Override
    public Vector negative(boolean mutate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector normalise(boolean mutate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector add(Vector b, boolean mutate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector subtract(Vector b, boolean mutate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector addScalar(Number v, boolean mutate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector subtractScalar(Number v, boolean mutate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector multiplyScalar(Number v, boolean mutate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector divideScalar(Number v, boolean mutate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
