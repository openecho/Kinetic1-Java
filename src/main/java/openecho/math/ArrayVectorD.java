/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package openecho.math;

/**
 *
 * @author jmarsden
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
        if (mutable) {
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
        if (mutable) {
            return data[i];
        } else {
            Double output = new Double(data[i]);
            return output;
        }
    }

    @Override
    public void setData(Number[] data) {
        if (mutable) {
            n = data.length;
            if (data instanceof Double[]) {
                this.data = (Double[]) data;
            } else {
                for (int i = 0; i < n; i++) {
                    this.data[i] = data[i].doubleValue();

                }
            }
        } else {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
    }

    @Override
    public void setData(int i, Number data) {
        if (mutable) {
            if (data instanceof Double) {
                this.data[i] = (Double) data;
            } else {
                this.data[i] = data.doubleValue();
            }
        } else {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
    }

    @Override
    public final VectorD negative() {
        ArrayVectorD c;
        if(mutable) {
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
        if(mutable) {
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
        if(mutable) {
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
        if(mutable) {
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
        if(mutable) {
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
        if(mutable) {
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
        if(mutable) {
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
        if(mutable) {
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
        if(mutable) {
            c = this;
        } else {
            c = new ArrayVectorD(n);
        }
        for (int i = 0; i < n; i++) {
            c.data[i] = data[i] / v.doubleValue();
        }
        return c;
    }
}
