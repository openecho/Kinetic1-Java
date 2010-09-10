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
public abstract class Vector {

    /**
     * n dimension
     */
    int n;

    boolean mutable = false;
    
    public static final int X, Y, Z;

    static {
        X = 0;
        Y = 1;
        Z = 2;
    }


    public final int getN() {
        return n;
    }

    /**
     * Flag indicating if this version of the Vector is mutable.
     * @return boolean Vector mutable when true otherwise not mutable.
     */
    public boolean isMutable() {
        return mutable;
    }

    public void setMutable(boolean mutable) {
        this.mutable = mutable;
    }

    public abstract Number[] getData();

    public abstract Number getData(int i);

    public abstract void setData(Number[] data);

    public abstract void setData(int i, Number data);

    public abstract Number magnitude();

    public Number length() {
        return magnitude();
    }

    public abstract Vector negative();

    public abstract Vector normalise();

    @Override
    public boolean equals(Object b) {
        if(b instanceof Vector) {
            Number[] bData = ((Vector)b).getData();
            for (int i = 0; i < n; i++) {
                if (!getData(i).equals(bData[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + getData().hashCode();
        return hash;
    }

    public abstract Vector add(Vector b);

    public abstract Vector subtract(Vector b);

    public abstract Number dot(Vector b);

    public abstract Vector cross(Vector b);

    public abstract Vector addScalar(Number v);

    public abstract Vector subtractScalar(Number v);

    public abstract Vector multiplyScalar(Number v);

    public abstract Vector divideScalar(Number v);
}
