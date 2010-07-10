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

/**
 *
 * @author openecho
 */
public class Square implements Shape {

    public double mass;

    public Point2D centroid;

    public double rotation;

    public Point2D a,b,c,d;

    public Square(Point2D a, Point2D b, Point2D c, Point2D d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
