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
public class Transformation3F {

    Matrix4F rotation;
    Matrix4F scale, translation;

    public static final Vector3F X_UNIT, Y_UNIT, Z_UNIT;

    static {
        X_UNIT = Vector3F.X_UNIT;
        Y_UNIT = Vector3F.Y_UNIT;
        Z_UNIT = Vector3F.Z_UNIT;
    }

    public Transformation3F() {
        rotation = new Matrix4F();
        scale = new Matrix4F();
        translation = new Matrix4F();
    }
   
    public static Matrix4F createRotationMatrix(float yaw, float pitch, float roll) {
        return null;
    }
        
    public static Matrix4F createRotationMatrix(float rotation, Vector3F unit) {
        Matrix4F rot = Matrix4F.identity();
        if(unit == X_UNIT) {
            rot = createXAxisRoationMatrix(rotation);
        } else if(unit == Y_UNIT) {
            rot = createYAxisRoationMatrix(rotation);
        } else if(unit == Z_UNIT) {
            rot = createZAxisRoationMatrix(rotation);
        } else if(unit.equals(X_UNIT)) {
            rot = createXAxisRoationMatrix(rotation);
        } else if(unit.equals(Y_UNIT)) {
            rot = createYAxisRoationMatrix(rotation);
        } else if(unit.equals(Z_UNIT)) {
            rot = createZAxisRoationMatrix(rotation);
        } else {
            /**
             * TODO: Implement this.
             */
            throw new UnsupportedOperationException();
        }
        return rot;
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
        } else if(unit.equals(Z_UNIT)) {
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
        xRot.m11 = xRot.m22 = QuickMath.cos(angle);
        xRot.m21 = QuickMath.sin(angle);
        xRot.m12 = -xRot.m21;
        return xRot;
    }

    public static Matrix4F createYAxisRoationMatrix(float angle) {
        Matrix4F yRot = Matrix4F.identity();
        yRot.m00 = yRot.m22 = QuickMath.cos(angle);
        yRot.m02 = QuickMath.sin(angle);
        yRot.m20 = -yRot.m02;
        return yRot;
    }

    public static Matrix4F createZAxisRoationMatrix(float angle) {
        Matrix4F zRot = Matrix4F.identity();
        zRot.m00 = zRot.m11 = QuickMath.cos(angle);
        zRot.m10 = QuickMath.sin(angle);
        zRot.m01 = -zRot.m10;
        return zRot;
    }
}
