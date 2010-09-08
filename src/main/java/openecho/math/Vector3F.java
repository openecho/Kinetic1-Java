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

import java.lang.reflect.Array;

/**
 *
 * @author openecho
 */
public class Vector3F extends VectorF {

    float x, y, z;

    public Vector3F(float x, float y, float z) {
        super(3, false);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3F(float x, float y, float z, boolean mutable) {
        super(3, mutable);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3F(Number[] data) {
        super(3,false);
        if(Array.getLength(data) != 3) {
            throw new IllegalArgumentException("Number[] data does not have cardinality of three");
        }
        x = data[X].floatValue();
        y = data[Y].floatValue();
        z = data[Z].floatValue();
    }

    public Vector3F(Number[] data, boolean mutable) {
        super(3, mutable);
        if(Array.getLength(data) != 3) {
            throw new IllegalArgumentException("Number[] data does not have cardinality of three");
        }
        x = data[X].floatValue();
        y = data[Y].floatValue();
        z = data[Z].floatValue();
    }

    @Override
    protected void initData(Number[] data) {
        if(Array.getLength(data) != 3) {
            throw new IllegalArgumentException("Number[] data does not have cardinality of three");
        }
        x = data[X].floatValue();
        y = data[Y].floatValue();
        z = data[Z].floatValue();
    }

    @Override
    protected void initData(int i, Number data) {
        if(i == X) {
            x = data.floatValue();
        } else if(i == Y) {
            y = data.floatValue();
        } else if(i == Z) {
            z = data.floatValue();
        } else {
            throw new IllegalArgumentException("index i must be 0 <= i < 3");
        }
    }

    @Override
    public Float[] getData() {
        return new Float[] {x,y,z};
    }

    @Override
    public Float getData(int i) {
        if(i == X) {
            return x;
        } else if(i == Y) {
            return y;
        } else if(i == Z) {
            return z;
        } else {
            throw new IllegalArgumentException("index i must be 0 <= i < 3");
        }
    }

    @Override
    public void setData(Number[] data) {
        if(mutable) {
            if(Array.getLength(data) != 3) {
                throw new IllegalArgumentException("Number[] data does not have cardinality of three");
            }
            x = data[X].floatValue();
            y = data[Y].floatValue();
            z = data[Z].floatValue();
        } else {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
    }

    @Override
    public void setData(int i, Number data) {
        if(mutable) {
            if(i == X) {
                x = data.floatValue();
            } else if(i == Y) {
                y = data.floatValue();
            } else if(i == Z) {
                z = data.floatValue();
            } else {
                throw new IllegalArgumentException("index i must be 0 <= i < 3");
            }
        } else {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
    }

    @Override
    public Vector3F negative() {
        if(mutable) {
            x *= -1;
            y *= -1;
            z *= -1;
            return this;
        } else {
            return new Vector3F(x*-1,y*-1,z*-1);
        }
    }

    @Override
    public VectorF normalise() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VectorF add(Vector b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VectorF subtract(Vector b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VectorF cross(Vector b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VectorF addScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VectorF subtractScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VectorF multiplyScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VectorF divideScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
