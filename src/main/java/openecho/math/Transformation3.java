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
        
    public static Matrix4F createRotationMatrix(float rotation, Vector3F unit) {
        return null;
    }

    public static Matrix4F createScaleMatrix(float xFactor, float yFactor, float zFactor) {
        return null;
    }

    public static Matrix4F createScaleMatrix(float factor, Vector3F unit) {
        return null;
    }

    public static Matrix4F createTranslationMatrix(float xTranslation, float yTranslation, float zTranslation) {
        Matrix4F trans = Matrix4F.identity();
        trans.m03 = xTranslation;
        trans.m13 = yTranslation;
        trans.m23 = zTranslation;
        return trans;
    }

    public static Matrix4F createTranslationMatrix(float translation, Vector3F unit) {
        Matrix4F trans = Matrix4F.identity();
        if(unit == X_UNIT) {
            trans.m03 = translation;
        } else if(unit == Y_UNIT) {
            trans.m13 = translation;
        } else if(unit == Y_UNIT) {
            trans.m23 = translation;
        } else if(unit.equals(X_UNIT)) {
            trans.m03 = translation;
        } else if(unit.equals(Y_UNIT)) {
            trans.m13 = translation;
        } else if(unit.equals(Y_UNIT)) {
            trans.m23 = translation;
        } else {
            /**
             * TODO: Implement this.
             */
            throw new UnsupportedOperationException();
        }
        return trans;
    }

    public static Matrix4F createXAxisRoationMatrix(float angle) {
        Matrix4F xRot = Matrix4F.identity();
        xRot.m11 = QuickMath.cos(angle);
        xRot.m21 = QuickMath.sin(angle);
        xRot.m12 = -xRot.m21; // -sin(a)
        xRot.m22 = xRot.m11; // cos(a)
        return xRot;
    }

    public static Matrix4F createYAxisRoationMatrix(float angle) {
        Matrix4F yRot = Matrix4F.identity();

        return null;
    }

    public static Matrix4F createZAxisRoationMatrix(float angle) {
        Matrix4F Rot = Matrix4F.identity();

        return null;
    }


}
