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

/**
 * Standardize utility for Standardizing matrix (m x n) data.
 *
 * Standardize centers and scales the input data.
 *
 * @author openecho
 * @version 1.0.0
 */
public class Standardize {
    /**
     * Standardizes the input data matrix. This process involves subtracting
     * the sample (m dimension) mean from each observation (n dimension) then
     * dividing by the sample standard deviation.
     * @param data matrix (m x n) of m samples containing n observations
     * @return standardized matrix
     */
    public static double[][] evaluate(double[][] data) {
        if(data == null) {
            throw new NullPointerException();
        }
        Matrix a = Matrix.create(data);
        double[][] d = new double[a.getM()][a.getN()];
        for(int j=0;j<a.getN();j++) {
            double[] nData = a.getColumn(j);
            double std = StandardDeviation.evaluate(nData);
            double mean = Mean.evaluate(nData);
            for(int i=0;i<a.getM();i++) {
                d[i][j] = (nData[i]-mean)/std;
            }
        }
        return d;
    }
}
