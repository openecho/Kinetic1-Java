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

import kinetic.math.MatrixD;

/**
 * Deviation Score utility.
 *
 * x = X - 11'X ( 1 / n )
 *
 * @author openecho
 * @version 1.0.1
 */
public class DeviationScore {
    public static Double[][] evaluate(Double[][] data) {
        if(data==null) {
            throw new NullPointerException();
        }
        MatrixD x = MatrixD.create(data);
        MatrixD o = MatrixD.oneMatrix(x.getM(), x.getM());
        MatrixD d = (x.subtract(o.multiply(x).divideScalar(x.getM())));
        return d.getData();
    }
}
