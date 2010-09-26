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
 * RowArrayMatrixD Implementation.
 *
 * Holds an m-by-n matrix in a Double[m][n] (row based) where,
 *
 * <pre>
 *         n1  n2  n3 ...  nN
 *      +---------------------
 *   m1 |[a11 a12 a13 ... aN1] = data[0][n]
 *   m2 |[a21 a22 a23 ... aN1] = data[1][n]
 *   m3 |[a31 a32 a33 ... aN1] = data[2][n]
 *   .. |[... ... ...     ...]
 *   mN |[aM1 aM2 aM3 ... aMN] = data[m][n]
 * </pre>
 *
 * @author openecho
 * @version 1.0.0
 */
public class RowArrayMatrixD extends MatrixD {

    Double[][] data;

    public RowArrayMatrixD(int m, int n) {
        super(m, n);
        data = new Double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                data[i][j] = 0D;
            }
        }
    }

    public RowArrayMatrixD(int m, int n, boolean mutable) {
        super(m, n, mutable);
        data = new Double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                data[i][j] = 0D;
            }
        }
    }

    public RowArrayMatrixD(Double[][] data) {
        super(data);
    }

    public RowArrayMatrixD(Double[][] data, boolean mutable) {
        super(data, mutable);
    }

    @Override
    protected final void initData(Number[][] data) {
        m = data.length;
        n = data[0].length;
        if (data instanceof Double[][]) {
            this.data = (Double[][]) data;
        } else {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    this.data[i][j] = data[i][j].doubleValue();
                }
            }
        }
    }

    @Override
    protected final void initData(int i, int j, Number data) {
        if (data instanceof Double) {
            this.data[i][j] = (Double) data;
        } else {
            this.data[i][j] = data.doubleValue();
        }
    }

    @Override
    public final Double[][] getData() {
        if (mutate) {
            return data;
        } else {
            Double[][] output = new Double[m][n];
            for (int i = 0; i < m; i++) {
                System.arraycopy(this.data[i], 0, output[i], 0, n);
            }
            return output;
        }
    }

    @Override
    public final Double getData(int i, int j) {
        if (!(i < m)) {
            throw new IndexOutOfBoundsException(String.format("i value of %s is not < then m of %s", i, m));
        }
        if (!(j < n)) {
            throw new IndexOutOfBoundsException(String.format("j value of %s is not < then n of %s", j, n));
        }
        if (mutate) {
            return data[i][j];
        } else {
            Double output = new Double(data[i][j]);
            return output;
        }
    }

    @Override
    public final void setData(int i, int j, Number data) {
        if (data instanceof Double) {
            this.data[i][j] = (Double) data;
        } else {
            this.data[i][j] = data.doubleValue();
        }
    }

    @Override
    public final void setData(Number[][] data) {
        m = data.length;
        n = data[0].length;
        if (data instanceof Double[][]) {
            this.data = (Double[][]) data;
        } else {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    this.data[i][j] = data[i][j].doubleValue();
                }
            }
        }
    }

    @Override
    final void setMutate(boolean mutable) {
        this.mutate = mutable;
    }

    @Override
    public final boolean willMutate() {
        return mutate;
    }

    @Override
    public final Double[] getRow(int i) {
        if (i >= m) {
            throw new IndexOutOfBoundsException();
        }
        Double[] output = new Double[n];
        System.arraycopy(this.data[i], 0, output, 0, n);
        return output;
    }

    @Override
    public final MatrixD add(Matrix b) {
        RowArrayMatrixD a = this;
        if (a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        RowArrayMatrixD c;
        if (mutate) {
            c = this;
        } else {
            c = new RowArrayMatrixD(m, n);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] + b.getData(i, j).doubleValue();
            }
        }
        return c;
    }

    @Override
    public final MatrixD subtract(Matrix b) {
        RowArrayMatrixD a = this;
        if (a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        RowArrayMatrixD c;
        if (mutate) {
            c = this;
        } else {
            c = new RowArrayMatrixD(m, n);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] - b.getData(i, j).doubleValue();
            }
        }
        return c;
    }

    @Override
    public MatrixD multiply(Matrix b) {
        RowArrayMatrixD a = this;
        if (a.n != b.m) {
            throw new RuntimeException("Matrix dimensions are incorrect.");
        }
        RowArrayMatrixD c = new RowArrayMatrixD(a.m, b.n);
        for (int i = 0; i < c.m; i++) {
            for (int j = 0; j < c.n; j++) {
                for (int k = 0; k < a.n; k++) {
                    c.data[i][j] += (a.data[i][k] * b.getData(k, j).doubleValue());
                }
            }
        }
        return c;
    }

    @Override
    public MatrixD transpose() {
        if (mutate) {
            /**
             * TODO: Figure this out
             */
            RowArrayMatrixD a = this;
            RowArrayMatrixD t = new RowArrayMatrixD(a.n, a.m);
            for (int i = 0; i < a.m; i++) {
                for (int j = 0; j < a.n; j++) {
                    t.data[j][i] = a.data[i][j];
                }
            }
            return t;
        } else {
            RowArrayMatrixD a = this;
            RowArrayMatrixD t = new RowArrayMatrixD(a.n, a.m);
            for (int i = 0; i < a.m; i++) {
                for (int j = 0; j < a.n; j++) {
                    t.data[j][i] = a.data[i][j];
                }
            }
            return t;
        }
    }

    @Override
    public MatrixD addScalar(Number v) {
        RowArrayMatrixD a = this;
        RowArrayMatrixD c = new RowArrayMatrixD(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] + v.doubleValue();
            }
        }
        return c;
    }

    @Override
    public MatrixD subtractScalar(Number v) {
        RowArrayMatrixD a = this;
        RowArrayMatrixD c = new RowArrayMatrixD(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] - v.doubleValue();
            }
        }
        return c;
    }

    @Override
    public MatrixD multiplyScalar(Number v) {
        RowArrayMatrixD a = this;
        RowArrayMatrixD c = new RowArrayMatrixD(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] * v.doubleValue();
            }
        }
        return c;
    }

    @Override
    public MatrixD divideScalar(Number v) {
        if (v.doubleValue() == 0) {
            throw new RuntimeException("Divide by Zero");
        }
        RowArrayMatrixD a = this;
        RowArrayMatrixD c = new RowArrayMatrixD(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = a.data[i][j] / v.doubleValue();
            }
        }
        return c;
    }
}
