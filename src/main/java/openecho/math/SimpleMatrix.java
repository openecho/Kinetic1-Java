/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.math;

/**
 *
 * @author jmarsden
 */
public class SimpleMatrix {
    private final int m;
    private final int n;
    private final double[][] data;

    public SimpleMatrix(int m, int n) {
        this.m = m;
        this.n = n;
        data = new double[m][n];
    }

    public SimpleMatrix(double[][] data) {
        m = data.length;
        n = data[0].length;
        this.data = new double[m][n];
        for (int i = 0; i < m; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, n);
        }
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    
}
