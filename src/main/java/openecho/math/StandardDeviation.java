/**
 * openecho Java-Pack
 */
package openecho.math;

import java.lang.reflect.Array;

/**
 *
 * @author openecho
 */
public class StandardDeviation {
    public static double calculateStandardDeviation(double[] vector) {
        if(vector==null) {
            throw new NullPointerException();
        }
        int length = Array.getLength(vector);
        if(length < 2) {
            throw new RuntimeException("More than two values are required to calculate a standard deviation");
        }
        double mean = Mean.calculateMean(vector);
        double sumDifferenceSquared = 0;
        for(int i=0;i<length;i++) {
            sumDifferenceSquared += Math.pow(vector[i]-mean,2);
        }
        return Math.sqrt(sumDifferenceSquared/(length-1));
    }
}
