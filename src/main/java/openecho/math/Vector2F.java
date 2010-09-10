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
 * Performance optimised 2 dimensional float vector. This class can be mutable
 * or immutable.
 *
 * @author openecho
 */
public class Vector2F extends VectorF {
    /**
     * Instance variables for x and y components.
     */
    float x, y;

    public static final Vector2F ZERO, X_UNIT, Y_UNIT;

    static {
        ZERO = new Vector2F(0F, 0F);
        X_UNIT = new Vector2F(1F, 0F);
        Y_UNIT = new Vector2F(0F, 1F);
    }

    /**
     * Default constructor (defaults to mutable 0,0).
     */
    public Vector2F() {
        super(2, true);
        this.x = 0;
        this.y = 0;
    }

    /**
     * Constructor allowing mutable flag (defaults to 0,0).
     * @param mutable mutable flag
     */
    public Vector2F(boolean mutable) {
        super(2, mutable);
        this.x = 0;
        this.y = 0;
    }

    /**
     * Default x,y constructor (defaults to not mutable).
     * @param x component for x
     * @param y component for y
     */
    public Vector2F(float x, float y) {
        super(2, false);
        this.x = x;
        this.y = y;
    }

    /**
     * Default constructor with mutable flag.
     * @param x component for x
     * @param y component for y
     * @param mutable flag to make this instance mutable
     */
    public Vector2F(float x, float y, boolean mutable) {
        super(2, mutable);
        this.x = x;
        this.y = y;
    }

    public Vector2F(Number[] data) {
        super(2, false);
        if (Array.getLength(data) != 2) {
            throw new IllegalArgumentException("Number[] data does not have cardinality of three");
        }
        x = data[X].floatValue();
        y = data[Y].floatValue();
    }

    public Vector2F(Number[] data, boolean mutable) {
        super(2, mutable);
        if (Array.getLength(data) != 2) {
            throw new IllegalArgumentException("Number[] data does not have cardinality of three");
        }
        x = data[X].floatValue();
        y = data[Y].floatValue();
    }

    @Override
    protected final void initData(Number[] data) {
        if (Array.getLength(data) != 2) {
            throw new IllegalArgumentException("Number[] data does not have cardinality of three");
        }
        x = data[X].floatValue();
        y = data[Y].floatValue();
    }

    @Override
    protected final void initData(int i, Number data) {
        if (i == X) {
            x = data.floatValue();
        } else if (i == Y) {
            y = data.floatValue();
        } else {
            throw new IllegalArgumentException("index i must be 0 <= i < 2");
        }
    }

    public final float getX() {
        return x;
    }

