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

import openecho.math.random.FastRandom;

/**
 * Abstract m by n Float Matrix in the following form.
 * <pre>
 *         n1  n2  n3 ...  nN
 *      +---------------------
 *   m1 | a11 a12 a13 ... aN1
 *   m2 | a21 a22 a23 ... aN1
 *   m3 | a31 a32 a33 ... aN1
 *   .. | ... ... ...     ...
 *   mN | aM1 aM2 aM3 ... aMN
 * </pre>
 * Provides static methods to construct various Matrix instances
 * that are both mutable and immutable.
 *
 * @author openecho
 * @version 1.0.0
 */
public abstract class MatrixF extends Matrix {

    /**
     * Default constructor to specify the dimensions of the m by n MatrixF
     * @param m rows in the MatrixF.
     * @param n columns in the MatrixF.
     */
    public MatrixF(int m, int n) {
        this(m,n,false);
    }

    public MatrixF(int m, int n, boolean mutable) {
        this.m = m;
        this.n = n;
        this.mutable = mutable;
    }

    /**
     * Constructor to create a MatrixF with the given data set.
     * @param data datum for the matrix.
     */
    public MatrixF(Float[][] data) {
        this(data,false);
    }


    public MatrixF(Float[][] data, boolean mutable) {
        m = data.length;
        n = data[0].length;
        this.mutable = mutable;
        this.initData(data);
    }

    protected abstract void initData(Number[][] data);

    protected abstract void initData(int i, int j, Number data);

    /**
     * Retrieve the data from the MatrixF. This will be unsupported on some
     * implementations.
     * @return Float[][] data from this MatrixF.
     */
    public abstract Float[][] getData();

    public abstract Float getData(int i, int j);

    /**
     * Sets the data into the Matrix. This will be unsupported on some
     * implementations.
     */
    public abstract void setData(Number[][] data);

    public abstract void setData(int i, int j, Number data);

    /**
     * Retrieve the data for a row from the MatrixF.
     * @param i the row to retrieve (0 <= i < m)
     * @return Float[] the row data
     */
    public abstract Float[] getRow(int i);

    /**
     * Retrieve the data for a column from the MatrixF. It is worth noting
     * that this data is never mutable.
     * @param i the row to retrieve (0 <= i < n)
     * @return Float[] the column data
     */
    public Float[] getColumn(int i) {
        if (i >= n) {
            throw new IndexOutOfBoundsException();
        }
        Float[] result = new Float[m];
        for (int j = 0; j < m; j++) {
            result[j] = getData(j,i);
        }
        return result;
    }

  /**
     * Adds a Matrix to this instance. Matrix A + Matrix B = Matrix C.
     * @param b Matrix B.
     * @return Matrix Matrix C.
     */
    public abstract MatrixF add(Matrix b);

    /**
     * Subtracts a Matrix from this instance. Matrix A - Matrix B = Matrix C.
     * @param b Matrix B.
     * @return Matrix Matrix C.
     */
    public abstract MatrixF subtract(Matrix b);

    /**
     * Multiplies a Matrix to this instance. Matrix A * Matrix B = Matrix C.
     * @param b Matrix B.
     * @return Matrix Matrix C.
     */
    public abstract MatrixF multiply(Matrix b);

