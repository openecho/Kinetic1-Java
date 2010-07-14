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

package openecho.geometry;

import openecho.math.MutableVector;
import openecho.math.Vector;

/**
 *
 * @author openecho
 */
public class Point2D extends MutableVector {
    
    public Point2D(double[] data) {
        super(data);
    }

    public Point2D(int n) {
        super(n);
    }

    public double getX() {
        return data[X];
    }

    public double getY() {
        return data[Y];
    }

    
}