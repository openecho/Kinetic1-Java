/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package openecho.math;

import openecho.math.random.MersenneTwisterFast;

/**
 *
 * @author openecho
 */
public class QuickMath {

    MersenneTwisterFast mtf = null;
    static QuickMath random = null;

    private QuickMath() {
        mtf = new MersenneTwisterFast();
    }

    public static QuickMath getInstance() {
        if (random == null) {
            random = new QuickMath();
        }
        return random;
    }

    public static double random() {
        return getInstance().mtf.nextDouble();
    }

    public static float sin(float a) {
        return (float) Math.sin(a);
    }

    public static float asin(float a) {
        return (float) Math.asin(a);
    }

    public static float cos(float a) {
        return (float) Math.cos(a);
    }

    public static float acos(float a) {
        return (float) Math.acos(a);
    }

    public static float tan(float a) {
        return (float) Math.tan(a);
    }

    public static float atan(float a) {
        return (float) Math.atan(a);
    }
}
