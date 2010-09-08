/**
 * openecho Java-Pack
 */

package openecho.math;

import java.lang.reflect.Array;
import junit.framework.TestCase;

/**
 * Unit Tests for ImmutableMatrixDTest.
 *
 * @author openecho
 */
public class ImmutableMatrixDTest extends TestCase {
    
    public ImmutableMatrixDTest(String testName) {
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
        MatrixD instance = MatrixD.random(m, n);
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
        MatrixD instance = MatrixD.random(m, n);
        int result = instance.getN();
        int expResult = n;
        assertEquals(expResult, result);
    }

    /**
     * Test of getRow method, of class SimpleImmutableMatrix.
     */
    public void testGetRow() {
        System.out.println("getRow");
        Double[][] data = new Double[][] {{1D,2D,3D},{3D,2D,1D},{1D,2D,3D}};
        int i = 1;
        ImmutableMatrixD instance = new ImmutableMatrixD(data);
        Double[] expResult = data[1];
        Double[] result = instance.getRow(i);
        for(int j=0;j<Array.getLength(expResult);j++) {
            assertEquals(expResult[j], result[j]);
        }
    }

    /**
     * Test of getColumn method, of class SimpleImmutableMatrix.
     */
    public void testGetColumn() {
        System.out.println("getColumn");
        Double[][] data = new Double[][] {{1D,2D,3D},{3D,2D,1D},{1D,2D,3D}};
        int i = 1;
        ImmutableMatrixD instance = new ImmutableMatrixD(data);
        Double[] expResult = new Double[] {2D,2D,2D};
        Double[] result = instance.getColumn(i);
        for(int j=0;j<Array.getLength(expResult);j++) {
            assertEquals(expResult[j], result[j]);
        }
    }

    /**
     * Test of equals method, of class SimpleImmutableMatrix.
     */
    public void testEquals() {
        System.out.println("equals");
        Double[][] data = new Double[][] {{1D,2D,3D},{3D,2D,1D},{1D,2D,3D}};
        MatrixD a = MatrixD.random(3, 3);
        ImmutableMatrixD b = new ImmutableMatrixD(data);
        ImmutableMatrixD instance = new ImmutableMatrixD(data);
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
        Double[][] data = new Double[][] {{1D,2D,3D},{3D,2D,1D},{1D,2D,3D}};
        ImmutableMatrixD b = new ImmutableMatrixD(data);
        ImmutableMatrixD instance = new ImmutableMatrixD(data);
        data = new Double[][] {{2D,4D,6D},{6D,4D,2D},{2D,4D,6D}};
        ImmutableMatrixD expResult = new ImmutableMatrixD(data);
        MatrixD result = instance.add(b);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of subtract method, of class SimpleImmutableMatrix.
     */
    public void testSubtract() {
        System.out.println("minus");
        Double[][] data = new Double[][] {{2D,4D,6D},{6D,4D,2D},{2D,4D,6D}};
        ImmutableMatrixD instance = new ImmutableMatrixD(data);
        data = new Double[][] {{1D,2D,3D},{3D,2D,1D},{1D,2D,3D}};
        ImmutableMatrixD b = new ImmutableMatrixD(data);
        ImmutableMatrixD expResult = new ImmutableMatrixD(data);
        MatrixD result = instance.subtract(b);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of multiply method, of class SimpleImmutableMatrix.
     */
    public void testMultiply() {
        System.out.println("multiply");
        Double[][] data = new Double[][] {{1D,2D,3D}};
        ImmutableMatrixD b = new ImmutableMatrixD(data);
        System.out.println(b);
        data = new Double[][] {{4D},{5D},{6D}};
        ImmutableMatrixD instance = new ImmutableMatrixD(data);
        System.out.println(instance);
        data = new Double[][] {{32D}};
        ImmutableMatrixD expResult = new ImmutableMatrixD(data);
        System.out.println(expResult);
        MatrixD result = b.multiply(instance);
        System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of transpose method, of class SimpleImmutableMatrix.
     */
    public void testTranspose() {
        System.out.println("transpose");
        Double[][] data = new Double[][] {{1D,2D,3D}};
        ImmutableMatrixD a = new ImmutableMatrixD(data);
        data = new Double[][] {{1D},{2D},{3D}};
        ImmutableMatrixD expResult = new ImmutableMatrixD(data);
        MatrixD result = ImmutableMatrixD.transpose(a);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of identity method, of class SimpleImmutableMatrix.
     */
    public void testIdentity() {
        System.out.println("identity");
        int n = 3;
        Double[][] data = new Double[][] {{1D,0D,0D},{0D,1D,0D},{0D,0D,1D}};
        ImmutableMatrixD expResult = new ImmutableMatrixD(data);;
        MatrixD result = ImmutableMatrixD.identity(n);
        System.out.println(expResult);
        System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of random method, of class SimpleImmutableMatrix.
     */
    public void testRandom_int_int() {
        System.out.println("random");
        int m = 3;
        int n = 3;
        MatrixD result = ImmutableMatrixD.random(m, n);
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
        MatrixD result = ImmutableMatrixD.random(m, n);
        assertEquals(m, result.getM());
        assertEquals(n, result.getN());
    }

}
