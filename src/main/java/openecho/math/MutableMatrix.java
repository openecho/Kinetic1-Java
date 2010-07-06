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
 *
 * @author openecho
 */
public class MutableMatrix extends Matrix {

    public MutableMatrix(int m, int n) {
        super(m,n);
    }

    public MutableMatrix(double[][] data) {
        super(data);
    }

    @Override
    public double[][] getData() {
        return data;
    }

    @Override
    public final boolean isMutable() {
        return true;
    }

    @Override
    public double[] getRow(int i) {
        if(i>=m) {
            throw new IndexOutOfBoundsException();
        }
        return data[i];
    }

    @Override
    public Matrix add(Matrix b) {
        MutableMatrix a = this;
        if(a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]+b.data[i][j];
            }
        }
        return a;
    }

    @Override
    public Matrix subtract(Matrix b) {
        MutableMatrix a = this;
        if(a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]-b.data[i][j];
            }
        }
        return a;
    }

    @Override
    public Matrix multiply(Matrix b) {
        MutableMatrix a = this;
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
        MutableMatrix a = this;
        MutableMatrix t = new MutableMatrix(a.n, a.m);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                t.data[j][i] = a.data[i][j];
            }
        }
        return t;
    }

    @Override
    public Matrix addScalar(double v) {
        MutableMatrix a = this;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]+v;
            }
        }
        return a;
    }

    @Override
    public Matrix subtractScalar(double v) {
        MutableMatrix a = this;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]-v;
            }
        }
        return a;
    }

    @Override
    public Matrix multiplyScalar(double v) {
        MutableMatrix a = this;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]*v;
            }
        }
        return a;
    }

    @Override
    public Matrix divideScalar(double v) {
        MutableMatrix a = this;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]/v;
            }
        }
        return a;
    }
}
