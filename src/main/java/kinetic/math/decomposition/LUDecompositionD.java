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
package kinetic.math.decomposition;

import kinetic.math.Matrix;
import kinetic.math.MatrixD;
import kinetic.math.QuickMath;

/**
 *
 * @author openecho
 */
public class LUDecompositionD extends MatrixDecomposition {

    double[][] lu;
    int m, n, pivsign;
    private int[] piv;

    public LUDecompositionD(Matrix a) {
        super(a);
    }

    @Override
    protected boolean handleDecompose() {
        m = a.getM();
        n = a.getN();
        lu = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                lu[i][j] = a.getData(i, j).doubleValue();
            }
        }
        piv = new int[m];
        for (int i = 0; i < m; i++) {
            piv[i] = i;
        }
        pivsign = 1;
        double[] LUrowi;
        double[] LUcolj = new double[m];
        for (int j = 0; j < n; j++) {
            // Make a copy of the j-th column to localize references.
            for (int i = 0; i < m; i++) {
                LUcolj[i] = lu[i][j];
            }
            // Apply previous transformations.
            for (int i = 0; i < m; i++) {
                LUrowi = lu[i];
                int kmax = QuickMath.min(i, j);
                double s = 0.0;
                for (int k = 0; k < kmax; k++) {
                    s += LUrowi[k] * LUcolj[k];
                }
                LUrowi[j] = LUcolj[i] -= s;
            }
            // Find pivot and exchange if necessary.
            int p = j;
            for (int i = j + 1; i < m; i++) {
                if (QuickMath.abs(LUcolj[i]) > Math.abs(LUcolj[p])) {
                    p = i;
                }
            }
            if (p != j) {
                for (int k = 0; k < n; k++) {
                    double t = lu[p][k];
                    lu[p][k] = lu[j][k];
                    lu[j][k] = t;
                }
                int k = piv[p];
                piv[p] = piv[j];
                piv[j] = k;
                pivsign = -pivsign;
            }
            // Compute multipliers.
            if (j < m & lu[j][j] != 0.0) {
                for (int i = j + 1; i < m; i++) {
                    lu[i][j] /= lu[j][j];
                }
            }
        }
        return true;
    }

    public MatrixD getL() {
        if (!isDecomposed()) {
            decompose();
        }
        MatrixD l = MatrixD.empty(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    l.setData(i, j, lu[i][j]);
                } else if (i == j) {
                    l.setData(i, j, 1D);
                } else {
                    l.setData(i, j, 0D);
                }
            }
        }
        return l;
    }

    public MatrixD getU() {
        if (!isDecomposed()) {
            decompose();
        }
        MatrixD u = MatrixD.empty(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i <= j) {
                    u.setData(i, j, lu[i][j]);
                } else {
                    u.setData(i, j, 0D);
                }
            }
        }
        return u;
    }

    public int[] getPivot() {
        if (!isDecomposed()) {
            decompose();
        }
        int[] p = new int[m];
        System.arraycopy(piv, 0, p, 0, m);
        return p;
    }

    public double determinant() {
        if (!isDecomposed()) {
            decompose();
        }
        if (m != n) {
            throw new IllegalArgumentException("Matrix must be square. m != n.");
        }
        double d = (double) pivsign;
        for (int j = 0; j < n; j++) {
            d *= lu[j][j];
        }
        return d;
    }
}
