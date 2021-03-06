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
package kinetic.math.linear;

import kinetic.math.Matrix;

/**
 * Base Matrix Decomposition class.
 *
 * @author openecho
 */
public abstract class MatrixDecomposition {

    boolean decomposeFlag = false;
    /**
     * Matrix to be decomposed.
     */
    Matrix a;

    /**
     * Default Constructor.
     * @param a Matrix a.
     */
    public MatrixDecomposition(Matrix a) {
        if (a == null) {
            throw new NullPointerException("Matrix a cannot be null.");
        }
        this.a = a;
    }

    public boolean isDecomposed() {
        return decomposeFlag;
    }

    public void setDecomposeFlag(boolean decomposeFlag) {
        this.decomposeFlag = decomposeFlag;
    }

    public final void decompose() {
        decomposeFlag = handleDecompose();
    }

    /**
     * Abstract Decompose Method.
     */
    protected abstract boolean handleDecompose();
}
