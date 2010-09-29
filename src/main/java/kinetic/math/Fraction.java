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
 * @version 1.0.0
 */
public class Fraction {

    int numerator;
    int denominator;

    public Fraction(int n, int d) {
        numerator = n;
        denominator = d;
    }

    public double floatValue() {
        return ((float) numerator) / ((float) denominator);
    }

    public double doubleValue() {
        return ((double) numerator) / ((double) denominator);
    }

    public static Fraction add(Fraction a, Fraction b) {
        if (a.denominator != b.denominator) {
            int aTop = b.denominator * a.numerator;
            int bTop = a.denominator * b.numerator;
            return new Fraction(aTop + bTop, a.denominator * b.denominator);
        } else {
            return new Fraction(a.numerator + b.numerator, a.denominator);
        }
    }

    public static Fraction subtract(Fraction a, Fraction b) {
        if (a.denominator != b.denominator) {
            int aTop = b.denominator * a.numerator;
            int bTop = a.denominator * b.numerator;
            return new Fraction(aTop - bTop, a.denominator + b.denominator);
        } else {
            return new Fraction(a.numerator - b.numerator, a.denominator);
        }
    }

    public static Fraction multiply(Fraction a, Fraction b) {
        return new Fraction(a.numerator * b.numerator, a.denominator * b.denominator);
    }

    public static Fraction divide(Fraction a, Fraction b) {
        return new Fraction(a.numerator * b.denominator, a.denominator * b.numerator);
    }
}
