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
 * Array based float Vector.
 *
 * @author openecho
 * @version 1.0.0
 */
public class ArrayVectorF extends VectorF {

    Float[] data;

    public ArrayVectorF(int n) {
        super(n);
        data = new Float[n];
        for (int i = 0; i < n; i++) {
            data[i] = 0F;
        }
    }

    public ArrayVectorF(int n, boolean mutate) {
        super(n, mutate);
        data = new Float[n];
        for (int i = 0; i < n; i++) {
            data[i] = 0F;
        }
    }

    public ArrayVectorF(Number[] data) {
        super(data);
    }

    public ArrayVectorF(Number[] data, boolean mutable) {
        super(data, mutable);
    }

    protected void initData(Number[] data) {
        n = data.length;
        if (data instanceof Float[]) {
            this.data = (Float[]) data;
        } else {
            for (int i = 0; i < n; i++) {
                this.data[i] = data[i].floatValue();
            }
        }
    }

    protected void initData(int i, Number data) {
        if (data instanceof Float) {
            this.data[i] = (Float) data;
        } else {
            this.data[i] = data.floatValue();
        }
    }

    @Override
    public final Float[] getData() {
        if (mutate) {
            return data;
        } else {
            Float[] output = new Float[n];
            System.arraycopy(this.data, 0, output, 0, n);
            return output;
        }
    }

    @Override
    public Float getData(int i) {
        if (!(i < n)) {
            throw new IndexOutOfBoundsException(String.format("i value of %s is not < then n of %s", i, n));
        }
        if (mutate) {
            return data[i];
        } else {
            Float output = new Float(data[i]);
            return output;
        }
    }

    @Override
    public void setData(Number[] data) {
        if (mutate) {
            n = data.length;
            if (data instanceof Float[]) {
                this.data = (Float[]) data;
            } else {
                for (int i = 0; i < n; i++) {
                    this.data[i] = data[i].floatValue();

                }
            }
        } else {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
    }

    @Override
    public void setData(int i, Number data) {
        if (mutate) {
            if (data instanceof Float) {
                this.data[i] = (Float) data;
            } else {
                this.data[i] = data.floatValue();
            }
        } else {
            throw new UnsupportedOperationException("Cannot Set Data on an Immutable Vector.");
        }
    }

    @Override
    public final VectorF negative() {
        if(mutate) {
            for(int i=0;i<n;i++) {
                data[i] *= -1;
            }
            return this;
        } else {
            ArrayVectorF b = new ArrayVectorF(n);
            for (int i = 0; i < n; i++) {
                b.data[i] = data[i] * -1;
            }
            return b;
        }
    }

    @Override
    public final VectorF normalise() {
        float m = magnitude();
        if(m == 0) {
            // TODO: What does one do here? Error?
            return null;
        }
        if(mutate) {
            for(int i=0;i<n;i++) {
               data[i] = data[i] / m;
            }
            return this;
        } else {
            ArrayVectorF b = new ArrayVectorF(n);
            for (int i = 0; i < n; i++) {
                data[i] = data[i] / m;
            }
            return b;
        }
    }

    @Override
    public final ArrayVectorF add(Vector b) {
        if(mutate) {
            for(int i=0;i<n;i++) {
               data[i] = data[i] + b.getData(i).floatValue();
            }
            return this;
        } else {
            ArrayVectorF c = new ArrayVectorF(n);
            for (int i = 0; i < n; i++) {
                data[i] = data[i] + b.getData(i).floatValue();
            }
            return c;
        }
    }

    @Override
    public final ArrayVectorF subtract(Vector b) {
        if(mutate) {
            for(int i=0;i<n;i++) {
               data[i] = data[i] - b.getData(i).floatValue();
            }
            return this;
        } else {
            ArrayVectorF c = new ArrayVectorF(n);
            for (int i = 0; i < n; i++) {
                data[i] = data[i] - b.getData(i).floatValue();
            }
            return c;
        }
    }

    @Override
    public final ArrayVectorF cross(Vector b) {
        if (n != 3 || b.n != 3) {
            throw new RuntimeException("Vector dimensions are not both equal to three.");
        }
        float xCross, yCross, zCross;
        xCross = data[Y] * b.getData(Z).floatValue() - data[Z] * b.getData(Y).floatValue();
        yCross = data[Z] * b.getData(X).floatValue() - data[X] * b.getData(Z).floatValue();
        zCross = data[X] * b.getData(Y).floatValue() - data[Y] * b.getData(X).floatValue();
        if(mutate) {
            data[X] = xCross;
            data[Y] = yCross;
            data[Z] = zCross;
            return this;
        } else {
            ArrayVectorF c = new ArrayVectorF(n);
            data[X] = xCross;
            data[Y] = yCross;
            data[Z] = zCross;
            return c;
        }
    }

    @Override
    public final ArrayVectorF addScalar(Number v) {
        if(mutate) {
            for(int i=0;i<n;i++) {
               data[i] = data[i] + v.floatValue();
            }
            return this;
        } else {
            ArrayVectorF c = new ArrayVectorF(n);
            for (int i = 0; i < n; i++) {
                data[i] = data[i] + v.floatValue();
            }
            return c;
        }
    }

    @Override
    public final ArrayVectorF subtractScalar(Number v) {
        if(mutate) {
            for(int i=0;i<n;i++) {
               data[i] = data[i] - v.floatValue();
            }
            return this;
        } else {
            ArrayVectorF c = new ArrayVectorF(n);
            for (int i = 0; i < n; i++) {
                data[i] = data[i] - v.floatValue();
            }
            return c;
        }
    }

    @Override
    public final ArrayVectorF multiplyScalar(Number v) {
        if(mutate) {
            for(int i=0;i<n;i++) {
               data[i] = data[i] * v.floatValue();
            }
            return this;
        } else {
            ArrayVectorF c = new ArrayVectorF(n);
            for (int i = 0; i < n; i++) {
                data[i] = data[i] * v.floatValue();
            }
            return c;
        }
    }

    @Override
    public final ArrayVectorF divideScalar(Number v) {
        if (v.intValue() == 0F) {
            throw new RuntimeException("Divide By Zero.");
        }
        if(mutate) {
            for(int i=0;i<n;i++) {
               data[i] = data[i] / v.floatValue();
            }
            return this;
        } else {
            ArrayVectorF c = new ArrayVectorF(n);
            for (int i = 0; i < n; i++) {
                data[i] = data[i] / v.floatValue();
            }
            return c;
        }
    }
}
