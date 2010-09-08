/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.math.random;

/**
 *
 * @author jmarsden
 */
public class FastRandom {

    MersenneTwisterFast mtf = null;

    static FastRandom random = null;

    public FastRandom() {
        mtf = new MersenneTwisterFast();
    }

    public static FastRandom getInstance() {
        if(random == null) {
            random = new FastRandom();
        }
        return random;
    }

    public static double random() {
        return getInstance().mtf.nextDouble();
    }
}