    /**
     * Returns the transpose of this instance. Matrix A' = transpose(Matrix A)
     * @return Matrix A' which is a transpose of this instance.
     */
    public abstract MatrixF transpose();

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]+v
     * @param v Value to add
     * @return Matrix Matrix C
     */
    public abstract MatrixF addScalar(Number v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]-v
     * @param v Value to subtract
     * @return Matrix Matrix C
     */
    public abstract MatrixF subtractScalar(Number v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]*v
     * @param v Value to multiply
     * @return Matrix Matrix C
     */
    public abstract MatrixF multiplyScalar(Number v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]/v
     * @param v Value to divide
     * @return Matrix Matrix C
     */
    public abstract MatrixF divideScalar(Number v);
    

      /**
     * Calculates the determinant of the MatrixF if it is square (n=m). Currently
     * this supports cases where n < 3.
     * @return double detminant of the MatrixF.
     */
    public Float determinant() {
        if (n != m) {
            throw new RuntimeException("Not a square matrix.");
        }
        Float[][] data = getData();
        if (n == 1) {
            return data[0][0];
        } else if (n == 2) {
            return data[0][0] * data[1][1]
                    - data[0][1] * data[1][0];
        } else if (n == 3) {
            return data[0][0] * data[1][1] * data[2][2]
                    + data[0][1] * data[1][2] * data[2][0]
                    + data[0][2] * data[1][0] * data[2][1]
                    - data[0][0] * data[1][2] * data[2][1]
                    - data[0][1] * data[1][0] * data[2][2]
                    - data[0][2] * data[1][1] * data[2][0];
        }
        throw new UnsupportedOperationException("Not supported yet. n must equal m and 0 < n < 4");
    }

    /**
     * Convert the MatrixF to a String
     * @return String MatrixF as String
     */
    @Override
    public String toString() {
        String dataString = "{";
        for (int i = 0; i < m; i++) {
            dataString += "{";
            for (int j = 0; j < n; j++) {
                dataString += getData(i,j) + ((j < n - 1) ? "," : "");
            }
            dataString += "}" + ((i < m - 1) ? "," : "");
        }
        dataString += "}";
        return String.format("%s %s", super.toString(), dataString);
    }

    /**
     * Create an empty m by n MatrixF. All values are set to zero. Returns a
     * MutableMatrixF instance.
     * @param m row count for the MatrixF.
     * @param n column count for the MatrixF.
     * @return MatrixD the constructed MatrixF.
     */
    public static MatrixF empty(int m, int n) {
        MatrixF e = new RowArrayMatrixF(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                e.setData(i,j,0F);
            }
        }
        return e;
    }

    /**
     * Create a MatrixF for the specified Data. Returns an Immutable MatrixF.
     * @param data datum for the MatrixF.
     * @return MatrixF the constructed MatrixF.
     */
    public static MatrixF create(Float[][] data) {
        MatrixF c = new RowArrayMatrixF(data);
        return c;
    }

    /**
     * Create a MatrixD for the specified Data. Allows the construction of
     * a Mutable MatrixD.
     * @param data datum for the MatrixF.
     * @param mutable flag when true a mutable MatrixD is created. Otherwise
     * creates an immutable MatrixF.
     * @return MatrixD the constructed MatrixD.
     */
    public static MatrixF create(Float[][] data, boolean mutable) {
        MatrixF c = null;
        if (!mutable) {
            c = new RowArrayMatrixF(data);
        } else {
            c = new RowArrayMatrixF(data, true);
        }
        return c;
    }

    /**
     * Returns the transpose of this instance. MatrixF A' = transpose(MatrixF A)
     * @param a MatrixF A to be transposed.
     * @return MatrixF A' which is a transpose of this instance.
     */
    public static MatrixF transpose(MatrixF a) {
        MatrixF t = new RowArrayMatrixF(a.n, a.m);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                t.initData(j, i, a.getData(i, j));
            }
        }
        return t;
    }

    /**
     * Creates an identity n by n MatrixF
     * @param n dimension of square MatrixF.
     * @return MatrixF constructed identity MatrixF.
     */
    public static MatrixF identity(int n) {
        MatrixF i = new RowArrayMatrixF(n, n);
        for (int j = 0; j < n; j++) {
            i.initData(j,j,1F);
        }
        return i;
    }

    /**
     * Creates a random m by n MatrixF R with all values 0 <= r[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for MatrixF.
     * @param n column count for MatrixF.
     * @return MatrixF R.
     */
    public static MatrixF random(int m, int n) {
        MatrixF r = new RowArrayMatrixF(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r.initData(i, j, FastRandom.random());
            }
        }
        return r;
    }

    /**
     * Creates a random m by n MatrixF R with all values 0 <= r[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for MatrixF.
     * @param n column count for MatrixF.
     * @param lowerBound low bound for generated values
     * @param higherBound high bound for generated values
     * @return MatrixF R.
     */
    public static MatrixF random(int m, int n, float lowerBound, float higherBound) {
        MatrixF r = new RowArrayMatrixF(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r.initData(i, j, (FastRandom.random() * (higherBound - lowerBound)) + lowerBound);
            }
        }
        return r;
    }

    /**
     * Creates a random m by n MatrixF G with all values 0 <= g[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for MatrixF.
     * @param n column count for MatrixF.
     * @param v value for cell entries
     * @return MatrixF G.
     */
    public static MatrixF generate(int m, int n, float v) {
        MatrixF g = new RowArrayMatrixF(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                g.initData(i, j, v);
            }
        }
        return g;
    }

    /**
     * Creates a random m by n MatrixF A with all values 0 <= a[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for MatrixF.
     * @param n column count for MatrixF.
     * @return MatrixF A.
     */
    public static MatrixF oneMatrix(int m, int n) {
        return generate(m, n, 1F);
    }
}
