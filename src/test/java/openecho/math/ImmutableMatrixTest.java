/**
 * openecho Java-Pack
 */

package openecho.math;

import java.lang.reflect.Array;
import junit.framework.TestCase;

/**
 * Unit Tests for SimpleImmutableMatrixTest.
 *
 * @author openecho
 */
public class ImmutableMatrixTest extends TestCase {
    
    public ImmutableMatrixTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getM method, of class SimpleImmutableMatrix.
     */
    public void testGetM() {
        System.out.println("getM");
        int m = (int) Math.random()*100;
        int n = (int) Math.random()*100;
        Matrix instance = Matrix.random(m, n);
        int expResult = m;
        int result = instance.getM();
        assertEquals(expResult, result);
    }

    /**
     * Test of getN method, of class SimpleImmutableMatrix.
     */
    public void testGetN() {
        System.out.println("getN");
        int m = (int) Math.random()*100;
        int n = (int) Math.random()*100;
        Matrix instance = Matrix.random(m, n);
        int result = instance.getN();
        int expResult = n;
        assertEquals(expResult, result);
    }

    /**
     * Test of getRow method, of class SimpleImmutableMatrix.
     */
    public void testGetRow() {
        System.out.println("getRow");
        double[][] data = new double[][] {{1,2,3},{3,2,1},{1,2,3}};
        int i = 1;
        ImmutableMatrix instance = new ImmutableMatrix(data);
        double[] expResult = data[1];
        double[] result = instance.getRow(i);
        for(int j=0;j<Array.getLength(expResult);j++) {
            assertEquals(expResult[j], result[j]);
        }
    }

    /**
     * Test of getColumn method, of class SimpleImmutableMatrix.
     */
    public void testGetColumn() {
        System.out.println("getColumn");
        double[][] data = new double[][] {{1,2,3},{3,2,1},{1,2,3}};
        int i = 1;
        ImmutableMatrix instance = new ImmutableMatrix(data);
        double[] expResult = new double[] {2,2,2};
        double[] result = instance.getColumn(i);
        for(int j=0;j<Array.getLength(expResult);j++) {
            assertEquals(expResult[j], result[j]);
        }
    }

    /**
     * Test of equals method, of class SimpleImmutableMatrix.
     */
    public void testEquals() {
        System.out.println("equals");
        double[][] data = new double[][] {{1,2,3},{3,2,1},{1,2,3}};
        Matrix a = Matrix.random(3, 3);
        ImmutableMatrix b = new ImmutableMatrix(data);
        ImmutableMatrix instance = new ImmutableMatrix(data);
        boolean expResult = false;
        boolean result = instance.equals(a);
        assertEquals(expResult, result);
        expResult = true;
        result = instance.equals(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class SimpleImmutableMatrix.
     */
    public void testAdd() {
        System.out.println("add");
        double[][] data = new double[][] {{1,2,3},{3,2,1},{1,2,3}};
        ImmutableMatrix b = new ImmutableMatrix(data);
        ImmutableMatrix instance = new ImmutableMatrix(data);
        data = new double[][] {{2,4,6},{6,4,2},{2,4,6}};
        ImmutableMatrix expResult = new ImmutableMatrix(data);
        Matrix result = instance.add(b);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of subtract method, of class SimpleImmutableMatrix.
     */
    public void testSubtract() {
        System.out.println("minus");
        double[][] data = new double[][] {{2,4,6},{6,4,2},{2,4,6}};
        ImmutableMatrix instance = new ImmutableMatrix(data);
        data = new double[][] {{1,2,3},{3,2,1},{1,2,3}};
        ImmutableMatrix b = new ImmutableMatrix(data);
        ImmutableMatrix expResult = new ImmutableMatrix(data);
        Matrix result = instance.subtract(b);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of multiply method, of class SimpleImmutableMatrix.
     */
    public void testMultiply() {
        System.out.println("multiply");
        double[][] data = new double[][] {{3D,2D,4D},{1D,2D,3D}};
        ImmutableMatrix b = new ImmutableMatrix(data);
        System.out.println(b);
        data = new double[][] {{4D},{3D},{2D}};
        ImmutableMatrix instance = new ImmutableMatrix(data);
        System.out.println(instance);
        data = new double[][] {{26D},{16D}};
        ImmutableMatrix expResult = new ImmutableMatrix(data);
        Matrix result = b.multiply(instance);
        System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of transpose method, of class SimpleImmutableMatrix.
     */
    public void testTranspose() {
        System.out.println("transpose");
        double[][] data = new double[][] {{1,2,3}};
        ImmutableMatrix a = new ImmutableMatrix(data);
        data = new double[][] {{1},{2},{3}};
        ImmutableMatrix expResult = new ImmutableMatrix(data);
        Matrix result = ImmutableMatrix.transpose(a);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of identity method, of class SimpleImmutableMatrix.
     */
    public void testIdentity() {
        System.out.println("identity");
        int n = 3;
        double[][] data = new double[][] {{1,0,0},{0,1,0},{0,0,1}};
        ImmutableMatrix expResult = new ImmutableMatrix(data);;
        Matrix result = ImmutableMatrix.identity(n);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of random method, of class SimpleImmutableMatrix.
     */
    public void testRandom_int_int() {
        System.out.println("random");
        int m = 3;
        int n = 3;
        Matrix result = ImmutableMatrix.random(m, n);
        assertEquals(m, result.getM());
        assertEquals(n, result.getN());
    }

    /**
     * Test of random method, of class SimpleImmutableMatrix.
     */
    public void testRandom_4args() {
        System.out.println("random");
        int m = 3;
        int n = 3;
        Matrix result = ImmutableMatrix.random(m, n);
        assertEquals(m, result.getM());
        assertEquals(n, result.getN());
    }

}
