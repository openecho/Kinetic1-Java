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
public class Matrix4F extends MatrixF {

    float m00, m01, m02, m03,
            m10, m11, m12, m13,
            m20, m21, m22, m23,
            m30, m31, m32, m33;

    public Matrix4F(float m00, float m01, float m02, float m03,
            float m10, float m11, float m12, float m13,
            float m20, float m21, float m22, float m23,
            float m30, float m31, float m32, float m33) {
        super(3, 3, false);
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    public Matrix4F(Float[][] data) {
        this(data, false);
    }

    public Matrix4F(Float[][] data, boolean mutable) {
        super(data, mutable);
        m = data.length;
        if (m > 0) {
            n = data[0].length;
        } else {
            throw new IllegalArgumentException("data dimensions must be > 0");
        }
        if (m != 4 || n != 4) {
            throw new IllegalArgumentException("data dimensions must be = 3");
        }
        m00 = data[0][0];
        m01 = data[0][1];
        m02 = data[0][2];
        m03 = data[0][3];
        m10 = data[1][0];
        m11 = data[1][1];
        m12 = data[1][2];
        m13 = data[1][3];
        m20 = data[2][0];
        m21 = data[2][1];
        m22 = data[2][2];
        m23 = data[2][3];
        m30 = data[3][0];
        m31 = data[3][1];
        m32 = data[3][2];
        m33 = data[3][3];
    }

    public Matrix4F() {
        this(true);
    }

    public Matrix4F(boolean mutable) {
        super(4, 4, mutable);
    }

    @Override
    protected void initData(Number[][] data) {
        m = data.length;
        if (m > 0) {
            n = data[0].length;
        } else {
            throw new IllegalArgumentException("data dimensions must be > 0");
        }
        if (m != 4 || n != 4) {
            throw new IllegalArgumentException("data dimensions must be = 3");
        }
        m00 = data[0][0].floatValue();
        m01 = data[0][1].floatValue();
        m02 = data[0][2].floatValue();
        m03 = data[0][3].floatValue();
        m10 = data[1][0].floatValue();
        m11 = data[1][1].floatValue();
        m12 = data[1][2].floatValue();
        m13 = data[1][3].floatValue();
        m20 = data[2][0].floatValue();
        m21 = data[2][1].floatValue();
        m22 = data[2][2].floatValue();
        m23 = data[2][3].floatValue();
        m30 = data[3][0].floatValue();
        m31 = data[3][1].floatValue();
        m32 = data[3][2].floatValue();
        m33 = data[3][3].floatValue();
    }

    @Override
    protected void initData(int i, int j, Number data) {
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        m00 = data.floatValue();
                    case 1:
                        m01 = data.floatValue();
                    case 2:
                        m02 = data.floatValue();
                    case 3:
                        m03 = data.floatValue();
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            case 1:
                switch (j) {
                    case 0:
                        m10 = data.floatValue();
                    case 1:
                        m11 = data.floatValue();
                    case 2:
                        m12 = data.floatValue();
                    case 3:
                        m13 = data.floatValue();
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            case 2:
                switch (j) {
                    case 0:
                        m20 = data.floatValue();
                    case 1:
                        m21 = data.floatValue();
                    case 2:
                        m22 = data.floatValue();
                    case 3:
                        m23 = data.floatValue();
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            case 3:
                switch (j) {
                    case 0:
                        m30 = data.floatValue();
                    case 1:
                        m31 = data.floatValue();
                    case 2:
                        m32 = data.floatValue();
                    case 3:
                        m33 = data.floatValue();
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            default:
                throw new IllegalArgumentException("i must be < 4");
        }
    }

    @Override
    public Float[][] getData() {
        return new Float[][]{{m00, m01, m02, m03}, {m10, m11, m12, m13},
                    {m20, m21, m22, m23}, {m30, m31, m32, m33}};
    }

    @Override
    public Float getData(int i, int j) {
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        return m00;
                    case 1:
                        return m01;
                    case 2:
                        return m02;
                    case 3:
                        return m03;
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            case 1:
                switch (j) {
                    case 0:
                        return m10;
                    case 1:
                        return m11;
                    case 2:
                        return m12;
                    case 3:
                        return m13;
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            case 2:
                switch (j) {
                    case 0:
                        return m20;
                    case 1:
                        return m21;
                    case 2:
                        return m22;
                    case 3:
                        return m23;
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            case 3:
                switch (j) {
                    case 0:
                        return m30;
                    case 1:
                        return m31;
                    case 2:
                        return m32;
                    case 3:
                        return m33;
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            default:
                throw new IllegalArgumentException("i must be < 4");
        }
    }

    @Override
    public void setData(Number[][] data) {
        if(!mutate) {
            throw new UnsupportedOperationException("Matrix not mutable");
        }
        m = data.length;
        if (m > 0) {
            n = data[0].length;
        } else {
            throw new IllegalArgumentException("data dimensions must be > 0");
        }
        if (m != 4 || n != 4) {
            throw new IllegalArgumentException("data dimensions must be = 3");
        }
        m00 = data[0][0].floatValue();
        m01 = data[0][1].floatValue();
        m02 = data[0][2].floatValue();
        m03 = data[0][3].floatValue();
        m10 = data[1][0].floatValue();
        m11 = data[1][1].floatValue();
        m12 = data[1][2].floatValue();
        m13 = data[1][3].floatValue();
        m20 = data[2][0].floatValue();
        m21 = data[2][1].floatValue();
        m22 = data[2][2].floatValue();
        m23 = data[2][3].floatValue();
        m30 = data[3][0].floatValue();
        m31 = data[3][1].floatValue();
        m32 = data[3][2].floatValue();
        m33 = data[3][3].floatValue();
    }

    @Override
    public void setData(int i, int j, Number data) {
        if(!mutate) {
            throw new UnsupportedOperationException("Matrix not mutable");
        }
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        m00 = data.floatValue();
                    case 1:
                        m01 = data.floatValue();
                    case 2:
                        m02 = data.floatValue();
                    case 3:
                        m03 = data.floatValue();
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            case 1:
                switch (j) {
                    case 0:
                        m10 = data.floatValue();
                    case 1:
                        m11 = data.floatValue();
                    case 2:
                        m12 = data.floatValue();
                    case 3:
                        m13 = data.floatValue();
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            case 2:
                switch (j) {
                    case 0:
                        m20 = data.floatValue();
                    case 1:
                        m21 = data.floatValue();
                    case 2:
                        m22 = data.floatValue();
                    case 3:
                        m23 = data.floatValue();
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            case 3:
                switch (j) {
                    case 0:
                        m30 = data.floatValue();
                    case 1:
                        m31 = data.floatValue();
                    case 2:
                        m32 = data.floatValue();
                    case 3:
                        m33 = data.floatValue();
                    default:
                        throw new IllegalArgumentException("j must be < 4");
                }
            default:
                throw new IllegalArgumentException("i must be < 4");
        }
    }

    @Override
    public Float[] getRow(int j) {
        if (j < 0 || j >= m) {
            throw new IndexOutOfBoundsException();
        }
        Float[] result = new Float[n];
        for (int i = 0; i < n; i++) {
            result[i] = getData(j, i);
        }
        return result;
    }

    @Override
    public final Matrix4F add(Matrix b) {
        if (m != b.m || n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        if (mutate) {
            m00 += b.getData(0, 0).floatValue();
            m01 += b.getData(0, 1).floatValue();
            m02 += b.getData(0, 2).floatValue();
            m03 += b.getData(0, 3).floatValue();
            m10 += b.getData(1, 0).floatValue();
            m11 += b.getData(1, 1).floatValue();
            m12 += b.getData(1, 2).floatValue();
            m13 += b.getData(1, 3).floatValue();
            m20 += b.getData(2, 0).floatValue();
            m21 += b.getData(2, 1).floatValue();
            m22 += b.getData(2, 2).floatValue();
            m23 += b.getData(2, 3).floatValue();
            m30 += b.getData(3, 0).floatValue();
            m31 += b.getData(3, 1).floatValue();
            m32 += b.getData(3, 2).floatValue();
            m33 += b.getData(3, 3).floatValue();
            return this;
        } else {
            return new Matrix4F(m00 + b.getData(0, 0).floatValue(),
                    m01 + b.getData(0, 1).floatValue(),
                    m02 + b.getData(0, 2).floatValue(),
                    m03 + b.getData(0, 3).floatValue(),
                    m10 + b.getData(1, 0).floatValue(),
                    m11 + b.getData(1, 1).floatValue(),
                    m12 + b.getData(1, 2).floatValue(),
                    m13 + b.getData(1, 3).floatValue(),
                    m20 + b.getData(2, 0).floatValue(),
                    m21 + b.getData(2, 1).floatValue(),
                    m22 + b.getData(2, 2).floatValue(),
                    m23 + b.getData(2, 3).floatValue(),
                    m30 + b.getData(3, 0).floatValue(),
                    m31 + b.getData(3, 1).floatValue(),
                    m32 + b.getData(3, 2).floatValue(),
                    m33 + b.getData(3, 3).floatValue());
        }
    }

    public final Matrix4F add4F(Matrix4F b) {
        if (mutate) {
            m00 += b.m00;
            m01 += b.m01;
            m02 += b.m02;
            m03 += b.m03;
            m10 += b.m10;
            m11 += b.m11;
            m12 += b.m12;
            m13 += b.m13;
            m20 += b.m20;
            m21 += b.m21;
            m22 += b.m22;
            m23 += b.m23;
            m30 += b.m30;
            m31 += b.m31;
            m32 += b.m32;
            m33 += b.m33;
            return this;
        } else {
            return new Matrix4F(m00 + b.m00, m01 + b.m01, m02 + b.m02, m03 + b.m03,
                    m10 + b.m10, m11 + b.m11, m12 + b.m12, m13 + b.m13,
                    m20 + b.m20, m21 + b.m21, m22 + b.m22, m23 + b.m23,
                    m30 + b.m30, m31 + b.m31, m32 + b.m32, m33 + b.m33);
        }
    }

    @Override
    public final Matrix4F subtract(Matrix b) {
        if (m != b.m || n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        if (mutate) {
            m00 -= b.getData(0, 0).floatValue();
            m01 -= b.getData(0, 1).floatValue();
            m02 -= b.getData(0, 2).floatValue();
            m03 -= b.getData(0, 3).floatValue();
            m10 -= b.getData(1, 0).floatValue();
            m11 -= b.getData(1, 1).floatValue();
            m12 -= b.getData(1, 2).floatValue();
            m13 -= b.getData(1, 3).floatValue();
            m20 -= b.getData(2, 0).floatValue();
            m21 -= b.getData(2, 1).floatValue();
            m22 -= b.getData(2, 2).floatValue();
            m23 -= b.getData(2, 3).floatValue();
            m30 -= b.getData(3, 0).floatValue();
            m31 -= b.getData(3, 1).floatValue();
            m32 -= b.getData(3, 2).floatValue();
            m33 -= b.getData(3, 3).floatValue();
            return this;
        } else {
            return new Matrix4F(m00 - b.getData(0, 0).floatValue(),
                    m01 - b.getData(0, 1).floatValue(),
                    m02 - b.getData(0, 2).floatValue(),
                    m03 - b.getData(0, 3).floatValue(),
                    m10 - b.getData(1, 0).floatValue(),
                    m11 - b.getData(1, 1).floatValue(),
                    m12 - b.getData(1, 2).floatValue(),
                    m13 - b.getData(1, 3).floatValue(),
                    m20 - b.getData(2, 0).floatValue(),
                    m21 - b.getData(2, 1).floatValue(),
                    m22 - b.getData(2, 2).floatValue(),
                    m23 - b.getData(2, 3).floatValue(),
                    m30 - b.getData(3, 0).floatValue(),
                    m31 - b.getData(3, 1).floatValue(),
                    m32 - b.getData(3, 2).floatValue(),
                    m33 - b.getData(3, 3).floatValue());
        }
    }

    public final Matrix4F subtract4F(Matrix4F b) {
        if (mutate) {
            m00 -= b.m00;
            m01 -= b.m01;
            m02 -= b.m02;
            m03 -= b.m03;
            m10 -= b.m10;
            m11 -= b.m11;
            m12 -= b.m12;
            m13 -= b.m13;
            m20 -= b.m20;
            m21 -= b.m21;
            m22 -= b.m22;
            m23 -= b.m23;
            m30 -= b.m30;
            m31 -= b.m31;
            m32 -= b.m32;
            m33 -= b.m33;
            return this;
        } else {
            return new Matrix4F(m00 - b.m00, m01 - b.m01, m02 - b.m02, m03 - b.m03,
                    m10 - b.m10, m11 - b.m11, m12 - b.m12, m13 - b.m13,
                    m20 - b.m20, m21 - b.m21, m22 - b.m22, m23 - b.m23,
                    m30 - b.m30, m31 - b.m31, m32 - b.m32, m33 - b.m33);
        }
    }

    @Override
    public MatrixF multiply(Matrix b) {
        /**
         * TODO: Optimise and move to MatrixF
         */
        if (n != b.m) {
            throw new RuntimeException("Matrix dimensions are incorrect.");
        }
        RowArrayMatrixF c = new RowArrayMatrixF(m, b.n);
        for (int i = 0; i < c.m; i++) {
            for (int j = 0; j < c.n; j++) {
                for (int k = 0; k < n; k++) {
                    c.data[i][j] += (getData(i, j) * b.getData(k, j).floatValue());
                }
            }
        }
        return c;
    }

    public Matrix4F multiply3F(Matrix4F b) {
        /**
         * TODO: Optimise
         */
        if (n != b.m) {
            throw new RuntimeException("Matrix dimensions are incorrect.");
        }
        Matrix4F c = new Matrix4F();
        for (int i = 0; i < c.m; i++) {
            for (int j = 0; j < c.n; j++) {
                for (int k = 0; k < n; k++) {
                    c.setData(i, j, getData(i, j) * b.getData(k, j).floatValue());
                }
            }
        }
        return c;
    }

    @Override
    public Matrix4F transpose() {
        if (mutate) {
            /**
             * TODO: Figure out transpose.
             */
            Matrix4F t = new Matrix4F();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    t.setData(j, i, getData(i, j));
                }
            }
            return t;
        } else {
            Matrix4F t = new Matrix4F();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    t.setData(j, i, getData(i, j));
                }
            }
            return t;
        }
    }

    @Override
    public Matrix4F addScalar(Number v) {
        return addScalar4F(v.floatValue());
    }

    public final Matrix4F addScalar4F(float f) {
        if (mutate) {
            m00 = m00 + f;
            m01 = m01 + f;
            m02 = m02 + f;
            m03 = m03 + f;
            m10 = m10 + f;
            m11 = m11 + f;
            m12 = m12 + f;
            m13 = m13 + f;
            m20 = m20 + f;
            m21 = m21 + f;
            m22 = m22 + f;
            m23 = m23 + f;
            m30 = m30 + f;
            m31 = m31 + f;
            m32 = m32 + f;
            m33 = m33 + f;
            return this;
        } else {
            return new Matrix4F(m00 + f, m01 + f, m02 + f, m03 + f,
                    m10 + f, m11 + f, m12 + f, m13 + f,
                    m20 + f, m21 + f, m22 + f, m23 + f,
                    m30 + f, m31 + f, m32 + f, m33 + f);
        }
    }

    @Override
    public Matrix4F subtractScalar(Number v) {
        return subtractScalar4F(v.floatValue());
    }

    public final Matrix4F subtractScalar4F(float f) {
        if (mutate) {
            m00 = m00 - f;
            m01 = m01 - f;
            m02 = m02 - f;
            m03 = m03 - f;
            m10 = m10 - f;
            m11 = m11 - f;
            m12 = m12 - f;
            m13 = m13 - f;
            m20 = m20 - f;
            m21 = m21 - f;
            m22 = m22 - f;
            m23 = m23 - f;
            m30 = m30 - f;
            m31 = m31 - f;
            m32 = m32 - f;
            m33 = m33 - f;
            return this;
        } else {
            return new Matrix4F(m00 - f, m01 - f, m02 - f, m03 - f,
                    m10 - f, m11 - f, m12 - f, m13 - f,
                    m20 - f, m21 - f, m22 - f, m23 - f,
                    m30 - f, m31 - f, m32 - f, m33 - f);
        }
    }

    @Override
    public Matrix4F multiplyScalar(Number v) {
        return multiplyScalar4F(v.floatValue());
    }

    public final Matrix4F multiplyScalar4F(float f) {
        if (mutate) {
            m00 = m00 * f;
            m01 = m01 * f;
            m02 = m02 * f;
            m03 = m03 * f;
            m10 = m10 * f;
            m11 = m11 * f;
            m12 = m12 * f;
            m13 = m13 * f;
            m20 = m20 * f;
            m21 = m21 * f;
            m22 = m22 * f;
            m23 = m23 * f;
            m30 = m30 * f;
            m31 = m31 * f;
            m32 = m32 * f;
            m33 = m33 * f;
            return this;
        } else {
            return new Matrix4F(m00 * f, m01 * f, m02 * f, m03 * f,
                    m10 * f, m11 * f, m12 * f, m13 * f,
                    m20 * f, m21 * f, m22 * f, m23 * f,
                    m30 * f, m31 * f, m32 * f, m33 * f);
        }
    }

    @Override
    public Matrix4F divideScalar(Number v) {
        return divideScalar4F(v.floatValue());
    }

    public final Matrix4F divideScalar4F(float f) {
        if (f == 0F) {
            throw new RuntimeException("Divide By Zero.");
        }
        if (mutate) {
            m00 = m00 / f;
            m01 = m01 / f;
            m02 = m02 / f;
            m03 = m03 / f;
            m10 = m10 / f;
            m11 = m11 / f;
            m12 = m12 / f;
            m13 = m13 / f;
            m20 = m20 / f;
            m21 = m21 / f;
            m22 = m22 / f;
            m23 = m23 / f;
            m30 = m30 / f;
            m31 = m31 / f;
            m32 = m32 / f;
            m33 = m33 / f;
            return this;
        } else {
            return new Matrix4F(m00 / f, m01 / f, m02 / f, m03 / f,
                    m10 / f, m11 / f, m12 / f, m13 / f,
                    m20 / f, m21 / f, m22 / f, m23 / f,
                    m30 / f, m31 / f, m32 / f, m33 / f);
        }
    }

    /**
     * Creates an identity n by n Matrix4F
     * @return Matrix4F constructed identity Matrix4F.
     */
    public static Matrix4F identity() {
        Matrix4F i = new Matrix4F(1F,0,0,0,0,1F,0,0,0,0,1F,0,0,0,0,1F);
        return i;
    }
}
