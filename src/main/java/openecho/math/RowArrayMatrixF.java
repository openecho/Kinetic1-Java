/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.math;

/**
 *
 * @author jmarsden
 */
public class RowArrayMatrixF extends MatrixF {

    Float[][] data;

    public RowArrayMatrixF(int m, int n) {
        super(m,n);
    }

    public RowArrayMatrixF(int m, int n, boolean mutable) {
        super(m, n, mutable);
        data = new Float[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                data[i][j] = 0F;
            }
        }
    }

    public RowArrayMatrixF(Float[][] data) {
        super(data);
    }

    public RowArrayMatrixF(Float[][] data, boolean mutable) {
        super(data, mutable);
    }

    @Override
    protected final void initData(Number[][] data) {
        m = data.length;
        n = data[0].length;
        if (data instanceof Double[][]) {
            this.data = (Float[][]) data;
        } else {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    this.data[i][j] = data[i][j].floatValue();
                }
            }
        }
    }

    @Override
    protected final void initData(int i, int j, Number data) {
        if (data instanceof Float) {
            this.data[i][j] = (Float) data;
        } else {
            this.data[i][j] = data.floatValue();
        }
    }

    @Override
    public final Float[][] getData() {
        if (mutable) {
            return data;
        } else {
            Float[][] output = new Float[m][n];
            for (int i = 0; i < m; i++) {
                System.arraycopy(this.data[i], 0, output[i], 0, n);
            }
            return output;
        }
    }

    @Override
    public final Float getData(int i, int j) {
        if(!(i < m)) {
            throw new IndexOutOfBoundsException(String.format("i value of %s is not < then m of %s", i, m));
        }
        if(!(j < n)) {
            throw new IndexOutOfBoundsException(String.format("j value of %s is not < then n of %s", j, n));
        }
        if (mutable) {
            return data[i][j];
        } else {
            Float output = new Float(data[i][j]);
            return output;
        }
    }

    @Override
    public final void setData(int i, int j, Number data) {
        if (mutable) {
            if (data instanceof Float) {
                this.data[i][j] = (Float) data;
            } else {
                this.data[i][j] = data.floatValue();
            }
        } else {
            throw new UnsupportedOperationException("Cannot Set Data on an ImmutableMatrix.");
        }
    }

    @Override
    public final void setData(Number[][] data) {
        if (mutable) {
            m = data.length;
            n = data[0].length;
            if (data instanceof Float[][]) {
                this.data = (Float[][]) data;
            } else {
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        this.data[i][j] = data[i][j].floatValue();
                    }
                }
            }
        } else {
            throw new UnsupportedOperationException("Cannot Set Data on an ImmutableMatrix.");
        }
    }

    @Override
    final void setMutable(boolean mutable) {
        this.mutable = mutable;
    }

    @Override
    public final boolean isMutable() {
        return mutable;
    }

    @Override
    public final Float[] getRow(int i) {
        if (i >= m) {
            throw new IndexOutOfBoundsException();
        }
        Float[] output = new Float[n];
        System.arraycopy(this.data[i], 0, output, 0, n);
        return output;
    }

    @Override
    public final MatrixF add(Matrix b) {
        RowArrayMatrixF a = this;
        if (a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        RowArrayMatrixF c;
        if (mutable) {
            c = this;
        } else {
            c = new RowArrayMatrixF(m, n);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] + b.getData(i, j).floatValue();
            }
        }
        return c;
    }

    @Override
    public final MatrixF subtract(Matrix b) {
        RowArrayMatrixF a = this;
        if (a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        RowArrayMatrixF c;
        if (mutable) {
            c = this;
        } else {
            c = new RowArrayMatrixF(m, n);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] - b.getData(i, j).floatValue();
            }
        }
        return c;
    }

    @Override
    public MatrixF multiply(Matrix b) {
        RowArrayMatrixF a = this;
        if (a.n != b.m) {
            throw new RuntimeException("Matrix dimensions are not incorrect.");
        }
        RowArrayMatrixF c = new RowArrayMatrixF(a.m, b.n);
        for(int i=0;i<c.m;i++) {
            for(int j=0;j<c.n;j++) {
                for(int k=0;k<a.n;k++) {
                    c.data[i][j] += (a.data[i][k]*b.getData(k, j).floatValue());
                }
            }
        }
        return c;
    }

    @Override
    public MatrixF transpose() {
        RowArrayMatrixF a = this;
        RowArrayMatrixF t = new RowArrayMatrixF(a.n, a.m);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                t.data[j][i] = a.data[i][j];
            }
        }
        return t;
    }

    @Override
    public MatrixF addScalar(Number v) {
        RowArrayMatrixF a = this;
        RowArrayMatrixF c = new RowArrayMatrixF(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] + v.floatValue();
            }
        }
        return c;
    }

    @Override
    public MatrixF subtractScalar(Number v) {
        RowArrayMatrixF a = this;
        RowArrayMatrixF c = new RowArrayMatrixF(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] - v.floatValue();
            }
        }
        return c;
    }

    @Override
    public MatrixF multiplyScalar(Number v) {
        RowArrayMatrixF a = this;
        RowArrayMatrixF c = new RowArrayMatrixF(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] * v.floatValue();
            }
        }
        return c;
    }

    @Override
    public MatrixF divideScalar(Number v) {
        if (v.doubleValue() == 0) {
            throw new RuntimeException("Divide by Zero");
        }
        RowArrayMatrixF a = this;
        RowArrayMatrixF c = new RowArrayMatrixF(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] / v.floatValue();
            }
        }
        return c;
    }
}
