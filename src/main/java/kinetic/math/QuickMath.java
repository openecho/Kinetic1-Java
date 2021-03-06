/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinetic.math;

import kinetic.math.random.MersenneTwisterFast;

/**
 *
 * @author openecho
 */
public class QuickMath {

    /**
     * TODO:
     * Implement this correctly.
     *
     * angles that should be hard coded, 0, 30, 45, 90, 120, 180, 240, 360 (360 should reduce to 0 anyway)
     */

    float[] quickSin = null;
    float[] quickCos = null;
    float[] quickTan = null;

    MersenneTwisterFast mtf = null;
    static QuickMath random = null;

    public static final float PI;
    public static final float E;
    public static final int DEFAULT_QUICK_TRIG_SIZE;

    static {
        PI = (float) Math.PI;
        E = (float) Math.E;
        DEFAULT_QUICK_TRIG_SIZE = 360;
    }

    private QuickMath() {
        quickSin = new float[DEFAULT_QUICK_TRIG_SIZE];
        quickCos = new float[DEFAULT_QUICK_TRIG_SIZE];
        quickTan = new float[DEFAULT_QUICK_TRIG_SIZE];
        double chunk = 2D*Math.PI/DEFAULT_QUICK_TRIG_SIZE;
        double chunkStep;
        for(int i=0;i<DEFAULT_QUICK_TRIG_SIZE;i++) {
            chunkStep = chunk * (double) i;
            quickSin[i] = (float) Math.sin(chunkStep);
            quickCos[i] = (float) Math.cos(chunkStep);
            quickTan[i] = (float) Math.tan(chunkStep);
        }
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

    public static float abs(float d) {
        return Math.abs(d);
    }

    public static double abs(double d) {
        return Math.abs(d);
    }

    public static int min(int i, int j) {
        return Math.min(i, j);
    }

    public static double sqrt(double d) {
        return Math.sqrt(d);
    }

    public static float sqrt(float f) {
        return (float) Math.sqrt(f);
    }

    public static int max(int a, int b) {
        return Math.max(a, b);
    }

    public static float max(float a, float b) {
        return Math.max(a, b);
    }

    public static double max(double a, double b) {
        return Math.max(a, b);
    }

    public static float pow(float b, float p) {
        return (float) Math.pow(p, b);
    }

    public static double pow(double b, double p) {
        return Math.pow(p, b);
    }

    public static double hypot(double a, double b) {
        double r;
        if (Math.abs(a) > Math.abs(b)) {
            r = b / a;
            r = Math.abs(a) * Math.sqrt(1 + r * r);
        } else if (b != 0) {
            r = a / b;
            r = Math.abs(b) * Math.sqrt(1 + r * r);
        } else {
            r = 0.0;
        }
        return r;
    }
}
