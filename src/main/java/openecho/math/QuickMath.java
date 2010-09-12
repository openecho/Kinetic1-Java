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
        if(random == null) {
            random = new QuickMath();
        }
        return random;
    }

    public static double random() {
        return getInstance().mtf.nextDouble();
    }
}
