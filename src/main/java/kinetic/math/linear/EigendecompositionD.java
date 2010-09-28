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
 * Decomposes a real matrix A into its eigenvalues and eigenvectors
 *
 * If A is symmetric, then A = V*D*V' where the eigenvalue matrix D is
 * diagonal and the eigenvector matrix V is orthogonal.
 * I.e. A = V.times(D.times(V.transpose())) and
 * V.times(V.transpose()) equals the identity matrix.
 *
 * If A is not symmetric, then the eigenvalue matrix D is block diagonal
 * with the real eigenvalues in 1-by-1 blocks and any complex eigenvalues,
 * lambda + i*mu, in 2-by-2 blocks, [lambda, mu; -mu, lambda].  The
 * columns of V represent the eigenvectors in the sense that A*V = V*D,
 * i.e. A.times(V) equals V.times(D).  The matrix V may be badly
 * conditioned, or even singular, so the validity of the equation
 * A = V*D*inverse(V) depends upon V.cond().
 *
 * @author openecho
 * @author The Mathworks and NIST
 **/
public class EigendecompositionD extends MatrixDecomposition {

    /**
     * Row and column dimension (square matrix).
     */
    private int n;
    /**
     * Symmetry flag.
     */
    private boolean issymmetric;
    /**
     * Arrays for internal storage of eigenvalues.
     */
    private double[] d, e;
    /**
     * Array for internal storage of eigenvectors.
     */
    private double[][] eigenVectors;
    /**
     * Array for internal storage of nonsymmetric Hessenberg form.
     */
    private double[][] hessenberg;
    /**
     * Working storage for nonsymmetric algorithm.
     */
    private double[] ort;

    public EigendecompositionD(Matrix a) {
        super(a);
    }

