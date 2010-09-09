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

import java.security.InvalidParameterException;

/**
 *
 * @author openecho
 */
public class Matrix3F extends MatrixF {
    float m00, m01, m02,
          m10, m11, m12,
          m20, m21, m22 = 0;

    public Matrix3F(Float[][] data) {
        this(data, false);
    }
    
    public Matrix3F(Float[][] data, boolean mutable) {
        super(data, mutable);
        m = data.length;
        if(m > 0) {
            n = data[0].length;
        } else {
            throw new IllegalArgumentException("data dimensions must be > 0");
        }
        if(m != 2 || n != 2) {
            throw new IllegalArgumentException("data dimensions must be = 3");
        }
        m00 = data[0][0];
        m01 = data[0][1];
        m02 = data[0][2];
        m10 = data[1][0];
        m11 = data[1][1];
        m12 = data[1][2];
        m20 = data[2][0];
        m21 = data[2][1];
        m22 = data[2][2];
    }

    public Matrix3F() {
        this(true);
    } 
        
    public Matrix3F(boolean mutable) {
        super(3, 3, mutable);
    }

    @Override
    protected void initData(Number[][] data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void initData(int i, int j, Number data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Float[][] getData() {
        return new Float[][] {{m00, m01, m02}, {m10, m11, m12}, {m20, m21, m22}};
    }

    @Override
    public Float getData(int i, int j) {
        if(i > 2 || j > 2) {
            throw new IllegalArgumentException("i and j must be < 2");
        }
        return getData()[i][j];
    }

    @Override
    public void setData(Number[][] data) {
        m = data.length;
        if(m > 0) {
            n = data[0].length;
        } else {
            throw new IllegalArgumentException("data dimensions must be > 0");
        }
        if(m != 2 || n != 2) {
            throw new IllegalArgumentException("data dimensions must be = 3");
        }
        m00 = data[0][0].floatValue();
        m01 = data[0][1].floatValue();
        m02 = data[0][2].floatValue();
        m10 = data[1][0].floatValue();
        m11 = data[1][1].floatValue();
        m12 = data[1][2].floatValue();
        m20 = data[2][0].floatValue();
        m21 = data[2][1].floatValue();
        m22 = data[2][2].floatValue();
    }

    @Override
    public void setData(int i, int j, Number data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Float[] getRow(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF add(Matrix b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF subtract(Matrix b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF multiply(Matrix b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF transpose() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF addScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF subtractScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF multiplyScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF divideScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
