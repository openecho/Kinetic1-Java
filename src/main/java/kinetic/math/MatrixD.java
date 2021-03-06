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

import kinetic.math.linear.LUDecompositionD;

/**
 * Abstract m by n Double Matrix in the following form.
 * <pre>
 *         n1  n2  n3 ...  nN
 *      +---------------------
 *   m1 | a11 a12 a13 ... aN1
 *   m2 | a21 a22 a23 ... aN1
 *   m3 | a31 a32 a33 ... aN1
 *   .. | ... ... ...     ...
 *   mN | aM1 aM2 aM3 ... aMN
 * </pre>
 * Provides static methods to construct various MatrixD instances
 * that are both mutable and immutable.
 *
 * @author openecho
 * @version 2.0.0
 */
public abstract class MatrixD extends Matrix {

    /**
     * Default constructor to specify the dimensions of the m by n MatrixD
     * @param m rows in the MatrixD.
     * @param n columns in the MatrixD.
     */
    public MatrixD(int m, int n) {
        this(m, n, false);
    }

    public MatrixD(int m, int n, boolean mutable) {
        this.m = m;
        this.n = n;
        this.mutate = mutable;
    }

    /**
     * Constructor to create a MatrixD with the given data set.
     * @param data datum for the matrix.
     */
    public MatrixD(Double[][] data) {
        this(data, false);
    }

    public MatrixD(Double[][] data, boolean mutable) {
        m = data.length;
        n = data[0].length;
        this.mutate = mutable;
        this.setData(data);
    }

    /**
     * Retrieve the data from the MatrixD. This will be unsupported on some
     * implementations.
     * @return Double[][] data from this MatrixD.
     */
    public abstract Double[][] getData();

    public abstract Double getData(int i, int j);

    /**
     * Sets the data into the MatrixD. This will be unsupported on some
     * implementations.
     */
    public abstract void setData(Number[][] data);

    public abstract void setData(int i, int j, Number data);

    /**
     * Retrieve the data for a row from the MatrixD.
     * @param i the row to retrieve (0 <= i < m)
     * @return double[] the row data
     */
    public abstract Double[] getRow(int i);

    /**
     * Retrieve the data for a column from the MatrixD. It is worth noting
     * that this data is never mutable.
     * @param i the row to retrieve (0 <= i < n)
     * @return double[] the column data
     */
    public Double[] getColumn(int i) {
        if (i >= n) {
            throw new IndexOutOfBoundsException();
        }
        Double[] result = new Double[m];
        for (int j = 0; j < m; j++) {
            result[j] = getData(j, i);
        }
        return result;
    }

    /**
     * MatrixD equality check. True when A = B, (a[i,j]) = (b[i,j]) where
     * 0 <= i < m and 0 <= j < n.
     * @param b MatrixD B.
     * @return boolean true when equal otherwise false.
     */
    @Override
    public boolean equals(Matrix b) {
        MatrixD a = this;
        Number[][] bData = b.getData();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!getData(i, j).equals(bData[i][j])) {
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
    public abstract MatrixD add(Matrix b);

    public abstract MatrixD add(Matrix b, boolean mutate);

    /**
     * Subtracts a Matrix from this instance. Matrix A - Matrix B = Matrix C.
     * @param b Matrix B.
     * @return Matrix Matrix C.
     */
    public abstract MatrixD subtract(Matrix b);

    public abstract MatrixD subtract(Matrix b, boolean mutate);

    /**
     * Multiplies a Matrix to this instance. Matrix A * Matrix B = Matrix C.
     * @param b Matrix B.
     * @return Matrix Matrix C.
     */
    public abstract MatrixD multiply(Matrix b);

    public abstract MatrixD multiply(Matrix b, boolean mutate);

    /**
     * Returns the transpose of this instance. Matrix A' = transpose(Matrix A)
     * @return Matrix A' which is a transpose of this instance.
     */
    public abstract MatrixD transpose();

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]+v
     * @param v Value to add
     * @return Matrix Matrix C
     */
    public abstract MatrixD addScalar(Number v);

