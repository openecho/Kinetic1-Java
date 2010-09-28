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

/** Decomposes a real matrix A into its eigenvalues and eigenvectors.
<P>
If A is symmetric, then A = V*D*V' where the eigenvalue matrix D is
diagonal and the eigenvector matrix V is orthogonal.
I.e. A = V.times(D.times(V.transpose())) and
V.times(V.transpose()) equals the identity matrix.
<P>
If A is not symmetric, then the eigenvalue matrix D is block diagonal
with the real eigenvalues in 1-by-1 blocks and any complex eigenvalues,
lambda + i*mu, in 2-by-2 blocks, [lambda, mu; -mu, lambda].  The
columns of V represent the eigenvectors in the sense that A*V = V*D,
i.e. A.times(V) equals V.times(D).  The matrix V may be badly
conditioned, or even singular, so the validity of the equation
A = V*D*inverse(V) depends upon V.cond().
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
    private double[][] ev;
    /**
     * Array for internal storage of nonsymmetric Hessenberg form.
     */
    private double[][] hes;
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
        ev = new double[n][n];
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
                System.arraycopy(aData[i], 0, ev[i], 0, n);
            }
            // Tridiagonalize.
            tred2();
            // Diagonalize.
            tql2();
        } else {
            hes = new double[n][n];
            ort = new double[n];
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < n; i++) {
                    hes[i][j] = aData[i][j];
                }
            }
            // Reduce to Hessenberg form.
            orthes();
            // Reduce Hessenberg to real Schur form.
            hqr2();
        }
        return true;
    }

    public Matrix getV() {
        return MatrixD.create(ev);
    }

    public double[] getRealEigenvalues() {
        return d;
    }

    public double[] getImagEigenvalues() {
        return e;
    }

    public MatrixD getD() {
        MatrixD x = MatrixD.empty(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
               x.setData(i, j, 0D);
            }
            x.setData(i,i, d[i]);
            if (e[i] > 0) {
                x.setData(i, i + 1, e[i]);
            } else if (e[i] < 0) {
                x.setData(i, i - 1, e[i]);
            }
        }
        return x;
    }

    // Symmetric Householder reduction to tridiagonal form.
    private void tred2() {
        System.arraycopy(ev[n - 1], 0, d, 0, n);
        // Householder reduction to tridiagonal form.
        for (int i = n - 1; i > 0; i--) {
            // Scale to avoid under/overflow.
            double scale = 0.0;
            double h = 0.0;
            for (int k = 0; k < i; k++) {
                scale = scale + QuickMath.abs(d[k]);
            }
            if (scale == 0.0) {
                e[i] = d[i - 1];
                for (int j = 0; j < i; j++) {
                    d[j] = ev[i - 1][j];
                    ev[i][j] = 0.0;
                    ev[j][i] = 0.0;
                }
            } else {
                // Generate Householder vector.
                for (int k = 0; k
                        < i; k++) {
                    d[k] /= scale;
                    h += d[k] * d[k];
                }
                double f = d[i - 1];
                double g = QuickMath.sqrt(h);
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
                    ev[j][i] = f;
                    g = e[j] + ev[j][j] * f;
                    for (int k = j + 1; k <= i - 1; k++) {
                        g += ev[k][j] * d[k];
                        e[k] += ev[k][j] * f;
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
                        ev[k][j] -= (f * e[k] + g * d[k]);
                    }
                    d[j] = ev[i - 1][j];
                    ev[i][j] = 0.0;
                }
            }
            d[i] = h;
        }
        // Accumulate transformations.
        for (int i = 0; i < n - 1; i++) {
            ev[n - 1][i] = ev[i][i];
            ev[i][i] = 1.0;
            double h = d[i + 1];
            if (h != 0.0) {
                for (int k = 0; k <= i; k++) {
                    d[k] = ev[k][i + 1] / h;
                }
                for (int j = 0; j <= i; j++) {
                    double g = 0.0;
                    for (int k = 0; k <= i; k++) {
                        g += ev[k][i + 1] * ev[k][j];
                    }
                    for (int k = 0; k <= i; k++) {
                        ev[k][j] -= g * d[k];
                    }
                }
            }
            for (int k = 0; k <= i; k++) {
                ev[k][i + 1] = 0.0;
            }
        }
        for (int j = 0; j < n; j++) {
            d[j] = ev[n - 1][j];
            ev[n - 1][j] = 0.0;
        }
        ev[n - 1][n - 1] = 1.0;
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
        double eps = QuickMath.pow(2.0, -52.0);
        for (int l = 0; l < n; l++) {
            // Find small subdiagonal element
            tst1 = QuickMath.max(tst1, QuickMath.abs(d[l]) + QuickMath.abs(e[l]));
            int m = l;
            while (m < n) {
                if (QuickMath.abs(e[m]) <= eps * tst1) {
                    break;
                }
                m++;
            } // If m == l, d[l] is an eigenvalue,
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
                            h = ev[k][i + 1];
                            ev[k][i + 1] = s * ev[k][i] + c * h;
                            ev[k][i] = c * ev[k][i] - s * h;
                        }
                    }
                    p = -s * s2 * c3 * el1 * e[l] / dl1;
                    e[l] = s * p;
                    d[l] = c * p;
                    // Check for convergence.
                } while (QuickMath.abs(e[l]) > eps * tst1);
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
                    p = ev[j][i];
                    ev[j][i] = ev[j][k];
                    ev[j][k] = p;
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
                scale = scale + QuickMath.abs(hes[i][m - 1]);
            }
            if (scale != 0.0) {
                // Compute Householder transformation.
                double h = 0.0;
                for (int i = high; i >= m; i--) {
                    ort[i] = this.hes[i][m - 1] / scale;
                    h += ort[i] * ort[i];
                }
                double g = QuickMath.sqrt(h);
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
                        f += ort[i] * this.hes[i][j];
                    }
                    f = f / h;
                    for (int i = m; i <= high; i++) {
                        this.hes[i][j] -= f * ort[i];
                    }
                }
                for (int i = 0; i <= high; i++) {
                    double f = 0.0;
                    for (int j = high; j >= m; j--) {
                        f += ort[j] * this.hes[i][j];
                    }
                    f = f / h;
                    for (int j = m; j <= high; j++) {
                        this.hes[i][j] -= f * ort[j];
                    }
                }
                ort[m] = scale * ort[m];
                this.hes[m][m - 1] = scale * g;
            }
        }
        // Accumulate transformations (Algol's ortran).
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ev[i][j] = (i == j ? 1.0 : 0.0);
            }
        }
        for (int m = high - 1; m >= low + 1; m--) {
            if (hes[m][m - 1] != 0.0) {
                for (int i = m + 1; i <= high; i++) {
                    ort[i] = hes[i][m - 1];
                }
                for (int j = m; j
                        <= high; j++) {
                    double g = 0.0;
                    for (int i = m; i <= high; i++) {
                        g += ort[i] * ev[i][j];
                    } // Double division avoids possible underflow
                    g = (g / ort[m]) / hes[m][m - 1];
                    for (int i = m; i <= high; i++) {
                        ev[i][j] += g * ort[i];
                    }
                }
            }
        }
    }
    // Complex scalar division.
    private transient double cdivr, cdivi;
    private void cdiv(double xr, double xi, double yr, double yi) {
        double r, d;
        if (QuickMath.abs(yr) > QuickMath.abs(yi)) {
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
        double eps = QuickMath.pow(2.0, -52.0);
        double exshift = 0.0;
        double p = 0, q = 0, r = 0, s = 0, z = 0, t, w, x, y;
        // Store roots isolated by balanc and compute matrix norm
        double norm = 0.0;
        for (int i = 0; i
                < nn; i++) {
            if (i < low | i > high) {
                d[i] = hes[i][i];
                e[i] = 0.0;
            }
            for (int j = QuickMath.max(i - 1, 0); j < nn; j++) {
                norm = norm + QuickMath.abs(hes[i][j]);
            }
        }
        // Outer loop over eigenvalue index
        int iter = 0;
        while (n >= low) {
            // Look for single small sub-diagonal element
            int l = n;
            while (l > low) {
                s = QuickMath.abs(hes[l - 1][l - 1]) + QuickMath.abs(hes[l][l]);
                if (s == 0.0) {
                    s = norm;
                }
                if (QuickMath.abs(hes[l][l - 1]) < eps * s) {
                    break;
                }
                l--;
            }
            // Check for convergence
            // One root found
            if (l == n) {
                hes[n][n] = hes[n][n] + exshift;
                d[n] = hes[n][n];
                e[n] = 0.0;
                n--;
                iter = 0;
                // Two roots found
            } else if (l == n - 1) {
                w = hes[n][n - 1] * hes[n - 1][n];
                p = (hes[n - 1][n - 1] - hes[n][n]) / 2.0;
                q = p * p + w;
                z = QuickMath.sqrt(QuickMath.abs(q));
                hes[n][n] = hes[n][n] + exshift;
                hes[n - 1][n - 1] = hes[n - 1][n - 1] + exshift;
                x = hes[n][n];
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
                    x = hes[n][n - 1];
                    s = QuickMath.abs(x) + QuickMath.abs(z);
                    p = x / s;
                    q = z / s;
                    r = QuickMath.sqrt(p * p + q * q);
                    p = p / r;
                    q = q / r;
                    // Row modification
                    for (int j = n - 1; j < nn; j++) {
                        z = hes[n - 1][j];
                        hes[n - 1][j] = q * z + p * hes[n][j];
                        hes[n][j] = q * hes[n][j] - p * z;
                    }
                    // Column modification
                    for (int i = 0; i <= n; i++) {
                        z = hes[i][n - 1];
                        hes[i][n - 1] = q * z + p * hes[i][n];
                        hes[i][n] = q * hes[i][n] - p * z;
                    }
                    // Accumulate transformations
                    for (int i = low; i
                            <= high; i++) {
                        z = ev[i][n - 1];
                        ev[i][n - 1] = q * z + p * ev[i][n];
                        ev[i][n] = q * ev[i][n] - p * z;
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
                x = hes[n][n];
                y = 0.0;
                w = 0.0;
                if (l < n) {
                    y = hes[n - 1][n - 1];
                    w = hes[n][n - 1] * hes[n - 1][n];
               } // Wilkinson's original ad hoc shift
                if (iter == 10) {
                    exshift += x;
                    for (int i = low; i<= n; i++) {
                        hes[i][i] -= x;
                    }
                    s = QuickMath.abs(hes[n][n - 1]) + QuickMath.abs(hes[n - 1][n - 2]);
                    x = y = 0.75 * s;
                    w = -0.4375 * s * s;
                }
                // MATLAB's new ad hoc shift
                if (iter == 30) {
                    s = (y - x) / 2.0;
                    s = s * s + w;
                    if (s > 0) {
                        s = QuickMath.sqrt(s);
                        if (y < x) {
                            s = -s;
                        }
                        s = x - w / ((y - x) / 2.0 + s);
                        for (int i = low; i <= n; i++) {
                            hes[i][i] -= s;
                        }
                        exshift += s;
                        x = y = w = 0.964;
                    }
                }
                iter = iter + 1;   // (Could check iteration count here.)
                // Look for two consecutive small sub-diagonal elements
                int m = n - 2;
                while (m >= l) {
                    z = hes[m][m];
                    r = x - z;
                    s = y - z;
                    p = (r * s - w) / hes[m + 1][m] + hes[m][m + 1];
                    q = hes[m + 1][m + 1] - z - r - s;
                    r = hes[m + 2][m + 1];
                    s = QuickMath.abs(p) + QuickMath.abs(q) + QuickMath.abs(r);
                    p = p / s;
                    q = q / s;
                    r = r / s;
                    if (m == l) {
                        break;
                    }
                    if (QuickMath.abs(hes[m][m - 1]) * (QuickMath.abs(q) + QuickMath.abs(r)) < eps * (QuickMath.abs(p) * (QuickMath.abs(hes[m - 1][m - 1]) + QuickMath.abs(z) + QuickMath.abs(hes[m + 1][m + 1])))) {
                        break;
                    }
                    m--;
                }
                for (int i = m + 2; i <= n; i++) {
                    hes[i][i - 2] = 0.0;
                    if (i > m + 2) {
                        hes[i][i - 3] = 0.0;
                    }
                }
                // Double QR step involving rows l:n and columns m:n
                for (int k = m; k <= n - 1; k++) {
                    boolean notlast = (k != n - 1);
                    if (k != m) {
                        p = hes[k][k - 1];
                        q = hes[k + 1][k - 1];
                        r = (notlast ? hes[k + 2][k - 1] : 0.0);
                        x = QuickMath.abs(p) + QuickMath.abs(q) + QuickMath.abs(r);
                        if (x != 0.0) {
                            p = p / x;
                            q = q / x;
                            r = r / x;
                        }
                    }
                    if (x == 0.0) {
                        break;
                    }
                    s = QuickMath.sqrt(p * p + q * q + r * r);
                    if (p < 0) {
                        s = -s;
                    }
                    if (s != 0) {
                        if (k != m) {
                            hes[k][k - 1] = -s * x;
                        } else if (l != m) {
                            hes[k][k - 1] = -hes[k][k - 1];
                        }
                        p = p + s;
                        x = p / s;
                        y = q / s;
                        z = r / s;
                        q = q / p;
                        r = r / p;
                        // Row modification
                        for (int j = k; j < nn; j++) {
                            p = hes[k][j] + q * hes[k + 1][j];
                            if (notlast) {
                                p = p + r * hes[k + 2][j];
                                hes[k + 2][j] = hes[k + 2][j] - p * z;
                            }
                            hes[k][j] = hes[k][j] - p * x;
                            hes[k + 1][j] = hes[k + 1][j] - p * y;
                        }
                        // Column modification
                        for (int i = 0; i <= QuickMath.min(n, k + 3); i++) {
                            p = x * hes[i][k] + y * hes[i][k + 1];
                            if (notlast) {
                                p = p + z * hes[i][k + 2];
                                hes[i][k + 2] = hes[i][k + 2] - p * r;
                            }
                            hes[i][k] = hes[i][k] - p;
                            hes[i][k + 1] = hes[i][k + 1] - p * q;
                        }
                        // Accumulate transformations
                        for (int i = low; i <= high; i++) {
                            p = x * ev[i][k] + y * ev[i][k + 1];
                            if (notlast) {
                                p = p + z * ev[i][k + 2];
                                ev[i][k + 2] = ev[i][k + 2] - p * r;
                            }
                            ev[i][k] = ev[i][k] - p;
                            ev[i][k + 1] = ev[i][k + 1] - p * q;
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
                hes[n][n] = 1.0;
                for (int i = n - 1; i >= 0; i--) {
                    w = hes[i][i] - p;
                    r = 0.0;
                    for (int j = l; j <= n; j++) {
                        r = r + hes[i][j] * hes[j][n];
                    }
                    if (e[i] < 0.0) {
                        z = w;
                        s = r;
                    } else {
                        l = i;
                        if (e[i] == 0.0) {
                            if (w != 0.0) {
                                hes[i][n] = -r / w;
                            } else {
                                hes[i][n] = -r / (eps * norm);
                            }
                            // Solve real equations
                        } else {
                            x = hes[i][i + 1];
                            y = hes[i + 1][i];
                            q = (d[i] - p) * (d[i] - p) + e[i] * e[i];
                            t = (x * s - z * r) / q;
                            hes[i][n] = t;
                            if (QuickMath.abs(x) > QuickMath.abs(z)) {
                                hes[i + 1][n] = (-r - w * t) / x;
                            } else {
                                hes[i + 1][n] = (-s - y * t) / z;
                            }
                        }
                        // Overflow control
                        t = QuickMath.abs(hes[i][n]);
                        if ((eps * t) * t > 1) {
                            for (int j = i; j  <= n; j++) {
                                hes[j][n] = hes[j][n] / t;
                            }
                        }
                    }
                }
                // Complex vector
           } else if (q < 0) {
                int l = n - 1;
                // Last vector component imaginary so matrix is triangular
                if (QuickMath.abs(hes[n][n - 1]) > QuickMath.abs(hes[n - 1][n])) {
                    hes[n - 1][n - 1] = q / hes[n][n - 1];
                    hes[n - 1][n] = -(hes[n][n] - p) / hes[n][n - 1];
                } else {
                    cdiv(0.0, -hes[n - 1][n], hes[n - 1][n - 1] - p, q);
                    hes[n - 1][n - 1] = cdivr;
                    hes[n - 1][n] = cdivi;
                }
                hes[n][n - 1] = 0.0;
                hes[n][n] = 1.0;
                for (int i = n - 2; i
                        >= 0; i--) {
                    double ra, sa, vr, vi;
                    ra = 0.0;
                    sa = 0.0;
                    for (int j = l; j
                            <= n; j++) {
                        ra = ra + hes[i][j] * hes[j][n - 1];
                        sa = sa + hes[i][j] * hes[j][n];
                    }
                    w = hes[i][i] - p;
                    if (e[i] < 0.0) {
                        z = w;
                        r = ra;
                        s = sa;
                    } else {
                        l = i;
                        if (e[i] == 0) {
                            cdiv(-ra, -sa, w, q);
                            hes[i][n - 1] = cdivr;
                            hes[i][n] = cdivi;
                        } else {
                            // Solve complex equations
                            x = hes[i][i + 1];
                            y = hes[i + 1][i];
                            vr = (d[i] - p) * (d[i] - p) + e[i] * e[i] - q * q;
                            vi = (d[i] - p) * 2.0 * q;


                            if (vr == 0.0 & vi == 0.0) {
                                vr = eps * norm * (QuickMath.abs(w) + QuickMath.abs(q)
                                        + QuickMath.abs(x) + QuickMath.abs(y) + QuickMath.abs(z));
                            }
                            cdiv(x * r - z * ra + q * sa, x * s - z * sa - q * ra, vr, vi);
                            hes[i][n - 1] = cdivr;
                            hes[i][n] = cdivi;
                           if (QuickMath.abs(x) > (QuickMath.abs(z) + QuickMath.abs(q))) {
                                hes[i + 1][n - 1] = (-ra - w * hes[i][n - 1] + q * hes[i][n]) / x;
                                hes[i + 1][n] = (-sa - w * hes[i][n] - q * hes[i][n - 1]) / x;
                            } else {
                                cdiv(-r - y * hes[i][n - 1], -s - y * hes[i][n], z, q);
                                hes[i + 1][n - 1] = cdivr;
                                hes[i + 1][n] = cdivi;
                            }
                        }
                        // Overflow control
                        t = QuickMath.max(QuickMath.abs(hes[i][n - 1]), QuickMath.abs(hes[i][n]));
                        if ((eps * t) * t > 1) {
                            for (int j = i; j
                                    <= n; j++) {
                                hes[j][n - 1] = hes[j][n - 1] / t;
                                hes[j][n] = hes[j][n] / t;


                            }
                        }
                    }
                }
            }
        }
        // Vectors of isolated roots
        for (int i = 0; i
                < nn; i++) {
            if (i < low | i > high) {
                for (int j = i; j
                        < nn; j++) {
                    ev[i][j] = hes[i][j];


                }
            }
        }
        // Back transformation to get eigenvectors of original matrix
        for (int j = nn - 1; j
                >= low; j--) {
            for (int i = low; i
                    <= high; i++) {
                z = 0.0;


                for (int k = low; k
                        <= QuickMath.min(j, high); k++) {
                    z = z + ev[i][k] * hes[k][j];


                }
                ev[i][j] = z;

            }
        }
    }
}
