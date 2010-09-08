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
package openecho.math.statistic;

import openecho.math.Matrix;
import openecho.math.MatrixD;

/**
 * Variance-Covariance utility for to generate a variance-covariance matrix (n x n)
 * for a matrix (m x n) of  data.
 *
 * The calculation is based on the following formula,
 *
 * Cov(X, Y) = Σ ( Xi - X ) ( Yi - Y ) / N = Σ xiyi / N
 *
 * This version first calculates the a deviation score matrix,
 *
 * x = X - 11'X ( 1 / n )
 *
 * Then calculates the a covariance matrix using,
 *
 * V = x'x ( 1 / n )
 *
 * @author openecho
 * @version 1.0.0
 */
public class VarianceCovariance {
    public static Double[][] evaluate(Double[][] data) {
        if(data == null) {
            throw new NullPointerException();
        }
        MatrixD a = MatrixD.create(DeviationScore.evaluate(data));
        return a.transpose().multiply(a).divideScalar(a.getM()).getData();
    }
}
