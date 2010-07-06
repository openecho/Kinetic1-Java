/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package openecho.math;

/**
 *
 * @author openecho
 */
public abstract class Matrix {

    /**
     * m dimension
     */
    final int m;
    /**
     * n dimension
     */
    final int n;
    /**
     * matrix data
     */
    double[][] data;

    public Matrix(int m, int n) {
        this.m = m;
        this.n = n;
        data = new double[m][n];
    }

    public Matrix(double[][] data) {
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

    public abstract double[][] getData();

    public abstract double[] getRow(int i);

    public double[] getColumn(int i) {
        if (i >= n) {
            throw new IndexOutOfBoundsException();
        }
        double[] result = new double[m];
        for (int j = 0; j < m; j++) {
            result[j] = data[j][i];
        }
        return result;
    }

    public boolean equals(Matrix b) {
        Matrix a = this;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a.data[i][j] != b.data[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public abstract Matrix add(Matrix b);

    public abstract Matrix subtract(Matrix b);

    public abstract Matrix multiply(Matrix b);

    public abstract Matrix transpose();

    public abstract Matrix addScalar(double v);

    public abstract Matrix subtractScalar(double v);

    public abstract Matrix multiplyScalar(double v);

    public abstract Matrix divideScalar(double v);

    @Override
    public String toString() {
        String dataString = "{";
        for (int i = 0; i < m; i++) {
            dataString += "{";
            for (int j = 0; j < n; j++) {
                dataString += data[i][j] + ((j < n - 1) ? "," : "");
            }
            dataString += "}" + ((i < m - 1) ? "," : "");
        }
        dataString += "}";
        return String.format("%s %s", super.toString(), dataString);
    }

    public static Matrix empty(int m, int n) {
        Matrix e = new MutableMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                e.data[i][j] = 0D;
            }
        }
        return e;
    }

    public static Matrix create(double[][] data) {
        Matrix c = new ImmutableMatrix(data);
        return c;
    }

    public static Matrix create(double[][] data, boolean mutable) {
        Matrix c = null;
        if(!mutable) {
            c = new ImmutableMatrix(data);
        } else {
            c = new MutableMatrix(data);
        }
        return c;
    }

    public static Matrix transpose(Matrix a) {
        Matrix t = new ImmutableMatrix(a.n, a.m);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                t.data[j][i] = a.data[i][j];
            }
        }
        return t;
    }

    public static Matrix identity(int n) {
        Matrix i = new ImmutableMatrix(n, n);
        for (int j = 0; j < n; j++) {
            i.data[j][j] = 1;
        }
        return i;
    }

    public static Matrix random(int m, int n) {
        Matrix r = new ImmutableMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r.data[i][j] = Math.random();
            }
        }
        return r;
    }

    public static Matrix random(int m, int n, double lowerBound, double higherBound) {
        Matrix r = new ImmutableMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r.data[i][j] = (Math.random() * (higherBound - lowerBound)) + lowerBound;
            }
        }
        return r;
    }

    public static Matrix generate(int m, int n, double v) {
        Matrix g = new ImmutableMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                g.data[i][j] = v;
            }
        }
        return g;
    }

    public static Matrix oneMatrix(int m, int n) {
        return generate(m, n, 1D);
    }
}
