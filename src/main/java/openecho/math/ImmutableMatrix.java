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
 * Matrix Implementation. This version is immutable.
 *
 * Holds an m-by-n matrix where,
 *
 * <pre>
 *         n1  n2  n3 ...  nN
 *      +---------------------
 *   m1 | a11 a12 a13 ... aN1
 *   m2 | a21 a22 a23 ... aN1
 *   m3 | a31 a32 a33 ... aN1
 *   .. | ... ... ...     ...
 *   mN | aM1 aM2 aM3 ... aMN
 * </pre>
 *
 * @author openecho
 * @version 1.0.0
 */
public class ImmutableMatrix extends Matrix {

    public ImmutableMatrix(int m, int n) {
        super(m,n);
    }

    public ImmutableMatrix(double[][] data) {
        super(data);
    }

    @Override
    public double[][] getData() {
        double[][] output = new double[m][n];
        for (int i = 0; i < m; i++) {
            System.arraycopy(this.data[i], 0, output[i], 0, n);
        }
        return output;
    }

    @Override
    public final boolean isMutable() {
        return false;
    }
    
    @Override
    public double[] getRow(int i) {
        if(i>=m) {
            throw new IndexOutOfBoundsException();
        }
        double[] output = new double[n];
        System.arraycopy(this.data[i], 0, output, 0, n);
        return output;
    }

    @Override
    public Matrix add(Matrix b) {
        ImmutableMatrix a = this;
        if(a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        ImmutableMatrix c = new ImmutableMatrix(m,n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]+b.data[i][j];
            }
        }
        return c;
    }

    @Override
    public Matrix subtract(Matrix b) {
        ImmutableMatrix a = this;
        if(a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        ImmutableMatrix c = new ImmutableMatrix(m,n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]-b.data[i][j];
            }
        }
        return c;
    }

    @Override
    public Matrix multiply(Matrix b) {
        ImmutableMatrix a = this;
        if(a.n != b.m) {
            throw new RuntimeException("Matrix dimensions are not incorrect.");
        }
        ImmutableMatrix c = new ImmutableMatrix(a.m, b.n);
        for(int i=0;i<c.m;i++) {
            for(int j=0;j<c.n;j++) {
                for(int k=0;k<a.n;k++) {
                    c.data[i][j] += (a.data[i][k]*b.data[k][j]);
                }
            }
        }
        return c;
    }

    @Override
    public Matrix transpose() {
        ImmutableMatrix a = this;
        ImmutableMatrix t = new ImmutableMatrix(a.n, a.m);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                t.data[j][i] = a.data[i][j];
            }
        }
        return t;
    }

    @Override
    public Matrix addScalar(double v) {
        ImmutableMatrix a = this;
        ImmutableMatrix c = new ImmutableMatrix(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]+v;
            }
        }
        return c;
    }

    @Override
    public Matrix subtractScalar(double v) {
        ImmutableMatrix a = this;
        ImmutableMatrix c = new ImmutableMatrix(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]-v;
            }
        }
        return c;
    }

    @Override
    public Matrix multiplyScalar(double v) {
        ImmutableMatrix a = this;
        ImmutableMatrix c = new ImmutableMatrix(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]*v;
            }
        }
        return c;
    }

    @Override
    public Matrix divideScalar(double v) {
        if(v==0) {
            throw new RuntimeException("Divide by Zero");
        }
        ImmutableMatrix a = this;
        ImmutableMatrix c = new ImmutableMatrix(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]/v;
            }
        }
        return c;
    }
}