    public final void setX(float x) {
        if (!mutable) {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
        this.x = x;
    }

    public final float getY() {
        return y;
    }

    public final void setY(float y) {
        if (!mutable) {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
        this.y = y;
    }


    @Override
    public final Float[] getData() {
        return new Float[]{x, y};
    }

    @Override
    public final Float getData(int i) {
        if (i == X) {
            return x;
        } else if (i == Y) {
            return y;
        } else {
            throw new IllegalArgumentException("index i must be 0 <= i < 2");
        }
    }

    @Override
    public final void setData(Number[] data) {
        if (mutable) {
            if (Array.getLength(data) != 2) {
                throw new IllegalArgumentException("Number[] data does not have cardinality of three");
            }
            x = data[X].floatValue();
            y = data[Y].floatValue();
        } else {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
    }

    @Override
    public final void setData(int i, Number data) {
        if (mutable) {
            if (i == X) {
                x = data.floatValue();
            } else if (i == Y) {
                y = data.floatValue();
            } else {
                throw new IllegalArgumentException("index i must be 0 <= i < 2");
            }
        } else {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
    }

    @Override
    public final Vector2F negative() {
        if (mutable) {
            x *= -1;
            y *= -1;
            return this;
        } else {
            return new Vector2F(x * -1, y * -1);
        }
    }

    @Override
    public final Vector2F normalise() {
        float m = magnitude();
        if (m == 0) {
            return Vector2F.zero();
        }
        if (mutable) {
            x = x / m;
            y = y / m;
            return this;
        } else {
            return new Vector2F(x / m, y / m);
        }
    }

    @Override
    public final Vector2F add(Vector b) {
        if (n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        if (mutable) {
            x += b.getData(X).floatValue();
            y += b.getData(Y).floatValue();
            return this;
        } else {
            return new Vector2F(x + b.getData(X).floatValue(), y + b.getData(Y).floatValue());
        }
    }

    public final Vector2F add(Vector2F b) {
        return add2F(b);
    }

    public final Vector2F add2F(Vector2F b) {
        if (mutable) {
            x += b.x;
            y += b.y;
            return this;
        } else {
            return new Vector2F(x + b.x, y + b.y);
        }
    }

    @Override
    public final Vector2F subtract(Vector b) {
        if (n != b.n) {
            throw new RuntimeException("Vector dimensions are not equal.");
        }
        if (mutable) {
            x -= b.getData(X).floatValue();
            y -= b.getData(Y).floatValue();
            return this;
        } else {
            return new Vector2F(x - b.getData(X).floatValue(), y - b.getData(Y).floatValue());
        }
    }

    public final Vector2F subtract(Vector2F b) {
        return subtract2F(b);
    }

    public final Vector2F subtract2F(Vector2F b) {
        if (mutable) {
            x -= b.x;
            y -= b.y;
            return this;
        } else {
            return new Vector2F(x - b.x, y - b.y);
        }
    }

    @Override
    public final Vector2F cross(Vector b) {
        throw new UnsupportedOperationException("Cross does not apply to a two dimensional vector.");
    }

    @Override
    public final Vector2F addScalar(Number v) {
        if (mutable) {
            x += v.floatValue();
            y += v.floatValue();
            return this;
        } else {
            return new Vector2F(x + v.floatValue(), y + v.floatValue());
        }
    }

    public final Vector2F addScalar(float v) {
        return addScalar2F(v);
    }

    public final Vector2F addScalar2F(float v) {
        if (mutable) {
            x += v;
            y += v;
            return this;
        } else {
            return new Vector2F(x + v, y + v);
        }
    }

    @Override
    public final Vector2F subtractScalar(Number v) {
        if (mutable) {
            x -= v.floatValue();
            y -= v.floatValue();
            return this;
        } else {
            return new Vector2F(x - v.floatValue(), y - v.floatValue());
        }
    }

    public final Vector2F subtractScalar(float v) {
        return subtractScalar2F(v);
    }

    public final Vector2F subtractScalar2F(float v) {
        if (mutable) {
            x -= v;
            y -= v;
            return this;
        } else {
            return new Vector2F(x - v, y - v);
        }
    }

    @Override
    public final Vector2F multiplyScalar(Number v) {
        if (mutable) {
            x *= v.floatValue();
            y *= v.floatValue();
            return this;
        } else {
            return new Vector2F(x * v.floatValue(), y * v.floatValue());
        }
    }

    public final Vector2F multiplyScalar(float v) {
        return multiplyScalar2F(v);
    }

    public final Vector2F multiplyScalar2F(float v) {
        if (mutable) {
            x *= v;
            y *= v;
            return this;
        } else {
            return new Vector2F(x * v, y * v);
        }
    }

    @Override
    public final Vector2F divideScalar(Number v) {
        if (v.intValue() == 0) {
            throw new RuntimeException("Divide By Zero.");
        }
        if (mutable) {
            x /= v.floatValue();
            y /= v.floatValue();
            return this;
        } else {
            return new Vector2F(x / v.floatValue(), y / v.floatValue());
        }
    }

    public final Vector2F divideScalar(float v) {
        return divideScalar2F(v);
    }

    public final Vector2F divideScalar2F(float v) {
        if (v == 0F) {
            throw new RuntimeException("Divide By Zero.");
        }
        if (mutable) {
            x /= v;
            y /= v;
            return this;
        } else {
            return new Vector2F(x / v, y / v);
        }
    }

    public static Vector2F zero() {
        return ZERO;
    }
}
