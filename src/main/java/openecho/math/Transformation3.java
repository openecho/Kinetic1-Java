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

/**
 *
 * @author openecho
 */
public class Transformation3 {

    Matrix4F rotation;
    Matrix4F scale, translation;

    public static final Vector3F X_UNIT, Y_UNIT, Z_UNIT;

    static {
        X_UNIT = Vector3F.X_UNIT;
        Y_UNIT = Vector3F.Y_UNIT;
        Z_UNIT = Vector3F.Z_UNIT;
    }

    public Transformation3() {
        rotation = new Matrix4F();
        scale = new Matrix4F();
        translation = new Matrix4F();
    }
   
    public static Matrix4F createRotationMatrix(float yaw, float pitch, float roll) {
        return null;
    }
        
    public static Matrix4F createRotationMatrix(float rotation, Vector3F axis) {
        return null;
    }

    public static Matrix4F createScaleMatrix(float xFactor, float yFactor, float zFactor) {
        return null;
    }

    public static Matrix4F createScaleMatrix(float factor, Vector3F axis) {
        return null;
    }

    public static Matrix4F createTranslationMatrix(float xTranslation, float yTranslation, float zTranslation) {
        return null;
    }

    public static Matrix4F createTranslationMatrix(float translation, Vector3F axis) {
        return null;
    }
}
