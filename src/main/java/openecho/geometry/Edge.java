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

import openecho.math.Vector;

/**
 *
 * @author openecho
 */
public class Edge implements Shape {

    public Vector a,b;

    public double mass;

    public Vector centroid;

    public double rotation;

    // Radius

    // Bounding Box

    // Bounding Hull
    
    public Edge(Vector a, Vector b) {
        this.a = a;
        this.b = b;
    }

    public Vector getStartPoint() {
        return a;
    }

    public void setStartPoint(Vector startPoint) {
        this.a = startPoint;
    }

    public Vector getEndPoint() {
        return b;
    }

    public void setEndPoint(Vector endPoint) {
        this.b = endPoint;
    }

    
    
}
