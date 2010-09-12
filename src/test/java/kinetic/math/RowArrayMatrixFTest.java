/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinetic.math;

import kinetic.math.RowArrayMatrixF;
import kinetic.math.MatrixF;
import java.lang.reflect.Array;
import junit.framework.TestCase;

/**
 *
 * @author jmarsden
 */
public class RowArrayMatrixFTest extends TestCase {

    public RowArrayMatrixFTest(String testName) {
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
        MatrixF instance = MatrixF.random(m, n);
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
        MatrixF instance = MatrixF.random(m, n);
        int result = instance.getN();
        int expResult = n;
        assertEquals(expResult, result);
    }

    /**
     * Test of getData method, of class RowArrayMatrixF.
     */
    public void testGetData_0args() {
        System.out.println("getData");
        Float[][] data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        Float[][] expResult = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        Float[][] result = instance.getData();
        int m = data.length;
        int n = data[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                assertEquals(expResult[i][j], result[i][j]);
            }
        }
    }

    /**
     * Test of getData method, of class RowArrayMatrixF.
     */
    public void testGetData_int_int() {
        System.out.println("getData");
        Float[][] data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        Float expResult = null;
        int m = data.length;
        int n = data[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                expResult = data[i][j];
                Float result = instance.getData(i, j);
                assertEquals(expResult, result);
            }
        }
    }

    /**
     * Test of isMutable method, of class RowArrayMatrixF.
     */
    public void testIsMutable() {
        System.out.println("isMutable");
        Float[][] data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        boolean expResult = false;
        boolean result = instance.willMutate();
        assertEquals(expResult, result);
        instance = new RowArrayMatrixF(data, true);
        expResult = true;
        result = instance.willMutate();
        assertEquals(expResult, result);
        instance = new RowArrayMatrixF(data, false);
        expResult = false;
        result = instance.willMutate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRow method, of class RowArrayMatrixF.
     */
    public void testGetRow() {
        System.out.println("getRow");
        Float[][] data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        int i = 1;
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        Float[] expResult = data[1];
        Float[] result = instance.getRow(i);
        for (int j = 0; j < Array.getLength(expResult); j++) {
            assertEquals(expResult[j], result[j]);
        }
    }

    /**
     * Test of getColumn method, of class SimpleImmutableMatrix.
     */
    public void testGetColumn() {
        System.out.println("getColumn");
        Float[][] data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        int i = 1;
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        Float[] expResult = new Float[]{2F, 2F, 2F};
        Float[] result = instance.getColumn(i);
        for (int j = 0; j < Array.getLength(expResult); j++) {
            assertEquals(expResult[j], result[j]);
        }
    }

    /**
     * Test of add method, of class RowArrayMatrixF.
     */
    public void testAdd() {
        System.out.println("add");
        Float[][] data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        RowArrayMatrixF b = new RowArrayMatrixF(data);
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        data = new Float[][]{{2F, 4F, 6F}, {6F, 4F, 2F}, {2F, 4F, 6F}};
        RowArrayMatrixF expResult = new RowArrayMatrixF(data);
        MatrixF result = instance.add(b);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of subtract method, of class RowArrayMatrixF.
     */
    public void testSubtract() {
        System.out.println("subtract");
        Float[][] data = new Float[][]{{2F, 4F, 6F}, {6F, 4F, 2F}, {2F, 4F, 6F}};
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        RowArrayMatrixF b = new RowArrayMatrixF(data);
        RowArrayMatrixF expResult = new RowArrayMatrixF(data);
        MatrixF result = instance.subtract(b);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of multiply method, of class RowArrayMatrixF.
     */
    public void testMultiply() {
        System.out.println("multiply");
        Float[][] data = new Float[][]{{1F, 2F, 3F}};
        RowArrayMatrixF b = new RowArrayMatrixF(data);
        data = new Float[][]{{4F}, {5F}, {6F}};
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        data = new Float[][]{{32F}};
        RowArrayMatrixF expResult = new RowArrayMatrixF(data);
        MatrixF result = b.multiply(instance);
        assertTrue(expResult.equals(result));

        data = new Float[][]{{1F, 2F, 3F},{4F, 5F, 6F}};
        b = new RowArrayMatrixF(data);
        data = new Float[][]{{7F,1F}, {8F,2F}, {9F,3F}};
        instance = new RowArrayMatrixF(data);
        data = new Float[][]{{50F, 14F}, {122F, 32F}};
        expResult = new RowArrayMatrixF(data);
        System.out.println(expResult);
        result = b.multiply(instance);
        System.out.println(result);
        assertTrue(expResult.equals(result));

        b = (RowArrayMatrixF) MatrixF.create(new Float[][] {{-1.0F},{-1.0F},{0.0F},{1.0F}});
        instance = (RowArrayMatrixF) MatrixF.create(new Float[][] {{1.0F,0.0F,0.0F,0.0F},{0.0F,0.6468713F,-0.7625992F,0.0F},{0.0F,0.7625992F,0.6468713F,0.0F},{0.0F,0.0F,0.0F,1.0F}});
        // -1.0, -0.6468713, -0.7625992, 1.0

        expResult = (RowArrayMatrixF) MatrixF.create(new Float[][] {{-1.0F},{-0.6468713F},{-0.7625992F},{1.0F}});
        System.out.println(expResult);
        result = instance.multiply(b);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class SimpleImmutableMatrix.
     */
    public void testEquals() {
        System.out.println("equals");
        Float[][] data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        MatrixF a = MatrixF.random(3, 3);
        RowArrayMatrixF b = new RowArrayMatrixF(data);
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        boolean expResult = false;
        boolean result = instance.equals(a);
        assertEquals(expResult, result);
        expResult = true;
        result = instance.equals(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of transpose method, of class RowArrayMatrixF.
     */
    public void testTranspose() {
        System.out.println("transpose");
        Float[][] data = new Float[][]{{1F, 2F, 3F}};
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        data = new Float[][]{{1F}, {2F}, {3F}};
        MatrixF expResult = new RowArrayMatrixF(data);
        MatrixF result = instance.transpose();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of addScalar method, of class RowArrayMatrixF.
     */
    public void testAddScalar() {
        System.out.println("addScalar");
        Number v = 5D;
        Float[][] data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        data = new Float[][]{{6F, 7F, 8F}, {8F, 7F, 6F}, {6F, 7F, 8F}};
        MatrixF expResult = new RowArrayMatrixF(data);
        MatrixF result = instance.addScalar(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of subtractScalar method, of class RowArrayMatrixF.
     */
    public void testSubtractScalar() {
        System.out.println("subtractScalar");
        Number v = 1D;
        Float[][] data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        data = new Float[][]{{0F, 1F, 2F}, {2F, 1F, 0F}, {0F, 1F, 2F}};
        MatrixF expResult = new RowArrayMatrixF(data);
        MatrixF result = instance.subtractScalar(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of multiplyScalar method, of class RowArrayMatrixF.
     */
    public void testMultiplyScalar() {
        System.out.println("multiplyScalar");
        Number v = 5D;
        Float[][] data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        data = new Float[][]{{5F, 10F, 15F}, {15F, 10F, 5F}, {5F, 10F, 15F}};
        MatrixF expResult = new RowArrayMatrixF(data);
        MatrixF result = instance.multiplyScalar(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of divideScalar method, of class RowArrayMatrixF.
     */
    public void testDivideScalar() {
        System.out.println("divideScalar");
        Number v = 5D;
        Float[][] data = new Float[][]{{5F, 10F, 15F}, {15F, 10F, 5F}, {5F, 10F, 15F}};
        RowArrayMatrixF instance = new RowArrayMatrixF(data);
        data = new Float[][]{{1F, 2F, 3F}, {3F, 2F, 1F}, {1F, 2F, 3F}};
        MatrixF expResult = new RowArrayMatrixF(data);
        MatrixF result = instance.divideScalar(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of transpose method, of class SimpleImmutableMatrix.
     */
    public void testTranspose_static() {
        System.out.println("transpose");
        Float[][] data = new Float[][]{{1F, 2F, 3F}};
        RowArrayMatrixF a = new RowArrayMatrixF(data);
        data = new Float[][]{{1F}, {2F}, {3F}};
        RowArrayMatrixF expResult = new RowArrayMatrixF(data);
        MatrixF result = RowArrayMatrixF.transpose(a);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of identity method, of class SimpleImmutableMatrix.
     */
    public void testIdentity() {
        System.out.println("identity");
        int n = 3;
        Float[][] data = new Float[][]{{1F, 0F, 0F}, {0F, 1F, 0F}, {0F, 0F, 1F}};
        RowArrayMatrixF expResult = new RowArrayMatrixF(data);
        ;
        MatrixF result = RowArrayMatrixF.identity(n);
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
        MatrixF result = RowArrayMatrixF.random(m, n);
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
        MatrixF result = RowArrayMatrixF.random(m, n);
        assertEquals(m, result.getM());
        assertEquals(n, result.getN());
    }
}
