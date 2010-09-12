/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinetic.math;

import kinetic.math.RowArrayMatrixD;
import kinetic.math.MatrixD;
import java.lang.reflect.Array;
import junit.framework.TestCase;

/**
 *
 * @author jmarsden
 */
public class RowArrayMatrixDTest extends TestCase {

    public RowArrayMatrixDTest(String testName) {
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
        int m = (int) Math.random() * 100;
        int n = (int) Math.random() * 100;
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
        int m = (int) Math.random() * 100;
        int n = (int) Math.random() * 100;
        MatrixD instance = MatrixD.random(m, n);
        int result = instance.getN();
        int expResult = n;
        assertEquals(expResult, result);
    }

    /**
     * Test of getData method, of class RowArrayMatrixD.
     */
    public void testGetData_0args() {
        System.out.println("getData");
        Double[][] data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        Double[][] expResult = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        Double[][] result = instance.getData();
        int m = data.length;
        int n = data[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                assertEquals(expResult[i][j], result[i][j]);
            }
        }
    }

    /**
     * Test of getData method, of class RowArrayMatrixD.
     */
    public void testGetData_int_int() {
        System.out.println("getData");
        Double[][] data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        Double expResult = null;
        int m = data.length;
        int n = data[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                expResult = data[i][j];
                Double result = instance.getData(i, j);
                assertEquals(expResult, result);
            }
        }
    }

    /**
     * Test of isMutable method, of class RowArrayMatrixD.
     */
    public void testIsMutable() {
        System.out.println("isMutable");
        Double[][] data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        boolean expResult = false;
        boolean result = instance.willMutate();
        assertEquals(expResult, result);
        instance = new RowArrayMatrixD(data, true);
        expResult = true;
        result = instance.willMutate();
        assertEquals(expResult, result);
        instance = new RowArrayMatrixD(data, false);
        expResult = false;
        result = instance.willMutate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRow method, of class RowArrayMatrixD.
     */
    public void testGetRow() {
        System.out.println("getRow");
        Double[][] data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        int i = 1;
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        Double[] expResult = data[1];
        Double[] result = instance.getRow(i);
        for (int j = 0; j < Array.getLength(expResult); j++) {
            assertEquals(expResult[j], result[j]);
        }
    }

    /**
     * Test of getColumn method, of class SimpleImmutableMatrix.
     */
    public void testGetColumn() {
        System.out.println("getColumn");
        Double[][] data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        int i = 1;
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        Double[] expResult = new Double[]{2D, 2D, 2D};
        Double[] result = instance.getColumn(i);
        for (int j = 0; j < Array.getLength(expResult); j++) {
            assertEquals(expResult[j], result[j]);
        }
    }

    /**
     * Test of add method, of class RowArrayMatrixD.
     */
    public void testAdd() {
        System.out.println("add");
        Double[][] data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        RowArrayMatrixD b = new RowArrayMatrixD(data);
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        data = new Double[][]{{2D, 4D, 6D}, {6D, 4D, 2D}, {2D, 4D, 6D}};
        RowArrayMatrixD expResult = new RowArrayMatrixD(data);
        MatrixD result = instance.add(b);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of subtract method, of class RowArrayMatrixD.
     */
    public void testSubtract() {
        System.out.println("subtract");
        Double[][] data = new Double[][]{{2D, 4D, 6D}, {6D, 4D, 2D}, {2D, 4D, 6D}};
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        RowArrayMatrixD b = new RowArrayMatrixD(data);
        RowArrayMatrixD expResult = new RowArrayMatrixD(data);
        MatrixD result = instance.subtract(b);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of multiply method, of class RowArrayMatrixD.
     */
    public void testMultiply() {
        System.out.println("multiply");
        Double[][] data = new Double[][]{{1D, 2D, 3D}};
        RowArrayMatrixD b = new RowArrayMatrixD(data);
        System.out.println(b);
        data = new Double[][]{{4D}, {5D}, {6D}};
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        System.out.println(instance);
        data = new Double[][]{{32D}};
        RowArrayMatrixD expResult = new RowArrayMatrixD(data);
        System.out.println(expResult);
        MatrixD result = b.multiply(instance);
        System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class SimpleImmutableMatrix.
     */
    public void testEquals() {
        System.out.println("equals");
        Double[][] data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        MatrixD a = MatrixD.random(3, 3);
        RowArrayMatrixD b = new RowArrayMatrixD(data);
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        boolean expResult = false;
        boolean result = instance.equals(a);
        assertEquals(expResult, result);
        expResult = true;
        result = instance.equals(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of transpose method, of class RowArrayMatrixD.
     */
    public void testTranspose() {
        System.out.println("transpose");
        Double[][] data = new Double[][]{{1D, 2D, 3D}};
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        data = new Double[][]{{1D}, {2D}, {3D}};
        MatrixD expResult = new RowArrayMatrixD(data);
        MatrixD result = instance.transpose();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of addScalar method, of class RowArrayMatrixD.
     */
    public void testAddScalar() {
        System.out.println("addScalar");
        Number v = 5D;
        Double[][] data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        data = new Double[][]{{6D, 7D, 8D}, {8D, 7D, 6D}, {6D, 7D, 8D}};
        MatrixD expResult = new RowArrayMatrixD(data);
        MatrixD result = instance.addScalar(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of subtractScalar method, of class RowArrayMatrixD.
     */
    public void testSubtractScalar() {
        System.out.println("subtractScalar");
        Number v = 1D;
        Double[][] data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        data = new Double[][]{{0D, 1D, 2D}, {2D, 1D, 0D}, {0D, 1D, 2D}};
        MatrixD expResult = new RowArrayMatrixD(data);
        MatrixD result = instance.subtractScalar(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of multiplyScalar method, of class RowArrayMatrixD.
     */
    public void testMultiplyScalar() {
        System.out.println("multiplyScalar");
        Number v = 5D;
        Double[][] data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        data = new Double[][]{{5D, 10D, 15D}, {15D, 10D, 5D}, {5D, 10D, 15D}};
        MatrixD expResult = new RowArrayMatrixD(data);
        MatrixD result = instance.multiplyScalar(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of divideScalar method, of class RowArrayMatrixD.
     */
    public void testDivideScalar() {
        System.out.println("divideScalar");
        Number v = 5D;
        Double[][] data = new Double[][]{{5D, 10D, 15D}, {15D, 10D, 5D}, {5D, 10D, 15D}};
        RowArrayMatrixD instance = new RowArrayMatrixD(data);
        data = new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {1D, 2D, 3D}};
        MatrixD expResult = new RowArrayMatrixD(data);
        MatrixD result = instance.divideScalar(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of transpose method, of class SimpleImmutableMatrix.
     */
    public void testTranspose_static() {
        System.out.println("transpose");
        Double[][] data = new Double[][]{{1D, 2D, 3D}};
        RowArrayMatrixD a = new RowArrayMatrixD(data);
        data = new Double[][]{{1D}, {2D}, {3D}};
        RowArrayMatrixD expResult = new RowArrayMatrixD(data);
        MatrixD result = RowArrayMatrixD.transpose(a);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of identity method, of class SimpleImmutableMatrix.
     */
    public void testIdentity() {
        System.out.println("identity");
        int n = 3;
        Double[][] data = new Double[][]{{1D, 0D, 0D}, {0D, 1D, 0D}, {0D, 0D, 1D}};
        RowArrayMatrixD expResult = new RowArrayMatrixD(data);
        ;
        MatrixD result = RowArrayMatrixD.identity(n);
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
        MatrixD result = RowArrayMatrixD.random(m, n);
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
        MatrixD result = RowArrayMatrixD.random(m, n);
        assertEquals(m, result.getM());
        assertEquals(n, result.getN());
    }
}
