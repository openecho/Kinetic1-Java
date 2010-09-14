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
package kinetic.shape;

/**
 *
 * @author openecho
 */
public class RectangularPrism extends Shape {
    float height;
    float width;
    float depth;

    public RectangularPrism(float height, float width, float depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
    }
}
