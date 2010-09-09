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

    boolean mutable = false;

    /**
     * matrix data
     */
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
     * Flag indicating if this version of the Matrix is mutable.
     * @return boolean Matrix mutable when true otherwise not mutable.
     */
    public boolean isMutable() {
        return mutable;
    }

    void setMutable(boolean mutable) {
        this.mutable = mutable;
    }

    /**
     * Retrieve the data from the Matrix. This will be unsupported on some
     * implementations.
     * @return Number[][] data from this Matrix.
     */
    public abstract Number[][] getData();

    public abstract Number getData(int i, int j);

    public abstract void setData(Number[][] data);

    public abstract void setData(int i, int j, Number data);

    /**
     * Matrix equality check. True when A = B, (a[i,j]) = (b[i,j]) where
     * 0 <= i < m and 0 <= j < n.
     * @param b Matrix B.
     * @return boolean true when equal otherwise false.
     */
    public boolean equals(Matrix b) {
        Number[][] bData = b.getData();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!getData(i,j).equals(bData[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public abstract Number[] getRow(int i);

    public abstract Number[] getColumn(int i);


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
    public abstract Matrix addScalar(Number v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]-v
     * @param v Value to subtract
     * @return Matrix Matrix C
     */
    public abstract Matrix subtractScalar(Number v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]*v
     * @param v Value to multiply
     * @return Matrix Matrix C
     */
    public abstract Matrix multiplyScalar(Number v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]/v
     * @param v Value to divide
     * @return Matrix Matrix C
     */
    public abstract Matrix divideScalar(Number v);


    public abstract Number determinant();
}