    @Override
    protected boolean handleDecompose() {
        n = a.getN();
        double[][] aData = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                aData[i][j] = a.getData(i, j).doubleValue();
            }
        }

        eigenVectors = new double[n][n];
        d = new double[n];
        e = new double[n];

        issymmetric = true;
        for (int j = 0; (j < n) & issymmetric; j++) {
            for (int i = 0; (i < n) & issymmetric; i++) {
                issymmetric = (aData[i][j] == aData[j][i]);
            }
        }

        if (issymmetric) {
            for (int i = 0; i < n; i++) {
                System.arraycopy(aData[i], 0, eigenVectors[i], 0, n);
            }

            // Tridiagonalize.
            tred2();

            // Diagonalize.
            tql2();

        } else {
            hessenberg = new double[n][n];
            ort = new double[n];

            for (int j = 0; j < n; j++) {
                for (int i = 0; i < n; i++) {
                    hessenberg[i][j] = aData[i][j];
                }
            }

            // Reduce to Hessenberg form.
            orthes();

            // Reduce Hessenberg to real Schur form.
            hqr2();
        }
        return true;
    }

    public MatrixD getV() {
        if (!isDecomposed()) {
            decompose();
        }
        return MatrixD.create(eigenVectors);
    }

    public double[] getRealEigenvalues() {
        if (!isDecomposed()) {
            decompose();
        }
        return d;
    }

    public double[] getImagEigenvalues() {
        if (!isDecomposed()) {
            decompose();
        }
        return e;
    }

    public MatrixD getD() {
        if (!isDecomposed()) {
            decompose();
        }
        MatrixD X = MatrixD.empty(n, n);
        Double[][] D = X.getData();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                D[i][j] = 0.0;
            }
            D[i][i] = d[i];
            if (e[i] > 0) {
                D[i][i + 1] = e[i];
            } else if (e[i] < 0) {
                D[i][i - 1] = e[i];
            }
        }
        return X;
    }

    // Symmetric Householder reduction to tridiagonal form.
    private void tred2() {
        System.arraycopy(eigenVectors[n - 1], 0, d, 0, n);

        // Householder reduction to tridiagonal form.

        for (int i = n - 1; i > 0; i--) {

            // Scale to avoid under/overflow.

            double scale = 0.0;
            double h = 0.0;
            for (int k = 0; k < i; k++) {
                scale = scale + Math.abs(d[k]);
            }
            if (scale == 0.0) {
                e[i] = d[i - 1];
                for (int j = 0; j < i; j++) {
                    d[j] = eigenVectors[i - 1][j];
                    eigenVectors[i][j] = 0.0;
                    eigenVectors[j][i] = 0.0;
                }
            } else {

                // Generate Householder vector.

                for (int k = 0; k < i; k++) {
                    d[k] /= scale;
                    h += d[k] * d[k];
                }
                double f = d[i - 1];
                double g = Math.sqrt(h);
                if (f > 0) {
                    g = -g;
                }
                e[i] = scale * g;
                h = h - f * g;
                d[i - 1] = f - g;
                for (int j = 0; j < i; j++) {
                    e[j] = 0.0;
                }

                // Apply similarity transformation to remaining columns.

                for (int j = 0; j < i; j++) {
                    f = d[j];
                    eigenVectors[j][i] = f;
                    g = e[j] + eigenVectors[j][j] * f;
                    for (int k = j + 1; k <= i - 1; k++) {
                        g += eigenVectors[k][j] * d[k];
                        e[k] += eigenVectors[k][j] * f;
                    }
                    e[j] = g;
                }
                f = 0.0;
                for (int j = 0; j < i; j++) {
                    e[j] /= h;
                    f += e[j] * d[j];
                }
                double hh = f / (h + h);
                for (int j = 0; j < i; j++) {
                    e[j] -= hh * d[j];
                }
                for (int j = 0; j < i; j++) {
                    f = d[j];
                    g = e[j];
                    for (int k = j; k <= i - 1; k++) {
                        eigenVectors[k][j] -= (f * e[k] + g * d[k]);
                    }
                    d[j] = eigenVectors[i - 1][j];
                    eigenVectors[i][j] = 0.0;
                }
            }
            d[i] = h;
        }

        // Accumulate transformations.

        for (int i = 0; i < n - 1; i++) {
            eigenVectors[n - 1][i] = eigenVectors[i][i];
            eigenVectors[i][i] = 1.0;
            double h = d[i + 1];
            if (h != 0.0) {
                for (int k = 0; k <= i; k++) {
                    d[k] = eigenVectors[k][i + 1] / h;
                }
                for (int j = 0; j <= i; j++) {
                    double g = 0.0;
                    for (int k = 0; k <= i; k++) {
                        g += eigenVectors[k][i + 1] * eigenVectors[k][j];
                    }
                    for (int k = 0; k <= i; k++) {
                        eigenVectors[k][j] -= g * d[k];
                    }
                }
            }
            for (int k = 0; k <= i; k++) {
                eigenVectors[k][i + 1] = 0.0;
            }
        }
        for (int j = 0; j < n; j++) {
            d[j] = eigenVectors[n - 1][j];
            eigenVectors[n - 1][j] = 0.0;
        }
        eigenVectors[n - 1][n - 1] = 1.0;
        e[0] = 0.0;
    }

    // Symmetric tridiagonal QL algorithm.
    private void tql2() {

        //  This is derived from the Algol procedures tql2, by
        //  Bowdler, Martin, Reinsch, and Wilkinson, Handbook for
        //  Auto. Comp., Vol.ii-Linear Algebra, and the corresponding
        //  Fortran subroutine in EISPACK.

        for (int i = 1; i < n; i++) {
            e[i - 1] = e[i];
        }
        e[n - 1] = 0.0;

        double f = 0.0;
        double tst1 = 0.0;
        double eps = Math.pow(2.0, -52.0);
        for (int l = 0; l < n; l++) {

            // Find small subdiagonal element

            tst1 = Math.max(tst1, Math.abs(d[l]) + Math.abs(e[l]));
            int m = l;
            while (m < n) {
                if (Math.abs(e[m]) <= eps * tst1) {
                    break;
                }
                m++;
            }

            // If m == l, d[l] is an eigenvalue,
            // otherwise, iterate.

            if (m > l) {
                int iter = 0;
                do {
                    iter = iter + 1;  // (Could check iteration count here.)

                    // Compute implicit shift

                    double g = d[l];
                    double p = (d[l + 1] - g) / (2.0 * e[l]);
                    double r = QuickMath.hypot(p, 1.0);
                    if (p < 0) {
                        r = -r;
                    }
                    d[l] = e[l] / (p + r);
                    d[l + 1] = e[l] * (p + r);
                    double dl1 = d[l + 1];
                    double h = g - d[l];
                    for (int i = l + 2; i < n; i++) {
                        d[i] -= h;
                    }
                    f = f + h;

                    // Implicit QL transformation.

                    p = d[m];
                    double c = 1.0;
                    double c2 = c;
                    double c3 = c;
                    double el1 = e[l + 1];
                    double s = 0.0;
                    double s2 = 0.0;
                    for (int i = m - 1; i >= l; i--) {
                        c3 = c2;
                        c2 = c;
                        s2 = s;
                        g = c * e[i];
                        h = c * p;
                        r = QuickMath.hypot(p, e[i]);
                        e[i + 1] = s * r;
                        s = e[i] / r;
                        c = p / r;
                        p = c * d[i] - s * g;
                        d[i + 1] = h + s * (c * g + s * d[i]);

                        // Accumulate transformation.

                        for (int k = 0; k < n; k++) {
                            h = eigenVectors[k][i + 1];
                            eigenVectors[k][i + 1] = s * eigenVectors[k][i] + c * h;
                            eigenVectors[k][i] = c * eigenVectors[k][i] - s * h;
                        }
                    }
                    p = -s * s2 * c3 * el1 * e[l] / dl1;
                    e[l] = s * p;
                    d[l] = c * p;

                    // Check for convergence.

                } while (Math.abs(e[l]) > eps * tst1);
            }
            d[l] = d[l] + f;
            e[l] = 0.0;
        }

        // Sort eigenvalues and corresponding vectors.

        for (int i = 0; i < n - 1; i++) {
            int k = i;
            double p = d[i];
            for (int j = i + 1; j < n; j++) {
                if (d[j] < p) {
                    k = j;
                    p = d[j];
                }
            }
            if (k != i) {
                d[k] = d[i];
                d[i] = p;
                for (int j = 0; j < n; j++) {
                    p = eigenVectors[j][i];
                    eigenVectors[j][i] = eigenVectors[j][k];
                    eigenVectors[j][k] = p;
                }
            }
        }
    }

    // Nonsymmetric reduction to Hessenberg form.
    private void orthes() {

        //  This is derived from the Algol procedures orthes and ortran,
        //  by Martin and Wilkinson, Handbook for Auto. Comp.,
        //  Vol.ii-Linear Algebra, and the corresponding
        //  Fortran subroutines in EISPACK.

        int low = 0;
        int high = n - 1;

        for (int m = low + 1; m <= high - 1; m++) {

            // Scale column.

            double scale = 0.0;
            for (int i = m; i <= high; i++) {
                scale = scale + Math.abs(hessenberg[i][m - 1]);
            }
            if (scale != 0.0) {

                // Compute Householder transformation.

                double h = 0.0;
                for (int i = high; i >= m; i--) {
                    ort[i] = hessenberg[i][m - 1] / scale;
                    h += ort[i] * ort[i];
                }
                double g = Math.sqrt(h);
                if (ort[m] > 0) {
                    g = -g;
                }
                h = h - ort[m] * g;
                ort[m] = ort[m] - g;

                // Apply Householder similarity transformation
                // H = (I-u*u'/h)*H*(I-u*u')/h)

                for (int j = m; j < n; j++) {
                    double f = 0.0;
                    for (int i = high; i >= m; i--) {
                        f += ort[i] * hessenberg[i][j];
                    }
                    f = f / h;
                    for (int i = m; i <= high; i++) {
                        hessenberg[i][j] -= f * ort[i];
                    }
                }

                for (int i = 0; i <= high; i++) {
                    double f = 0.0;
                    for (int j = high; j >= m; j--) {
                        f += ort[j] * hessenberg[i][j];
                    }
                    f = f / h;
                    for (int j = m; j <= high; j++) {
                        hessenberg[i][j] -= f * ort[j];
                    }
                }
                ort[m] = scale * ort[m];
                hessenberg[m][m - 1] = scale * g;
            }
        }

        // Accumulate transformations (Algol's ortran).

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                eigenVectors[i][j] = (i == j ? 1.0 : 0.0);
            }
        }

        for (int m = high - 1; m >= low + 1; m--) {
            if (hessenberg[m][m - 1] != 0.0) {
                for (int i = m + 1; i <= high; i++) {
                    ort[i] = hessenberg[i][m - 1];
                }
                for (int j = m; j <= high; j++) {
                    double g = 0.0;
                    for (int i = m; i <= high; i++) {
                        g += ort[i] * eigenVectors[i][j];
                    }
                    // Double division avoids possible underflow
                    g = (g / ort[m]) / hessenberg[m][m - 1];
                    for (int i = m; i <= high; i++) {
                        eigenVectors[i][j] += g * ort[i];
                    }
                }
            }
        }
    }
    // Complex scalar division.
    private transient double cdivr, cdivi;

    private void cdiv(double xr, double xi, double yr, double yi) {
        double r, d;
        if (Math.abs(yr) > Math.abs(yi)) {
            r = yi / yr;
            d = yr + r * yi;
            cdivr = (xr + r * xi) / d;
            cdivi = (xi - r * xr) / d;
        } else {
            r = yr / yi;
            d = yi + r * yr;
            cdivr = (r * xr + xi) / d;
            cdivi = (r * xi - xr) / d;
        }
    }

    // Nonsymmetric reduction from Hessenberg to real Schur form.
    private void hqr2() {

        //  This is derived from the Algol procedure hqr2,
        //  by Martin and Wilkinson, Handbook for Auto. Comp.,
        //  Vol.ii-Linear Algebra, and the corresponding
        //  Fortran subroutine in EISPACK.

        // Initialize

        int nn = this.n;
        int n = nn - 1;
        int low = 0;
        int high = nn - 1;
        double eps = Math.pow(2.0, -52.0);
        double exshift = 0.0;
        double p = 0, q = 0, r = 0, s = 0, z = 0, t, w, x, y;

        // Store roots isolated by balanc and compute matrix norm

        double norm = 0.0;
        for (int i = 0; i < nn; i++) {
            if (i < low | i > high) {
                d[i] = hessenberg[i][i];
                e[i] = 0.0;
            }
            for (int j = Math.max(i - 1, 0); j < nn; j++) {
                norm = norm + Math.abs(hessenberg[i][j]);
            }
        }

        // Outer loop over eigenvalue index

        int iter = 0;
        while (n >= low) {

            // Look for single small sub-diagonal element

            int l = n;
            while (l > low) {
                s = Math.abs(hessenberg[l - 1][l - 1]) + Math.abs(hessenberg[l][l]);
                if (s == 0.0) {
                    s = norm;
                }
                if (Math.abs(hessenberg[l][l - 1]) < eps * s) {
                    break;
                }
                l--;
            }

            // Check for convergence
            // One root found

            if (l == n) {
                hessenberg[n][n] = hessenberg[n][n] + exshift;
                d[n] = hessenberg[n][n];
                e[n] = 0.0;
                n--;
                iter = 0;

                // Two roots found

            } else if (l == n - 1) {
                w = hessenberg[n][n - 1] * hessenberg[n - 1][n];
                p = (hessenberg[n - 1][n - 1] - hessenberg[n][n]) / 2.0;
                q = p * p + w;
                z = Math.sqrt(Math.abs(q));
                hessenberg[n][n] = hessenberg[n][n] + exshift;
                hessenberg[n - 1][n - 1] = hessenberg[n - 1][n - 1] + exshift;
                x = hessenberg[n][n];

                // Real pair

                if (q >= 0) {
                    if (p >= 0) {
                        z = p + z;
                    } else {
                        z = p - z;
                    }
                    d[n - 1] = x + z;
                    d[n] = d[n - 1];
                    if (z != 0.0) {
                        d[n] = x - w / z;
                    }
                    e[n - 1] = 0.0;
                    e[n] = 0.0;
                    x = hessenberg[n][n - 1];
                    s = Math.abs(x) + Math.abs(z);
                    p = x / s;
                    q = z / s;
                    r = Math.sqrt(p * p + q * q);
                    p = p / r;
                    q = q / r;

                    // Row modification

                    for (int j = n - 1; j < nn; j++) {
                        z = hessenberg[n - 1][j];
                        hessenberg[n - 1][j] = q * z + p * hessenberg[n][j];
                        hessenberg[n][j] = q * hessenberg[n][j] - p * z;
                    }

                    // Column modification

                    for (int i = 0; i <= n; i++) {
                        z = hessenberg[i][n - 1];
                        hessenberg[i][n - 1] = q * z + p * hessenberg[i][n];
                        hessenberg[i][n] = q * hessenberg[i][n] - p * z;
                    }

                    // Accumulate transformations

                    for (int i = low; i <= high; i++) {
                        z = eigenVectors[i][n - 1];
                        eigenVectors[i][n - 1] = q * z + p * eigenVectors[i][n];
                        eigenVectors[i][n] = q * eigenVectors[i][n] - p * z;
                    }

                    // Complex pair

                } else {
                    d[n - 1] = x + p;
                    d[n] = x + p;
                    e[n - 1] = z;
                    e[n] = -z;
                }
                n = n - 2;
                iter = 0;

                // No convergence yet

            } else {

                // Form shift

                x = hessenberg[n][n];
                y = 0.0;
                w = 0.0;
                if (l < n) {
                    y = hessenberg[n - 1][n - 1];
                    w = hessenberg[n][n - 1] * hessenberg[n - 1][n];
                }

                // Wilkinson's original ad hoc shift

                if (iter == 10) {
                    exshift += x;
                    for (int i = low; i <= n; i++) {
                        hessenberg[i][i] -= x;
                    }
                    s = Math.abs(hessenberg[n][n - 1]) + Math.abs(hessenberg[n - 1][n - 2]);
                    x = y = 0.75 * s;
                    w = -0.4375 * s * s;
                }

                // MATLAB's new ad hoc shift

                if (iter == 30) {
                    s = (y - x) / 2.0;
                    s = s * s + w;
                    if (s > 0) {
                        s = Math.sqrt(s);
                        if (y < x) {
                            s = -s;
                        }
                        s = x - w / ((y - x) / 2.0 + s);
                        for (int i = low; i <= n; i++) {
                            hessenberg[i][i] -= s;
                        }
                        exshift += s;
                        x = y = w = 0.964;
                    }
                }

                iter = iter + 1;   // (Could check iteration count here.)

                // Look for two consecutive small sub-diagonal elements

                int m = n - 2;
                while (m >= l) {
                    z = hessenberg[m][m];
                    r = x - z;
                    s = y - z;
                    p = (r * s - w) / hessenberg[m + 1][m] + hessenberg[m][m + 1];
                    q = hessenberg[m + 1][m + 1] - z - r - s;
                    r = hessenberg[m + 2][m + 1];
                    s = Math.abs(p) + Math.abs(q) + Math.abs(r);
                    p = p / s;
                    q = q / s;
                    r = r / s;
                    if (m == l) {
                        break;
                    }
                    if (Math.abs(hessenberg[m][m - 1]) * (Math.abs(q) + Math.abs(r))
                            < eps * (Math.abs(p) * (Math.abs(hessenberg[m - 1][m - 1]) + Math.abs(z)
                            + Math.abs(hessenberg[m + 1][m + 1])))) {
                        break;
                    }
                    m--;
                }

                for (int i = m + 2; i <= n; i++) {
                    hessenberg[i][i - 2] = 0.0;
                    if (i > m + 2) {
                        hessenberg[i][i - 3] = 0.0;
                    }
                }

                // Double QR step involving rows l:n and columns m:n

                for (int k = m; k <= n - 1; k++) {
                    boolean notlast = (k != n - 1);
                    if (k != m) {
                        p = hessenberg[k][k - 1];
                        q = hessenberg[k + 1][k - 1];
                        r = (notlast ? hessenberg[k + 2][k - 1] : 0.0);
                        x = Math.abs(p) + Math.abs(q) + Math.abs(r);
                        if (x != 0.0) {
                            p = p / x;
                            q = q / x;
                            r = r / x;
                        }
                    }
                    if (x == 0.0) {
                        break;
                    }
                    s = Math.sqrt(p * p + q * q + r * r);
                    if (p < 0) {
                        s = -s;
                    }
                    if (s != 0) {
                        if (k != m) {
                            hessenberg[k][k - 1] = -s * x;
                        } else if (l != m) {
                            hessenberg[k][k - 1] = -hessenberg[k][k - 1];
                        }
                        p = p + s;
                        x = p / s;
                        y = q / s;
                        z = r / s;
                        q = q / p;
                        r = r / p;

                        // Row modification

                        for (int j = k; j < nn; j++) {
                            p = hessenberg[k][j] + q * hessenberg[k + 1][j];
                            if (notlast) {
                                p = p + r * hessenberg[k + 2][j];
                                hessenberg[k + 2][j] = hessenberg[k + 2][j] - p * z;
                            }
                            hessenberg[k][j] = hessenberg[k][j] - p * x;
                            hessenberg[k + 1][j] = hessenberg[k + 1][j] - p * y;
                        }

                        // Column modification

                        for (int i = 0; i <= Math.min(n, k + 3); i++) {
                            p = x * hessenberg[i][k] + y * hessenberg[i][k + 1];
                            if (notlast) {
                                p = p + z * hessenberg[i][k + 2];
                                hessenberg[i][k + 2] = hessenberg[i][k + 2] - p * r;
                            }
                            hessenberg[i][k] = hessenberg[i][k] - p;
                            hessenberg[i][k + 1] = hessenberg[i][k + 1] - p * q;
                        }

                        // Accumulate transformations

                        for (int i = low; i <= high; i++) {
                            p = x * eigenVectors[i][k] + y * eigenVectors[i][k + 1];
                            if (notlast) {
                                p = p + z * eigenVectors[i][k + 2];
                                eigenVectors[i][k + 2] = eigenVectors[i][k + 2] - p * r;
                            }
                            eigenVectors[i][k] = eigenVectors[i][k] - p;
                            eigenVectors[i][k + 1] = eigenVectors[i][k + 1] - p * q;
                        }
                    }  // (s != 0)
                }  // k loop
            }  // check convergence
        }  // while (n >= low)

        // Backsubstitute to find vectors of upper triangular form

        if (norm == 0.0) {
            return;
        }

        for (n = nn - 1; n >= 0; n--) {
            p = d[n];
            q = e[n];

            // Real vector

            if (q == 0) {
                int l = n;
                hessenberg[n][n] = 1.0;
                for (int i = n - 1; i >= 0; i--) {
                    w = hessenberg[i][i] - p;
                    r = 0.0;
                    for (int j = l; j <= n; j++) {
                        r = r + hessenberg[i][j] * hessenberg[j][n];
                    }
                    if (e[i] < 0.0) {
                        z = w;
                        s = r;
                    } else {
                        l = i;
                        if (e[i] == 0.0) {
                            if (w != 0.0) {
                                hessenberg[i][n] = -r / w;
                            } else {
                                hessenberg[i][n] = -r / (eps * norm);
                            }

                            // Solve real equations

                        } else {
                            x = hessenberg[i][i + 1];
                            y = hessenberg[i + 1][i];
                            q = (d[i] - p) * (d[i] - p) + e[i] * e[i];
                            t = (x * s - z * r) / q;
                            hessenberg[i][n] = t;
                            if (Math.abs(x) > Math.abs(z)) {
                                hessenberg[i + 1][n] = (-r - w * t) / x;
                            } else {
                                hessenberg[i + 1][n] = (-s - y * t) / z;
                            }
                        }

                        // Overflow control

                        t = Math.abs(hessenberg[i][n]);
                        if ((eps * t) * t > 1) {
                            for (int j = i; j <= n; j++) {
                                hessenberg[j][n] = hessenberg[j][n] / t;
                            }
                        }
                    }
                }

                // Complex vector

            } else if (q < 0) {
                int l = n - 1;

                // Last vector component imaginary so matrix is triangular

                if (Math.abs(hessenberg[n][n - 1]) > Math.abs(hessenberg[n - 1][n])) {
                    hessenberg[n - 1][n - 1] = q / hessenberg[n][n - 1];
                    hessenberg[n - 1][n] = -(hessenberg[n][n] - p) / hessenberg[n][n - 1];
                } else {
                    cdiv(0.0, -hessenberg[n - 1][n], hessenberg[n - 1][n - 1] - p, q);
                    hessenberg[n - 1][n - 1] = cdivr;
                    hessenberg[n - 1][n] = cdivi;
                }
                hessenberg[n][n - 1] = 0.0;
                hessenberg[n][n] = 1.0;
                for (int i = n - 2; i >= 0; i--) {
                    double ra, sa, vr, vi;
                    ra = 0.0;
                    sa = 0.0;
                    for (int j = l; j <= n; j++) {
                        ra = ra + hessenberg[i][j] * hessenberg[j][n - 1];
                        sa = sa + hessenberg[i][j] * hessenberg[j][n];
                    }
                    w = hessenberg[i][i] - p;

                    if (e[i] < 0.0) {
                        z = w;
                        r = ra;
                        s = sa;
                    } else {
                        l = i;
                        if (e[i] == 0) {
                            cdiv(-ra, -sa, w, q);
                            hessenberg[i][n - 1] = cdivr;
                            hessenberg[i][n] = cdivi;
                        } else {

                            // Solve complex equations

                            x = hessenberg[i][i + 1];
                            y = hessenberg[i + 1][i];
                            vr = (d[i] - p) * (d[i] - p) + e[i] * e[i] - q * q;
                            vi = (d[i] - p) * 2.0 * q;
                            if (vr == 0.0 & vi == 0.0) {
                                vr = eps * norm * (Math.abs(w) + Math.abs(q)
                                        + Math.abs(x) + Math.abs(y) + Math.abs(z));
                            }
                            cdiv(x * r - z * ra + q * sa, x * s - z * sa - q * ra, vr, vi);
                            hessenberg[i][n - 1] = cdivr;
                            hessenberg[i][n] = cdivi;
                            if (Math.abs(x) > (Math.abs(z) + Math.abs(q))) {
                                hessenberg[i + 1][n - 1] = (-ra - w * hessenberg[i][n - 1] + q * hessenberg[i][n]) / x;
                                hessenberg[i + 1][n] = (-sa - w * hessenberg[i][n] - q * hessenberg[i][n - 1]) / x;
                            } else {
                                cdiv(-r - y * hessenberg[i][n - 1], -s - y * hessenberg[i][n], z, q);
                                hessenberg[i + 1][n - 1] = cdivr;
                                hessenberg[i + 1][n] = cdivi;
                            }
                        }

                        // Overflow control

                        t = Math.max(Math.abs(hessenberg[i][n - 1]), Math.abs(hessenberg[i][n]));
                        if ((eps * t) * t > 1) {
                            for (int j = i; j <= n; j++) {
                                hessenberg[j][n - 1] = hessenberg[j][n - 1] / t;
                                hessenberg[j][n] = hessenberg[j][n] / t;
                            }
                        }
                    }
                }
            }
        }

        // Vectors of isolated roots

        for (int i = 0; i < nn; i++) {
            if (i < low | i > high) {
                System.arraycopy(hessenberg[i], i, eigenVectors[i], i, nn - i);
            }
        }

        // Back transformation to get eigenvectors of original matrix

        for (int j = nn - 1; j >= low; j--) {
            for (int i = low; i <= high; i++) {
                z = 0.0;
                for (int k = low; k <= Math.min(j, high); k++) {
                    z = z + eigenVectors[i][k] * hessenberg[k][j];
                }
                eigenVectors[i][j] = z;
            }
        }
    }
}