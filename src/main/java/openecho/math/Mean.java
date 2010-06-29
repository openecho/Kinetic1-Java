/**
 * openecho Java-Pack
 */
package openecho.math;

import java.lang.reflect.Array;

/**
 *
 * @author openecho
 */
public class Mean {
   public static double calculateMean(double[] vector) {
        if(vector==null) {
            throw new NullPointerException();
        }
        int length = Array.getLength(vector);
        if(length == 0) {
            return 0;
        }
        double sum = 0;
        for(int i=0;i<length;i++) {
            sum += vector[i];
        }
        return sum/length;
    }
}
