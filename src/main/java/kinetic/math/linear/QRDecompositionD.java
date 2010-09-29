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
 *
 * This class contains portions of code which are Copyright The Mathworks and
 * NIST. Their license statement follows,
 *
 * --------------------------------------------------------------------------
 *
 * This software is a cooperative product of The MathWorks and the National
 * Institute of Standards and Technology (NIST) which has been released to the
 * public domain. Neither The MathWorks nor NIST assumes any responsibility
 * whatsoever for its use by other parties, and makes no guarantees, expressed
 * or implied, about its quality, reliability, or any other characteristic.
 *
 * Copyright (C) 1999 The Mathworks and NIST
 **/
package kinetic.math.linear;

import kinetic.math.Matrix;
import kinetic.math.MatrixD;
import kinetic.math.QuickMath;

/**
 *
 * @author openecho
 */
public class QRDecompositionD extends MatrixDecomposition {

    private double[][] qr;
    private int m, n;
    private double[] rDiagonal;

    public QRDecompositionD(Matrix a) {
        super(a);
    }

    @Override
    protected boolean handleDecompose() {
        m = a.getM();
        n = a.getN();
        qr = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                qr[i][j] = a.getData(i, j).doubleValue();
            }
        }
        rDiagonal = new double[n];

        // Main loop.
        for (int k = 0; k < n; k++) {
            // Compute 2-norm of k-th column without under/overflow.
            double nrm = 0;
            for (int i = k; i < m; i++) {
                nrm = QuickMath.hypot(nrm, qr[i][k]);
            }

            if (nrm != 0.0) {
                // Form k-th Householder vector.
                if (qr[k][k] < 0) {
                    nrm = -nrm;
                }
                for (int i = k; i < m; i++) {
                    qr[i][k] /= nrm;
                }
                qr[k][k] += 1.0;

                // Apply transformation to remaining columns.
                for (int j = k + 1; j < n; j++) {
                    double s = 0.0;
                    for (int i = k; i < m; i++) {
                        s += qr[i][k] * qr[i][j];
                    }
                    s = -s / qr[k][k];
                    for (int i = k; i < m; i++) {
                        qr[i][j] += s * qr[i][k];
                    }
                }
            }
            rDiagonal[k] = -nrm;
        }
        return true;
    }

    public boolean isFullRank() {
        for (int j = 0; j < n; j++) {
            if (rDiagonal[j] == 0) {
                return false;
            }
        }
        return true;
    }

    public MatrixD getH() {
        MatrixD h = MatrixD.empty(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i >= j) {
                    h.setData(i, j, qr[i][j]);
                } else {
                    h.setData(i, j, 0D);
                }
            }
        }
        return h;
    }

    public Matrix getR() {
        MatrixD r = MatrixD.empty(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i < j) {
                    r.setData(i, j, qr[i][j]);
                } else if (i == j) {
                    r.setData(i, j, rDiagonal[i]);
                } else {
                    r.setData(i, j, 0D);
                }
            }
        }
        return r;
    }

    public Matrix getQ() {
        MatrixD q = MatrixD.empty(n, n);
        for (int k = n - 1; k >= 0; k--) {
            for (int i = 0; i < m; i++) {
                q.setData(i, k, 0D);
            }
            q.setData(k, k, 1D);
            for (int j = k; j < n; j++) {
                if (qr[k][k] != 0) {
                    double s = 0.0;
                    for (int i = k; i < m; i++) {
                        s += qr[i][k] * q.getData(i, j);
                    }
                    s = -s / qr[k][k];
                    for (int i = k; i < m; i++) {
                        q.setData(i, j, q.getData(i, j) + (s * q.getData(i, k)));
                    }
                }
            }
        }
        return q;
    }
}
