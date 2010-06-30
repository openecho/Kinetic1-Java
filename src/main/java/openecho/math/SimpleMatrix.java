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
 * Simple Matrix Implementation. This version is immutable.
 *
 * Holds and m-by-n matrix where,
 *
 * <pre>
 *         n1  n2  n3  ... nN
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
public class SimpleMatrix {
    /**
     * m dimension
     */
    private final int m;
    /**
     * n dimension
     */
    private final int n;
    /**
     * matrix data
     */
    private final double[][] data;

    public SimpleMatrix(int m, int n) {
        this.m = m;
        this.n = n;
        data = new double[m][n];
    }

    public SimpleMatrix(double[][] data) {
        m = data.length;
        n = data[0].length;
        this.data = new double[m][n];
        for (int i = 0; i < m; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, n);
        }
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public double[][] getData() {
        double[][] output = new double[m][n];
        for (int i = 0; i < m; i++) {
            System.arraycopy(this.data[i], 0, output[i], 0, n);
        }
        return output;
    }

    public double[] getRow(int i) {
        if(i>=m) {
            throw new IndexOutOfBoundsException();
        }
        return data[i];
    }

    public double[] getColumn(int i) {
        if(i>=n) {
            throw new IndexOutOfBoundsException();
        }
        double[] result = new double[m];
        for(int j=0;j<m;j++) {
            result[j]=data[j][i];
        }
        return result;
    }

    public boolean equals(SimpleMatrix b) {
        SimpleMatrix a = this;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                if(a.data[i][j]!=b.data[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public SimpleMatrix add(SimpleMatrix b) {
        SimpleMatrix a = this;
        if(a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        SimpleMatrix c = new SimpleMatrix(m,n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]+b.data[i][j];
            }
        }
        return c;
    }

    public SimpleMatrix subtract(SimpleMatrix b) {
        SimpleMatrix a = this;
        if(a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        SimpleMatrix c = new SimpleMatrix(m,n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]-b.data[i][j];
            }
        }
        return c;
    }

    public SimpleMatrix multiply(SimpleMatrix b) {
        SimpleMatrix a = this;
        if(a.n != b.m) {
            throw new RuntimeException("Matrix dimensions are not incorrect.");
        }
        SimpleMatrix c = new SimpleMatrix(a.m, b.n);
        for(int i=0;i<c.m;i++) {
            for(int j=0;j<c.n;j++) {
                for(int k=0;k<a.n;k++) {
                    c.data[i][j] += (a.data[i][k]*b.data[k][j]);
                }
            }
        }
        return c;
    }

    public SimpleMatrix transpose() {
        SimpleMatrix a = this;
        SimpleMatrix t = new SimpleMatrix(a.n, a.m);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                t.data[j][i] = a.data[i][j];
            }
        }
        return t;
    }

    public SimpleMatrix addScalar(double v) {
        SimpleMatrix a = this;
        SimpleMatrix c = new SimpleMatrix(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]+v;
            }
        }
        return c;
    }

    public SimpleMatrix subtractScalar(double v) {
        SimpleMatrix a = this;
        SimpleMatrix c = new SimpleMatrix(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]-v;
            }
        }
        return c;
    }

    public SimpleMatrix multiplyScalar(double v) {
        SimpleMatrix a = this;
        SimpleMatrix c = new SimpleMatrix(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]*v;
            }
        }
        return c;
    }

    public SimpleMatrix divideScalar(double v) {
        if(v==0) {
            throw new RuntimeException("Divide by Zero");
        }
        SimpleMatrix a = this;
        SimpleMatrix c = new SimpleMatrix(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]/v;
            }
        }
        return c;
    }

    @Override
    public String toString() {
        String dataString = "{";
        for(int i=0;i<m;i++) {
            dataString+="{";
            for(int j=0;j<n;j++) {
                dataString += data[i][j]+((j<n-1)?",":"");
            }
            dataString+="}"+((i<m-1)?",":"");
        }
        dataString+="}";
        return String.format("%s %s", super.toString(),dataString);
    }

    public static SimpleMatrix transpose(SimpleMatrix a) {
        SimpleMatrix t = new SimpleMatrix(a.n, a.m);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                t.data[j][i] = a.data[i][j];
            }
        }
        return t;
    }

    public static SimpleMatrix identity(int n) {
        SimpleMatrix i = new SimpleMatrix(n, n);
        for (int j = 0; j < n; j++) {
            i.data[j][j] = 1;
        }
        return i;
    }

    public static SimpleMatrix random(int m, int n) {
        SimpleMatrix r = new SimpleMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r.data[i][j] = Math.random();
            }
        }
        return r;
    }

    public static SimpleMatrix random(int m, int n, double lowerBound, double higherBound) {
        SimpleMatrix r = new SimpleMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r.data[i][j] = (Math.random()*(higherBound-lowerBound))+lowerBound;
            }
        }
        return r;
    }

    public static SimpleMatrix generate(int m, int n, double v) {
        SimpleMatrix g = new SimpleMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                g.data[i][j] = v;
            }
        }
        return g;
    }

    public static SimpleMatrix oneMatrix(int m, int n) {
        return generate(m,n,1D);
    }
}
