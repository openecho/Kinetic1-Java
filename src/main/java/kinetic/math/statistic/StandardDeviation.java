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
package kinetic.math.statistic;

import java.lang.reflect.Array;

/**
 * Standard Deviation Utility
 *
 * @author openecho
 * @version 1.0.1
 */
public class StandardDeviation {

    public static Double evaluate(Double[] data) {
        return StandardDeviation.evaluate(data, true);
    }

    public static Double evaluate(Double[] data, boolean partialPopulation) {
        if(data==null) {
            throw new NullPointerException();
        }
        int length = Array.getLength(data);
        if(length < 2) {
            throw new RuntimeException("More than two values are required to calculate a standard deviation");
        }
        double mean = Mean.evaluate(data);
        double sumDifferenceSquared = 0;
        for(int i=0;i<length;i++) {
            sumDifferenceSquared += Math.pow(data[i]-mean,2);
        }
        return Math.sqrt(sumDifferenceSquared/((partialPopulation) ? (length-1) : (length)));
    }
}