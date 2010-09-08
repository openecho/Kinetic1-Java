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
 * MatrixD Implementation. This version is immutable.
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
public class ImmutableMatrixD extends MatrixD {

    public ImmutableMatrixD(int m, int n) {
        super(m,n);
    }

    public ImmutableMatrixD(Double[][] data) {
        super(data);
    }

    @Override
    public Double[][] getData() {
        Double[][] output = new Double[m][n];
        for (int i = 0; i < m; i++) {
            System.arraycopy(this.data[i], 0, output[i], 0, n);
        }
        return output;
    }

    @Override
    public Double getData(int i, int j) {
        Double output = new Double(data[i][j]);
        return output;
    }

    @Override
    public void setData(int i, int j, Number data) {
        throw new UnsupportedOperationException("Cannot Set Data on an ImmutableMatrix.");
    }

    @Override
    public void setData(Number[][] data) {
        throw new UnsupportedOperationException("Cannot Set Data on an ImmutableMatrix.");
    }

    @Override
    public final boolean isMutable() {
        return false;
    }
    
    @Override
    public Double[] getRow(int i) {
        if(i>=m) {
            throw new IndexOutOfBoundsException();
        }
        Double[] output = new Double[n];
        System.arraycopy(this.data[i], 0, output, 0, n);
        return output;
    }

    @Override
    public MatrixD add(Matrix b) {
        ImmutableMatrixD a = this;
        if(a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        ImmutableMatrixD c = new ImmutableMatrixD(m,n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]+b.getData(i, j).doubleValue();
            }
        }
        return c;
    }

    @Override
    public MatrixD subtract(Matrix b) {
        ImmutableMatrixD a = this;
        if(a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        ImmutableMatrixD c = new ImmutableMatrixD(m,n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]-b.getData(i, j).doubleValue();
            }
        }
        return c;
    }

    @Override
    public MatrixD multiply(Matrix b) {
        ImmutableMatrixD a = this;
        if(a.n != b.m) {
            throw new RuntimeException("Matrix dimensions are not incorrect.");
        }
        ImmutableMatrixD c = new ImmutableMatrixD(a.m, b.n);
        for(int i=0;i<c.m;i++) {
            for(int j=0;j<c.n;j++) {
                for(int k=0;k<a.n;k++) {
                    c.data[i][j] += (a.data[i][k]*b.getData(k, j).doubleValue());
                }
            }
        }
        return c;
    }

    @Override
    public MatrixD transpose() {
        ImmutableMatrixD a = this;
        ImmutableMatrixD t = new ImmutableMatrixD(a.n, a.m);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                t.data[j][i] = a.data[i][j];
            }
        }
        return t;
    }

    @Override
    public MatrixD addScalar(Number v) {
        ImmutableMatrixD a = this;
        ImmutableMatrixD c = new ImmutableMatrixD(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]+v.doubleValue();
            }
        }
        return c;
    }

    @Override
    public MatrixD subtractScalar(Number v) {
        ImmutableMatrixD a = this;
        ImmutableMatrixD c = new ImmutableMatrixD(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]-v.doubleValue();
            }
        }
        return c;
    }

    @Override
    public MatrixD multiplyScalar(Number v) {
        ImmutableMatrixD a = this;
        ImmutableMatrixD c = new ImmutableMatrixD(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]*v.doubleValue();
            }
        }
        return c;
    }

    @Override
    public MatrixD divideScalar(Number v) {
        if(v.doubleValue()==0) {
            throw new RuntimeException("Divide by Zero");
        }
        ImmutableMatrixD a = this;
        ImmutableMatrixD c = new ImmutableMatrixD(m, n);
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                c.data[i][j]=a.data[i][j]/v.doubleValue();
            }
        }
        return c;
    }
}
