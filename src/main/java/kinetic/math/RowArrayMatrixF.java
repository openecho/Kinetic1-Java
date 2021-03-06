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
 * @version 2.0.0
 */
public class RowArrayMatrixF extends MatrixF {

    Float[][] data;

    public RowArrayMatrixF(int m, int n) {
        super(m, n);
        data = new Float[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                data[i][j] = 0F;
            }
        }
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
    public final Float[][] getData() {
        if (mutate) {
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
        if (!(i < m)) {
            throw new IndexOutOfBoundsException(String.format("i value of %s is not < then m of %s", i, m));
        }
        if (!(j < n)) {
            throw new IndexOutOfBoundsException(String.format("j value of %s is not < then n of %s", j, n));
        }
        if (mutate) {
            return data[i][j];
        } else {
            Float output = new Float(data[i][j]);
            return output;
        }
    }

    @Override
    public final void setData(int i, int j, Number data) {
        if (data instanceof Float) {
            this.data[i][j] = (Float) data;
        } else {
            this.data[i][j] = data.floatValue();
        }
    }

    @Override
    public final void setData(Number[][] data) {
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
    public final RowArrayMatrixF add(Matrix b) {
        return add(b, mutate);
    }

    @Override
    public final RowArrayMatrixF add(Matrix b, boolean mutate) {
        RowArrayMatrixF a = this;
        if (a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        RowArrayMatrixF c;
        if (mutate) {
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
    public final RowArrayMatrixF subtract(Matrix b) {
        return subtract(b, mutate);
    }

    @Override
    public final RowArrayMatrixF subtract(Matrix b, boolean mutate) {
        RowArrayMatrixF a = this;
        if (a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        RowArrayMatrixF c;
        if (mutate) {
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
    public RowArrayMatrixF multiply(Matrix b) {
        RowArrayMatrixF a = this;
        if (a.n != b.m) {
            throw new RuntimeException("Matrix dimensions are incorrect.");
        }
        RowArrayMatrixF c = new RowArrayMatrixF(a.m, b.n);
        for (int i = 0; i < c.m; i++) {
            for (int j = 0; j < c.n; j++) {
                for (int k = 0; k < a.n; k++) {
                    c.data[i][j] += (float) (a.data[i][k] * b.getData(k, j).doubleValue());
                }
            }
        }
        return c;
    }

    @Override
    public RowArrayMatrixF multiply(Matrix b, boolean mutate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RowArrayMatrixF transpose() {
        if (mutate) {
            /**
             * TODO: Transpose!
             */
            RowArrayMatrixF a = this;
            RowArrayMatrixF t = new RowArrayMatrixF(a.n, a.m);
            for (int i = 0; i < a.m; i++) {
                for (int j = 0; j < a.n; j++) {
                    t.data[j][i] = a.data[i][j];
                }
            }
            return t;
        } else {
            RowArrayMatrixF a = this;
            RowArrayMatrixF t = new RowArrayMatrixF(a.n, a.m);
            for (int i = 0; i < a.m; i++) {
                for (int j = 0; j < a.n; j++) {
                    t.data[j][i] = a.data[i][j];
                }
            }
            return t;
        }
    }

    @Override
    public final RowArrayMatrixF addScalar(Number v) {
        return addScalar(v, mutate);
    }

    @Override
    public final RowArrayMatrixF addScalar(Number v, boolean mutate) {
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
    public final RowArrayMatrixF subtractScalar(Number v) {
        return subtractScalar(v, mutate);
    }

    @Override
    public final RowArrayMatrixF subtractScalar(Number v, boolean mutate) {
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
    public final RowArrayMatrixF multiplyScalar(Number v) {
        return multiplyScalar(v, mutate);
    }

    @Override
    public final RowArrayMatrixF multiplyScalar(Number v, boolean mutate) {
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
    public final RowArrayMatrixF divideScalar(Number v) {
        return divideScalar(v, mutate);
    }

    @Override
    public final RowArrayMatrixF divideScalar(Number v, boolean mutate) {
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

    @Override
    public RowArrayMatrixF invert() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF solve(Matrix b) {
        return super.solve(b);
    }

    @Override
    public VectorF transformVector(VectorF v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
