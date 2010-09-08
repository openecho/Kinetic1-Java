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

import java.lang.reflect.Array;

/**
 * Mean utility.
 *
 * @author openecho
 * @version 1.0.1
 */
public class Mean {

    /**
     * Sum of added values
     */
    double sum;
    /**
     * Count of added values
     */
    int count;

    /**
     * Default Constructor
     */
    public Mean() {
        sum = 0D;
        count = 0;
    }

    /**
     * Adds a moment (new value to be calculated in the mean).
     * @param value new moment value.
     */
    public void addMoment(double value) {
        sum += value;
        count++;
    }

    /**
     * Finds the count of the moments added to this Mean.
     * @return int the count of moments added.
     */
    public int getCount() {
        return count;
    }

    /**
     * Evaluates the value for the current state of the Mean.
     * @return The mean value.
     */
    public double evaulate() {
        if (count == 0) {
            return 0D;
        }
        return sum / (double) count;
    }

    /**
     * Rest the mean.
     */
    public void reset() {
        sum = 0D;
        count = 0;
    }

    /**
     * Calculates the mean of the provided vector of data.
     * @param vector data to calculate the mean.
     * @return mean
     */
    public static Double evaluate(Double[] data) {
        if (data == null) {
            throw new NullPointerException();
        }
        int length = Array.getLength(data);
        if (length == 0) {
            return 0D;
        }
        double sum = 0;
        for (int i = 0; i < length; i++) {
            sum += data[i];
        }
        return sum / length;
    }
}
