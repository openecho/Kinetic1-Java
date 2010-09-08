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
public class MutableMatrixD extends MatrixD {

    public MutableMatrixD(int m, int n) {
        super(m,n);
    }

    public MutableMatrixD(Double[][] data) {
        super(data);
    }

    @Override
    public Double[][] getData() {
        return data;
    }

    @Override
    public Double getData(int i, int j) {
        return data[i][j];
    }

    @Override
    public void setData(Number[][] data) {
        m = data.length;
        n = data[0].length;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                this.data[i][j] = data[i][j].doubleValue();
            }
        }
    }

    @Override
    public void setData(int i, int j, Number data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final boolean isMutable() {
        return true;
    }

    @Override
    public Double[] getRow(int i) {
        if(i>=m) {
            throw new IndexOutOfBoundsException();
        }
        return data[i];
    }

    @Override
    public MatrixD add(Matrix b) {
        MutableMatrixD a = this;
        if(a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]+b.getData(i, j).doubleValue();
            }
        }
        return a;
    }

    @Override
    public MatrixD subtract(Matrix b) {
        MutableMatrixD a = this;
        if(a.m != b.m || a.n != b.n) {
            throw new RuntimeException("Matrix dimensions are not equal.");
        }
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]-b.getData(i, j).doubleValue();;
            }
        }
        return a;
    }

    @Override
    public MatrixD multiply(Matrix b) {
        MutableMatrixD a = this;
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
        MutableMatrixD a = this;
        MutableMatrixD t = new MutableMatrixD(a.n, a.m);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                t.data[j][i] = a.data[i][j];
            }
        }
        return t;
    }

    @Override
    public MatrixD addScalar(Number v) {
        MutableMatrixD a = this;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]+v.doubleValue();
            }
        }
        return a;
    }

    @Override
    public MatrixD subtractScalar(Number v) {
        MutableMatrixD a = this;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]-v.doubleValue();
            }
        }
        return a;
    }

    @Override
    public MatrixD multiplyScalar(Number v) {
        MutableMatrixD a = this;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]*v.doubleValue();
            }
        }
        return a;
    }

    @Override
    public MatrixD divideScalar(Number v) {
        MutableMatrixD a = this;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                a.data[i][j]=a.data[i][j]/v.doubleValue();
            }
        }
        return a;
    }

}
