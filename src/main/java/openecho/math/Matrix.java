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
 * Abstract m by n Matrix in the following form.
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
public abstract class Matrix {

    /**
     * m dimension
     */
    int m;
    /**
     * n dimension
     */
    int n;
    /**
     * matrix data
     */
    double[][] data;

    /**
     * Default constructor to specify the dimensions of the m by n Matrix
     * @param m rows in the Matrix.
     * @param n columns in the Matrix.
     */
    public Matrix(int m, int n) {
        this.m = m;
        this.n = n;
        data = new double[m][n];
    }

    /**
     * Constructor to create a Matrix with the given data set.
     * @param data datum for the matrix.
     */
    public Matrix(double[][] data) {
        m = data.length;
        n = data[0].length;
        this.data = new double[m][n];
        for (int i = 0; i < m; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, n);
        }
    }

    /**
     * Find the number of rows in the Matrix (M)
     * @return row count.
     */
    public final int getM() {
        return m;
    }

    /**
     * Find the number of columns in the Matrix (N)
     * @return column count.
     */
    public final int getN() {
        return n;
    }

    /**
     * Retrieve the data from the Matrix. This will be unsupported on some
     * implementations.
     * @return double[][] data from this Matrix.
     */
    public abstract double[][] getData();

    /**
     * Sets the data into the Matrix. This will be unsupported on some 
     * implementations.
     */
    public abstract void setData(double[][] data);

    /**
     * Flag indicating if this version of the Matrix is mutable.
     * @return boolean Matrix mutable when true otherwise not mutable.
     */
    public abstract boolean isMutable();

    /**
     * Retrieve the data for a row from the Matrix.
     * @param i the row to retrieve (0 <= i < m)
     * @return double[] the row data
     */
    public abstract double[] getRow(int i);

    /**
     * Retrieve the data for a column from the Matrix. It is worth noting
     * that this data is never mutable.
     * @param i the row to retrieve (0 <= i < n)
     * @return double[] the column data
     */
    public double[] getColumn(int i) {
        if (i >= n) {
            throw new IndexOutOfBoundsException();
        }
        double[] result = new double[m];
        for (int j = 0; j < m; j++) {
            result[j] = data[j][i];
        }
        return result;
    }

    /**
     * Matrix equality check. True when A = B, (a[i,j]) = (b[i,j]) where
     * 0 <= i < m and 0 <= j < n.
     * @param b Matrix B.
     * @return boolean true when equal otherwise false.
     */
    public boolean equals(Matrix b) {
        Matrix a = this;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a.data[i][j] != b.data[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds a Matrix to this instance. Matrix A + Matrix B = Matrix C.
     * @param b Matrix B.
     * @return Matrix Matrix C.
     */
    public abstract Matrix add(Matrix b);

    /**
     * Subtracts a Matrix from this instance. Matrix A - Matrix B = Matrix C.
     * @param b Matrix B.
     * @return Matrix Matrix C.
     */
    public abstract Matrix subtract(Matrix b);

    /**
     * Multiplies a Matrix to this instance. Matrix A * Matrix B = Matrix C.
     * @param b Matrix B.
     * @return Matrix Matrix C.
     */
    public abstract Matrix multiply(Matrix b);

    /**
     * Returns the transpose of this instance. Matrix A' = transpose(Matrix A)
     * @return Matrix A' which is a transpose of this instance.
     */
    public abstract Matrix transpose();

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]+v
     * @param v Value to add
     * @return Matrix Matrix C
     */
    public abstract Matrix addScalar(double v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]-v
     * @param v Value to subtract
     * @return Matrix Matrix C
     */
    public abstract Matrix subtractScalar(double v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]*v
     * @param v Value to multiply
     * @return Matrix Matrix C
     */
    public abstract Matrix multiplyScalar(double v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]/v
     * @param v Value to divide
     * @return Matrix Matrix C
     */
    public abstract Matrix divideScalar(double v);

    /**
     * Calculates the determinant of the Matrix if it is square (n=m). Currently
     * this supports cases where n < 3.
     * @return double detminant of the Matrix.
     */
    public double determinant() {
        if (n != m) {
            throw new RuntimeException("Not a square matrix.");
        }
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
     * Convert the Matrix to a String
     * @return String Matrix as String
     */
    @Override
    public String toString() {
        String dataString = "{";
        for (int i = 0; i < m; i++) {
            dataString += "{";
            for (int j = 0; j < n; j++) {
                dataString += data[i][j] + ((j < n - 1) ? "," : "");
            }
            dataString += "}" + ((i < m - 1) ? "," : "");
        }
        dataString += "}";
        return String.format("%s %s", super.toString(), dataString);
    }

    /**
     * Create an empty m by n Matrix. All values are set to zero. Returns a
     * MutableMatrix instance.
     * @param m row count for the Matrix.
     * @param n column count for the Matrix.
     * @return Matrix the constructed Matrix.
     */
    public static Matrix empty(int m, int n) {
        Matrix e = new MutableMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                e.data[i][j] = 0D;
            }
        }
        return e;
    }

    /**
     * Create a Matrix for the specified Data. Returns an Immutable Matrix.
     * @param data datum for the Matrix.
     * @return Matrix the constructed Matrix.
     */
    public static Matrix create(double[][] data) {
        Matrix c = new ImmutableMatrix(data);
        return c;
    }

    /**
     * Create a Matrix for the specified Data. Allows the construction of
     * a Mutable Matrix.
     * @param data datum for the Matrix.
     * @param mutable flag when true a mutable Matrix is created. Otherwise
     * creates an immutable Matrix.
     * @return Matrix the constructed Matrix.
     */
    public static Matrix create(double[][] data, boolean mutable) {
        Matrix c = null;
        if (!mutable) {
            c = new ImmutableMatrix(data);
        } else {
            c = new MutableMatrix(data);
        }
        return c;
    }

    /**
     * Returns the transpose of this instance. Matrix A' = transpose(Matrix A)
     * @param a Matrix A to be transposed.
     * @return Matrix A' which is a transpose of this instance.
     */
    public static Matrix transpose(Matrix a) {
        Matrix t = new ImmutableMatrix(a.n, a.m);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                t.data[j][i] = a.data[i][j];
            }
        }
        return t;
    }

    /**
     * Creates an identity n by n Matrix
     * @param n dimension of square Matrix.
     * @return Matrix constructed identity Matrix.
     */
    public static Matrix identity(int n) {
        Matrix i = new ImmutableMatrix(n, n);
        for (int j = 0; j < n; j++) {
            i.data[j][j] = 1;
        }
        return i;
    }

    /**
     * Creates a random m by n Matrix R with all values 0 <= r[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for Matrix.
     * @param n column count for Matrix.
     * @return Matrix R.
     */
    public static Matrix random(int m, int n) {
        Matrix r = new ImmutableMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r.data[i][j] = Math.random();
            }
        }
        return r;
    }

    /**
     * Creates a random m by n Matrix R with all values 0 <= r[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for Matrix.
     * @param n column count for Matrix.
     * @param lowerBound low bound for generated values
     * @param higherBound high bound for generated values
     * @return Matrix R.
     */
    public static Matrix random(int m, int n, double lowerBound, double higherBound) {
        Matrix r = new ImmutableMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r.data[i][j] = (Math.random() * (higherBound - lowerBound)) + lowerBound;
            }
        }
        return r;
    }

    /**
     * Creates a random m by n Matrix G with all values 0 <= g[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for Matrix.
     * @param n column count for Matrix.
     * @param v value for cell entries
     * @return Matrix G.
     */
    public static Matrix generate(int m, int n, double v) {
        Matrix g = new ImmutableMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                g.data[i][j] = v;
            }
        }
        return g;
    }

    /**
     * Creates a random m by n Matrix A with all values 0 <= a[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for Matrix.
     * @param n column count for Matrix.
     * @return Matrix A.
     */
    public static Matrix oneMatrix(int m, int n) {
        return generate(m, n, 1D);
    }
}
