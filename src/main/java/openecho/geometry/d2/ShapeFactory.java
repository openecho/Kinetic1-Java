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
package openecho.geometry.d2;

/**
 *
 * @author openecho
 */
public class ShapeFactory {

    public static Point createCentreOriginPoint() {
        return new Point(0D,0D);
    }

    public static Line createCentreOriginLine(double length) {
        double halfLength = length/2D;
        Line line = new Line(
                new Point(-halfLength, 0),
                new Point(halfLength, 0)
        );
        return line;
    }

    public static Rectangle createCentreOriginRectangle(double width, double height) {
        double halfWidth = width/2D;
        double halfHeight = height/2D;
        Rectangle output = new Rectangle(
                new Point(-halfWidth,-halfHeight),
                new Point(-halfWidth,halfHeight),
                new Point(halfWidth,halfHeight),
                new Point(halfWidth,-halfHeight)
        );
        return output;
    }

    public static Circle createCentreOriginCircle(double radius) {
        Circle output = new Circle(new Point(0D,0D), radius);
        return output;
    }
}
