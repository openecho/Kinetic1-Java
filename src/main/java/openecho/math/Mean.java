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
 * Mean utility.
 *
 * @author openecho
 * @version 1.0.0
 */
public class Mean {
    /**
     * Calculates the mean of the provided vector of data.
     * @param vector data to calculate the mean.
     * @return mean
     */
    public static double evaluate(double[] data) {
        if(data==null) {
            throw new NullPointerException();
        }
        int length = Array.getLength(data);
        if(length == 0) {
            return 0;
        }
        double sum = 0;
        for(int i=0;i<length;i++) {
            sum += data[i];
        }
        return sum/length;
    }
}
