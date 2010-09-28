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

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

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
 * @version 2.1.0
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
     * mutate flag
     */
    boolean mutate = false;

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
     * Flag indicating if this version of the Matrix will mutate or create new objects.
     * @return boolean Matrix will mutate when true otherwise it will create new objects.
     */
    public boolean willMutate() {
        return mutate;
    }

    /**
     * Mutator for mutate behavior
     * @param mutate mutate flag
     */
    void setMutate(boolean mutate) {
        this.mutate = mutate;
    }

    /**
     * Retrieve the data from the Matrix. This will be unsupported on some
     * implementations.
     * @return Number[][] data from this Matrix.
     */
    public abstract Number[][] getData();

    /**
     * Retrieve data from the Matrix. This should work on all implementations.
     * @param i The row to extract the data from.
     * @param j The column to extract the data from.
     * @return The data
     */
    public abstract Number getData(int i, int j);

    /**
     * Set Data into the Matrix. This method will be unavailable on sparse
     * implementations.
     * @param data The data to set
     */
    public abstract void setData(Number[][] data);

    /**
     * Set Data into the Matrix. This should work on all implementations.
     * @param i The row to set the data to.
     * @param j The column to set the data to.
     * @param data The data to set.
     */
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
                if (!getData(i, j).equals(bData[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Extract a row from the Matrix
     * @param i The row number to extract where i < m
     * @return The Row
     */
    public abstract Number[] getRow(int i);

    /**
     * Extract a column from the Matrix
     * @param i The column number to extract where i < n
     * @return The Column
     */
    public abstract Number[] getColumn(int i);

    /**
     * Adds a Matrix to this instance. Matrix A + Matrix B = Matrix C.
     * @param b Matrix B.
     * @return Matrix Matrix C.
     */
    public abstract Matrix add(Matrix b);

    /**
     * Adds a Matrix to this instance. Matrix A + Matrix B = Matrix C.
     * @param b Matrix B.
     * @param mutate flag to specify mutation.
     * @return Matrix Matrix C.
     */
    public abstract Matrix add(Matrix b, boolean mutate);

    /**
     * Subtracts a Matrix from this instance. Matrix A - Matrix B = Matrix C.
     * @param b Matrix B.
     * @return Matrix Matrix C.
     */
    public abstract Matrix subtract(Matrix b);

    /**
     * Subtracts a Matrix from this instance. Matrix A - Matrix B = Matrix C.
     * @param b Matrix B.
     * @param mutate flag to specify mutation.
     * @return Matrix Matrix C.
     */
    public abstract Matrix subtract(Matrix b, boolean mutate);

    /**
     * Multiplies a Matrix to this instance. Matrix A * Matrix B = Matrix C.
     * @param b Matrix B.
     * @return Matrix Matrix C.
     */
    public abstract Matrix multiply(Matrix b);

    /**
     * Multiplies a Matrix to this instance. Matrix A * Matrix B = Matrix C.
     * @param b Matrix B.
     * @param mutate flag to specify mutation.
     * @return Matrix Matrix C.
     */
    public abstract Matrix multiply(Matrix b, boolean mutate);

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
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]+v
     * @param v Value to add
     * @param mutate flag to specify mutation.
     * @return Matrix Matrix C
     */
    public abstract Matrix addScalar(Number v, boolean mutate);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]-v
     * @param v Value to subtract
     * @return Matrix Matrix C
     */
    public abstract Matrix subtractScalar(Number v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]-v
     * @param v Value to subtract
     * @param mutate flag to specify mutation.
     * @return Matrix Matrix C
     */
    public abstract Matrix subtractScalar(Number v, boolean mutate);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]*v
     * @param v Value to multiply
     * @return Matrix Matrix C
     */
    public abstract Matrix multiplyScalar(Number v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]*v
     * @param v Value to multiply
     * @param mutate flag to specify mutation.
     * @return Matrix Matrix C
     */
    public abstract Matrix multiplyScalar(Number v, boolean mutate);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]/v
     * @param v Value to divide
     * @return Matrix Matrix C
     */
    public abstract Matrix divideScalar(Number v);

    /**
     * Adds a scalar value to this Matrix instance. Matrix C = C(c[i,j]) = a[i,j]/v
     * @param v Value to divide
     * @param mutate flag to specify mutation.
     * @return Matrix Matrix C
     */
    public abstract Matrix divideScalar(Number v, boolean mutate);

    /**
     * Return the invert of the Matrix A.
     * @return Matrix A^-1
     */
    public abstract Matrix invert();

    /**
     * Matrix Solver in the form A*X=B where A is this Matrix and X is the
     * solutions.
     * @param b Matrix B
     * @return Solution Matrix X
     */
    public abstract Matrix solve(Matrix b);

    /**
     * Find the determinant of this Matrix. Must be a square Matrix (n=m)
     * @return The determinant of the Matrix.
     */
    public abstract Number determinant();

    /**
     * Prints the Matrix to the System.out (Stdout)
     * @param w Width for each cell.
     * @param d Number of decimal points for each cell.
     */
    public void print(int w, int d) {
        print(new PrintWriter(System.out, true), w, d);
    }

    /**
     * Prints the Matrix to the specified output.
     * @param output PrintWriter to write to.
     * @param w Width for each cell.
     * @param d Number of decimal points for each cell.
     */
    public void print(PrintWriter output, int w, int d) {
        DecimalFormat format = new DecimalFormat();
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(d);
        format.setMinimumFractionDigits(d);
        format.setGroupingUsed(false);
        print(output, format, w + 2);
    }

    /**
     * Prints the Matrix to the specified output.
     * @param output PrintWriter to write to.
     * @param format Format to write numbers.
     * @param width The width to pad each cell to.
     */
    public void print(PrintWriter output, NumberFormat format, int width) {
        output.println();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String s = format.format(getData(i, j));
                int padding = Math.max(1, width - s.length());
                for (int k = 0; k < padding; k++) {
                    output.print(' ');
                }
                output.print(s);
            }
            output.println();
        }
        output.println();
    }
}