    public abstract MatrixD addScalar(Number v, boolean mutate);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]-v
     * @param v Value to subtract
     * @return Matrix Matrix C
     */
    public abstract MatrixD subtractScalar(Number v);

    public abstract MatrixD subtractScalar(Number v, boolean mutate);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]*v
     * @param v Value to multiply
     * @return Matrix Matrix C
     */
    public abstract MatrixD multiplyScalar(Number v);

    public abstract MatrixD multiplyScalar(Number v, boolean mutate);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]/v
     * @param v Value to divide
     * @return Matrix Matrix C
     */
    public abstract MatrixD divideScalar(Number v);

    public abstract MatrixD divideScalar(Number v, boolean mutate);

    /**
     * Return the invert of the MatrixD A.
     * @return MatrixD A^-1
     */
    public abstract MatrixD invert();

    /**
     * MatrixD Solver in the form A*X=B where A is this MatrixD and X is the
     * solutions.
     * @param b MatrixD B
     * @return Solution MatrixD X
     */
    public MatrixD solve(Matrix b) {
        if (m != n || b.getM() != n || b.getN() != 1) {
            throw new RuntimeException("Incorrect matrix dimensions.");
        }
        for (int i = 0; i < n; i++) {
            int max = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(getData(j, i)) > Math.abs(getData(max, i))) {
                    max = j;
                }
            }
            /**
             * Swap i and max
             */
            double tmp;
            for (int j = 0; j < n; j++) {
                tmp = getData(i, j).doubleValue();
                setData(i, j, getData(max, j));
                setData(max, j, tmp);
            }
            for (int j = 0; j < b.getN(); j++) {
                tmp = b.getData(i, j).doubleValue();
                b.setData(i, j, b.getData(max, j));
                b.setData(max, j, tmp);
            }
            /**
             * Check Singular
             */
            if (getData(i, i) == 0) {
                throw new RuntimeException("Matrix is singular.");
            }
            /**
             * Pivot B
             */
            for (int j = i + 1; j < n; j++) {
                b.setData(j, 0, (b.getData(j, 0).doubleValue() - (b.getData(i, 0).doubleValue() * getData(j, i).doubleValue() / getData(i, i).doubleValue())));
            }
            /**
             * Pivot A
             */
            for (int j = i + 1; j < n; j++) {
                Double f = getData(j, i) / getData(i, i);
                for (int k = i + 1; k < n; k++) {
                    setData(j, k, (getData(j, k) - getData(i, k) * f));
                }
                setData(j, i, 0);
            }
        }
        MatrixD x = MatrixD.empty(n, 1);
        for(int j = n - 1; j >= 0; j--) {
            double v = 0;
            for(int k = j + 1; k < n; k++) {
                v += getData(j,k).doubleValue() * x.getData(k, 0).doubleValue();
            }
            x.setData(j, 0, ((b.getData(j, 0).doubleValue() - v) / getData(j, j).doubleValue()));
        }
        return x;
    }

    /**
     * Calculates the determinant of the MatrixD if it is square (n=m). Currently
     * this handles cases where n < 3 internally and utilises LUDecompositionD
     * for bigger instances.
     * @return double detminant of the MatrixD.
     */
    public Double determinant() {
        if (n != m) {
            throw new RuntimeException("Not a square matrix.");
        }
        Double[][] data = getData();
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
        } else {
            return (new LUDecompositionD(this)).determinant();
        }
    }

    @Override
    public Matrix getSubMatrix(int mi, int mj, int ni, int nj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Convert the MatrixD to a String
     * @return String MatrixD as String
     */
    @Override
    public String toString() {
        String dataString = "{";
        for (int i = 0; i < m; i++) {
            dataString += "{";
            for (int j = 0; j < n; j++) {
                dataString += getData(i, j) + ((j < n - 1) ? "," : "");
            }
            dataString += "}" + ((i < m - 1) ? "," : "");
        }
        dataString += "}";
        return String.format("%s %s", super.toString(), dataString);
    }

    /**
     * Create an empty m by n MatrixD. All values are set to zero. Returns a
     * MutableMatrixD instance.
     * @param m row count for the MatrixD.
     * @param n column count for the MatrixD.
     * @return MatrixD the constructed MatrixD.
     */
    public static MatrixD empty(int m, int n) {
        MatrixD e = new RowArrayMatrixD(m, n, true);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                e.setData(i, j, 0D);
            }
        }
        return e;
    }

    /**
     * Create a MatrixD for the specified Data. Returns an Immutable MatrixD.
     * @param data datum for the MatrixD.
     * @return MatrixD the constructed MatrixD.
     */
    public static MatrixD create(double[][] data) {
        int m = data.length;
        if (m == 0) {
            throw new RuntimeException("Invalid Argument.");
        }
        int n = data[0].length;
        if (n == 0) {
            throw new RuntimeException("Invalid Argument.");
        }
        MatrixD c = MatrixD.empty(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.setData(i, j, data[i][j]);
            }
        }
        return c;
    }

    /**
     * Create a MatrixD for the specified Data. Returns an Immutable MatrixD.
     * @param data datum for the MatrixD.
     * @return MatrixD the constructed MatrixD.
     */
    public static MatrixD create(Double[][] data) {
        MatrixD c = new RowArrayMatrixD(data);
        return c;
    }

    /**
     * Create a MatrixD for the specified Data. Allows the construction of
     * a Mutable MatrixD.
     * @param data datum for the MatrixD.
     * @param mutable flag when true a mutable MatrixD is created. Otherwise
     * creates an immutable MatrixD.
     * @return MatrixD the constructed MatrixD.
     */
    public static MatrixD create(Double[][] data, boolean mutable) {
        MatrixD c = new RowArrayMatrixD(data, mutable);
        return c;
    }

    /**
     * Returns the transpose of this instance. MatrixD A' = transpose(MatrixD A)
     * @param a MatrixD A to be transposed.
     * @return MatrixD A' which is a transpose of this instance.
     */
    public static MatrixD transpose(MatrixD a) {
        MatrixD t = new RowArrayMatrixD(a.n, a.m);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                t.setData(j, i, a.getData(i, j));
            }
        }
        return t;
    }

    /**
     * Creates an identity n by n MatrixD
     * @param n dimension of square MatrixD.
     * @return MatrixD constructed identity MatrixD.
     */
    public static MatrixD identity(int n) {
        MatrixD i = new RowArrayMatrixD(n, n);
        for (int j = 0; j < n; j++) {
            i.setData(j, j, 1D);
        }
        return i;
    }

    /**
     * Creates a random m by n MatrixD R with all values 0 <= r[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for MatrixD.
     * @param n column count for MatrixD.
     * @return MatrixD R.
     */
    public static MatrixD random(int m, int n) {
        MatrixD r = new RowArrayMatrixD(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r.setData(i, j, QuickMath.random());
            }
        }
        return r;
    }

    /**
     * Creates a random m by n MatrixD R with all values 0 <= r[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for MatrixD.
     * @param n column count for MatrixD.
     * @param lowerBound low bound for generated values
     * @param higherBound high bound for generated values
     * @return MatrixD R.
     */
    public static MatrixD random(int m, int n, double lowerBound, double higherBound) {
        MatrixD r = new RowArrayMatrixD(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r.setData(i, j, (QuickMath.random() * (higherBound - lowerBound)) + lowerBound);
            }
        }
        return r;
    }

    /**
     * Creates a random m by n MatrixD G with all values 0 <= g[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for MatrixD.
     * @param n column count for MatrixD.
     * @param v value for cell entries
     * @return MatrixD G.
     */
    public static MatrixD generate(int m, int n, double v) {
        MatrixD g = new RowArrayMatrixD(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                g.setData(i, j, v);
            }
        }
        return g;
    }

    /**
     * Creates a random m by n MatrixD A with all values 0 <= a[i,j] < 1 where
     * 0 <= i < m and 0 <= j < n.
     * @param m row count for MatrixD.
     * @param n column count for MatrixD.
     * @return MatrixD A.
     */
    public static MatrixD oneMatrix(int m, int n) {
        return generate(m, n, 1D);
    }
}
