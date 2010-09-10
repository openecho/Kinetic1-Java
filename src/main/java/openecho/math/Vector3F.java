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
 * Performance optimised 3 dimensional float vector. This class can be mutable 
 * or immutable.
 *
 * @author openecho
 * @version 1.0.0
 */
public final class Vector3F extends VectorF {

    /**
     * Instance variables for x, y and z components.
     */
    float x, y, z;

    public static final Vector3F ZERO, X_UNIT, Y_UNIT, Z_UNIT;

    static {
        ZERO = new Vector3F(0F, 0F, 0F);
        X_UNIT = new Vector3F(1F, 0F, 0F);
        Y_UNIT = new Vector3F(0F, 1F, 0F);
        Z_UNIT = new Vector3F(0F, 0F, 1F);
    }

    /**
     * Default constructor (defaults to mutable 0,0,0).
     */
    public Vector3F() {
        super(3, true);
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Constructor allowing mutable flag (defaults to 0,0,0).
     * @param mutable mutable flag
     */
    public Vector3F(boolean mutable) {
        super(3, mutable);
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Default constructor (defaults to not mutable).
     * @param x component for x
     * @param y component for y
     * @param z component for z
     */
    public Vector3F(float x, float y, float z) {
        super(3, false);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Default constructor with mutable flag.
     * @param x component for x
     * @param y component for y
     * @param z component for z
     * @param mutable flag to make this instance mutable
     */
    public Vector3F(float x, float y, float z, boolean mutable) {
        super(3, mutable);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3F(Number[] data) {
        super(3, false);
        if (Array.getLength(data) != 3) {
            throw new IllegalArgumentException("Number[] data does not have cardinality of three");
        }
        x = data[X].floatValue();
        y = data[Y].floatValue();
        z = data[Z].floatValue();
    }

    public Vector3F(Number[] data, boolean mutable) {
        super(3, mutable);
        if (Array.getLength(data) != 3) {
            throw new IllegalArgumentException("Number[] data does not have cardinality of three");
        }
        x = data[X].floatValue();
        y = data[Y].floatValue();
        z = data[Z].floatValue();
    }

    @Override
    protected final void initData(Number[] data) {
        if (Array.getLength(data) != 3) {
            throw new IllegalArgumentException("Number[] data does not have cardinality of three");
        }
        x = data[X].floatValue();
        y = data[Y].floatValue();
        z = data[Z].floatValue();
    }

    @Override
    protected final void initData(int i, Number data) {
        if (i == X) {
            x = data.floatValue();
        } else if (i == Y) {
            y = data.floatValue();
        } else if (i == Z) {
            z = data.floatValue();
        } else {
            throw new IllegalArgumentException("index i must be 0 <= i < 3");
        }
    }

    public final float getX() {
        return x;
    }

    public final void setX(float x) {
        if (!mutate) {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
        this.x = x;
    }

    public final float getY() {
        return y;
    }

    public final void setY(float y) {
        if (!mutate) {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
        this.y = y;
    }

    public final float getZ() {
        return z;
    }

    public final void setZ(float z) {
        if (!mutate) {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
        this.z = z;
    }

    @Override
    public final Float[] getData() {
        return new Float[]{x, y, z};
    }

    @Override
    public final Float getData(int i) {
        if (i == X) {
            return x;
        } else if (i == Y) {
            return y;
        } else if (i == Z) {
            return z;
        } else {
            throw new IllegalArgumentException("index i must be 0 <= i < 3");
        }
    }

    @Override
    public final void setData(Number[] data) {
        if (mutate) {
            if (Array.getLength(data) != 3) {
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
    public final void setData(int i, Number data) {
        if (mutate) {
            if (i == X) {
                x = data.floatValue();
            } else if (i == Y) {
                y = data.floatValue();
            } else if (i == Z) {
                z = data.floatValue();
            } else {
                throw new IllegalArgumentException("index i must be 0 <= i < 3");
            }
        } else {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
    }

    @Override
    public final Vector3F negative() {
        if (mutate) {
            x *= -1;
            y *= -1;
            z *= -1;
            return this;
        } else {
            return new Vector3F(x * -1, y * -1, z * -1);
        }
    }

    @Override
    public final Vector3F normalise() {
        float m = magnitude();
        if (m == 0) {
            return Vector3F.zero();
        }
        if (mutate) {
            x = x / m;
            y = y / m;
            z = z / m;
            return this;
        } else {
            return new Vector3F(x / m, y / m, z / m);
        }
    }

    @Override
    public final Vector3F add(Vector b) {
        if (n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        if (mutate) {
            x += b.getData(X).floatValue();
            y += b.getData(Y).floatValue();
            z += b.getData(Z).floatValue();
            return this;
        } else {
            return new Vector3F(x + b.getData(X).floatValue(), y + b.getData(Y).floatValue(), z + b.getData(Z).floatValue());
        }
    }

    public final Vector3F add(Vector3F b) {
        return add3F(b);
    }

    public final Vector3F add3F(Vector3F b) {
        if (mutate) {
            x += b.x;
            y += b.y;
            z += b.y;
            return this;
        } else {
            return new Vector3F(x + b.x, y + b.y, z + b.z);
        }
    }

    @Override
    public final Vector3F subtract(Vector b) {
        if (n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        if (mutate) {
            x -= b.getData(X).floatValue();
            y -= b.getData(Y).floatValue();
            z -= b.getData(Z).floatValue();
            return this;
        } else {
            return new Vector3F(x - b.getData(X).floatValue(), y - b.getData(Y).floatValue(), z - b.getData(Z).floatValue());
        }
    }

    public final Vector3F subtract(Vector3F b) {
        return subtract3F(b);
    }

    public final Vector3F subtract3F(Vector3F b) {
        if (mutate) {
            x -= b.x;
            y -= b.y;
            z -= b.y;
            return this;
        } else {
            return new Vector3F(x - b.x, y - b.y, z - b.z);
        }
    }

    @Override
    public final Vector3F cross(Vector b) {
        if (b.n != 3) {
            throw new RuntimeException("Vector dimensions are not both equal to three.");
        }
        float xCross, yCross, zCross;
        xCross = y * b.getData(Z).floatValue() - z * b.getData(Y).floatValue();
        yCross = z * b.getData(X).floatValue() - x * b.getData(Z).floatValue();
        zCross = x * b.getData(Y).floatValue() - y * b.getData(X).floatValue();
        if (mutate) {
            x = xCross;
            y = yCross;
            z = zCross;
            return this;
        } else {
            return new Vector3F(xCross, yCross, zCross);
        }
    }

    public final Vector3F cross(Vector3F b) {
        return cross3F(b);
    }

    public final Vector3F cross3F(Vector3F b) {
        float xCross, yCross, zCross;
        xCross = y * b.z - z * b.y;
        yCross = z * b.x - x * b.z;
        zCross = x * b.y - y * b.x;
        if (mutate) {
            x = xCross;
            y = yCross;
            z = zCross;
            return this;
        } else {
            return new Vector3F(xCross, yCross, zCross);
        }
    }

    @Override
    public final Vector3F addScalar(Number v) {
        if (mutate) {
            x += v.floatValue();
            y += v.floatValue();
            z += v.floatValue();
            return this;
        } else {
            return new Vector3F(x + v.floatValue(), y + v.floatValue(), z + v.floatValue());
        }
    }

    public final Vector3F addScalar(float v) {
        return addScalar3F(v);
    }

    public final Vector3F addScalar3F(float v) {
        if (mutate) {
            x += v;
            y += v;
            z += v;
            return this;
        } else {
            return new Vector3F(x + v, y + v, z + v);
        }
    }

    @Override
    public final Vector3F subtractScalar(Number v) {
        if (mutate) {
            x -= v.floatValue();
            y -= v.floatValue();
            z -= v.floatValue();
            return this;
        } else {
            return new Vector3F(x - v.floatValue(), y - v.floatValue(), z - v.floatValue());
        }
    }

    public final Vector3F subtractScalar(float v) {
        return subtractScalar3F(v);
    }

    public final Vector3F subtractScalar3F(float v) {
        if (mutate) {
            x -= v;
            y -= v;
            z -= v;
            return this;
        } else {
            return new Vector3F(x - v, y - v, z - v);
        }
    }

    @Override
    public final Vector3F multiplyScalar(Number v) {
        if (mutate) {
            x *= v.floatValue();
            y *= v.floatValue();
            z *= v.floatValue();
            return this;
        } else {
            return new Vector3F(x * v.floatValue(), y * v.floatValue(), z * v.floatValue());
        }
    }

    public final Vector3F multiplyScalar(float v) {
        return multiplyScalar3F(v);
    }

    public final Vector3F multiplyScalar3F(float v) {
        if (mutate) {
            x *= v;
            y *= v;
            z *= v;
            return this;
        } else {
            return new Vector3F(x * v, y * v, z * v);
        }
    }

    @Override
    public final Vector3F divideScalar(Number v) {
        if (v.intValue() == 0) {
            throw new RuntimeException("Divide By Zero.");
        }
        if (mutate) {
            x /= v.floatValue();
            y /= v.floatValue();
            z /= v.floatValue();
            return this;
        } else {
            return new Vector3F(x / v.floatValue(), y / v.floatValue(), z / v.floatValue());
        }
    }

    public final Vector3F divideScalar(float v) {
        return divideScalar3F(v);
    }

    public final Vector3F divideScalar3F(float v) {
        if (v == 0F) {
            throw new RuntimeException("Divide By Zero.");
        }
        if (mutate) {
            x /= v;
            y /= v;
            z /= v;
            return this;
        } else {
            return new Vector3F(x / v, y / v, z / v);
        }
    }

    public static Vector3F zero() {
        return ZERO;
    }
}
