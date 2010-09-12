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
package kinetic.math;

/**
 *
 * @author openecho
 */
public class Transformation2F {

    Matrix3F rotation;
    Matrix3F scale, translation;

    public static final Vector2F X_UNIT, Y_UNIT;

    static {
        X_UNIT = Vector2F.X_UNIT;
        Y_UNIT = Vector2F.Y_UNIT;
    }

    public Transformation2F() {
        rotation = new Matrix3F();
        scale = new Matrix3F();
        translation = new Matrix3F();
    }
}
