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
import kinetic.math.MatrixF;
import kinetic.math.QuickMath;

/**
 *
 * @author openecho
 */
public class LUDecompositionF extends MatrixDecomposition {

    float[][] lu;
    int m, n, pivSign;
    private int[] piv;

    public LUDecompositionF(Matrix a) {
        super(a);
    }

    @Override
    protected boolean handleDecompose() {
        m = a.getM();
        n = a.getN();
        lu = new float[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                lu[i][j] = a.getData(i, j).floatValue();
            }
        }
        piv = new int[m];
        for (int i = 0; i < m; i++) {
            piv[i] = i;
        }
        pivSign = 1;
        float[] luRowI;
        float[] luColJ = new float[m];
        for (int j = 0; j < n; j++) {
            // Make a copy of the j-th column to localize references.
            for (int i = 0; i < m; i++) {
                luColJ[i] = lu[i][j];
            }
            // Apply previous transformations.
            for (int i = 0; i < m; i++) {
                luRowI = lu[i];
                int kMax = QuickMath.min(i, j);
                float s = 0.0F;
                for (int k = 0; k < kMax; k++) {
                    s += luRowI[k] * luColJ[k];
                }
                luRowI[j] = luColJ[i] -= s;
            }
            // Find pivot and exchange if necessary.
            int p = j;
            for (int i = j + 1; i < m; i++) {
                if (QuickMath.abs(luColJ[i]) > Math.abs(luColJ[p])) {
                    p = i;
                }
            }
            if (p != j) {
                for (int k = 0; k < n; k++) {
                    float t = lu[p][k];
                    lu[p][k] = lu[j][k];
                    lu[j][k] = t;
                }
                int k = piv[p];
                piv[p] = piv[j];
                piv[j] = k;
                pivSign = -pivSign;
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

    public MatrixF getL() {
        if (!isDecomposed()) {
            decompose();
        }
        MatrixF l = MatrixF.empty(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    l.setData(i, j, lu[i][j]);
                } else if (i == j) {
                    l.setData(i, j, 1D);
                } else {
                    l.setData(i, j, 0F);
                }
            }
        }
        return l;
    }

    public MatrixF getU() {
        if (!isDecomposed()) {
            decompose();
        }
        MatrixF u = MatrixF.empty(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i <= j) {
                    u.setData(i, j, lu[i][j]);
                } else {
                    u.setData(i, j, 0F);
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

    public float determinant() {
        if (!isDecomposed()) {
            decompose();
        }
        if (m != n) {
            throw new IllegalArgumentException("Matrix must be square. m != n.");
        }
        float d = (float) pivSign;
        for (int j = 0; j < n; j++) {
            d *= lu[j][j];
        }
        return d;
    }
}
